package info.nivaldobondanca.trellodoro.viewmodel;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.IBinder;
import android.view.View;

import info.nivaldobondanca.trellodoro.BR;
import info.nivaldobondanca.trellodoro.ui.timer.TimerReceiver;
import info.nivaldobondanca.trellodoro.ui.timer.TimerService;

/**
 * @author Nivaldo Bondança
 */
public class TasksViewModel extends BaseObservable
		implements TimerReceiver.Callbacks, ServiceConnection {
	private TimerService.Binder timerBinder;

	@Bindable
	public int getFooterVisibility() {
		return isTaskRunning() ? View.VISIBLE : View.GONE;
	}

	@Bindable
	public CharSequence getRunningTaskLabel() {
		return timerBinder != null ? timerBinder.label() : null;
	}


	private boolean isTaskRunning() {
		return timerBinder != null && timerBinder.isRunning();
	}

	private void updateRunningState() {
		// TODO update the play/pause button state
	}


	///////////////////////////////
	/// TimerService.Callbacks
	///////////////////////////////

	@Override public void onUpdateTimer(CharSequence label, long durationMillis) {}

	@Override
	public void onStopTimer(CharSequence label) {
		notifyPropertyChanged(BR.runningTaskLabel);
		notifyPropertyChanged(BR.footerVisibility);
		updateRunningState();
	}

	@Override
	public void onFinishTimer(CharSequence label) {
		notifyPropertyChanged(BR.runningTaskLabel);
		notifyPropertyChanged(BR.footerVisibility);
		updateRunningState();
	}


	///////////////////////////////
	/// ServiceConnection
	///////////////////////////////

	@Override
	public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
		timerBinder = (TimerService.Binder) iBinder;
		notifyPropertyChanged(BR.runningTaskLabel);
		notifyPropertyChanged(BR.footerVisibility);
	}

	@Override
	public void onServiceDisconnected(ComponentName componentName) {
		timerBinder = null;
		notifyPropertyChanged(BR.runningTaskLabel);
		notifyPropertyChanged(BR.footerVisibility);
	}
}
