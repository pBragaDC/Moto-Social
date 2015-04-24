package pc.motorcycle.androidapp.AndroidActivites;

import pc.motorcycle.androidapp.R;
import pc.motorcycle.androidapp.R.id;
import pc.motorcycle.androidapp.R.layout;
import pc.motorcycle.androidapp.R.string;
import pc.motorcycle.androidapp.ServerActivities.CompletedTasks;
import pc.motorcycle.androidapp.ServerActivities.EncryptString;
import pc.motorcycle.androidapp.ServerActivities.HttpAsyncTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignUp extends Activity {
	
	//variable declaration
	private static final int FILL_ALL_FIELDS = 0;
	protected static final int TYPE_SAME_PASSWORD_IN_PASSWORD_FIELDS = 1;
	private static final int SIGN_UP_FAILED = 2;
	private static final int SIGN_UP_USERNAME_CRASHED = 3;
	private static final int SIGN_UP_SUCCESSFULL = 4;
	protected static final int USERNAME_AND_PASSWORD_LENGTH_SHORT = 5;
	public static final int HELP_ID = Menu.FIRST;

	//gui components declaration
	private EditText usernameText;
	private EditText passwordText;
	private EditText passwordAgainText;
	private EditText fname;
	private EditText lname;
	private Button signUpButton;
	private Button cancelButton;

   

	public void onCreate(Bundle savedInstanceState)
	{
	        super.onCreate(savedInstanceState);  

	        //set layout and title
	        setContentView(R.layout.activity_sign_up);
	        setupUI(findViewById(R.id.signParent));
	        setTitle("Sign up");
	        
	        //assign button objects to corresponding gui item
	        signUpButton = (Button) findViewById(R.id.btnLog);
	        cancelButton = (Button) findViewById(R.id.btnCancel);
	        usernameText = (EditText) findViewById(R.id.userName);
	        passwordText = (EditText) findViewById(R.id.password);  
	        passwordAgainText = (EditText) findViewById(R.id.passwordAgain); 
	        fname = (EditText) findViewById(R.id.fname);
	        lname = (EditText) findViewById(R.id.lname);

	        
	        //wait for sign up button click
	        signUpButton.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View arg0) 
				{						
					//check length
					if (usernameText.length() >= 5 &&		
						passwordText.length() >= 5 && 
						passwordAgainText.length() >= 5
						&& 
						fname.length() >= 2
						&& 
						lname.length() >= 2
						)
					{
						//get username and password
						final String usernameString = usernameText.getText().toString();
						final String passwordString = passwordText.getText().toString();
						
						//initialize valid
						boolean isValid = true;
						
						//chek for special characters
						for (int charNum = 0; charNum < usernameString.length(); ++charNum)
						{
							if (usernameString.charAt(charNum) < '!' || usernameString.charAt(charNum) > '~') 
							{
								isValid = false;
								break;
							}
						}
						
						//check password for special charatcters
						if (isValid) 
						{
							for (int charNum = 0; charNum < passwordString.length(); ++charNum) 
							{
								if (passwordString.charAt(charNum) < '!' || passwordString.charAt(charNum) > '~') 
								{
									isValid = false;
									break;
								}
							}
						}
						
						//if valid, create login thread
						if (isValid) {
							if (passwordText.getText().toString().equals(passwordAgainText.getText().toString())) {
								if (usernameText.length() >= 5 && passwordText.length() >= 5) {
									
									//encrypt and send information to server
									class LoginThread implements Runnable 
									{
										@Override
										public void run() {
											String encryptedPassword = EncryptString
													.encryptSHA512(passwordString);
										
											//check encryption length
											if (!encryptedPassword.isEmpty()
													|| encryptedPassword.length() != 512) 
											{
												//create sync task
											HttpAsyncTask task = new HttpAsyncTask(new CompletedTasks()
											{
												public void callBack(String result) 
												{
													//start login class
													if (result.contains("0")) 
													{
													Intent i = new Intent(SignUp.this, Login.class);
											    	SignUp.this.startActivity(i);
													SignUp.this.finish();
													}
													
													//create toast if name exists
													else
													{
														Toast.makeText(getApplicationContext(),
																"Username Exists!",
																Toast.LENGTH_LONG).show();
													}
													}
											});
											
											if (task.isConnected(SignUp.this)) 
											{
												//execute register php and submit to server
												task.execute("register.php", "Username", usernameString, 
														"Password", encryptedPassword, "Firstname", 
														fname.getText().toString(), "Lastname", 
														lname.getText().toString());					
											}
										};
									}
									}
									
									//creat new thread
									LoginThread loginThread = new LoginThread();
									loginThread.run();
								
								}	else{
									//toast username message
									Toast.makeText(getApplicationContext(),R.string.username_and_password_length_short,
											Toast.LENGTH_LONG).show();
								}
							}
						}
						else {
							//toast if passwords dont match
							Toast.makeText(getApplicationContext(),"Make Sure Password Matches", 
									Toast.LENGTH_LONG).show();
						}
					}
					else {
						//toast if not all fields are input
						Toast.makeText(getApplicationContext(),
								"Make sure all fields are filled correctly", Toast.LENGTH_LONG).show();
					}				
				}       	
	        });
	        
	        //wait for cancel button click
	        cancelButton.setOnClickListener(new OnClickListener()
	        {
	        	//go to login if canceled
				public void onClick(View arg0) 
				{				
					Intent i = new Intent(
							SignUp.this,
							Login.class);
					SignUp.this
							.startActivity(i);
					SignUp.this
							.finish();
					
					finish();					
				}	        	
	        });
	    }
	
	//hide soft keyboard
	public static void hideSoftKeyboard(Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  
	    		activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
	
	//hide keybard if not text field
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
					hideSoftKeyboard(SignUp.this);
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
	
	protected Dialog onCreateDialog(int id) 
	{    	
		  	
		switch (id) 
		{
			//set messages
			case TYPE_SAME_PASSWORD_IN_PASSWORD_FIELDS:			
				return new AlertDialog.Builder(SignUp.this)       
				.setMessage(R.string.signup_type_same_password_in_password_fields)
				.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int whichButton) 
					{
					}
				})        
				.create();			
			case FILL_ALL_FIELDS:				
				return new AlertDialog.Builder(SignUp.this)       
				.setMessage("Make sure all fields are filled correctly")//ensure fields are typed up
				.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton) 
					{
					}
				})        
				.create();
			case SIGN_UP_FAILED:
				return new AlertDialog.Builder(SignUp.this)       
				.setMessage(R.string.signup_failed)//message if signup failed
				.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int whichButton) 
					{
					}
				})        
				.create();
			case SIGN_UP_USERNAME_CRASHED:
				return new AlertDialog.Builder(SignUp.this)       
				.setMessage(R.string.signup_username_crashed)//message if crashed
				.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int whichButton) 
					{
					}
				})        
				.create();
			case SIGN_UP_SUCCESSFULL:
				return new AlertDialog.Builder(SignUp.this)       
				.setMessage(R.string.signup_successfull)//success
				.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int whichButton) 
					{
						finish();
					}
				})        
				.create();	
			case USERNAME_AND_PASSWORD_LENGTH_SHORT:
				return new AlertDialog.Builder(SignUp.this)       
				.setMessage(R.string.username_and_password_length_short)//length too short
				.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int whichButton) 
					{
					}
				})        
				.create();
			default: 
				return null;
				
		}
	}
	
	@Override
	protected void onResume() 
	{	   
		super.onResume();
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
	}

}
	



