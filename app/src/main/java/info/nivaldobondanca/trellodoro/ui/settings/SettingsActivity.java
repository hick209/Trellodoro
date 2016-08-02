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

import java.util.Arrays;
import java.util.Collections;

import info.nivaldobondanca.trellodoro.R;
import info.nivaldobondanca.trellodoro.databinding.SettingsActivityBinding;
import info.nivaldobondanca.trellodoro.model.TrelloCard;
import info.nivaldobondanca.trellodoro.model.TrelloList;
import info.nivaldobondanca.trellodoro.model.factory.BoardFactory;
import info.nivaldobondanca.trellodoro.model.factory.ListFactory;

/**
 * @author Nivaldo Bondança
 */
public class SettingsActivity extends AppCompatActivity {
	public static Intent newIntent(Context context) {
		return new Intent(context, SettingsActivity.class);
	}


	private View viewRoot;
	private TrelloBoardsAdapter boardsAdapter;

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
	protected void onResume() {
		super.onResume();
		// TODO replace this mock data
		boardsAdapter.setData(Arrays.asList(
				BoardFactory.create("209L", "Hello World!",
						Arrays.asList(
								ListFactory.create("123to7do", "Scratch", Collections.<TrelloCard>emptyList()),
								ListFactory.create("123doing", "Writing", Collections.<TrelloCard>emptyList()),
								ListFactory.create("123done1", "Published", Collections.<TrelloCard>emptyList())
						)),
				BoardFactory.create("1001L", "Empty board", Collections.<TrelloList>emptyList())
		));
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


}
