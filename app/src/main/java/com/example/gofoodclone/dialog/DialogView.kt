package com.example.gofoodclone.dialog

import android.app.Dialog
import android.content.Context
import com.saadahmedsoft.popupdialog.PopupDialog
import com.saadahmedsoft.popupdialog.Styles
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener


object DialogView {
    fun createDialogError(msg:String,context: Context,onDismissClick:(dialog:Dialog)->Unit){
        PopupDialog.getInstance(context)
            .setStyle(Styles.FAILED)
            .setHeading("Error")
            .setDescription(
                msg
            )
            .setCancelable(false)
            .showDialog(object : OnDialogButtonClickListener() {
                override fun onDismissClicked(dialog: Dialog) {
                    super.onDismissClicked(dialog)
                    onDismissClick.invoke(dialog)
                }
            })
    }
}