package com.example.dialogpractice

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.dialogpractice.databinding.DialogCommonBinding
import kotlinx.parcelize.Parcelize

/**
 *  CommonDialogFragment.kt
 *
 *  Created by Hayeong Lee on 2022/04/11
 *  Copyright © 2022 Shinhan Bank. All rights reserved.
 */

class CustomDialog : DialogFragment() {

    private var _binding: DialogCommonBinding? = null
    private val binding get() = _binding!!

    private var model = DialogModel(
        title = null,
        message = null,
        positiveButton = null,
        negativeButton = null,
    )

    /**
     * 화면회전 시 builder 를 통해 저장되었던 값들(텍스트, 리스너 등)은 사라지므로 savedInstanceState 에서 복원해야 함
     */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCommonBinding.inflate(LayoutInflater.from(context))

        model.apply {
            title = arguments?.getString("title") ?: "오잉"
            message = arguments?.getString("message") ?: "오잉??"
            positiveButton = arguments?.getParcelable<Listener>("titleListener")
            negativeButton = arguments?.getParcelable<Listener>("messageListener")
        }

        binding.apply {
            message.text = model.message
            positiveButton.text = model.title
            negativeButton.text = model.title
            positiveButton.setOnClickListener { model.positiveButton?.onClick()?.invoke(this@CustomDialog) }
            negativeButton.setOnClickListener { model.negativeButton?.onClick()?.invoke(this@CustomDialog) }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 다이얼로그 style 지정
     */
    override fun getTheme(): Int {
        return R.style.CustomAlertDialog
    }

    /**
     * Activity or Fragment 에서 CustomDialog 를 chaining 형태로 호출하여 사용할 수 있도록 Builder Pattern 사용
     */
    class Builder {
        private val args: Bundle = Bundle()

        fun setTitle(title: String): Builder {
            args.putString("title", title)
            return this
        }

        fun setMessage(message: String): Builder {
            args.putString("message", message)
            return this
        }

        fun setPositiveButton(onClickListener: (CustomDialog) -> Unit): Builder {
            args.putParcelable("titleListener", Listener(onClickListener))
            return this
        }

        fun setNegativeButton(onClickListener: (CustomDialog) -> Unit): Builder {
            args.putParcelable("messageListener", Listener(onClickListener))

            return this
        }

        fun show(manager: FragmentManager): CustomDialog {
            val newCustomDialog = CustomDialog().apply {
                arguments = args
            }
            newCustomDialog.show(manager, TAG)
            return newCustomDialog
        }
    }

    @Parcelize
    class Listener(private val clickAction: (CustomDialog) -> Unit) : Parcelable {
        fun onClick() = clickAction
    }

    companion object {
        val TAG: String = CustomDialog::class.java.simpleName
    }
}
