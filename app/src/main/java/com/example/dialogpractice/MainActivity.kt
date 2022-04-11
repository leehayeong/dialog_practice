package com.example.dialogpractice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dialogpractice.databinding.ActivityMainBinding

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
}
