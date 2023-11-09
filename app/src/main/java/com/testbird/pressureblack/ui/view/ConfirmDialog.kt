package com.testbird.pressureblack.ui.view

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import com.testbird.pressureblack.databinding.LayoutDialogBinding

class ConfirmDialog(
    context: Context,
    private val clickConfirm: () -> Unit,
    private val clickCancel: () -> Unit = {}
) : AlertDialog(context) {
    lateinit var binding: LayoutDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        this.window?.setGravity(Gravity.CENTER)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.window?.setDimAmount(0.5f)
        binding.dialogCancel.setOnClickListener {
            dismiss()
            clickCancel.invoke()
        }
        binding.dialogSure.setOnClickListener {
            dismiss()
            clickConfirm.invoke()
        }
    }
}