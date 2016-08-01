package info.nivaldobondanca.trellodoro.ui.timer;

import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import info.nivaldobondanca.trellodoro.R;
import info.nivaldobondanca.trellodoro.ui.timer.services.TimerService;
import info.nivaldobondanca.trellodoro.util.FormatUtils;

/**
 * @author Nivaldo Bondança
 */
public class TimerNotificationHelper {
	private static final int NOTIFICATION_ID = 0;

	private final Context                    context;
	private final NotificationManagerCompat  notificationManager;

	private NotificationCompat.Builder notificationBuilder;

	public TimerNotificationHelper(Context context) {
		this.context = context;
		notificationManager = NotificationManagerCompat.from(context);
	}

	public void startNotification(String title, long remainingTimeMillis) {
		final PendingIntent pauseIntent = PendingIntent.getService(context, 0, TimerService.stopTimer(context), 0);

		notificationBuilder = new NotificationCompat.Builder(context)
				.setOngoing(true)
				.setContentTitle(title)
				.setContentText(FormatUtils.millisToSecondsAndMinutesString(remainingTimeMillis))
				.addAction(R.drawable.ic_pause, context.getText(R.string.timer_pause), pauseIntent);

		notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
	}

	public void updateNotification(long remainingTimeMillis) {
		notificationBuilder.setContentText(FormatUtils.millisToSecondsAndMinutesString(remainingTimeMillis));

		notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
	}

	public void pauseNotification(String title, long remainingTimeMillis) {
		final PendingIntent resumeIntent = PendingIntent.getService(context, 0, TimerService.startTimer(context, title, remainingTimeMillis), 0);

		notificationBuilder.mActions.clear();
		notificationBuilder
				.setOngoing(false)
				.setContentText(FormatUtils.millisToSecondsAndMinutesString(remainingTimeMillis))
				.addAction(R.drawable.ic_play, context.getText(R.string.timer_resume), resumeIntent);

		notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
	}

	public void stopNotification() {
		notificationManager.cancel(NOTIFICATION_ID);
	}
}
