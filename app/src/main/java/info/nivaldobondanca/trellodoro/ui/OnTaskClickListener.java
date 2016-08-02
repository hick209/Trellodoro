package info.nivaldobondanca.trellodoro.ui;

import info.nivaldobondanca.trellodoro.databinding.TasksSingleTaskCardBinding;
import info.nivaldobondanca.trellodoro.model.TrellodoroCard;

public interface OnTaskClickListener {
	void onTaskClicked(TasksSingleTaskCardBinding binding, TrellodoroCard card);
}
