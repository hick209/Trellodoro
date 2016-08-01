package info.nivaldobondanca.trellodoro.ui.tasks;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Random;

import info.nivaldobondanca.trellodoro.databinding.TasksFragmentBinding;
import info.nivaldobondanca.trellodoro.databinding.TasksSingleTaskCardBinding;
import info.nivaldobondanca.trellodoro.ui.OnTaskClickListener;
import info.nivaldobondanca.trellodoro.ui.timer.TimerActivity;
import info.nivaldobondanca.trellodoro.viewmodel.TaskViewModel;

/**
 * @author Nivaldo Bondança
 */
public class TasksFragment extends Fragment implements OnTaskClickListener {

	@IntDef({
			TASK_LIST_TODO,
			TASK_LIST_DOING,
			TASK_LIST_DONE,
	})
	@Retention(RetentionPolicy.SOURCE)
	public @interface TaskList {}

	public static final int TASK_LIST_TODO  = 0;
	public static final int TASK_LIST_DOING = 1;
	public static final int TASK_LIST_DONE  = 2;

	private static final String ARG_TASK_LIST = "arg.TASK_LIST";

	public static TasksFragment newInstance(@TaskList int taskList) {
		final Bundle args = new Bundle();
		args.putInt(ARG_TASK_LIST, taskList);
		// TODO create the args

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
		binding.tasksList.setAdapter(new TasksAdapter(getContext(), this));
	}

	@Override
	public void onTaskClicked(TasksSingleTaskCardBinding binding, CharSequence cardName) {
		TimerActivity.startWithTransitionAnimation(getActivity(), cardName, binding.getRoot(), binding.taskTitle);
	}


	static class TasksAdapter extends BaseAdapter {
		private final LayoutInflater      inflater;
		private final OnTaskClickListener taskClickListener;

		// XXX temporary
		private final int count;

		public TasksAdapter(Context context, OnTaskClickListener taskClickListener) {
			this.taskClickListener = taskClickListener;
			inflater = LayoutInflater.from(context);
			count = new Random().nextInt(150);
		}

		@Override
		public int getCount() {
			return count;
		}

		@Override
		public CharSequence getItem(int position) {
			// XXX temporary
			return "Position " + position;
		}

		@Override
		public long getItemId(int position) {
			// TODO update this
			return position;
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
				binding.setViewModel(new TaskViewModel(taskClickListener));

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
