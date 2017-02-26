package ritsolutions.preetam.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ritsolutions.preetam.transcendence.R;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__us);
    }

    public void callMobile(View view) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:+919928306870"));
        startActivity(callIntent);
    }

    public void emailUs(View View) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "lokesh.sehgal1@gmail.com");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "About Transcendence");
        startActivity(Intent.createChooser(emailIntent, "Send mail"));

    }
}
