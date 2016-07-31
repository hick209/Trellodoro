package info.nivaldobondanca.trellodoro.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import info.nivaldobondanca.trellodoro.R;

public class SplashActivity extends AppCompatActivity {

	private Scene connectScene;
	private Scene connectingScene;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.SplashTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);

		final ViewGroup container = (ViewGroup) findViewById(R.id.splash_sceneContainer);
		connectScene = Scene.getSceneForLayout(container, R.layout.splash_scene_connect, this);
		connectingScene = Scene.getSceneForLayout(container, R.layout.splash_scene_connecting, this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkTrelloConnection();
	}

	private void checkTrelloConnection() {
		// Check for Trello connection

		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... voids) {
				return isConnectedToTrello();
			}

			@Override
			protected void onPostExecute(Boolean connected) {
				super.onPostExecute(connected);
				if (connected) {
					// Move to the Task Activity
					final Intent intent = TasksActivity.newIntent(SplashActivity.this);
					startActivity(intent);
				}
				else {
					// Present the connect option
					TransitionManager.go(connectScene);
				}
			}
		}.execute();
	}

	private boolean isConnectedToTrello() {
		// TODO implement this
		final Random random = new Random();
		try {
			Thread.sleep(random.nextInt(1500) + 500);
		}
		catch (InterruptedException ignored) {
		}

		return random.nextBoolean();
	}

	public void connect(View v) {
		TransitionManager.go(connectingScene);

		// TODO Try to connect

		checkTrelloConnection();
	}
}
