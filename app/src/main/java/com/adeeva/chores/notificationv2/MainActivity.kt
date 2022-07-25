package com.adeeva.chores.notificationv2


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.adeeva.chores.notificationv2.databinding.ActivityMainBinding
import android.content.Intent as Intent


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private var notificationManager: NotificationManager? = null
    private val channel_id = "channel_1"
    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownTimer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channel_id, "countdown", "when countdown ends")

        binding.button.setOnClickListener {
            countDownTimer.start()
        }

        countDownTimer = object : CountDownTimer(10000, 100) {
            override fun onTick(p0: Long) {
                binding.timer.text = getString(R.string.time_reamining, p0 / 1000)
            }

            override fun onFinish() {
                displayNotification()
            }
        }
    }

    @SuppressLint("RemoteViewLayout")
    private fun displayNotification() {
        val notificationIntent = Intent(this, NotificationView::class.java)
        val resultPendingIntent = PendingIntent.getActivities(this,1,
            arrayOf(notificationIntent), PendingIntent.FLAG_UPDATE_CURRENT)
        RemoteViews(packageName, R.layout.activity_notification_view)
        val notificationId = 45
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification = NotificationCompat.Builder(this@MainActivity, channel_id)
            .setSmallIcon(R.drawable.ic_noti)
            .setContentTitle("Countdown Timer")
            .setContentText("Your timer has ended")
            .setAutoCancel(true)
            .setSound(alarmSound)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(resultPendingIntent)
            .build()

        notificationManager?.notify(notificationId, notification)

    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }

            notificationManager?.createNotificationChannel(channel)

        }
    }
}