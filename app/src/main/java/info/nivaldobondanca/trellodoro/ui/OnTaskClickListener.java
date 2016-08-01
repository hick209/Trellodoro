package info.nivaldobondanca.trellodoro.ui;

import info.nivaldobondanca.trellodoro.databinding.TasksSingleTaskCardBinding;

public interface OnTaskClickListener {
	void onTaskClicked(TasksSingleTaskCardBinding binding, CharSequence cardName);
}
