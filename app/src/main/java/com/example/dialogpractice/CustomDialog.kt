package com.example.dialogpractice

import android.os.Bundle
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

enum class DialogType {
    ALERT, CONFIRM
}

class CustomDialog : DialogFragment() {

    private var _binding: DialogCommonBinding? = null
    private val binding get() = _binding!!

    // 다이얼로그 타입 설정
    // 1. 아니오 버튼이 있을 경우에만 CONFIRM 형태의 다이얼로그
    // 2. 아니오 버튼이 없으면 확인 버튼만 있는 CONFIRM 형태의 다이얼로그
    private val type: DialogType
        get() = if (negativeButton == null) DialogType.ALERT else DialogType.CONFIRM

    private var title: String? = null
    private var message: String? = null

    private var positiveButton: Pair<String?, CustomDialogListener?>? = null
    private var negativeButton: Pair<String?, CustomDialogListener?>? = null

    private var defaultDismissListener: CustomDialogListener = object : CustomDialogListener {
        override fun onClick(dialog: CustomDialog) {
            dialog.dismiss()
        }
    }

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

        binding.type = type
        binding.message.text = message

        binding.positiveButton.apply {
            val buttonText = positiveButton?.first ?: "확인"
            val buttonListener = positiveButton?.second ?: defaultDismissListener

            text = buttonText
            setOnClickListener { buttonListener.onClick(this@CustomDialog) }
        }

        binding.negativeButton.apply {
            val buttonText = negativeButton?.first ?: "취소"
            val buttonListener = negativeButton?.second ?: defaultDismissListener

            text = buttonText
            setOnClickListener { buttonListener.onClick(this@CustomDialog) }
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
    // TODO(java.lang.RuntimeException: Parcelable encountered IOException writing serializable object (name = kotlin.Pair))
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TITLE, title)
        outState.putSerializable(KEY_MESSAGE, message)
        outState.putSerializable(KEY_POSITIVE_BUTTON, positiveButton)
        outState.putSerializable(KEY_NEGATIVE_BUTTON, negativeButton)
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
        title = savedInstanceState.getString(KEY_TITLE)
        message = savedInstanceState.getString(KEY_MESSAGE)
        positiveButton =
            savedInstanceState.getSerializable(KEY_POSITIVE_BUTTON) as? Pair<String?, CustomDialogListener?>?
        negativeButton =
            savedInstanceState.getSerializable(KEY_NEGATIVE_BUTTON) as? Pair<String?, CustomDialogListener?>?
    }

    /**
     * Activity or Fragment 에서 CustomDialog 를 chaining 형태로 호출하여 사용할 수 있도록 Builder Pattern 사용
     */
    class Builder {
        private val dialog = CustomDialog()

        fun setTitle(title: String): Builder {
            dialog.title = title
            return this
        }

        fun setMessage(message: String): Builder {
            dialog.message = message
            return this
        }

        fun setPositiveButton(text: String, onClickListener: CustomDialogListener): Builder {
            dialog.positiveButton = Pair(text, onClickListener)
            return this
        }

        fun setNegativeButton(text: String, onClickListener: CustomDialogListener): Builder {
            dialog.negativeButton = Pair(text, onClickListener)
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
        private const val KEY_TITLE = "TITLE"
        private const val KEY_MESSAGE = "TITLE"
        private const val KEY_POSITIVE_BUTTON = "POSITIVE_BUTTON"
        private const val KEY_NEGATIVE_BUTTON = "NEGATIVE_BUTTON"
    }
}
