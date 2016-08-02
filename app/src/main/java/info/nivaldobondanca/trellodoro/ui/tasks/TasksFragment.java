package info.nivaldobondanca.trellodoro.ui.tasks;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

import info.nivaldobondanca.trellodoro.databinding.TasksFragmentBinding;
import info.nivaldobondanca.trellodoro.databinding.TasksSingleTaskCardBinding;
import info.nivaldobondanca.trellodoro.model.TrelloCard;
import info.nivaldobondanca.trellodoro.model.TrelloList;
import info.nivaldobondanca.trellodoro.ui.OnTaskClickListener;
import info.nivaldobondanca.trellodoro.ui.timer.TimerActivity;
import info.nivaldobondanca.trellodoro.viewmodel.TaskCardViewModel;

/**
 * @author Nivaldo Bondança
 */
public class TasksFragment extends Fragment implements OnTaskClickListener {
	private static final String ARG_TASK_LIST_TYPE = "arg.TASK_LIST_TYPE";

	public static TasksFragment newInstance(@TrelloList.Type int taskList) {
		final Bundle args = new Bundle();
		args.putInt(ARG_TASK_LIST_TYPE, taskList);

		final TasksFragment fragment = new TasksFragment();
		fragment.setArguments(args);
		return fragment;
	}


	private TasksFragmentBinding binding;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = TasksFragmentBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		final TasksAdapter adapter = new TasksAdapter(getContext(), this);
		binding.tasksList.setAdapter(adapter);
	}

	@Override
	public void onTaskClicked(TasksSingleTaskCardBinding binding, CharSequence cardName) {
		TimerActivity.startWithTransitionAnimation(getActivity(), cardName, binding.getRoot(), binding.taskTitle);
	}


	static class TasksAdapter extends BaseAdapter {
		private final LayoutInflater      inflater;
		private final OnTaskClickListener taskClickListener;

		private List<TrelloCard> cards = Collections.emptyList();

		public TasksAdapter(Context context, OnTaskClickListener taskClickListener) {
			this.taskClickListener = taskClickListener;
			inflater = LayoutInflater.from(context);
		}

		public void setData(@NonNull List<TrelloCard> newCards) {
			cards = newCards;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return cards.size();
		}

		@Override
		public TrelloCard getItem(int position) {
			return cards.get(position);
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).id().hashCode();
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getView(int position, View recycledView, ViewGroup container) {
			final TasksSingleTaskCardBinding binding;
			if (recycledView == null) {
				binding = TasksSingleTaskCardBinding.inflate(inflater, container, false);
				binding.setViewModel(new TaskCardViewModel(taskClickListener));

				recycledView = binding.getRoot();
				recycledView.setTag(binding);
			}
			else {
				binding = (TasksSingleTaskCardBinding) recycledView.getTag();
			}

			binding.getViewModel().setData(getItem(position));

			return recycledView;
		}
	}
}
