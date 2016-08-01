package info.nivaldobondanca.trellodoro.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;

import info.nivaldobondanca.trellodoro.BR;
import info.nivaldobondanca.trellodoro.databinding.TasksSingleTaskCardBinding;
import info.nivaldobondanca.trellodoro.ui.OnTaskClickListener;

/**
 * @author Nivaldo Bondança
 */
public class TaskViewModel extends BaseObservable {

	private final OnTaskClickListener listener;

	private CharSequence cardName;

	public TaskViewModel(@NonNull OnTaskClickListener listener) {
		this.listener = listener;
	}

	// TODO update this method
	public void setData(@NonNull CharSequence cardName) {
		this.cardName = cardName;
		notifyPropertyChanged(BR.cardName);
	}

	@Bindable
	public CharSequence getCardName() {
		return cardName;
	}

	public View.OnClickListener getClickListener() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.onTaskClicked((TasksSingleTaskCardBinding) view.getTag(), cardName);
			}
		};
	}
}

