package info.nivaldobondanca.trellodoro.ui.tasks;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

import info.nivaldobondanca.trellodoro.R;
import info.nivaldobondanca.trellodoro.databinding.TasksActivityBinding;
import info.nivaldobondanca.trellodoro.ui.settings.SettingsActivity;

public class TasksActivity extends AppCompatActivity {

	public static Intent newIntent(Context context) {
		return new Intent(context, TasksActivity.class);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final TasksActivityBinding binding =
				DataBindingUtil.setContentView(this, R.layout.tasks_activity);

		final PagerAdapter adapter = new TasksPagerAdapter(this, getSupportFragmentManager());

		// Setup toolbar
		setSupportActionBar(binding.toolbar);

		// Setup view pager
		binding.pager.setAdapter(adapter);
		binding.tabs.setupWithViewPager(binding.pager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tasks_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.tasks_menu_settings:
				startActivity(SettingsActivity.newIntent(this));
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	static class TasksPagerAdapter extends FragmentPagerAdapter {
		private static final int COUNT = 3;

		private final Fragment[]     fragments;
		private final CharSequence[] titles;

		public TasksPagerAdapter(Context context, FragmentManager fm) {
			super(fm);
			titles = new CharSequence[]{
					context.getText(R.string.tasks_tabTitle_todo),
					context.getText(R.string.tasks_tabTitle_doing),
					context.getText(R.string.tasks_tabTitle_done),
			};

			fragments = new Fragment[] {
					TasksFragment.newInstance(),
					TasksFragment.newInstance(),
					TasksFragment.newInstance(),
			};

			if (fragments.length != COUNT) {
				throw new IllegalArgumentException(String.format(Locale.ENGLISH, "There must be EXACTLY %d fragments", COUNT));
			}
			if (titles.length != COUNT) {
				throw new IllegalArgumentException(String.format(Locale.ENGLISH, "There must be EXACTLY %d fragment titles", COUNT));
			}
		}

		@Override
		public int getCount() {
			return COUNT;
		}

		@Override
		public Fragment getItem(final int position) {
			return fragments[position];
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}
	}
}
