package info.nivaldobondanca.trellodoro.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.Random;

import info.nivaldobondanca.trellodoro.databinding.TasksFragmentBinding;
import info.nivaldobondanca.trellodoro.databinding.TasksSingleTaskCardBinding;
import info.nivaldobondanca.trellodoro.OnTaskClickListener;
import info.nivaldobondanca.trellodoro.viewmodel.TaskViewModel;

/**
 * @author Nivaldo Bondança
 */
public class TasksFragment extends Fragment implements OnTaskClickListener {

	private TasksFragmentBinding binding;

	public static TasksFragment newInstance() {
		final Bundle args = new Bundle();
		// TODO create the args

		final TasksFragment fragment = new TasksFragment();
		fragment.setArguments(args);
		return fragment;
	}


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
	public void onTaskClicked(View view, CharSequence cardName) {
		// TODO
		Toast.makeText(getContext(), "Clicked on " + cardName, Toast.LENGTH_SHORT).show();
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
