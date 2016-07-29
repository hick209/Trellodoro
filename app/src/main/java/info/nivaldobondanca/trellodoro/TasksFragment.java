package info.nivaldobondanca.trellodoro;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.Random;

import info.nivaldobondanca.trellodoro.databinding.TasksFragmentBinding;
import info.nivaldobondanca.trellodoro.databinding.TasksSingleTaskCardBinding;

/**
 * @author Nivaldo Bondança
 */
public class TasksFragment extends Fragment {

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
		binding.tasksList.setAdapter(new TasksAdapter(getContext()));
		binding.tasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Toast.makeText(getContext(), "Clicked on item " + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

	static class TasksAdapter extends BaseAdapter {

		private final LayoutInflater inflater;

		// XXX temporary
		private final int count;

		public TasksAdapter(Context context) {
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
				recycledView = binding.getRoot();
				recycledView.setTag(binding);
			}
			else {
				binding = (TasksSingleTaskCardBinding) recycledView.getTag();
			}

			// TODO
			binding.taskTitle.setText(getItem(position));

			return recycledView;
		}
	}
}
