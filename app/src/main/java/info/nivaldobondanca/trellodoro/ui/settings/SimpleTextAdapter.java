package info.nivaldobondanca.trellodoro.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Nivaldo Bondança
 */
abstract class SimpleTextAdapter extends BaseAdapter {
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
