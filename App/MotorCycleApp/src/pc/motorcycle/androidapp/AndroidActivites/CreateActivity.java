package pc.motorcycle.androidapp.AndroidActivites;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import pc.motorcycle.androidapp.R;
import pc.motorcycle.androidapp.R.id;
import pc.motorcycle.androidapp.R.layout;
import pc.motorcycle.androidapp.R.menu;
import pc.motorcycle.androidapp.ServerActivities.AppData;
import pc.motorcycle.androidapp.ServerActivities.CompletedTasks;
import pc.motorcycle.androidapp.ServerActivities.EncryptString;
import pc.motorcycle.androidapp.ServerActivities.HttpAsyncTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateActivity extends Activity 
{
	
	//declaration of variables for xml items
	private Spinner dropdown;
	private EditText title;
	private EditText date;
	private EditText time;
	private EditText locnumb;
	private EditText street;
	private String ampm = "";
	private EditText city;
	private EditText state;
	private EditText zip;
	private String type1 = "1";
	private String type2 = "2";
	private EditText desc;
	private Button submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		//set layout and title for class
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		setupUI(findViewById(R.id.createParent));
		
		//set variable to corresponding GUI item
		title = (EditText)findViewById(R.id.editText4);
		date = (EditText)findViewById(R.id.Date);
		time = (EditText)findViewById(R.id.Time);
		locnumb = (EditText)findViewById(R.id.House);
		street = (EditText)findViewById(R.id.Street);
		city = (EditText)findViewById(R.id.City);
		state = (EditText)findViewById(R.id.State);
		zip = (EditText)findViewById(R.id.Zip);
	    dropdown = (Spinner)findViewById(R.id.spinner1);
	    desc = (EditText)findViewById(R.id.editText2);
	    submit = (Button)findViewById(R.id.submit);
	    
	    //set spinner text
		String[] items = new String[]{"Ride", "Event"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
		dropdown.setAdapter(adapter);
		
		//wait for submit button click
		submit.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				//check length of items input in text field for events
				if (dropdown.getItemAtPosition(dropdown.getSelectedItemPosition()).toString().contains("E"))
				{
					if ( title.length() > 3 && date.length() > 3 && time.length() >= 1 && locnumb.length() >= 1 
							&& street.length() > 3 && city.length() > 3 && state.length() >= 2 
							&& zip.length() == 5 && desc.length() > 5) 
					{
						//create connectivity to server
					class LoginThread implements Runnable 
					{
						@Override
						public void run()
						{
								HttpAsyncTask event = new HttpAsyncTask(
										new CompletedTasks() {
											public void callBack(
													String result) {
												Intent i = new Intent(
														CreateActivity.this,
														EventActivity.class);
												CreateActivity.this
														.startActivity(i);
												CreateActivity.this
														.finish();
											
											}});
								
								//call create script on server and write to database
								if (event.isConnected(CreateActivity.this)) 
								{
									event.execute("create.php", "Title", title.getText().toString(), "Type", type1, "Date", date.getText().toString(), 
											"Hour", time.getText().toString(), "Time", ampm, "LocNumb", locnumb.getText().toString(), "Street", street.getText().toString(), "City",
											city.getText().toString(), "ZipCode", zip.getText().toString(), "State", state.getText().toString(), "Description", desc.getText().toString());
								}
							
						};
					}

					//new instance of login thread
					LoginThread loginThread = new LoginThread();
					loginThread.run();
					
				}
					else 
					{
						//display error message
						Toast.makeText(getApplicationContext(),"Make sure all fields are filled correctly", Toast.LENGTH_LONG).show();
					}
				}
				
				//check length of items input in text field for Rides
				if (dropdown.getItemAtPosition(dropdown.getSelectedItemPosition()).toString().contains("R")) 
				{
					//check length of items input in text field for events
					if ( title.length() > 3 && date.length() > 3 && time.length() >= 1 && locnumb.length() >= 1 
							&& street.length() > 3 && city.length() > 3 && state.length() >= 2 && zip.length() == 5 
							&& desc.length() > 5) 
					{
						//create connectivity to server
					class LoginThread implements Runnable 
					{
						@Override
						public void run() 
						{
		
								HttpAsyncTask ride = new HttpAsyncTask(
										new CompletedTasks() {
											public void callBack(
													String result) {
												Intent i = new Intent(
														CreateActivity.this,
														EventActivity.class);
												CreateActivity.this
														.startActivity(i);
												CreateActivity.this
														.finish();			
											}});
								
								//call create script on server and write to database
								if (ride.isConnected(CreateActivity.this)) 
								{
									ride.execute("create.php", "Title", title.getText().toString(), "Type", type2, "Date", date.getText().toString(), 
											"Hour", time.getText().toString(), "Time", ampm, "LocNumb", locnumb.getText().toString(), "Street", street.getText().toString(), "City",
											city.getText().toString(), "ZipCode", zip.getText().toString(), "State", state.getText().toString(), "Description", desc.getText().toString());
								}
							
						};
					}

					//run thread
					LoginThread loginThread = new LoginThread();
					loginThread.run();
					
					}	
					else 
					{
						//display error message
						Toast.makeText(getApplicationContext(),"Make sure all fields are filled correctly", Toast.LENGTH_LONG).show();				
					}
					
				}
				
				
			}
			
		});
	}

	//method to hide keyboard if it is clicked off a text field
	public static void hideSoftKeyboard(Activity activity) 
	{
	    InputMethodManager inputMethodManager = (InputMethodManager)  
	    		activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
	
	//hide keyboard for non text fields
	public void setupUI(View view) 
	{
	    //Set up touch listener for non-text box views to hide keyboard.
	    if(!(view instanceof EditText)) 
	    {
	        view.setOnTouchListener(new OnTouchListener() 
	        {
				@Override
				public boolean onTouch(View v, MotionEvent event) 
				{
					// TODO Auto-generated method stub
					hideSoftKeyboard(CreateActivity.this);
					return false;
				}
	        });
	    }

	    //If a layout container, iterate over children and seed recursion.
	    if (view instanceof ViewGroup) 
	    {
	        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) 
	        {
	            View innerView = ((ViewGroup) view).getChildAt(i);
	            setupUI(innerView);
	        }
	    }
	}
}


