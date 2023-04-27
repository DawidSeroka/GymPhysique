package com.myproject.gymphysique.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.GpNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = GPNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = GPNavigationDefaults.navigationContentColor(),
            selectedTextColor = GPNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = GPNavigationDefaults.navigationContentColor(),
            indicatorColor = GPNavigationDefaults.navigationIndicatorColor()
        )
    )
}

@Composable
fun GPNavigationBar(
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        contentColor = GPNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content
    )
}

object GPNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
