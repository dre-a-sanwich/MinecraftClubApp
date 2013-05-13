package com.minecraft.app;

import java.io.IOException;

import com.minecraft.app.R;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Lucas Stuyvesant
 * @author Andrea Sanchez 
 * Contains information and methods for the UI
 */
public class AboutActivity extends MainActivity {

	private String result;

	/**
	 * Creates the activity and sets the content view
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	/**
	 * Handles button clicks for the UI
	 * 
	 * @param v
	 *            view that was clicked
	 */
	public void handleButton(View v) {

		int id = v.getId();
		TextView display = (TextView) findViewById(R.id.display);
		ConnectivityManager connec = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); //for internet check

		switch (id) {

		case R.id.constitution: // if constitution button was clicked

			v.setAlpha(1);
			findViewById(R.id.officials).setAlpha((float) 0.25);
			findViewById(R.id.website).setAlpha((float) 0.25);
			findViewById(R.id.contact).setAlpha((float) 0.25);

			display.setText("The Minecraft Club Constitution");

			break;
		case R.id.officials: // if officials button was clicked

			v.setAlpha(1);
			findViewById(R.id.constitution).setAlpha((float) 0.25);
			findViewById(R.id.website).setAlpha((float) 0.25);
			findViewById(R.id.contact).setAlpha((float) 0.25);

			display.setText("President: Matthew Broomfield");

			break;
		case R.id.website: // if website button was clicked

			v.setAlpha(1); // brighten last clicked button dim others
			findViewById(R.id.constitution).setAlpha((float) 0.25);
			findViewById(R.id.officials).setAlpha((float) 0.25);
			findViewById(R.id.contact).setAlpha((float) 0.25);

			// display link
			display.setMovementMethod(LinkMovementMethod.getInstance());
			String url = "<a href='#'>https://sites.google.com/site/nmtminecraftclub/home</a>";
			display.setText(Html.fromHtml(url));

			break;
		case R.id.contact: // if contact button was clicked

			v.setAlpha(1); // brighten last clicked button dim others
			findViewById(R.id.constitution).setAlpha((float) 0.25);
			findViewById(R.id.officials).setAlpha((float) 0.25);
			findViewById(R.id.website).setAlpha((float) 0.25);
			
			if (connec != null && (connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) ||(connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)){
				// internet is connected		
				// creates thread for website scrape
				Thread contactThread = new Thread() {
	
					public void run() {
						try {
							result = WebMiner.getContactInfo();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				result = null;
				contactThread.start();
				while (result == null) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				display.setText(result);
	
				if (contactThread != null) {
					contactThread.interrupt();
					contactThread = null;
				}
			}else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||  connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED ) {             
		        // Internet is not connected.        
		        Toast.makeText(getApplicationContext(), "You must be connected to the internet", Toast.LENGTH_LONG).show();
		    }

			break;
		case R.id.display: // if text view was clicked

			// on click open browser and go to website
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(display.getText().toString()));
			startActivity(browserIntent);

			break;
		case R.id.back: // if back button was clicked
			finish();
			break;
		}
	}

}
