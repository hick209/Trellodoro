package info.nivaldobondanca.trellodoro.ui.timer.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.concurrent.TimeUnit;

import info.nivaldobondanca.trellodoro.ui.timer.TimerNotificationHelper;

/**
 * @author Nivaldo Bondança
 */
public class TimerService extends Service {
	public static final String ACTION_START  = "action.TIMER_START";
	public static final String ACTION_UPDATE = "action.TIMER_UPDATE";
	public static final String ACTION_DONE   = "action.TIMER_DONE";
	public static final String ACTION_STOP   = "action.TIMER_STOPPED";

	private static final String EXTRA_LABEL    = "extra.LABEL";
	private static final String EXTRA_DURATION = "extra.DURATION";

	public static Intent startTimer(Context context, CharSequence label, long durationMillis) {
		return new Intent(context, TimerService.class)
				.setAction(ACTION_START)
				.putExtra(EXTRA_LABEL, label)
				.putExtra(EXTRA_DURATION, durationMillis);
	}

	public static Intent stopTimer(Context context) {
		return new Intent(context, TimerService.class)
				.setAction(ACTION_STOP);
	}

	@CheckResult
	public static long readDuration(@NonNull Intent intent) {
		return intent.getLongExtra(EXTRA_DURATION, 0L);
	}

	@CheckResult
	public static CharSequence readLabel(@NonNull Intent intent) {
		return intent.getCharSequenceExtra(EXTRA_LABEL);
	}


	private TimerNotificationHelper notificationHelper;
	private LocalBroadcastManager   broadcastManager;

	private TrellodoroTimer timer;
	private CharSequence    label;

	@Override
	public void onCreate() {
		super.onCreate();
		broadcastManager = LocalBroadcastManager.getInstance(this);
		notificationHelper = new TimerNotificationHelper(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		switch (intent.getAction()) {
			case ACTION_START: {
				if (timer != null) {
					throw new IllegalStateException("There can only be one timer running.");
				}

				final long durationMillis = intent.getLongExtra(EXTRA_DURATION, 0);
				timer = new TrellodoroTimer(durationMillis);
				label = intent.getCharSequenceExtra(EXTRA_LABEL);

				broadcastManager.sendBroadcast(new Intent(ACTION_START)
						.putExtra(EXTRA_LABEL, label)
						.putExtra(EXTRA_DURATION, durationMillis));

				notificationHelper.startNotification(label.toString(), durationMillis);

				timer.start();
				break;
			}

			case ACTION_STOP:
				if (timer != null) {
					final long remainingTime = timer.getRemainingTime();
					timer.cancel();
					timer = null;

					broadcastManager.sendBroadcast(new Intent(ACTION_STOP)
							.putExtra(EXTRA_LABEL, label));

					notificationHelper.pauseNotification(label.toString(), remainingTime);
				}
				break;
		}

		return START_NOT_STICKY;
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		// TODO
		return null;
	}


	private class TrellodoroTimer extends CountDownTimer {
		private long remainingTime;

		public TrellodoroTimer(long millisInFuture) {
			super(millisInFuture, TimeUnit.MILLISECONDS.toMillis(500));
			remainingTime = millisInFuture;
		}

		@Override
		public void onTick(long millisUntilFinished) {
			broadcastManager.sendBroadcast(new Intent(ACTION_UPDATE)
					.putExtra(EXTRA_LABEL, label)
					.putExtra(EXTRA_DURATION, millisUntilFinished));

			remainingTime = millisUntilFinished;

			notificationHelper.updateNotification(millisUntilFinished);
		}

		@Override
		public void onFinish() {
			broadcastManager.sendBroadcast(new Intent(ACTION_DONE)
					.putExtra(EXTRA_LABEL, label)
					.putExtra(EXTRA_DURATION, 0L));

			remainingTime = 0L;
			timer = null;

			notificationHelper.stopNotification();
		}

		public long getRemainingTime() {
			return remainingTime;
		}
	}
}
