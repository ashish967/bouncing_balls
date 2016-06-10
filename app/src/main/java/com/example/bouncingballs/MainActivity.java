package com.example.bouncingballs;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	
		if (item.getItemId() == R.id.pause) {
			BallObjects bo = (BallObjects) findViewById(R.id.ball);

			if (bo.isRunning()) {
				item.setIcon(android.R.drawable.ic_media_play);
				Log.d("Ashish", "Pausing....");
				bo.stop();
			}
			
			else{
				
				item.setIcon(android.R.drawable.ic_media_pause);
				Log.d("Ashish", "starting....");
				bo.start();
				
			}
		}

		return true;
	}
}
