package info.nivaldobondanca.trellodoro.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.Objects;

import info.nivaldobondanca.trellodoro.MockData;
import info.nivaldobondanca.trellodoro.R;
import info.nivaldobondanca.trellodoro.databinding.SettingsActivityBinding;
import info.nivaldobondanca.trellodoro.model.TrelloBoard;
import info.nivaldobondanca.trellodoro.model.TrelloList;

/**
 * @author Nivaldo Bondança
 */
public class SettingsActivity extends AppCompatActivity {
	public static Intent newIntent(Context context) {
		return new Intent(context, SettingsActivity.class);
	}


	private SettingsActivityBinding binding;
	private TrelloBoardsAdapter     boardsAdapter;

	private final String[] selectedListIds = new String[3];

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.settings_activity);

		setSupportActionBar(binding.toolbar);

		final TrelloListsAdapter todoAdapter = new TrelloListsAdapter(this);
		final TrelloListsAdapter doingAdapter = new TrelloListsAdapter(this);
		final TrelloListsAdapter doneAdapter = new TrelloListsAdapter(this);
		boardsAdapter = new TrelloBoardsAdapter(this);

		final AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
				updateSelection();
			}

			@Override public void onNothingSelected(AdapterView<?> adapterView) {}
		};

		binding.settingsTodo.setAdapter(todoAdapter);
		binding.settingsTodo.setOnItemSelectedListener(onItemSelectedListener);

		binding.settingsDoing.setAdapter(doingAdapter);
		binding.settingsDoing.setOnItemSelectedListener(onItemSelectedListener);

		binding.settingsDone.setAdapter(doneAdapter);
		binding.settingsDone.setOnItemSelectedListener(onItemSelectedListener);

		binding.settingsBoard.setAdapter(boardsAdapter);
		binding.settingsBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
				final TrelloBoard board = boardsAdapter.getItem(position);
				todoAdapter.setData(board.lists());
				doingAdapter.setData(board.lists());
				doneAdapter.setData(board.lists());

				updateSelection();
			}

			@Override public void onNothingSelected(AdapterView<?> adapterView) {}
		});

		// TODO replace this mock data
		boardsAdapter.setData(MockData.trelloBoards());
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
				if (isSelectionValid()) {
					// TODO
					Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
					finish();
				}
				else {
					Snackbar.make(binding.getRoot(), getText(R.string.settings_invalidSelection),
							Snackbar.LENGTH_SHORT).show();
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateSelection() {
		final TrelloList todoList = (TrelloList) binding.settingsTodo.getSelectedItem();
		final TrelloList doingList = (TrelloList) binding.settingsDoing.getSelectedItem();
		final TrelloList doneList = (TrelloList) binding.settingsDone.getSelectedItem();

		selectedListIds[0] = todoList != null ? todoList.id() : null;
		selectedListIds[1] =  doingList != null ? doingList.id() : null;
		selectedListIds[2] = doneList != null ? doneList.id() : null;
	}

	public boolean isSelectionValid() {
		return selectedListIds[0] != null &&
				selectedListIds[1] != null &&
				selectedListIds[2] != null &&
				!Objects.equals(selectedListIds[0], selectedListIds[1]) &&
				!Objects.equals(selectedListIds[1], selectedListIds[2]) &&
				!Objects.equals(selectedListIds[2], selectedListIds[0]);
	}
}
