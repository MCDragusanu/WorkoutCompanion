package com.example.compose
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

import androidx.compose.ui.graphics.Color

// Light Theme
val md_theme_light_primary = Color(0xFF1189F3) // Blue
val md_theme_light_onPrimary = Color(0xFFFFFFFF) // White
val md_theme_light_primaryContainer = Color(0xFFD0DBF1) // Light Blue
val md_theme_light_onPrimaryContainer = Color(0xFF000000) // Black
val md_theme_light_secondary = Color(0xFF00B0FF) // Light Blue
val md_theme_light_onSecondary = Color( 0xFFFFFFFF) // Black
val md_theme_light_secondaryContainer = Color(0xFF86C4F5) // Pale Blue
val md_theme_light_onSecondaryContainer = Color( 0xFFFFFFFF) // Black
val md_theme_light_tertiary = Color(0xFF8BC34A) // Green
val md_theme_light_onTertiary = Color(0xFF000000) // Black
val md_theme_light_tertiaryContainer = Color(0xFF5E8E2A) // Dark Green
val md_theme_light_onTertiaryContainer = Color(0xFFFFFFFF) // White
val md_theme_light_error = Color(0xFFD32F2F) // Red
val md_theme_light_errorContainer = Color(0xFFFFCDD2) // Light Red
val md_theme_light_onError = Color(0xFFFFFFFF) // White
val md_theme_light_onErrorContainer = Color(0xFF000000) // Black
val md_theme_light_background = Color(0xFFE3F2FD) // Light Blue Nuance
val md_theme_light_onBackground = Color(0xFF000000) // Black
val md_theme_light_surface = Color(0xFFF5F5F5) // Light Gray
val md_theme_light_onSurface = Color(0xFF000000) // Black
val md_theme_light_surfaceVariant = Color(0xFFEEEEEE) // Gray
val md_theme_light_onSurfaceVariant = Color(0xFF000000) // Black
val md_theme_light_outline = Color(0xFFBDBDBD) // Light Gray
val md_theme_light_inverseOnSurface = Color(0xFFF5F5F5) // Light Gray
val md_theme_light_inverseSurface = Color(0xFF000000) // Black
val md_theme_light_inversePrimary = Color(0xFF2979FF) // Blue
val md_theme_light_shadow = Color(0x19000000) // Black with 10% opacity
val md_theme_light_surfaceTint = Color(0xFF8BC34A) // Green
val md_theme_light_outlineVariant = Color(0xFFEEEEEE) // Gray
val md_theme_light_scrim = Color(0x19000000) // Black with 10% opacity

val md_theme_dark_primary = Color(0xFF1189F3) // Softer Blue
val md_theme_dark_onPrimary = Color(0xFFFFFFFF) // White
val md_theme_dark_primaryContainer = Color(0xFF161618) // Dark Navy (Slightly Darker Shade)
val md_theme_dark_onPrimaryContainer = Color(0xFFFFFFFF) // White
val md_theme_dark_secondary = Color(0xFF00E6FF) // Neon Blue
val md_theme_dark_onSecondary = Color(0xFF000000) // Black
val md_theme_dark_secondaryContainer = Color(0xFF141416) // Dark Navy (Slightly Darker Shade)
val md_theme_dark_onSecondaryContainer = Color(0xFF000000) // White
val md_theme_dark_tertiary = Color(0xFF2E5F80) // Darker Light Blue
val md_theme_dark_onTertiary = Color(0xFFFFFFFF) // White
val md_theme_dark_tertiaryContainer = Color(0xFF173F57) // Subtle Shade of Navy
val md_theme_dark_onTertiaryContainer = Color(0xFFFFFFFF) // White
val md_theme_dark_error = Color(0xFFFF385C) // Red
val md_theme_dark_errorContainer = Color(0xFF7F1E30) // Dark Red
val md_theme_dark_onError = Color(0xFFFFFFFF) // White
val md_theme_dark_onErrorContainer = Color(0xFFFF385C) // Red
val md_theme_dark_background = Color(0xFF000000) // Black (or very dark shade of navy)
val md_theme_dark_onBackground = Color(0xFFFFFFFF) // White
val md_theme_dark_surface = Color(0xFF222325) // Darker Shade of Navy
val md_theme_dark_onSurface = Color(0xFFFFFFFF) // White
val md_theme_dark_surfaceVariant = Color(0xFF113343) // Dark Navy (Slightly Darker Shade)
val md_theme_dark_onSurfaceVariant = Color(0xFFFFFFFF) // White
val md_theme_dark_outline = Color(0xFF2E5F80) // Dark Navy
val md_theme_dark_inverseOnSurface = Color(0xFF021626) // Darker Shade of Navy
val md_theme_dark_inverseSurface = Color(0xFFFFFFFF) // White
val md_theme_dark_inversePrimary = Color(0xFF05599B) // Blue
val md_theme_dark_shadow = Color(0xFF000000) // Black
val md_theme_dark_surfaceTint = Color(0xFF2E5F80) // Darker Light Blue
val md_theme_dark_outlineVariant = Color(0xFF163345) // Dark Navy (Slightly Darker Shade)
val md_theme_dark_scrim = Color(0xFF000000) // Black

