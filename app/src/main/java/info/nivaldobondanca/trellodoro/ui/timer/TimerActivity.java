package info.nivaldobondanca.trellodoro.ui.timer;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import info.nivaldobondanca.trellodoro.R;
import info.nivaldobondanca.trellodoro.databinding.TimerActivityBinding;
import info.nivaldobondanca.trellodoro.viewmodel.TimerViewModel;

/**
 * @author Nivaldo Bondança
 */
public class TimerActivity extends AppCompatActivity {
	public static Intent newIntent(Context context, CharSequence cardName) {
		return new Intent(context, TimerActivity.class)
				.putExtra("extra.CARD_NAME", cardName);
	}

	public static void startWithTransitionAnimation(Activity activity, CharSequence cardName, View cardView, View cardTitle) {
		final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity,
				new Pair<>(cardView, activity.getString(R.string.transition_card)),
				new Pair<>(cardTitle, activity.getString(R.string.transition_cardTitle)));
		activity.startActivity(newIntent(activity, cardName), options.toBundle());
	}


	private TimerReceiver  timerReceiver;
	private TimerViewModel timerViewModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		supportPostponeEnterTransition();
		super.onCreate(savedInstanceState);
		final TimerActivityBinding binding =
				DataBindingUtil.setContentView(this, R.layout.timer_activity);

		final CharSequence title = getIntent().getCharSequenceExtra("extra.CARD_NAME");
		setTitle(title);

		setupToolbar(binding);

		timerViewModel = new TimerViewModel(title, binding.fab);
		binding.setViewModel(timerViewModel);

		timerReceiver = new TimerReceiver(timerViewModel);

		bindService(TimerService.bind(this), timerViewModel, BIND_AUTO_CREATE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(timerViewModel);
	}

	@Override
	protected void onStart() {
		super.onStart();
		timerReceiver.register(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		timerReceiver.unregister(this);
	}


	private void setupToolbar(TimerActivityBinding binding) {
		setSupportActionBar(binding.toolbar);
		// Enable 'Up' navigation
		final ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		// Setup the transition name (for the transition animation)
		final View textViewTitle = getTextViewTitle(binding.toolbar);
		if (textViewTitle != null) {
			textViewTitle.setTransitionName(getString(R.string.transition_cardTitle));
		}

		supportStartPostponedEnterTransition();
	}

	private View getTextViewTitle(Toolbar toolbar) {
		final int childCount = toolbar.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View view = toolbar.getChildAt(i);
			if (view instanceof TextView) {
				return view;
			}
		}
		return null;
	}
}
