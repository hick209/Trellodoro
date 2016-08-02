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
	private List<TrelloBoard> boards = Collections.emptyList();

	public TrelloBoardsAdapter(Context context) {
		super(context);
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
}
