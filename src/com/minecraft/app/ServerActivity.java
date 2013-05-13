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
public class ServerActivity extends MainActivity {

	private String result;

	/**
	 * Creates the activity and sets the content view
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server);
	}

	/**
	 * Handles button clicks for the UI
	 * 
	 * @param v
	 *            view that was clicked
	 */
	public void handleButton(View v) {

		int id = v.getId();

		TextView ip = (TextView) findViewById(R.id.ip_display); // get text
																// views in this
																// activity
		TextView world = (TextView) findViewById(R.id.world_display);
		TextView etiquette = (TextView) findViewById(R.id.etiquette_display);
		TextView plugin = (TextView) findViewById(R.id.plugin_display);
		TextView rank = (TextView) findViewById(R.id.rank_display);
		
		ConnectivityManager connec = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); //for internet check

		switch (id) {

		case R.id.ip: // if ip button was clicked

			world.setText(""); // erase other text fields
			etiquette.setText("");
			plugin.setText("");
			rank.setText("");

			findViewById(id).setAlpha(1); // brighten last clicked button dim
											// others
			findViewById(R.id.etiquette).setAlpha((float) 0.25);
			findViewById(R.id.worlds).setAlpha((float) 0.25);
			findViewById(R.id.ranks).setAlpha((float) 0.25);
			findViewById(R.id.plugins).setAlpha((float) 0.25);

			ip.setVisibility(View.VISIBLE); // makes current button-selected
											// view visible
			world.setVisibility(View.GONE);
			etiquette.setVisibility(View.GONE);
			plugin.setVisibility(View.GONE);
			rank.setVisibility(View.GONE);

			ip.setText("199.229.251.184");

			break;
		case R.id.worlds: // if worlds button was clicked

			ip.setText(""); // erase other text fields
			etiquette.setText("");
			plugin.setText("");
			rank.setText("");

			findViewById(id).setAlpha(1); // brighten last clicked button dim
											// others
			findViewById(R.id.etiquette).setAlpha((float) 0.25);
			findViewById(R.id.ip).setAlpha((float) 0.25);
			findViewById(R.id.ranks).setAlpha((float) 0.25);
			findViewById(R.id.plugins).setAlpha((float) 0.25);

			ip.setVisibility(View.GONE); // makes current button-selected view
											// visible
			world.setVisibility(View.VISIBLE);
			etiquette.setVisibility(View.GONE);
			plugin.setVisibility(View.GONE);
			rank.setVisibility(View.GONE);
			
			// check if internet is connect
			if (connec != null && (connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) ||(connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)){
				// internet is connected
				// creates thread for website scrape
				Thread worldThread = new Thread() {
	
					public void run() {
						try {
							result = WebMiner.getWorlds();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				result = null;
				worldThread.start();
				while (result == null) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
	
				world.setText(result);
				if (worldThread != null) {
					worldThread.interrupt();
					worldThread = null;
				}
			}else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||  connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED ) {             
		        // Internet is not connected.        
		        Toast.makeText(getApplicationContext(), "You must be connected to the internet", Toast.LENGTH_LONG).show();
		    } 

			break;
		case R.id.etiquette: // if etiquette button was clicked

			ip.setText(""); // erase other text fields
			world.setText("");
			plugin.setText("");
			rank.setText("");

			findViewById(id).setAlpha(1); // brighten last clicked button dim
											// others
			findViewById(R.id.ip).setAlpha((float) 0.25);
			findViewById(R.id.worlds).setAlpha((float) 0.25);
			findViewById(R.id.ranks).setAlpha((float) 0.25);
			findViewById(R.id.plugins).setAlpha((float) 0.25);

			ip.setVisibility(View.GONE); // makes current button-selected view
											// visible
			world.setVisibility(View.GONE);
			etiquette.setVisibility(View.VISIBLE);
			plugin.setVisibility(View.GONE);
			rank.setVisibility(View.GONE);

			etiquette.setText("Be nice and don't grief!");

			break;
		case R.id.plugins: // if plugins button was clicked

			ip.setText(""); // erase other text fields
			etiquette.setText("");
			world.setText("");
			rank.setText("");

			findViewById(id).setAlpha(1); // brighten last clicked button dim
											// others
			findViewById(R.id.etiquette).setAlpha((float) 0.25);
			findViewById(R.id.worlds).setAlpha((float) 0.25);
			findViewById(R.id.ranks).setAlpha((float) 0.25);
			findViewById(R.id.ip).setAlpha((float) 0.25);

			ip.setVisibility(View.GONE); // makes current button-selected view
											// visible
			world.setVisibility(View.GONE);
			etiquette.setVisibility(View.GONE);
			plugin.setVisibility(View.VISIBLE);
			rank.setVisibility(View.GONE);
			
			// check if internet is connect
			if (connec != null && (connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) ||(connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)){
				// internet is connected			
				// creates thread for website scrape
				Thread pluginThread = new Thread() {
		
					public void run() {
						try {
							result = WebMiner.getPlugins();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				result = null;
				pluginThread.start();
				while (result == null) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		
				plugin.setText(result);
				if (pluginThread != null) {
					pluginThread.interrupt();
					pluginThread = null;
				}
			}else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||  connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED ) {             
		        // Internet is not connected.        
		        Toast.makeText(getApplicationContext(), "You must be connected to the internet", Toast.LENGTH_LONG).show();
		    }

			break;
		case R.id.ranks: // if ranks button was clicked

			ip.setText(""); // erase other text fields
			etiquette.setText("");
			plugin.setText("");
			world.setText("");

			findViewById(id).setAlpha(1); // brighten last clicked button dim
											// others
			findViewById(R.id.etiquette).setAlpha((float) 0.25);
			findViewById(R.id.worlds).setAlpha((float) 0.25);
			findViewById(R.id.ip).setAlpha((float) 0.25);
			findViewById(R.id.plugins).setAlpha((float) 0.25);

			ip.setVisibility(View.GONE); // makes current button-selected view
											// visible
			world.setVisibility(View.GONE);
			etiquette.setVisibility(View.GONE);
			plugin.setVisibility(View.GONE);
			rank.setVisibility(View.VISIBLE);

			rank.setText("Ranks not yet implemented in website");

			break;
		case R.id.back: // if back button was clicked
			finish();
			break;
		}
	}

}
