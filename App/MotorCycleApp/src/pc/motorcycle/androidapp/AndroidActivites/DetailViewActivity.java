
	package pc.motorcycle.androidapp.AndroidActivites;

import pc.motorcycle.androidapp.R;
import pc.motorcycle.androidapp.R.id;
import pc.motorcycle.androidapp.R.layout;
import pc.motorcycle.androidapp.R.menu;
import pc.motorcycle.androidapp.ServerActivities.AppData;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class DetailViewActivity extends Activity 
{
	//declaration of variables for xml items
	private TextView title;
	private TextView date;
	private TextView time;
	private TextView street;
	private TextView city;
	private TextView state;
	private TextView zip;
	private TextView description;
	private TextView locnumb;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		//set layout and title for class
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_view_layout);
		
		//set variable to corresponding GUI item
		title = (TextView)findViewById(R.id.tit);
		date = (TextView)findViewById(R.id.date1);
		time = (TextView)findViewById(R.id.tim);
		street = (TextView)findViewById(R.id.stret);
		city = (TextView)findViewById(R.id.cit);
		state = (TextView)findViewById(R.id.stat);
		zip = (TextView)findViewById(R.id.zipe);
		description = (TextView)findViewById(R.id.desc);
		locnumb = (TextView)findViewById(R.id.locn);

		//get text and set it to layout
		title.setText(AppData.title);
		date.setText(AppData.date);
		time.setText(AppData.time);
		street.setText(AppData.street);
		city.setText(AppData.city);
		state.setText(AppData.state);
		zip.setText(AppData.zipcode);
		description.setText(AppData.description);
		locnumb.setText(AppData.locnumb);
	}
}




        
   
	
