package info.nivaldobondanca.trellodoro;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import info.nivaldobondanca.trellodoro.databinding.SettingsActivityBinding;

/**
 * @author Nivaldo Bondança
 */
public class SettingsActivity extends AppCompatActivity {

	private TrelloBoardsAdapter boardsAdapter;
	private View viewRoot;

	public static Intent newIntent(Context context) {
		return new Intent(context, SettingsActivity.class);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final SettingsActivityBinding binding
				= DataBindingUtil.setContentView(this, R.layout.settings_activity);

		setSupportActionBar(binding.toolbar);
		viewRoot = binding.getRoot();

		final TrelloListsAdapter todoAdapter = new TrelloListsAdapter(this);
		final TrelloListsAdapter doingAdapter = new TrelloListsAdapter(this);
		final TrelloListsAdapter doneAdapter = new TrelloListsAdapter(this);
		boardsAdapter = new TrelloBoardsAdapter(this, todoAdapter, doingAdapter, doneAdapter);

		final AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
				updateSelection();
			}

			@Override public void onNothingSelected(AdapterView<?> adapterView) {}

			public AdapterView.OnItemSelectedListener updateSelection() {
				final long todoListId = binding.settingsTodo.getSelectedItemId();
				final long doingListId = binding.settingsDoing.getSelectedItemId();
				final long doneListId = binding.settingsDone.getSelectedItemId();

				boardsAdapter.updateListSelection(todoListId, doingListId, doneListId);
				return this;
			}
		}.updateSelection();

		binding.settingsTodo.setAdapter(todoAdapter);
		binding.settingsTodo.setOnItemSelectedListener(onItemSelectedListener);

		binding.settingsDoing.setAdapter(doingAdapter);
		binding.settingsDoing.setOnItemSelectedListener(onItemSelectedListener);

		binding.settingsDone.setAdapter(doneAdapter);
		binding.settingsDone.setOnItemSelectedListener(onItemSelectedListener);

		binding.settingsBoard.setAdapter(boardsAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.settings_menu_save:
				if (boardsAdapter.isSelectionValid()) {
					// TODO
					Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
					finish();
				}
				else {
					Snackbar.make(viewRoot, getText(R.string.settings_invalidSelection), Snackbar.LENGTH_SHORT).show();
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}


	static abstract class SimpleTextAdapter extends BaseAdapter {

		private final LayoutInflater inflater;

		public SimpleTextAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public abstract CharSequence getLabel(int position);

		@Override
		public View getView(int position, View recycledView, ViewGroup parent) {
			if (recycledView == null) {
				recycledView = inflater.inflate(android.R.layout.select_dialog_item, parent, false);
			}

			((TextView) recycledView).setText(getLabel(position));

			return recycledView;
		}
	}

	static class TrelloListsAdapter extends SimpleTextAdapter {
		public TrelloListsAdapter(Context context) {
			super(context);
		}

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public CharSequence getItem(int position) {
			// XXX Mock
			return "List " + position;
		}

		@Override
		public CharSequence getLabel(int position) {
			return getItem(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	static class TrelloBoardsAdapter extends SimpleTextAdapter {
		private final TrelloListsAdapter todoAdapter;
		private final TrelloListsAdapter doingAdapter;
		private final TrelloListsAdapter doneAdapter;

		private final long[] selectedIds = new long[3];

		public TrelloBoardsAdapter(Context context,
		                           TrelloListsAdapter todoAdapter,
		                           TrelloListsAdapter doingAdapter,
		                           TrelloListsAdapter doneAdapter) {
			super(context);
			this.todoAdapter = todoAdapter;
			this.doingAdapter = doingAdapter;
			this.doneAdapter = doneAdapter;
		}

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public CharSequence getItem(int position) {
			// XXX Mock
			return "Board " + position;
		}

		@Override
		public CharSequence getLabel(int position) {
			return getItem(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public void updateListSelection(long todoListId, long doingListId, long doneListId) {
			selectedIds[0] = todoListId;
			selectedIds[1] = doingListId;
			selectedIds[2] = doneListId;
		}

		public boolean isSelectionValid() {
			return selectedIds[0] != selectedIds[1] &&
					selectedIds[1] != selectedIds[2] &&
					selectedIds[2] != selectedIds[0];
		}
	}
}
