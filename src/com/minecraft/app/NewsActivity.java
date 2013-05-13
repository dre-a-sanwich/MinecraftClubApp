package com.minecraft.app;

import java.io.IOException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author Lucas Stuyvesant
 * @author Andrea Sanchez 
 * Contains information and methods for the UI
 */
public class NewsActivity extends MainActivity {

	private String result = null;

	/**
	 * Creates the activity and sets the content view
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
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

		case R.id.meetings: // if meetings button was clicked

			v.setAlpha(1); // brighten last clicked button dim others
			findViewById(R.id.events).setAlpha((float) 0.25);
			findViewById(R.id.other).setAlpha((float) 0.25);
			
			if (connec != null && (connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) ||(connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)){
				// internet is connected		
				// creates thread for website scrape
				Thread thread = new Thread() {
	
					public void run() {
						try {
							result = WebMiner.getNews();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				result = null;
				thread.start();
				while (result == null) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				display.setText(result);
	
				if (thread != null) {
					thread.interrupt();
					thread = null;
				}
			}else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||  connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED ) {             
		        // Internet is not connected.        
		        Toast.makeText(getApplicationContext(), "You must be connected to the internet", Toast.LENGTH_LONG).show();
		    }

			break;
		case R.id.events: // if events button was clicked

			v.setAlpha(1); // brighten last clicked button dim others
			findViewById(R.id.meetings).setAlpha((float) 0.25);
			findViewById(R.id.other).setAlpha((float) 0.25);
			
			if (connec != null && (connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) ||(connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)){
				// internet is connected		
				// creates thread for website scrape
				Thread eventThread = new Thread() {
	
					public void run() {
						try {
							result = WebMiner.getEvents();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				result = null;
				eventThread.start();
				while (result == null) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				display.setText(result);
	
				if (eventThread != null) {
					eventThread.interrupt();
					eventThread = null;
				}
			}else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||  connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED ) {             
		        // Internet is not connected.        
		        Toast.makeText(getApplicationContext(), "You must be connected to the internet", Toast.LENGTH_LONG).show();
		    }

			break;
		case R.id.other: // if other button was clicked

			v.setAlpha(1); // brighten last clicked button dim others
			findViewById(R.id.events).setAlpha((float) 0.25);
			findViewById(R.id.meetings).setAlpha((float) 0.25);
			
			if (connec != null && (connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) ||(connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)){
				// internet is connected		
				// creates thread for website scrape
				Thread otherThread = new Thread() {
	
					public void run() {
						try {
							result = WebMiner.getOther();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				result = null;
				otherThread.start();
				while (result == null) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				display.setText(result);
	
				if (otherThread != null) {
					otherThread.interrupt();
					otherThread = null;
				}
			}else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||  connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED ) {             
		        // Internet is not connected.        
		        Toast.makeText(getApplicationContext(), "You must be connected to the internet", Toast.LENGTH_LONG).show();
		    }

			break;
		case R.id.back: // if back button was clicked
			finish();
			break;
		}
	}
}