package com.suhu.app.service

/**
 * Berisi daftar konstanta dari *package name* aplikasi M-Banking dan E-Wallet lokal.
 * Digunakan oleh NotificationListenerService untuk memfilter hanya notifikasi keuangan.
 */
object SupportedApps {
    val BANKING_AND_EWALLET_APPS = setOf(
        "com.bca", // BCA Mobile
        "id.co.bca.mybca", // myBCA
        "id.co.bankmandiri.livin.android", // Livin by Mandiri
        "com.gojek.app", // GoPay (GoJek)
        "id.gopay.wallet", // GoPay Standalone
        "ovo.id", // OVO
        "id.dana", // DANA
        "com.shopee.id" // ShopeePay
    )
}
