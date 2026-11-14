# SnapDisk — Data Safety form answers

Status: DRAFT (2026-11-14). Fill these into Play Console when the
developer account exists. Every answer follows from the code:
the app has no INTERNET permission, no analytics SDK, no ads SDK,
no account system.

## Does the app collect or share any user data?

**No.** SnapDisk collects no data of any kind.

- Photos and videos: the app reads them only to copy them from
  internal storage to the external disk the user chooses. They are
  never uploaded, shared, or sent anywhere. (Not collected.)
- Folder choices (From / To) and the Pro purchase state: stored
  only on the device, in the app's own preferences. (Not collected.)
- No location, contacts, identifiers, diagnostics, or financial
  data collected by the app.

## Purchases

SnapDisk Pro ($9.99, one-time) is sold through Google Play
Billing. Google processes the payment; the app receives no payment
details. Answer the Play Billing question with Google's own
payments data handling (the purchase itself is Google's).

## Security practices (for the form)

- Data is encrypted in transit: N/A — the app transmits nothing.
- Users can request data deletion: N/A — the app holds no user
  data on any server. On-device data (folder choices, Pro state)
  is removed by clearing the app's data or uninstalling.
- Independent security review: No.
