package info.nivaldobondanca.trellodoro.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import java.util.concurrent.TimeUnit;

import info.nivaldobondanca.trellodoro.BR;
import info.nivaldobondanca.trellodoro.R;
import info.nivaldobondanca.trellodoro.ui.timer.TimerReceiver;
import info.nivaldobondanca.trellodoro.util.FormatUtils;

/**
 * @author Nivaldo Bondança
 */
public class TimerViewModel extends BaseObservable
		implements TimerReceiver.Callbacks {
	public interface Callbacks {
		void startTimer(long duration);
		void pauseTimer();

		boolean isTimerRunning();
	}

	private static final long POMODORO_LENGTH = TimeUnit.SECONDS.toMillis(10);


	private long timeLeft = POMODORO_LENGTH;
	private int pomodoroCount = 0;

	private final FloatingActionButton fab;
	private final Callbacks            callbacks;

	private boolean fabShowsPause;

	public TimerViewModel(FloatingActionButton fab, Callbacks callbacks) {
		this.fab = fab;
		this.callbacks = callbacks;
		fabShowsPause = callbacks.isTimerRunning();
		updateFabState();
	}

	@Bindable
	public CharSequence getTimeText() {
		return FormatUtils.millisToSecondsAndMinutesString(timeLeft);
	}

	@Bindable
	public CharSequence getInformationalText() {
		return pomodoroCount + " pomodoros";
	}

	public View.OnClickListener getActionClick() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) fab.getDrawable();
				if (!drawable.isRunning()) {
					toggleTimerState();
				}
			}
		};
	}


	public void setTimeLeft(long timeLeft) {
		this.timeLeft = timeLeft;
		notifyPropertyChanged(BR.timeText);
	}

	private void toggleTimerState() {
		if (callbacks.isTimerRunning()) {
			callbacks.pauseTimer();
		}
		else {
			callbacks.startTimer(timeLeft);
		}
		updateFabState();
	}

	private void updateFabState() {
		if (callbacks.isTimerRunning()) {
			if (!fabShowsPause) {
				fab.setImageResource(R.drawable.ic_play_to_pause);
				((AnimatedVectorDrawable) fab.getDrawable()).start();
			}
			fabShowsPause = true;
		}
		else {
			if (!fabShowsPause) {
				fab.setImageResource(R.drawable.ic_pause_to_play);
				((AnimatedVectorDrawable) fab.getDrawable()).start();
			}
			fabShowsPause = false;
		}
	}


	///////////////////////////////
	/// TimerService.Callbacks
	///////////////////////////////

	@Override
	public void onUpdateTimer(CharSequence label, long durationMillis) {
		setTimeLeft(durationMillis);
	}

	@Override
	public void onStopTimer(CharSequence label) {
		updateFabState();
	}

	@Override
	public void onFinishTimer(CharSequence label) {
		pomodoroCount++;
		timeLeft = POMODORO_LENGTH;
		notifyPropertyChanged(BR.timeText);
		notifyPropertyChanged(BR.informationalText);

		updateFabState();
	}
}
