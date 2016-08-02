package info.nivaldobondanca.trellodoro.viewmodel;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import java.util.concurrent.TimeUnit;

import info.nivaldobondanca.trellodoro.BR;
import info.nivaldobondanca.trellodoro.MockData;
import info.nivaldobondanca.trellodoro.R;
import info.nivaldobondanca.trellodoro.model.TrellodoroCard;
import info.nivaldobondanca.trellodoro.ui.timer.TimerReceiver;
import info.nivaldobondanca.trellodoro.ui.timer.TimerService;
import info.nivaldobondanca.trellodoro.util.FormatUtils;

/**
 * @author Nivaldo Bondança
 */
public class TimerViewModel extends BaseObservable
		implements TimerReceiver.Callbacks, ServiceConnection {
	private static final long POMODORO_LENGTH = TimeUnit.SECONDS.toMillis(10);

	private long timeLeft = POMODORO_LENGTH;

	@NonNull
	private TrellodoroCard card;

	private final FloatingActionButton fab;
	private boolean                    fabShowsPause;

	private TimerService.Binder timerBinder;

	public TimerViewModel(@NonNull TrellodoroCard card, FloatingActionButton fab) {
		this.card = card;
		this.fab = fab;

		// TODO add loading state
	}

	@Bindable
	public boolean isLoading() {
		return timerBinder == null;
	}

	@Bindable
	public CharSequence getTimeText() {
		return FormatUtils.millisToSecondsAndMinutesString(timeLeft);
	}

	@Bindable
	public CharSequence getInformationalText() {
		return card.pomodoroCount() + " pomodoros";
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
		if (timerBinder == null) return;

		if (timerBinder.isRunning()) {
			timerBinder.stopTimer();
		}
		else {
			timerBinder.startTimer(card.name(), timeLeft);
		}
	}

	private void updateFabState() {
		if (timerBinder == null) return;

		if (timerBinder.isRunning()) {
			if (!fabShowsPause) {
				fab.setImageResource(R.drawable.ic_play_to_pause);
				((AnimatedVectorDrawable) fab.getDrawable()).start();
			}
			fabShowsPause = true;
		}
		else {
			if (fabShowsPause) {
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
		updateFabState();
	}

	@Override
	public void onStopTimer(CharSequence label) {
		updateFabState();
	}

	@Override
	public void onFinishTimer(CharSequence label) {
		card = MockData.updateCard(card, card.totalTimeSpent() + POMODORO_LENGTH, card.pomodoroCount()+1);
		timeLeft = POMODORO_LENGTH;
		notifyPropertyChanged(BR.timeText);
		notifyPropertyChanged(BR.informationalText);

		updateFabState();
	}


	///////////////////////////////
	/// ServiceConnection
	///////////////////////////////

	@Override
	public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
		timerBinder = (TimerService.Binder) iBinder;

		fabShowsPause = timerBinder.isRunning();
		updateFabState();

		notifyPropertyChanged(BR.loading);
	}

	@Override
	public void onServiceDisconnected(ComponentName componentName) {
		timerBinder = null;
		notifyPropertyChanged(BR.loading);
	}
}
