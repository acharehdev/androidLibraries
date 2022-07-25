package com.sanags.android.sample_toast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import ir.sanags.android.achar_toast.ToastType
import ir.sanags.android.achar_toast.toast

class MainActivity : AppCompatActivity() {
    private lateinit var btnInfo: Button
    private lateinit var btnSuccess: Button
    private lateinit var btnError: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInfo = findViewById<Button?>(R.id.btn_info).apply {
            setOnClickListener {
                toast("فارسی پیغام اطلاع رسانی", ToastType.Info, Toast.LENGTH_SHORT)
            }
        }
        btnSuccess = findViewById<Button?>(R.id.btn_sucess).apply {
            setOnClickListener {
                toast("پیغام موفقیت", ToastType.Success, Toast.LENGTH_SHORT)
            }
        }
        btnError = findViewById<Button?>(R.id.btn_error).apply {
            setOnClickListener {
                toast("پیغام خطا", ToastType.Error, Toast.LENGTH_SHORT)
            }
        }
    }
}