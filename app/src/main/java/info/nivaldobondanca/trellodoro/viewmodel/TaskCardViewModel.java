package info.nivaldobondanca.trellodoro.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import info.nivaldobondanca.trellodoro.BR;
import info.nivaldobondanca.trellodoro.databinding.TasksSingleTaskCardBinding;
import info.nivaldobondanca.trellodoro.model.TrelloCard;
import info.nivaldobondanca.trellodoro.ui.OnTaskClickListener;

/**
 * @author Nivaldo Bondança
 */
public class TaskCardViewModel extends BaseObservable {

	private final OnTaskClickListener listener;

	@Nullable
	private TrelloCard card;

	public TaskCardViewModel(@NonNull OnTaskClickListener listener) {
		this.listener = listener;
	}

	public void setData(@NonNull TrelloCard card) {
		this.card = card;
		notifyPropertyChanged(BR.cardName);
	}

	@Bindable
	public CharSequence getCardName() {
		return card != null ? card.title() : null;
	}

	public View.OnClickListener getClickListener() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (card != null) {
					final TasksSingleTaskCardBinding binding = (TasksSingleTaskCardBinding) view.getTag();
					listener.onTaskClicked(binding, card.title());
				}
			}
		};
	}
}

