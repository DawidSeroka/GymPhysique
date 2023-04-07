package com.myproject.gymphysique.core.common

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper

open class RequestBluetoothEnable : ActivityResultContract<Void?, Boolean>() {
    @CallSuper
    override fun createIntent(context: Context, input: Void?): Intent {
        return Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    }

    final override fun getSynchronousResult(
        context: Context,
        input: Void?
    ): SynchronousResult<Boolean>? = null

    final override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK
    }
}
