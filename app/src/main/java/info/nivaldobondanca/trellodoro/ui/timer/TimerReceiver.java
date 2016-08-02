package info.nivaldobondanca.trellodoro.ui.timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

/**
 * @author Nivaldo Bondança
 */
public class TimerReceiver extends BroadcastReceiver {
	public interface Callbacks {
		void onUpdateTimer(CharSequence label, long durationMillis);
		void onStopTimer(CharSequence label);
		void onFinishTimer(CharSequence label);
	}

	private final IntentFilter intentFilter;
	private final Callbacks    callbacks;

	public TimerReceiver(@NonNull Callbacks callbacks) {
		this.callbacks = callbacks;

		intentFilter = new IntentFilter();
		intentFilter.addAction(TimerService.ACTION_START);
		intentFilter.addAction(TimerService.ACTION_UPDATE);
		intentFilter.addAction(TimerService.ACTION_DONE);
		intentFilter.addAction(TimerService.ACTION_STOP);
	}

	public void register(@NonNull Context context) {
		LocalBroadcastManager.getInstance(context).registerReceiver(this, intentFilter);
	}

	public void unregister(@NonNull Context context) {
		LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		switch (intent.getAction()) {
			case TimerService.ACTION_START:
			case TimerService.ACTION_UPDATE: {
				final CharSequence label = TimerService.readLabel(intent);
				final long durationMillis = TimerService.readDuration(intent);

				callbacks.onUpdateTimer(label, durationMillis);
				break;
			}

			case TimerService.ACTION_STOP: {
				final CharSequence label = TimerService.readLabel(intent);
				callbacks.onStopTimer(label);
				break;
			}
			case TimerService.ACTION_DONE: {
				final CharSequence label = TimerService.readLabel(intent);
				callbacks.onFinishTimer(label);
				break;
			}
		}
	}
}
