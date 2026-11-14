package dev.dzianisv.snapdisk

import androidx.test.platform.app.InstrumentationRegistry
import dev.dzianisv.snapdisk.backup.ProPrefs
import dev.dzianisv.snapdisk.billing.BillingManager
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Billing tests. The emulator has no Play Store, so the billing
 * connection must degrade gracefully: no crash, Pro stays off, the
 * free core keeps working. A real sandbox purchase needs the Play
 * Console app + the `pro_unlock` in-app product to exist
 * (owner-gated) — that verification is NOT claimed here.
 */
class BillingTest {

    @Test
    fun billingWithoutPlayDegradesGracefully() {
        val ctx = InstrumentationRegistry.getInstrumentation().targetContext
        val prefs = ProPrefs(ctx)
        prefs.isPro = false
        val billing = BillingManager(ctx, prefs)
        assertFalse(billing.isPro)
        val done = CountDownLatch(1)
        val connected = AtomicBoolean(true)
        billing.startConnection { ok ->
            connected.set(ok)
            done.countDown()
        }
        assertTrue("billing connection never returned", done.await(30, TimeUnit.SECONDS))
        // No Play Store on the emulator: either it reports disconnected
        // or it connects but finds no Pro product — either way Pro
        // stays off and nothing crashes.
        assertFalse(billing.isPro)
        assertFalse(prefs.isPro)
        assertFalse(billing.canPurchase())
        billing.destroy()
        println("SMOKE billing: no Play on emulator, connected=${connected.get()}, Pro stays off")
    }

    @Test
    fun freeLimitIsFiveGb() {
        org.junit.Assert.assertEquals(
            5L * 1024 * 1024 * 1024,
            ProPrefs.FREE_BACKUP_BYTES_LIMIT,
        )
    }
}
