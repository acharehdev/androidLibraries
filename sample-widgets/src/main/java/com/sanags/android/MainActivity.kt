package com.sanags.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import ir.sanags.android.widgets.CategorizedKeyValueLayout

class MainActivity : AppCompatActivity() {

    lateinit var cat1: CategorizedKeyValueLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cat1 = findViewById(R.id.cat1)
        cat1.categoryNameFont = ResourcesCompat.getFont(this, R.font.mj_silicon_0)
        cat1.contentFont = ResourcesCompat.getFont(this, R.font.mj_silicon_0)

        cat1.categoryNameColor = ContextCompat.getColor(this, android.R.color.holo_purple)
        cat1.contentKeyColor = ContextCompat.getColor(this, android.R.color.darker_gray)
        cat1.contentValueColor = ContextCompat.getColor(this, android.R.color.holo_red_light)

        cat1.appendContent("\nعنوان تستی 2: ", "مقدار تستی 2", "\n")


        cat1.contentKeyColor = ContextCompat.getColor(this, android.R.color.holo_orange_dark)
        cat1.contentValueColor = ContextCompat.getColor(this, android.R.color.holo_green_dark)

        cat1.appendContent("عنوان تستی 3: ", "مقدار تستی 3", " ")
    }
}