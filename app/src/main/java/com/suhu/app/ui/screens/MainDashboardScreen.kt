package com.suhu.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suhu.app.ui.components.SubscriptionCard
import com.suhu.app.ui.components.SubscriptionListItem
import com.suhu.app.ui.components.SuhuBottomNavBar
import com.suhu.app.ui.components.SuhuNavRoute
import com.suhu.app.ui.components.SuhuTopAppBar
import com.suhu.app.ui.theme.InterFontFamily
import com.suhu.app.ui.theme.SuhuTheme

// ==========================================
// DATA CLASS SEMENTARA (Dummy)
// Akan diganti dengan SubscriptionEntity dari Room Database di Fase 5
// ==========================================
private data class SubscriptionSample(
    val name: String,
    val price: String,
    val date: String,
    val icon: String,
    val isAlert: Boolean = false
)

/**
 * LAYAR DASHBOARD UTAMA — Menampilkan data langganan aktif.
 *
 * Berisi Hero Card pengeluaran, Carousel "Mendatang", Daftar Langganan iOS-style,
 * dan hint swipe-to-delete. TopAppBar dan BottomNavBar AKTIF.
 *
 * @param onAddClick Callback ikon (+) di TopAppBar.
 * @param onProfileClick Callback ikon profil di TopAppBar.
 * @param onNavigate Callback navigasi BottomNavBar.
 * @param onSubscriptionClick Callback saat item langganan diklik (buka detail).
 * @param onDeleteSubscription Callback saat item langganan di-swipe delete.
 * @param onAddManualClick Callback navigasi ke form manual entry.
 */
@Composable
fun MainDashboardScreen(
    onAddClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onNavigate: (SuhuNavRoute) -> Unit = {},
    onSubscriptionClick: (String) -> Unit = {},
    onDeleteSubscription: (String) -> Unit = {},
    onAddManualClick: () -> Unit = {}
) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    // Dummy data — akan diganti dengan data dari ViewModel/Room di Fase 5
    val upcomingSubscriptions = remember {
        listOf(
            SubscriptionSample("Netflix Premium", "Rp 186.000", "14 Okt", "🎬", isAlert = true),
            SubscriptionSample("Spotify Family", "Rp 86.000", "15 Okt", "🎵"),
            SubscriptionSample("Google One", "Rp 26.900", "22 Okt", "☁️")
        )
    }

    val allSubscriptions = remember {
        listOf(
            SubscriptionSample("YouTube Premium", "Rp 59.000", "Bulanan • 20 Okt", "▶️"),
            SubscriptionSample("Adobe Creative Cloud", "Rp 245.000", "Bulanan • 24 Okt", "🎨"),
            SubscriptionSample("Amazon Prime", "Rp 79.000", "Bulanan • 28 Okt", "📦")
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        // ==========================================
        // KONTEN UTAMA — LazyColumn untuk performa optimal
        // ==========================================
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            // Ruang untuk TopAppBar (fixed) dan BottomNavBar (fixed)
            contentPadding = PaddingValues(top = 100.dp, bottom = 120.dp)
        ) {
            // ==========================================
            // 1. LARGE TITLE: "Bulan Ini" (34px bold ~ headlineLarge)
            // ==========================================
            item {
                BasicText(
                    text = "Bulan Ini",
                    style = SuhuTheme.typography.headlineLarge.copy(
                        color = colors.onSurface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.large)
                        .padding(bottom = spacing.large)
                )
            }

            // ==========================================
            // 2. HERO CARD: Total pengeluaran + Tagihan jatuh tempo + Badge + Maskot
            // ==========================================
            item {
                HeroCard()
                Spacer(modifier = Modifier.height(40.dp))
            }

            // ==========================================
            // 3. SECTION "Mendatang": Header + Carousel horizontal
            // ==========================================
            item {
                // Section Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.large),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicText(
                        text = "Mendatang",
                        style = TextStyle(
                            fontFamily = InterFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            letterSpacing = (-0.3).sp,
                            color = colors.onSurface
                        )
                    )
                    BasicText(
                        text = "Lihat Semua",
                        style = SuhuTheme.typography.bodySmall.copy(
                            color = colors.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(spacing.medium))

                // Carousel horizontal dengan LazyRow
                LazyRow(
                    contentPadding = PaddingValues(horizontal = spacing.large),
                    horizontalArrangement = Arrangement.spacedBy(spacing.medium)
                ) {
                    items(upcomingSubscriptions) { sub ->
                        SubscriptionCard(
                            serviceName = sub.name,
                            priceLabel = sub.price,
                            dateLabel = sub.date,
                            iconSymbol = sub.icon,
                            isAlert = sub.isAlert,
                            onClick = { onSubscriptionClick(sub.name) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))
            }

            // ==========================================
            // 4. SECTION "Daftar Langganan": Header
            // ==========================================
            item {
                BasicText(
                    text = "Daftar Langganan",
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        letterSpacing = (-0.3).sp,
                        color = colors.onSurface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.large)
                        .padding(bottom = spacing.medium)
                )
            }

            // ==========================================
            // 5. DAFTAR LANGGANAN: List bergaya iOS + swipe gesture
            // Container squircle yang membungkus semua item
            // ==========================================
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.large)
                        .shadow(
                            elevation = 16.dp,
                            shape = SuhuTheme.shapes.squircle,
                            spotColor = colors.onSurface.copy(alpha = 0.06f),
                            ambientColor = colors.onSurface.copy(alpha = 0.02f)
                        )
                        .clip(SuhuTheme.shapes.squircle)
                        .background(colors.surfaceContainerLowest)
                ) {
                    allSubscriptions.forEachIndexed { index, sub ->
                        SubscriptionListItem(
                            serviceName = sub.name,
                            priceLabel = sub.price,
                            dateLabel = sub.date,
                            iconSymbol = sub.icon,
                            showDivider = index < allSubscriptions.lastIndex,
                            onDelete = { onDeleteSubscription(sub.name) },
                            onClick = { onSubscriptionClick(sub.name) }
                        )
                    }
                }
            }

            // ==========================================
            // 6. HINT TEXT: Instruksi swipe gesture
            // ==========================================
            item {
                Spacer(modifier = Modifier.height(spacing.extraLarge))
                BasicText(
                    text = "Geser ke kiri pada item untuk menghapus atau menghentikan pelacakan tagihan.",
                    style = SuhuTheme.typography.labelSmall.copy(
                        color = colors.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                )
            }
        }

        // ==========================================
        // FIXED: TopAppBar (di atas)
        // ==========================================
        SuhuTopAppBar(
            onAddClick = onAddClick,
            onProfileClick = onProfileClick
        )

        // ==========================================
        // FIXED: BottomNavBar (di bawah)
        // ==========================================
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            SuhuBottomNavBar(
                currentRoute = SuhuNavRoute.HOME,
                onNavigate = onNavigate
            )
        }
    }
}

