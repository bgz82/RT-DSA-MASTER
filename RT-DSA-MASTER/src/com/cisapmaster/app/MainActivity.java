package com.cisapmaster.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class MainActivity extends Activity {

	Intent intent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	public void listEvents(View view) {
		intent = new Intent(this, ListEvent.class);
		startActivity(intent);

	}
	
	public void addEvents(View view) {
		intent = new Intent(this, AddEvent.class);
		startActivity(intent);

	}
}