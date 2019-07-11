class NotificationWorker(val context: Context, private val workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val TAG = NotificationWorker::class.java.simpleName

    override fun doWork(): Result {
        val title = workerParams.inputData.getString(Constants.WORKER_NOTIFICATION_TITLE)
        val message = workerParams.inputData.getString(Constants.WORKER_NOTIFICATION_MESSAGE)
        val notificationId = workerParams.inputData.getInt(Constants.WORKER_NOTIFICATION_ID, 0)
        val smallIcon = workerParams.inputData.getInt(Constants.WORKER_SMALL_ICON, 0)

        Log.d(TAG, "doWork : notification received $title $notificationId")

        createNotification(smallIcon, title, message, notificationId)

        return Result.success()
    }

    private fun createNotification(smallIcon: Int, title: String?, message: String?, notificationId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = Constants.NOTIFICATION_CHANNEL
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.NOTIFICATION_CHANNEL, name, importance).apply {
                description = message
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(INTENT_ACTION).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra(Constants.WORKER_NOTIFICATION_ID, notificationId)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL)
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }
}