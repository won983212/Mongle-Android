package com.won983212.mongle.presentation.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.won983212.mongle.R
import com.won983212.mongle.common.util.attachCompatVectorAnim
import com.won983212.mongle.common.util.startAnim
import com.won983212.mongle.databinding.DialogAnalyzeCompleteBinding
import com.won983212.mongle.databinding.DialogArrivedGiftBinding
import com.won983212.mongle.databinding.DialogLoadingBinding
import com.won983212.mongle.databinding.DialogTermsOfServiceDetailBinding

private fun openDialog(
    context: Context,
    view: View,
    hideBackground: Boolean = false,
    cancelable: Boolean = true
): AlertDialog {
    val dialog = AlertDialog.Builder(context)
        .setView(view)
        .setCancelable(cancelable)
        .create()
    if (hideBackground) {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    dialog.show()
    return dialog
}

fun openTermsOfServiceDialog(context: Context, @StringRes contentId: Int): AlertDialog {
    val layout = DialogTermsOfServiceDetailBinding.inflate(
        LayoutInflater.from(context),
        null, false
    )
    layout.textTermsOfServiceDetail.movementMethod = ScrollingMovementMethod()
    layout.textTermsOfServiceDetail.setText(contentId)
    return openDialog(context, layout.root)
}

fun openLoadingDialog(context: Context): AlertDialog {
    val layout = DialogLoadingBinding.inflate(
        LayoutInflater.from(context),
        null, false
    )
    layout.imageLoading.startAnim(true)
    return openDialog(context, layout.root, true)
}

fun openAnalyzeCompleteDialog(context: Context): AlertDialog {
    val layout = DialogAnalyzeCompleteBinding.inflate(
        LayoutInflater.from(context),
        null, false
    )
    layout.imageAnalyzeCompleteFlag.attachCompatVectorAnim(R.drawable.avd_flag_cross)
    layout.imageAnalyzeCompleteChecking.attachCompatVectorAnim(R.drawable.avd_complete)
    return openDialog(context, layout.root, true)
}

fun openGiftArrivedDialog(context: Context): AlertDialog {
    val layout = DialogArrivedGiftBinding.inflate(
        LayoutInflater.from(context),
        null, false
    )
    layout.imageArrivedGift.attachCompatVectorAnim(R.drawable.avd_gift)
    return openDialog(context, layout.root, true)
}