// ==========================================
// KOMPONEN INTERNAL: Hero Card
// Total pengeluaran, info tagihan jatuh tempo, badge motivasi,
// dan maskot Si Kancil peek-a-boo di pojok kanan atas.
// ==========================================
@Composable
private fun HeroCard() {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.large)
            .shadow(
                elevation = 16.dp,
                shape = SuhuTheme.shapes.squircle,
                spotColor = colors.onSurface.copy(alpha = 0.06f),
                ambientColor = colors.onSurface.copy(alpha = 0.02f)
            )
            .clip(SuhuTheme.shapes.squircle)
            .background(colors.surfaceContainerLowest)
            .padding(spacing.extraLarge)
    ) {
        // ==========================================
        // MASKOT SI KANCIL — Peek-a-boo di pojok kanan atas
        // Diposisikan secara absolut dengan offset dan rotasi
        // ==========================================
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 8.dp, y = (-8).dp)
                .rotate(12f)
                .size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder maskot — akan diganti dengan gambar asli
            BasicText(
                text = "🦌",
                style = TextStyle(fontSize = 48.sp)
            )
        }

        // ==========================================
        // KONTEN UTAMA HERO CARD
        // ==========================================
        Column(modifier = Modifier.fillMaxWidth()) {
            // Label subtitle
            BasicText(
                text = "Total Pengeluaran",
                style = SuhuTheme.typography.bodySmall.copy(
                    color = colors.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(spacing.extraSmall))

            // Nominal Total — extra bold, besar
            BasicText(
                text = "Rp 350.000",
                style = TextStyle(
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 36.sp,
                    lineHeight = 40.sp,
                    letterSpacing = (-1).sp,
                    color = colors.onSurface
                )
            )

            Spacer(modifier = Modifier.height(spacing.large))

            // Info tagihan jatuh tempo
            Row(verticalAlignment = Alignment.CenterVertically) {
                BasicText(
                    text = "📅",
                    style = TextStyle(fontSize = 16.sp)
                )
                Spacer(modifier = Modifier.width(spacing.small))
                BasicText(
                    text = "2 Tagihan Jatuh Tempo Besok",
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = colors.primary
                    )
                )
            }

            Spacer(modifier = Modifier.height(spacing.small))

            // Badge motivasi — pill biru transparan
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(colors.primary.copy(alpha = 0.1f))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                BasicText(
                    text = "HEMAT LEBIH BANYAK!",
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp,
                        color = colors.primary
                    )
                )
            }
        }
    }
}