/*val md_theme_dark_primary = Color(0xFF6C63FF) // Purple
val md_theme_dark_onPrimary = Color(0xFFFFFFFF) // White
val md_theme_dark_primaryContainer = Color(0xFF3D36A1) // Dark Purple
val md_theme_dark_onPrimaryContainer = Color(0xFFFFFFFF) // White
val md_theme_dark_secondary = Color(0xFF00E6FF) // Neon Blue
val md_theme_dark_onSecondary = Color(0xFF000000) // Black
val md_theme_dark_secondaryContainer = Color(0xFF0B4655) // Dark Blue
val md_theme_dark_onSecondaryContainer = Color(0xFFFFFFFF) // White
val md_theme_dark_tertiary = Color(0xFF78FFA7) // Light Green
val md_theme_dark_onTertiary = Color(0xFF000000) // Black
val md_theme_dark_tertiaryContainer = Color(0xFF4C9D59) // Dark Green
val md_theme_dark_onTertiaryContainer = Color(0xFFFFFFFF) // White
val md_theme_dark_error = Color(0xFFFF385C) // Red
val md_theme_dark_errorContainer = Color(0xFF7F1E30) // Dark Red
val md_theme_dark_onError = Color(0xFFFFFFFF) // White
val md_theme_dark_onErrorContainer = Color(0xFFFF385C) // Red
val md_theme_dark_background = Color(0xFF121212) // Dark Gray
val md_theme_dark_onBackground = Color(0xFFFFFFFF) // White
val md_theme_dark_surface = Color(0xFF1C1C1C) // Darker Gray
val md_theme_dark_onSurface = Color(0xFFFFFFFF) // White
val md_theme_dark_surfaceVariant = Color(0xFF2B2B2B) // Gray
val md_theme_dark_onSurfaceVariant = Color(0xFFFFFFFF) // White
val md_theme_dark_outline = Color(0xFFBFBFBF) // Light Gray
val md_theme_dark_inverseOnSurface = Color(0xFF1C1C1C) // Darker Gray
val md_theme_dark_inverseSurface = Color(0xFFFFFFFF) // White
val md_theme_dark_inversePrimary = Color(0xFF6C63FF) // Purple
val md_theme_dark_shadow = Color(0xFF000000) // Black
val md_theme_dark_surfaceTint = Color(0xFF78FFA7) // Light Green
val md_theme_dark_outlineVariant = Color(0xFF2B2B2B) // Gray
val md_theme_dark_scrim = Color(0xFF000000) // Black*/

val seed = Color(0xFF3C5BA9)
private val workoutTagColors = listOf(
    Color(0xFFBA68C8),Color(0xFFF06292),
    Color(0xFFBA68C8),Color(0xFF7986CB),
    Color(0xFF64B5F6),Color(0xFF4DD0E1),
    Color(0xFF4DB6AC),Color(0xFF9CCC65),
    Color(0xFFD4E157),Color(0xFFFFCA28),
    Color(0xFFCAC226),Color(0xFFFF7043),
    Color(0xFFCA6826) ,Color(0xFFFF4378),
    Color(0xFFEF9A9A),Color(0xFFCE93D8),
    Color(0xFFB39DDB),Color(0xFF90CAF9),
    Color(0xFF9FA8DA),Color(0xFF90CAF9),
    Color(0xFF90CAF9),Color(0xFF80DEEA),
    Color(0xFFC5E1A5),Color(0xFFFFF59D),
    Color(0xFFE6EE9C),Color(0xFFFFAB91),
    Color(0xFFEED39C) ,Color(0xFFFF9591) ,
)
val workoutTagColorPairs = workoutTagColors.shuffled().map { Pair(workoutTagColors.random() , workoutTagColors.random()) }


data class WorkoutCompanionColors(val materialTheme: ColorScheme,
                                  val successContainer:Color = Color(0xFF66BB6A),
                                  val onSuccessContainer:Color = Color(0xFFC8E6C9),
                                  val successColor:Color = Color(0xFF7CB342),
                                  val secondarySurfaceColor:Color,
                                  val tertiarySurfaceColor:Color,
                                  ){
    val primary: Color get() = materialTheme.primary
    val primaryVariant: Color get() = materialTheme.inversePrimary
    val secondary: Color get() = materialTheme.secondary
    val secondaryVariant: Color get() = materialTheme.secondary
    val background: Color get() = materialTheme.background
    val surface: Color get() = materialTheme.surface
    val error: Color get() = materialTheme.error
    val onPrimary: Color get() = materialTheme.onPrimary
    val onSecondary: Color get() = materialTheme.onSecondary
    val onBackground: Color get() = materialTheme.onBackground
    val onSurface: Color get() = materialTheme.onSurface
    val onError: Color get() = materialTheme.onError

}

 val LightColorPalette = staticCompositionLocalOf {
     WorkoutCompanionColors(
         materialTheme = LightColors ,
         successColor = Color(0xFF3CDB38) ,
         successContainer = Color(0xFF6DDB6F),
         onSuccessContainer = Color(0xFF09420A),
         secondarySurfaceColor = Color(0xFFD1EEF7) ,
         tertiarySurfaceColor = Color(0xFFBDD7DF)
     )
 }
 val DarkColorPalette = staticCompositionLocalOf {
     WorkoutCompanionColors(
         materialTheme = DarkColors , successColor = Color(0xFF3CDB38) ,
         successContainer = Color(0xFF6DDB6F) ,
         onSuccessContainer = Color(0xFF09420A) ,
         secondarySurfaceColor = Color(0xFF161C27) ,
         tertiarySurfaceColor = Color(0xFF10131A) ,
     )
 }

@Composable
fun getPalette() = if(isSystemInDarkTheme()) DarkColorPalette else LightColorPalette
