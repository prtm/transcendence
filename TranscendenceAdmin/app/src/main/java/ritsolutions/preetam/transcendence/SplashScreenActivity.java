package ritsolutions.preetam.transcendence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //Intialization
        ImageView imageView = (ImageView) findViewById(R.id.logoCompany);

        //load Animation
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.heart_beat));
        fadeAnimation();

        Handler h = new Handler();
        h.postDelayed(new Runnable() {

            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainNotification.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, 3000);
    }


    public void fadeAnimation() {

        TextView mSwitcher = (TextView) findViewById(R.id.CompanyName);

        Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(4000);
        mSwitcher.startAnimation(in);

    }
}
