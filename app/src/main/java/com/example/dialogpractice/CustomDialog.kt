package com.example.dialogpractice

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.dialogpractice.databinding.DialogCustomBinding
import kotlinx.parcelize.Parcelize

/**
 *  CommonDialogFragment.kt
 *
 *  Created by Hayeong Lee on 2022/04/11
 *  Copyright © 2022 Shinhan Bank. All rights reserved.
 */

class CustomDialog : DialogFragment() {

    private var _binding: DialogCustomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCustomBinding.inflate(LayoutInflater.from(context))
        initView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getTheme(): Int {
        return R.style.CustomAlertDialog    // 다이얼로그 style 지정
    }

    private fun initView() {
        val model = arguments?.getParcelable<CustomDialogModel>(KEY_DIALOG_MODEL) ?: return

        binding.message.text = model.message

        if (model.hasPositiveBtn) {
            binding.positiveButton.apply {
                setVisible()
                text = model.positiveBtnText
                setOnClickListener {
                    model.positiveBtnClick?.onClick()?.invoke(this@CustomDialog)
                    dismiss()
                }
            }
        } else {
            binding.positiveButton.setGone()
        }

        if (model.hasNegativeBtn) {
            binding.negativeButton.apply {
                setVisible()
                text = model.negativeBtnText
                setOnClickListener {
                    model.negativeBtnClick?.onClick()?.invoke(this@CustomDialog)
                    dismiss()
                }
            }
        } else {
            binding.negativeButton.setGone()
        }
    }

    /**
     * Activity or Fragment 에서 CustomDialog 를 chaining 형태로 호출하여 사용할 수 있도록 Builder Pattern 사용
     */
    class Builder {
        private var model = CustomDialogModel()

        fun setMessage(message: String): Builder {
            model.message = message
            return this
        }

        fun setPositiveButton(buttonText: String, onClick: (CustomDialog) -> Unit): Builder {
            model.hasPositiveBtn = true
            model.positiveBtnText = buttonText
            model.positiveBtnClick = ClickListener(onClick)
            return this
        }

        fun setNegativeButton(buttonText: String, onClick: (CustomDialog) -> Unit): Builder {
            model.hasNegativeBtn = true
            model.negativeBtnText = buttonText
            model.negativeBtnClick = ClickListener(onClick)
            return this
        }

        fun show(manager: FragmentManager): CustomDialog {
            val newCustomDialog = CustomDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_DIALOG_MODEL, model)
                }
            }
            newCustomDialog.show(manager, TAG)
            return newCustomDialog
        }
    }

    @Parcelize
    class ClickListener(private val clickAction: (CustomDialog) -> Unit) : Parcelable {
        fun onClick() = clickAction
    }

    companion object {
        val TAG: String = CustomDialog::class.java.simpleName
        const val KEY_DIALOG_MODEL = "KEY_DIALOG_MODEL"
    }
}
