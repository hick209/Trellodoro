package info.nivaldobondanca.trellodoro.ui.settings;

import android.content.Context;

/**
 * @author Nivaldo Bondança
 */
class TrelloListsAdapter extends SimpleTextAdapter {
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
