package info.nivaldobondanca.trellodoro;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

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

	public void connect(View v) {
		TransitionManager.go(connectingScene);

		// TODO this is only a mock
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				TransitionManager.go(connectScene);
			}
		}, 3000);
	}
}
