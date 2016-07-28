package info.nivaldobondanca.trellodoro;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.nivaldobondanca.trellodoro.databinding.TasksActivityBinding;

public class TasksActivity extends AppCompatActivity {

	public static Intent newIntent(Context context) {
		return new Intent(context, TasksActivity.class);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final TasksActivityBinding binding =
				DataBindingUtil.setContentView(this, R.layout.tasks_activity);

		// Setup toolbar
		setSupportActionBar(binding.toolbar);

		final PagerAdapter adapter = new TasksPagerAdapter(getSupportFragmentManager());

		// Setup view pager
		binding.pager.setAdapter(adapter);
		binding.tabs.setupWithViewPager(binding.pager);
	}


	static class TasksPagerAdapter extends FragmentPagerAdapter {
		public TasksPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(final int position) {
			return new Fragment() {
				@Nullable
				@Override
				public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
					final int color;
					switch (position) {
						case 0:
							color = Color.RED;
							break;

						case 1:
							color = Color.GREEN;
							break;

						case 2:
							color = Color.BLUE;
							break;

						default:
							color = Color.BLACK;
							break;
					}

					final View view = new View(getContext());
					view.setBackgroundColor(color);
					return view;
				}
			};
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return "TODO";
				case 1:
					return "DOING";
				case 2:
					return "DONE";
				default:
					return null;
			}
		}
	}
}
