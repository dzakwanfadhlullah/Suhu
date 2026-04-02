package com.suhu.app.service

import java.util.Locale

/**
 * Mesin Ekstraksi Teks (Regex Parser Engine)
 * Bertugas mencari nominal harga dan mendeteksi nama layanan/merchant
 * dari raw text notifikasi e-wallet dan perbankan lokal.
 */
object RegexParser {

    // Regex Explanation:
    // (?:Rp(?:\.?)\s?|IDR\s*) -> Non-capturing group. Membaca "Rp", "Rp.", "Rp ", "IDR", "IDR " 
    // \b(\d{1,3}(?:\.\d{3})*(?:,\d{1,2})?)\b -> Capturing Group 1. Membaca format angka.
    // \d{1,3} -> 1 sampai 3 digit awal
    // (?:\.\d{3})* -> Kelipatan 3 digit yang diawali titik (.)
    // (?:,\d{1,2})? -> Opsional: sen/desimal di akhir yang diawali koma (,)
    private val AMOUNT_PATTERN = Regex("""(?:Rp(?:\.?)\s?|IDR\s*)\b(\d{1,3}(?:\.\d{3})*(?:,\d{1,2})?)\b""", RegexOption.IGNORE_CASE)

    // Regex Explanation (Fallback / Contextual):
    // (?i) -> ignore case
    // (?:pembayaran ke|transfer ke|transaksi di|pembayaran untuk merchant|ke layanan)\s+ -> mencari prefix
    // ([a-zA-Z0-9\s&*]+) -> Capturing Group 1. Menangkap sekumpulan string sebagai nama Merchant
    private val CONTEXTUAL_MERCHANT_PATTERN = Regex("""(?:pembayaran ke|transfer ke|transaksi di|bayar ke|untuk)\s+([a-zA-Z0-9\s&*]+)""", RegexOption.IGNORE_CASE)

    // Kamus Merchant Umum (Direct Lookup Dictionary)
    // Kata kunci yang pasti merujuk pada langganan populer.
    private val KNOWN_MERCHANTS = listOf(
        "NETFLIX", "SPOTIFY", "GOOGLE", "APPLE", "CANVA",
        "DISNEY", "YOUTUBE P", "VIU", "VIDIO", "ZOOM", "MICROSOFT"
    )

    /**
     * Mengekstrak harga dari text.
     * Mengembalikan nilai Double yang sudah bersih (misal 153000.0).
     */
    fun parseAmount(text: String): Double? {
        val matchResult = AMOUNT_PATTERN.find(text) ?: return null
        
        // Group[1] berisi angkanya, misal: "153.000,00" atau "50.000"
        val rawAmount = matchResult.groupValues[1]

        // Pembersihan (Cleaning): 
        // 1. Ganti semua titik (pemisah ribuan Indonesia) dengan string kosong.
        // 2. Ganti koma (pemisah desimal Indonesia) dengan titik (standar double).
        val cleanAmount = rawAmount.replace(".", "").replace(",", ".")
        
        return cleanAmount.toDoubleOrNull()
    }

    /**
     * Mengekstrak nama Merchant dari text.
     * Menggunakan strategi Layered Heuristics: Dictionary Match -> Contextual Regex Match.
     */
    fun parseMerchant(text: String): String? {
        val upperText = text.uppercase(Locale.getDefault())

        // 1. Strategi Pertama: Dictionary Lookup
        for (merchant in KNOWN_MERCHANTS) {
            if (upperText.contains(merchant)) {
                return merchant // Jika ada "NETFLIX", langsung kembalikan "NETFLIX"
            }
        }

        // 2. Strategi Kedua: Contextual Regex (Mencari di balik kata sandang)
        val matchResult = CONTEXTUAL_MERCHANT_PATTERN.find(text)
        if (matchResult != null) {
            // Bersihkan white-space berlebih dari hasil tangkapan group 1
            return matchResult.groupValues[1].trim()
        }

        return null
    }
}
