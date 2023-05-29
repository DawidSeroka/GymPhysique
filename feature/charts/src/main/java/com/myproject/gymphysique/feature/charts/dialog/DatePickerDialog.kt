package com.myproject.gymphysique.feature.charts.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.dt.composedatepicker.ComposeCalendar
import com.dt.composedatepicker.SelectDateListener
import java.util.Date

@Composable
internal fun DatePickerDialog(
    onDateSelected: (Date) -> Unit,
    onDismissed: () -> Unit
) {
    Dialog(onDismissRequest = onDismissed) {
        ComposeCalendar(
            listener = object : SelectDateListener {
                override fun onCanceled() {
                    onDismissed()
                }

                override fun onDateSelected(date: Date) {
                    onDateSelected(date)
                }
            }
        )
    }
}
