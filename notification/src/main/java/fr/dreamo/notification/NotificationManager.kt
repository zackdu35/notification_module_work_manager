package fr.dreamo.notification

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.*
import java.util.concurrent.TimeUnit

class NotificationManager(var context: Context) {

    private val workManager = WorkManager.getInstance()
    private var notificationId: Int = 0
    private val fifteenMinutes = 15 * 60 * 1000
    private val TAG = NotificationManager::class.java.simpleName

    fun scheduleNotification(smallIcon: Int, title: String, message: String, date: Date) {
        notificationId = generateNotificationId(context)
        Log.d(TAG, "notification scheduled : $message $notificationId")

        val delay = (date.time - Calendar.getInstance().timeInMillis) - fifteenMinutes

        val notificationRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(createInputDataForNotification(smallIcon, title, message, notificationId))
                .addTag(notificationId.toString())
                .build()

        workManager.enqueue(notificationRequest)
    }

    fun cancelNotification() {
        val notificationId = if (notificationId == 0) getNotificationId(context) else notificationId
        Log.d(TAG, "notification canceled : $notificationId")
        workManager.cancelAllWorkByTag(notificationId.toString())
    }

    private fun createInputDataForNotification(smallIcon: Int, title: String, message: String, notificationId: Int): Data {
        val builder = Data.Builder()
        builder.putInt(Constants.WORKER_SMALL_ICON, smallIcon)
        builder.putString(Constants.WORKER_NOTIFICATION_TITLE, title)
        builder.putString(Constants.WORKER_NOTIFICATION_MESSAGE, message)
        builder.putInt(Constants.WORKER_NOTIFICATION_ID, notificationId)
        return builder.build()
    }

    private fun generateNotificationId(context: Context): Int {
        val notificationId = PrefUtils.getFromPrefs(context, Constants.NOTIFICATION_ID, 0) + 1
        PrefUtils.saveToPrefs(context, Constants.NOTIFICATION_ID, notificationId)
        return notificationId
    }

    fun getNotificationId(context: Context): Int =
            PrefUtils.getFromPrefs(context, Constants.NOTIFICATION_ID, 0)

}