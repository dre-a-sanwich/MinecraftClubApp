package com.minecraft.app;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.minecraft.app.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * @author Lucas Stuyvesant
 * @author Andrea Sanchez 
 * Contains information and methods for the UI, and 
 * Manages entering and retrieving data from data base
 */
public class BookActivity extends MainActivity {

    protected DataHandler dh;
    protected List<Location> lpList = new ArrayList<Location>();
    protected ListView list;
    protected TextView delItem = null;
    protected TextView prevClick = null;
    protected boolean resumeHasRun = false;
    
    /**
	 * Creates the activity and sets the content view
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.dh = new DataHandler(this);
		setContentView(R.layout.activity_book);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (!resumeHasRun){
			resumeHasRun = true;
			return;
		}
		this.dh = new DataHandler(this);
	}

	/**
	 * Handles button clicks for the UI
	 * @param v view that was clicked
	 */
	public void handleButton(View v) {

		int id = v.getId();
		
		EditText name = (EditText)findViewById(R.id.name_text);  //get text views in this activity
		EditText x = (EditText)findViewById(R.id.x_text);
		EditText y = (EditText)findViewById(R.id.y_text);
		EditText z = (EditText)findViewById(R.id.z_text);

		switch (id) {

		case R.id.new_entry:	// if new_entry button was clicked
			
			v.setAlpha(1); // brighten last clicked button dim others
			findViewById(R.id.load).setAlpha((float) 0.50);
			findViewById(R.id.relativeLayout1).setVisibility(View.VISIBLE);
			findViewById(R.id.list_layout).setVisibility(View.GONE);
			
			break;
		case R.id.delete:	// if delete button was clicked
			TextView del;
			
			if((del = getDelItem()) != null){
				String s = (String) del.getText();
				try {
					dh.deleteItem(URLEncoder.encode(s.substring(0, s.indexOf('\n')), "UTF-8"));	//delete encoded row name
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}	
			}
			
			delItem = null;
			v = findViewById(R.id.load);	//refresh list
			
		case R.id.load:	// if load button was clicked
			
			v.setAlpha(1); // brighten last clicked button dim others
			findViewById(R.id.new_entry).setAlpha((float) 0.50);
			findViewById(R.id.relativeLayout1).setVisibility(View.GONE);
			findViewById(R.id.list_layout).setVisibility(View.VISIBLE);
			
			lpList = dh.getLocations();
			list = (ListView) findViewById(R.id.list);
			list.setAdapter(new BaseAdapter() {	//adapter for list of Locations
				public int getCount() {
					return lpList.size();
				}

				public Object getItem(int position) {
					return lpList.get(position);
				}

				@Override
				public long getItemId(int position) {

					return position;
				}

				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View view = inflater.inflate(R.layout.list_row, null);
					TextView textView = (TextView) view.findViewById(R.id.textLocation);
					try {	//decode database text to print
						textView.setText(URLDecoder.decode(lpList.get(position).toString(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					return view;
				}
			});
			
			break;
		case R.id.back:	// if back button was clicked
			finish();
			break;
		case R.id.save:	// if save button was clicked
			
			if(x.getText().toString().isEmpty() || y.getText().toString().isEmpty() || z.getText().toString().isEmpty() || name.getText().toString().isEmpty()){
				TextView dbText = (TextView) findViewById(R.id.db);
				dbText.setText("Must fill all fields");
				break;
			}
			
			SaveToDBTask saveToDB = new SaveToDBTask();
			String s = name.getText().toString();
			
			try {	//encode for database storage
				s = URLEncoder.encode(s, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			saveToDB.execute(dh, s, Long.parseLong(x.getText().toString()), Long.parseLong(y.getText().toString()), Long.parseLong(z.getText().toString()));	//write Location info to database

			name.setText("");	//clear text views
			x.setText("");
			y.setText("");
			z.setText("");

			break;
		case R.id.clear:	// if clear button was clicked
			
			name.setText("");	//clear text views
			x.setText("");
			y.setText("");
			z.setText("");
			
			break;
		}
	}
	
	/**
	 * Keeps track of the most recently clicked item in the list view
	 * to delete it
	 * @param v item in list view
	 */
	public void setDelItem(View v){
		delItem = (TextView) v;
		if(prevClick != null){
			prevClick.setBackgroundColor(0x00000000);
		}
		v.setBackgroundColor(0x880000FF);
		prevClick = delItem;
	}
	
	/**
	 * @return last clicked item 
	 */
	public TextView getDelItem(){
		return delItem;
	}
	
	/**
	 * Closes database
	 */
	@Override
	public void onStop() {
		super.onStop();
		dh.close();
	}
	
	/**
	 * Contains methods for saving a row to the database in a background process
	 * @author Lucas Stuyvesant
	 * @author Andrea Sanchez
	 */
	protected class SaveToDBTask extends AsyncTask<Object, Void, String> {
		
		/**
		 * @return database message on success
		 */
		@Override
		protected String doInBackground(Object... params) {

			DataHandler dh = (DataHandler) params[0];
			String name = (String) params[1];
			Long x = (Long) params[2];
			Long y = (Long) params[3];
			Long z = (Long) params[4];

			dh.insertLocation(name, x, y, z);

			return "Saved \"" + name + "\" location";
		}

		/**
		 * The system calls this to perform work in the UI thread and delivers
		 * the result from doInBackground()
		 */
		protected void onPostExecute(String result) {
			TextView dbText = (TextView) findViewById(R.id.db);
			try {
				dbText.setText(URLDecoder.decode(result, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

	}
	
}