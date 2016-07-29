package info.nivaldobondanca.trellodoro;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import info.nivaldobondanca.trellodoro.databinding.SettingsActivityBinding;

/**
 * @author Nivaldo Bondança
 */
public class SettingsActivity extends AppCompatActivity {
	public static Intent newIntent(Context context) {
		return new Intent(context, SettingsActivity.class);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final SettingsActivityBinding binding
				= DataBindingUtil.setContentView(this, R.layout.settings_activity);

		setSupportActionBar(binding.toolbar);
		// TODO
	}
}
