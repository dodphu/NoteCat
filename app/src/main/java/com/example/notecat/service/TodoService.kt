package com.example.notecat.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.notecat.R
import com.example.notecat.activities.MainActivity
import com.example.notecat.fragments.TodoFragment
import com.example.notecat.model.Todo
import java.io.Serializable

class TodoService : Service() {

    val CHANNEL_ID = "todo_channel_id"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Todo Notification"
            val descriptionText = "Todo Notification for channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            if (it.action == "ACTION_PLAY") {
                val todo: Todo? = it.getSerializableExtra("todo_item_noti") as? Todo
                todo?.let {
                    startForeground(444, createNotification(todo))
                }
            } else if (it.action == "ACTION_STOP") {
                stopForeground(STOP_FOREGROUND_DETACH)
                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(444)
            }else{

            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun createNotification(data: Todo): Notification {
        val mainIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent
            .getActivity(this, 101, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val closeIntent = Intent(this, TodoService::class.java)
        closeIntent.action = "ACTION_STOP"
        val closePendingIntent = PendingIntent.getService(this, 102, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.no_task)
            .setContentTitle(data.todoitem)
            .setContentText("")
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.baseline_clear_24, "Đóng", closePendingIntent)
            .build()
        return notification
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
