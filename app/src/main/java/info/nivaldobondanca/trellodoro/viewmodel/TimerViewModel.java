package info.nivaldobondanca.trellodoro.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import info.nivaldobondanca.trellodoro.BR;
import info.nivaldobondanca.trellodoro.R;

/**
 * @author Nivaldo Bondança
 */
public class TimerViewModel extends BaseObservable {

	private static final long POMODORO_LENGTH = TimeUnit.SECONDS.toMillis(10);

	private long timeLeft = POMODORO_LENGTH;
	private int pomodoroCount = 0;
	private FloatingActionButton fab;
	private TrellodoroTimer timer;

	public TimerViewModel(FloatingActionButton fab) {
		this.fab = fab;
		updateActionResource();
	}

	@Bindable
	public CharSequence getTimeText() {
		final long seconds = TimeUnit.SECONDS.convert(timeLeft, TimeUnit.MILLISECONDS) % 60;
		final long minutes = TimeUnit.MINUTES.convert(timeLeft, TimeUnit.MILLISECONDS) % 60;
//		final long millis = (timeLeft % 1000) / 100;

		return String.format(Locale.ENGLISH, "%d:%02d", minutes, seconds);
	}

	@Bindable
	public CharSequence getInformationalText() {
		return pomodoroCount + " pomodoros";
	}

	public View.OnClickListener getActionClick() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!((AnimatedVectorDrawable) fab.getDrawable()).isRunning()) {
					toggleTimerState();
				}
			}
		};
	}

	private void toggleTimerState() {
		updateActionResource();
		((AnimatedVectorDrawable) fab.getDrawable()).start();
		if (timer == null) {
			startCountDown();
		}
		else {
			stopCountDown();
		}
	}

	private void startCountDown() {
		timer = new TrellodoroTimer(timeLeft);
		timer.start();
	}

	private void stopCountDown() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private void updateActionResource() {
		if (timer == null) {
			fab.setImageResource(R.drawable.ic_play_to_pause);
		}
		else {
			fab.setImageResource(R.drawable.ic_pause_to_play);
		}
	}


	private class TrellodoroTimer extends CountDownTimer {
		public TrellodoroTimer(long millisInFuture) {
			super(millisInFuture, TimeUnit.MILLISECONDS.toMillis(500));
		}

		@Override
		public void onTick(long millisUntilFinished) {
			timeLeft = millisUntilFinished;
			notifyPropertyChanged(BR.timeText);
		}

		@Override
		public void onFinish() {
			pomodoroCount++;
			timeLeft = 0;
			notifyPropertyChanged(BR.timeText);
			notifyPropertyChanged(BR.informationalText);

			toggleTimerState();
		}
	}
}
