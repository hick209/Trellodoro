package info.nivaldobondanca.trellodoro.ui.settings;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import info.nivaldobondanca.trellodoro.model.TrelloBoard;

/**
 * @author Nivaldo Bondança
 */
class TrelloBoardsAdapter extends SimpleTextAdapter {
	private final TrelloListsAdapter todoAdapter;
	private final TrelloListsAdapter doingAdapter;
	private final TrelloListsAdapter doneAdapter;

	private final long[] selectedIds = new long[3];

	private List<TrelloBoard> boards = Collections.emptyList();

	public TrelloBoardsAdapter(Context context,
	                           TrelloListsAdapter todoAdapter,
	                           TrelloListsAdapter doingAdapter,
	                           TrelloListsAdapter doneAdapter) {
		super(context);
		this.todoAdapter = todoAdapter;
		this.doingAdapter = doingAdapter;
		this.doneAdapter = doneAdapter;
	}

	public void setData(@NonNull List<TrelloBoard> newData) {
		boards = newData;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return boards.size();
	}

	@Override
	public TrelloBoard getItem(int position) {
		return boards.get(position);
	}

	@Override
	public CharSequence getLabel(int position) {
		return getItem(position).name();
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).id().hashCode();
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
