package com.suhu.app.ui.navigation

/**
 * Representasi State untuk rute level atas aplikasi Suhu.
 * Digunakan pada mekanisme navigasi kustom agar tidak bergantung
 * pada Compose Navigation Material.
 */
sealed class SuhuScreen {
    data object Onboarding : SuhuScreen()
    
    // Mewakili layar Home (baik Empty maupun Main ditentukan saat render).
    data object Dashboard : SuhuScreen()
    
    // Layar form, ditaruh di level atas agar menutupi bottom nav.
    data object ManualEntry : SuhuScreen()
}
