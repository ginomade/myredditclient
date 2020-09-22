package com.gino.myredditclient.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.gino.myredditclient.R

class ProgressDialog(context: Context) : Dialog(context, R.style.ProgressDialogTheme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress_bar)
        setCancelable(false)
    }
}