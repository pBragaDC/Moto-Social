package pc.motorcycle.androidapp.AndroidActivites;

import java.util.ArrayList;

import pc.motorcycle.androidapp.R;
import pc.motorcycle.androidapp.R.id;
import pc.motorcycle.androidapp.R.layout;
import pc.motorcycle.androidapp.R.menu;
import pc.motorcycle.androidapp.ServerActivities.AppData;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class RidesActivity extends ListActivity 
{
			//variable delarations
		    private ProgressDialog pDialog;
		    private static final String READ_COMMENTS_URL = "http://rideme.site88.net/rides.php";
		    //declaration of JSON IDS
		    private static final String TAG_SUCCESS = "success";
		    private static final String TAG_EVENTS = "events";
		    private static final String TAG_ERID = "ER_id";
		    private static final String TAG_TITLE = "Title";
		    private static final String TAG_DATE = "Date";
		    private static final String TAG_HOUR = "Hour";
		    private static final String TAG_TIME = "Time";
		    private static final String TAG_LOCNUMB = "LocNumb";
		    private static final String TAG_STREET = "Street";
		    private static final String TAG_CITY = "City";
		    private static final String TAG_ZIPCODE = "ZipCode";
		    private static final String TAG_STATE = "State";
		    private static final String TAG_DESCRIPTION = "Description";
		    
		    //button delcarations
		    private Button btnEvent;
			private Button btnSettings;
			private Button btnRides;
		
			//rides array
		    private JSONArray mRides = null;
		    //arraylist to manage events
		    private ArrayList<HashMap<String, String>> mRidesList;
		    
		    @Override
		    protected void onCreate(Bundle savedInstanceState) 
		    {
		    	//set layout and title
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.event_layout);  
		        setTitle("Rides");
		        
		        //declare Rides button and set it unclickable
		        //since we are in that page
		        btnRides = (Button) findViewById(R.id.btnRide);
				btnRides.setClickable(false);
				 
				//set view for events button and wait for user click
				btnEvent = (Button)findViewById(R.id.btnEvents);
				btnEvent.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						//go to events if clicked
						Intent i = new Intent(RidesActivity.this, EventActivity.class);
						startActivity(i);

					}
				});
				
				
				//set settings button view
				btnSettings = (Button)findViewById(R.id.btnSettings);
				//wait for settings button click
				btnSettings.setOnClickListener(new View.OnClickListener() 
				{
					public void onClick(View v) 
					{
						// go to settings page
						Intent i = new Intent(RidesActivity.this, Settings.class);
						startActivity(i);

					}
				});
		    }
		    
		  //set actionbar
		    public boolean onCreateOptionsMenu(Menu menu)
		    {
			    android.view.MenuInflater inflater = getMenuInflater();
			    inflater.inflate(R.menu.actionbar, menu);
			    return true;
			}
		    
		    //perform action if create is clicked on action bar
			@Override
			public boolean onOptionsItemSelected(MenuItem item){
			    Intent myIntent = new Intent(getApplicationContext(),CreateActivity.class);
			    startActivityForResult(myIntent, 0);
			    return true;
			}
		
		
		    @Override
		    protected void onResume() {
		        // TODO Auto-generated method stub
		        super.onResume();
		        //loading the comments via AsyncTask
		        new LoadRides().execute();
		    }
		
		  //method to call create
		    public void addER(View v)
		    {
		        Intent i = new Intent(RidesActivity.this, CreateActivity.class);
		        startActivity(i);
		    }
		    
		  //method to update json data in rides
		    public void updateJSONdata() 
		    {
		    	//declare variables
		    	mRidesList = new ArrayList<HashMap<String, String>>();
		    	JSONParser jParser = new JSONParser();
		    	
		    	//pass the JSON url so that the parse can extract the information
		    	JSONObject json = jParser.getJSONFromUrl(READ_COMMENTS_URL);
		    	
		    	        try {
		    	        	//get JSON array
		    	            mRides = json.getJSONArray(TAG_EVENTS);
		    	            //go through all events and put json tags in array
		    	            for (int i = 0; i < mRides.length(); i++) 
		    	            {
		    	                JSONObject c = mRides.getJSONObject(i);
		    	                
		    	                //gets the content of each tag
		    	                String title = c.getString(TAG_TITLE);
		    	                String date = c.getString(TAG_DATE);
		    	                String hour = c.getString(TAG_HOUR);
		    	                String time = c.getString(TAG_TIME);
		    	                String locNumb = c.getString(TAG_LOCNUMB);
		    	                String street = c.getString(TAG_STREET);
		    	                String city = c.getString(TAG_CITY);
		    	                String zipCode = c.getString(TAG_ZIPCODE);
		    	                String state = c.getString(TAG_STATE);
		    	                String description = c.getString(TAG_DESCRIPTION);
		    	                
		    	                //create new hashmap and put strings in map
		    	                HashMap<String, String> map = new HashMap<String, String>();
		    	                map.put(TAG_TITLE, title);
		    	                map.put(TAG_DATE, date);
		    	                map.put(TAG_HOUR, hour);
		    	                map.put(TAG_TIME, time);
		    	                map.put(TAG_LOCNUMB, locNumb);
		    	                map.put(TAG_STREET, street);
		    	                map.put(TAG_CITY, city);
		    	                map.put(TAG_ZIPCODE, zipCode);
		    	                map.put(TAG_STATE, state);
		    	                map.put(TAG_DESCRIPTION, description);
		    	                
		    	                //add map to arraylist
		    	                mRidesList.add(map);
		    	            }
		    	            //catch exceptions
		    	        } catch (JSONException e) {
		    	            e.printStackTrace();
		    	        }
		    }
		    
		    //insert data in list
		    private void updateList() 
		    {
		    	//populate listView adapter, which will populate listView UI
		    	ListAdapter adapter = new SimpleAdapter(this, mRidesList,
		    			                R.layout.single_post, new String[] { TAG_TITLE,
		    			                        TAG_DATE,TAG_HOUR,TAG_TIME,TAG_LOCNUMB,TAG_STREET,TAG_CITY,
		    			                        TAG_ZIPCODE,TAG_STATE,TAG_DESCRIPTION }, new int[] { R.id.title,
		    			R.id.date, R.id.hour, R.id.time, R.id.locNumb, R.id.street, R.id.city, R.id.zipcode
		    			,R.id.state,R.id.description});
		    	
		    	//set the adapter to the one we populated
		        setListAdapter(adapter);
		        
		        ListView lv = getListView(); 
		        //wait for click of a item
		        lv.setOnItemClickListener(new OnItemClickListener() 
		        {
		        	@Override
		            public void onItemClick(AdapterView<?> parent, View view,
		                    int position, long id) 
		        	{
		        		//get data of item clicked to populate description view
		        		Intent i = new Intent(getApplicationContext(), DetailViewActivity.class);
	        			AppData.title = ((TextView) view.findViewById(R.id.title)).getText().toString();    			        			
	        			AppData.date = ((TextView) view.findViewById(R.id.date)).getText().toString();
	        			AppData.time = ((TextView) view.findViewById(R.id.hour)).getText().toString();
	        			AppData.locnumb = ((TextView) view.findViewById(R.id.locNumb)).getText().toString();
	        			AppData.street = ((TextView) view.findViewById(R.id.street)).getText().toString();
	        			AppData.city = ((TextView) view.findViewById(R.id.city)).getText().toString();
	        			AppData.zipcode = ((TextView) view.findViewById(R.id.zipcode)).getText().toString();
	        			AppData.state = ((TextView) view.findViewById(R.id.state)).getText().toString();
	        			AppData.description = ((TextView) view.findViewById(R.id.description)).getText().toString();
	        			
	        			startActivity(i);
		            }
		        });	
}
		
		  //method to load Rides
		    public class LoadRides extends AsyncTask<Void, Void, Boolean> 
		    {
		        @Override
		        protected void onPreExecute() 
		        {
		        	//load calls method to load rides
		            super.onPreExecute();
		            pDialog = new ProgressDialog(RidesActivity.this);
		            pDialog.setMessage("Loading Rides...");//loading message
		            pDialog.setIndeterminate(false);
		            pDialog.setCancelable(true);
		            pDialog.show();
		        }
		
		        @Override
		        protected Boolean doInBackground(Void... arg0) 
		        {
		            //update list in background
		            updateJSONdata();
		            return null;
		        }
		
		        @Override
		        protected void onPostExecute(Boolean result) 
		        {
		        	//update list
		            super.onPostExecute(result);
		            pDialog.dismiss();
		            updateList();
		        }
		    }
		}

