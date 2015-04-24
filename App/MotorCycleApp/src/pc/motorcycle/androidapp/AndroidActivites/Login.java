package pc.motorcycle.androidapp.AndroidActivites;

import java.util.StringTokenizer;

import pc.motorcycle.androidapp.R;
import pc.motorcycle.androidapp.ServerActivities.AppData;
import pc.motorcycle.androidapp.ServerActivities.CompletedTasks;
import pc.motorcycle.androidapp.ServerActivities.EncryptString;
import pc.motorcycle.androidapp.ServerActivities.HttpAsyncTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class Login extends Activity 
{

	//declaration of variables and GUI objects
	protected static final int NOT_CONNECTED_TO_SERVICE = 0;
	protected static final int FILL_BOTH_USERNAME_AND_PASSWORD = 1;
	public static final String AUTHENTICATION_FAILED = "0";
	public static final String FRIEND_LIST = "FRIEND_LIST";
	protected static final int MAKE_SURE_USERNAME_AND_PASSWORD_CORRECT = 2;
	protected static final int NOT_CONNECTED_TO_NETWORK = 3;
	private EditText usernameText;
	private EditText passwordText;
	private Button signup;
	public static final int SIGN_UP_ID = Menu.FIRST;
	public static final int EXIT_ID = Menu.FIRST + 1;
	public static final int HELP_ID = Menu.FIRST + 2;
	EditText resultBox;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		//set layout, gui components, and title
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		setupUI(findViewById(R.id.loginParent));
		setTitle("Login");
		
		Button loginButton = (Button) findViewById(R.id.login);
		signup = (Button) findViewById(R.id.signUp);
		usernameText = (EditText) findViewById(R.id.username);
		passwordText = (EditText) findViewById(R.id.password);
		
		//wait for signup button click
		signup.setOnClickListener(new OnClickListener()
		{
			//execute on click
			@Override
			public void onClick(View arg0) 
			{
				Intent i = new Intent(
						Login.this,
						SignUp.class);
				Login.this
						.startActivity(i);
				Login.this
						.finish();
				
			}
		});

		//wait for login button click
		loginButton.setOnClickListener(new OnClickListener() 
		{
			//execute on click
			@Override
			public void onClick(View arg0) 
			{
				//check length of input
				if (usernameText.length() > 4 && passwordText.length() > 4)
				{
					//variables
					AppData.username = usernameText.getText().toString();
					final String passwordString = passwordText.getText().toString();

					boolean isValid = true;

					for (int charNum = 0; charNum < AppData.username.length(); ++charNum) 
					{
						//check for special characters
						if (AppData.username.charAt(charNum) < '!'
								|| AppData.username.charAt(charNum) > '~') 
						{
							isValid = false;
							break;
						}
					}

					//perform if it is valid
					if (isValid) 
					{
						//check password
						for (int charNum = 0; charNum < passwordString.length(); ++charNum) 
						{
							//check for special characters
							if (passwordString.charAt(charNum) < '!'
									|| passwordString.charAt(charNum) > '~') {
								isValid = false;
								break;
							}
						}
					}

					//perform if valid
					if (isValid) 
					{
						//create connection thread
						class LoginThread implements Runnable 
						{
							@Override
							public void run() 
							{
								String encryptedPassword = EncryptString
										.encryptSHA512(passwordString);

								//check if password length is correct
								if (!encryptedPassword.isEmpty()
										|| encryptedPassword.length() != 512) 
								{
									HttpAsyncTask loginTask = new HttpAsyncTask(
											new CompletedTasks() 
											{
												public void callBack(
														String result) 
												{
													//check length
													if (result.contains("5"))
													{
														Toast.makeText(getApplicationContext(),
																"Invalid username or password!",
																Toast.LENGTH_LONG).show();
					
													 }
													//check length
												 if (result.contains("0")) 
												 {
																	Intent i = new Intent(
																			Login.this,
																			EventActivity.class);
																	Login.this
																			.startActivity(i);
																	Login.this
																			.finish();
												}
												}});
									
									//execute php script
									if (loginTask.isConnected(Login.this)) 
									{
										loginTask.execute("login.php",
												"Username", AppData.username,
												"Password", encryptedPassword);
									}
								} 
							};
						}
						LoginThread loginThread = new LoginThread();
						loginThread.run();
					} 
				} 
				
				else {
					//throw error
					Toast.makeText(getApplicationContext(),
							R.string.fill_both_username_and_password,
							Toast.LENGTH_LONG).show();
				}
			}
		});

	
	}
	

	//method to hide keyboard
	public static void hideSoftKeyboard(Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  
	    		activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
	
	//method to hide keyboard 
	public void setupUI(View view) 
	{
	    //Set up touch listener for non-text box views to hide keyboard.
	    if(!(view instanceof EditText)) 
	    {
	        view.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					hideSoftKeyboard(Login.this);
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
	
	//error messages
	@Override
	protected Dialog onCreateDialog(int id) 
	{
		int message = -1;
		switch (id) {
		case NOT_CONNECTED_TO_SERVICE:
			message = R.string.not_connected_to_service;
			break;
		case FILL_BOTH_USERNAME_AND_PASSWORD:
			message = R.string.fill_both_username_and_password;
			break;
		case MAKE_SURE_USERNAME_AND_PASSWORD_CORRECT:
			message = R.string.make_sure_username_and_password_correct;
			break;
		case NOT_CONNECTED_TO_NETWORK:
			message = R.string.not_connected_to_network;
			break;
		default:
			break;
		}

		//check status
		if (message == -1) {
			return null;
		} else {
			//return alert
			return new AlertDialog.Builder(Login.this)
					.setMessage(message)
					.setPositiveButton(R.string.OK,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).create();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	
}
