package pc.motorcycle.androidapp.AndroidActivites;

import pc.motorcycle.androidapp.R;
import pc.motorcycle.androidapp.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity 
{
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
    	//set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        
        //set audio to play
        MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound2);
        
        mp.start(); //Starts your sound
 
        new Handler().postDelayed(new Runnable() 
        {
 
            @Override
            public void run() {
                //go to login when timer is over
                Intent i = new Intent(SplashActivity.this, Login.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
