package info.nivaldobondanca.trellodoro.ui.settings;

import android.content.Context;

import java.util.Collections;
import java.util.List;

import info.nivaldobondanca.trellodoro.model.TrelloList;

/**
 * @author Nivaldo Bondança
 */
class TrelloListsAdapter extends SimpleTextAdapter {
	private List<TrelloList> lists = Collections.emptyList();

	public TrelloListsAdapter(Context context) {
		super(context);
	}

	public void setData(List<TrelloList> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public TrelloList getItem(int position) {
		return lists.get(position);
	}

	@Override
	public CharSequence getLabel(int position) {
		return getItem(position).name();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
