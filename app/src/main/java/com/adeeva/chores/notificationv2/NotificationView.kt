package com.adeeva.chores.notificationv2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class NotificationView : AppCompatActivity() {
    var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_view)
        textView = findViewById(R.id.textView)
        val message = intent.getStringExtra("message")
        with(textView) {
            this?.setText(message)
        }
    }
}