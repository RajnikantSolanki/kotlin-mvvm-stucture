package com.example.kotlindemo.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.kotlindemo.R
import com.example.kotlindemo.activities.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.UnsupportedEncodingException

class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.e("From : ", remoteMessage.from!!)
        Log.e("Message : ", remoteMessage.data.toString())


        try {
            sendNotification(remoteMessage, MainActivity::class.java)
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun sendNotification(remoteMessage: RemoteMessage, cls: Class<*>) {
        var defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var notificationBuilder: NotificationCompat.Builder
        val notificationManager: NotificationManager
        val title: String?
        val message: String?
        message = remoteMessage.data["body"]
        title = remoteMessage.data["title"]
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val `when` = System.currentTimeMillis()
        val iconL = notificationIcon
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            notificationBuilder =
                NotificationCompat.Builder(this,resources.getString(R.string.default_notification_channel_id)).setSmallIcon(iconL)
                    .setContentTitle(title).setContentText(message).setAutoCancel(true)
                    .setWhen(`when`).setSound(defaultSoundUri)
                    .setColor(ContextCompat.getColor(this,R.color.colorPrimary)).setLargeIcon(
                        BitmapFactory.decodeResource(
                            resources,
                            R.mipmap.ic_launcher
                        )
                    ).setContentIntent(contentIntent).setStyle(
                        NotificationCompat.BigTextStyle().bigText(message)
                    ) as NotificationCompat.Builder
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        } else {
            defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            notificationBuilder = NotificationCompat.Builder(this,resources.getString(R.string.default_notification_channel_id))
                .setSmallIcon(notificationIcon).setContentTitle(title).setContentText(message)
                .setAutoCancel(true).setWhen(`when`).setSound(defaultSoundUri)
                .setColor(ContextCompat.getColor(this,R.color.colorPrimary))
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setContentIntent(contentIntent).setStyle(
                    NotificationCompat.BigTextStyle().bigText(message)
                ) as NotificationCompat.Builder
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var mChannel: NotificationChannel? = null
            val importance = NotificationManager.IMPORTANCE_HIGH
            val id = resources.getString(R.string.default_notification_channel_id)
            val name: CharSequence = getString(R.string.app_name)
            mChannel = NotificationChannel(id, name, importance)
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            val audioAttributes = AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE).build()
            mChannel.setSound(defaultSoundUri, audioAttributes)
            assert(notificationManager != null)
            notificationManager.createNotificationChannel(mChannel)
            notificationBuilder.setChannelId(resources.getString(R.string.default_notification_channel_id))
        }
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL)
        notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        notificationManager.notify(
            System.currentTimeMillis().toInt() /* ID of notification */,
            notificationBuilder.build()
        )
    }

    private val notificationIcon: Int
        private get() {
            val useWhiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            return if (useWhiteIcon) R.drawable.ic_notification else R.drawable.ic_notification
        }
}