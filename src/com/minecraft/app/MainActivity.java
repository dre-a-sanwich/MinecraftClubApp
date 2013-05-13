package com.minecraft.app;

import com.minecraft.app.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
/**
 * @author Lucas Stuyvesant
 * @author Andrea Sanchez 
 * Contains information and methods for the UI
 */
public class MainActivity extends Activity {

	/**
	 * Creates the activity and sets the content view
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
	}

	/**
	 * Handles button clicks for the UI
	 * 
	 * @param v
	 *            view that was clicked
	 */
	public void handleButton(View v) {

		int id = v.getId();

		switch (id) {

		case R.id.server: // start server activity
			startActivity(new Intent(this, ServerActivity.class));
			break;
		case R.id.news: // start news activity
			startActivity(new Intent(this, NewsActivity.class));
			break;
		case R.id.about: // start about activity
			startActivity(new Intent(this, AboutActivity.class));
			break;
		case R.id.book_and_quill: // start book and quill activity
			startActivity(new Intent(this, BookActivity.class));
			break;
		}
	}
}
