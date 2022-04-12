package com.example.dialogpractice

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.dialogpractice.databinding.DialogCommonBinding

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) getSavedInstanceView(savedInstanceState)
    }

    /**
     * 사용자가 dialog 의 디자인 및 클릭 리스너를 커스텀하고 싶을 때 onCreateView 에서 구현
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCommonBinding.inflate(LayoutInflater.from(context))

        binding.apply {
            dialogModel = model
            message.movementMethod = ScrollingMovementMethod()
            positiveButton.setOnClickListener { model.positiveButton?.second?.onClick(this@CustomDialog) }
            negativeButton.setOnClickListener { model.negativeButton?.second?.onClick(this@CustomDialog) }
        }

        return binding.root
    }

//    /**
//     * onCreateDialog 는 dialogFragment 에 내장된 AlertDialog 를 생성하여 (디자인 커스텀 없이) 반환할 때 사용
//     */
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        _binding = DialogCommonBinding.inflate(LayoutInflater.from(context))
//
//        binding.message.text = message
//        binding.positiveButton.setOnClickListener {
//            positiveButtonListener?.onClick(this)
//        }
//        binding.negativeButton.setOnClickListener {
//            negativeButtonListener?.onClick(this)
//        }
//
//        return activity?.let {
//            AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialog)
//                .setView(binding.root)
//                .create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }

    /**
     * 다이얼로그 style 지정
     */
    override fun getTheme(): Int {
        return R.style.CustomAlertDialog
    }

    /**
     * 화면 회전 시 builder 를 통해 받은 값들 저장
     */
    // TODO(Parcelable encountered IOException writing serializable object (name = kotlin.Pair))
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_MODEL, model)
    }

    /**
     * view destroy 될 때 fragment binding 이므로 null 처리
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 화면 회전 시 저장된 값 복구
     */
    private fun getSavedInstanceView(savedInstanceState: Bundle) {
        model = savedInstanceState.getParcelable<DialogModel>(KEY_MODEL) as DialogModel
    }

    /**
     * Activity or Fragment 에서 CustomDialog 를 chaining 형태로 호출하여 사용할 수 있도록 Builder Pattern 사용
     */
    class Builder {
        private val dialog = CustomDialog()

        fun setTitle(title: String): Builder {
            dialog.model.title = title
            return this
        }

        fun setMessage(message: String): Builder {
            dialog.model.message = message
            return this
        }

        fun setPositiveButton(text: String, onClickListener: CustomDialogListener): Builder {
            dialog.model.positiveButton = Pair(text, onClickListener)
            return this
        }

        fun setNegativeButton(text: String, onClickListener: CustomDialogListener): Builder {
            dialog.model.negativeButton = Pair(text, onClickListener)
            return this
        }

        fun show(manager: FragmentManager): CustomDialog {
            dialog.show(manager, TAG)
            return dialog
        }
    }

    interface CustomDialogListener {
        fun onClick(dialog: CustomDialog)
    }

    companion object {
        val TAG: String = CustomDialog::class.java.simpleName
        private const val KEY_MODEL = "KEY_MODEL"
    }
}
