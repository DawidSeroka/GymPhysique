package com.myproject.gymphysique.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.myproject.gymphysique.core.designsystem.R

object GPIcons {
    val Home = R.drawable.ic_home
    val Settings = R.drawable.ic_settings
}
/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}