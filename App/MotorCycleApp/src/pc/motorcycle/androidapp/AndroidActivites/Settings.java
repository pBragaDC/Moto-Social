package pc.motorcycle.androidapp.AndroidActivites;

import java.util.ArrayList;

import pc.motorcycle.androidapp.About;
import pc.motorcycle.androidapp.FAQ;
import pc.motorcycle.androidapp.Help;
import pc.motorcycle.androidapp.R;
import pc.motorcycle.androidapp.R.id;
import pc.motorcycle.androidapp.R.layout;
import pc.motorcycle.androidapp.R.menu;
import pc.motorcycle.androidapp.Settme;
import pc.motorcycle.androidapp.ServerActivities.CompletedTasks;
import pc.motorcycle.androidapp.ServerActivities.HttpAsyncTask;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Settings extends Activity {
	
	//variable declarations
	ArrayList<String> info;
	String x;

	//gui component declaration
	private Button btnEvent;
	private Button btnSettings;
	private Button btnRides;
	private Button btnAbout;
	private Button btnFaq;
	private Button btnHelp;
	private Button btnOptions;
	private Button btnLogout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{

		//set layout and title
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setTitle("Settings");
		
		//set button objects to gui componenets
		btnEvent = (Button) findViewById(R.id.btnEvents);
		btnSettings = (Button)findViewById(R.id.btnSettings);
		btnRides = (Button)findViewById(R.id.btnRide);
		btnAbout = (Button)findViewById(R.id.btnAbout);
		btnFaq = (Button)findViewById(R.id.btnFaq);
		btnHelp = (Button)findViewById(R.id.btnHelp);
		btnOptions = (Button)findViewById(R.id.btnOptions);
		btnLogout = (Button)findViewById(R.id.btnLogout);
		
		/*
		 * WAIT FOR BUTTON CLICKS
		 */
		btnSettings.setClickable(false);//set settings click to false
		
		btnEvent.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//go to event on event button click
				Intent i = new Intent(Settings.this, EventActivity.class);
				startActivity(i);

			}
		});
		
		//wait for rides button click
		btnRides.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//go to rides page
				Intent i = new Intent(Settings.this, RidesActivity.class);
				startActivity(i);

			}
		});
		
		//wait for about button click and to go page
		btnAbout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(Settings.this, About.class);
				startActivity(i);

			}
		});
		
		//wait for FAQ click and go to page
		btnFaq.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(Settings.this, FAQ.class);
				startActivity(i);

			}
		});
		
		//wait for help click and go to page
		btnHelp.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(Settings.this, Help.class);
				startActivity(i);

			}
		});
		
		//wait for options click and go to page
		btnOptions.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(Settings.this, Settme.class);
				startActivity(i);

			}
		});
		
		//wait for logout click and log out
		btnLogout.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{

				//log out
				Thread thread = new Thread(new Runnable()  {
	                @Override
	                public void run() {
								HttpAsyncTask GPS = new HttpAsyncTask(
										new CompletedTasks() {
											public void callBack(
													String result) {
												Intent i = new Intent(
														Settings.this,
														Login.class);
												Settings.this
														.startActivity(i);
												Settings.this
														.finish();
											}
												});

								//execute logout php script
								if (GPS.isConnected(Settings.this)) 
								{
									GPS.execute("logout.php");
								}
	            };
	            });
				
			thread.start();

			}
		});
    }
    
	//action bar menu create
	public boolean onCreateOptionsMenu(Menu menu) {
	    android.view.MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionbar, menu);
	    return true;
	}
	
	//wait for creat activity press in action bar
	public boolean onOptionsItemSelected(MenuItem item){
	    Intent myIntent = new Intent(getApplicationContext(),CreateActivity.class);
	    startActivityForResult(myIntent, 0);
	    return true;
	}

	//if back pressed
	 public void onBackPressed()
	    {
	        super.onBackPressed();
	        super.finish(); 
	    }
}


