package dev.dzianisv.snapdisk.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import dev.dzianisv.snapdisk.backup.ProPrefs

/**
 * Google Play Billing for the one-time Pro unlock ($9.99, product id
 * `pro_unlock`). Wired end to end; a real sandbox purchase needs the
 * Play Console app + the in-app product to exist (owner-gated — no
 * Play Console account yet), so on a device without Play, or before
 * the product exists, every call degrades gracefully to "not Pro"
 * instead of crashing (the free core always works).
 */
class BillingManager(
    context: Context,
    private val prefs: ProPrefs = ProPrefs(context),
) {
    interface Listener {
        fun onProChanged(isPro: Boolean)
        fun onBillingUnavailable(message: String)
    }

    var listener: Listener? = null

    val isPro: Boolean
        get() = prefs.isPro

    private var client: BillingClient? = null
    private var proProduct: ProductDetails? = null
    private var connected = false

    private val purchasesListener = PurchasesUpdatedListener { result, purchases ->
        if (result.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            handlePurchases(purchases)
        } else if (result.responseCode != BillingClient.BillingResponseCode.USER_CANCELED) {
            listener?.onBillingUnavailable(
                "Purchase failed: ${result.responseCode} ${result.debugMessage}",
            )
        }
    }

    fun startConnection(onReady: (connected: Boolean) -> Unit = {}) {
        if (client != null) {
            onReady(connected)
            return
        }
        try {
            client = BillingClient.newBuilder(context.applicationContext)
                .setListener(purchasesListener)
                .enablePendingPurchases(
                    PendingPurchasesParams.newBuilder().enableOneTimeProducts().build(),
                )
                .build()
            client?.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(result: BillingResult) {
                    connected =
                        result.responseCode == BillingClient.BillingResponseCode.OK
                    if (connected) {
                        queryProProduct()
                        queryExistingPurchases()
                    }
                    onReady(connected)
                }

                override fun onBillingServiceDisconnected() {
                    connected = false
                    listener?.onBillingUnavailable("Billing service disconnected")
                }
            })
        } catch (t: Throwable) {
            // No Play Store on the device (emulator) or Play missing:
            // the free core must keep working.
            connected = false
            listener?.onBillingUnavailable("Billing unavailable: ${t.message}")
            onReady(false)
        }
    }

    fun queryProProduct(onResult: (ProductDetails?) -> Unit = {}) {
        val c = client ?: run { onResult(null); return }
        if (!connected) {
            onResult(null)
            return
        }
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(ProPrefs.PRO_PRODUCT_ID)
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build(),
                ),
            )
            .build()
        c.queryProductDetailsAsync(params) { result, details ->
            proProduct = details.firstOrNull()
            if (result.responseCode != BillingClient.BillingResponseCode.OK) {
                listener?.onBillingUnavailable(
                    "Could not load the Pro product: ${result.responseCode} ${result.debugMessage}",
                )
            }
            onResult(proProduct)
        }
    }

    /** True when the Pro product is known (Play connected + product exists). */
    fun canPurchase(): Boolean = connected && proProduct != null

    fun launchPurchase(activity: Activity) {
        val product = proProduct ?: run {
            listener?.onBillingUnavailable(
                "Pro is not available yet — the Play listing is still being set up.",
            )
            return
        }
        val params = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(product)
                        .build(),
                ),
            )
            .build()
        client?.launchBillingFlow(activity, params)
    }

    private fun queryExistingPurchases() {
        val c = client ?: return
        c.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),
        ) { _, purchases -> handlePurchases(purchases) }
    }

    private fun handlePurchases(purchases: List<Purchase>) {
        for (purchase in purchases) {
            if (!purchase.products.contains(ProPrefs.PRO_PRODUCT_ID)) continue
            if (purchase.purchaseState != Purchase.PurchaseState.PURCHASED) continue
            if (!purchase.isAcknowledged) {
                val params = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                client?.acknowledgePurchase(params) { result ->
                    if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                        setPro()
                    }
                }
            } else {
                setPro()
            }
        }
    }

    private fun setPro() {
        if (!prefs.isPro) {
            prefs.isPro = true
            listener?.onProChanged(true)
        }
    }

    fun destroy() {
        try { client?.endConnection() } catch (_: Exception) { /* already gone */ }
        client = null
        connected = false
    }
}
