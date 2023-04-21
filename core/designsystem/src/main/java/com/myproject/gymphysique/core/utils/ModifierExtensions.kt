package com.myproject.gymphysique.core.utils

import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp

fun Modifier.adaptiveHeight(items: Int) = composed {
    when(items){
        0 -> this
        in 1..3 -> {
            val height = items * 58
            this.height(height.dp)
        }
        else -> this.height(174.dp)
    }
}