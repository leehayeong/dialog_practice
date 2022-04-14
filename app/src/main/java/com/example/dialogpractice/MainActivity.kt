package com.example.dialogpractice

import android.os.Bundle
import android.os.Parcel
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dialogpractice.databinding.ActivityMainBinding
import com.example.dialogpractice.databinding.DialogCommonBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAlertDialog()
        setAlertButtonDialog()
        setAlertListDialog()
        setAlertCheckBoxDialog()
        setRadioButtonDialog()
        setCustomViewAlertDialog()
        setCustomClassConfirmDialog()
        setCustomClassAlertDialog()
    }

    private fun setAlertDialog() {
        binding.alertDialog.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Hello, This is message")
                .show()
        }
    }

    private fun setAlertButtonDialog() {
        binding.alertButtonDialog.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Hello, This is message")
                .setPositiveButton("ok") { _, _ -> Log.d("hy", "positive") }
                .setNegativeButton("cancel") { _, _ -> Log.d("hy", "negative") }
                .setNeutralButton("neutral") { _, _ -> Log.d("hy", "neutral") }
                .show()
        }
    }

    private fun setAlertListDialog(array: Array<String> = arrayOf("A", "B", "C")) {
        binding.alertListDialog.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("list")
                .setItems(array) { _, which -> Log.d("hy", "currentItems : ${array[which]}") }
                .show()
        }
    }

    private fun setAlertCheckBoxDialog(
        array: Array<String> = arrayOf("A", "B", "C"),
        checkedArray: BooleanArray = booleanArrayOf(true, false, true)
    ) {
        binding.alertCheckBoxDialog.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("checkbox")
                .setMultiChoiceItems(array, checkedArray) { _, which, isChecked ->
                    Log.d("hy", "which: $which, isChecked : $isChecked")
                }
                .setPositiveButton("ok") { _, _ ->
                    Log.d("hy", "checkedItems: ${checkedArray.contentToString()}")
                }
                .show()
        }
    }

    private fun setRadioButtonDialog(
        array: Array<String> = arrayOf("A", "B", "C"),
        checkedItemPosition: Int = 0
    ) {
        binding.alertRadioButtonDialog.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("radio")
                .setSingleChoiceItems(array, checkedItemPosition) { _, which ->
                    Log.d("hy", "which: $which")
                }
                .setPositiveButton("ok") { _, _ ->
                    Log.d("hy", "checkedItemPosition: $checkedItemPosition")
                }
                .show()
        }
    }

    private fun setCustomViewAlertDialog() {
//        val layoutInflater = LayoutInflater.from(this)
//        val view = layoutInflater.inflate(R.layout.dialog_common, null)
//
//        val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
//            .setView(view)
//            .create()
//
//        val message = view.findViewById<TextView>(R.id.message)
//        val negativeButton = view.findViewById<AppCompatButton>(R.id.negativeButton)
//        val positiveButton = view.findViewById<AppCompatButton>(R.id.positiveButton)

        // dataBinding 사용
        val dialogBinding = DialogCommonBinding.inflate(layoutInflater)
        val dialogView = dialogBinding.root

        // 다이얼로그 create
        val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        // create 후에 버튼 이벤트 바인딩
        dialogBinding.negativeButton.setOnClickListener {
            Log.d("hy", "아니오 click")
            alertDialog.dismiss()
        }

        dialogBinding.positiveButton.setOnClickListener {
            Log.d("hy", "예 click")
            alertDialog.dismiss()
        }

        // 다이얼로그 show
        binding.customAlertDialog.setOnClickListener {
            alertDialog.show()
        }
    }

    private fun setCustomClassConfirmDialog() {
        binding.customClassConfirmDialog.setOnClickListener {
            CustomDialog.Builder()
                .setTitle("title")
                .setMessage("장시간 이용이 없어 로그아웃 합니다.")
                .setPositiveButton {
                    Log.d("hy", "예")
                    it.dismiss()
                }
                .show(supportFragmentManager)
        }
    }

    private fun setCustomClassAlertDialog() {
        binding.customClassAlertDialog.setOnClickListener {
            CustomDialog.Builder()
                .setTitle("title")
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton {
                    Log.d("hy", "예")
                    it.dismiss()
                }
                .setNegativeButton {
                    Log.d("hy", "아니오")
                    it.dismiss()
                }
                .show(supportFragmentManager)
        }
    }
}
