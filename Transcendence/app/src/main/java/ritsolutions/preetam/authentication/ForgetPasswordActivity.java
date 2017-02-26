package ritsolutions.preetam.authentication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import ritsolutions.preetam.transcendence.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String emailAddress = getIntent().getExtras().getString("email");
        final EditText email = (EditText) findViewById(R.id.email);
        final Button sendMail = (Button) findViewById(R.id.resetPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final TextView textView = (TextView) findViewById(R.id.tvReset);

        if (!TextUtils.isEmpty(emailAddress)) {
            email.setText(emailAddress);
        }
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String eAddress = email.getText().toString();
                if (!TextUtils.isEmpty(eAddress) && (eAddress.contains("@"))) {
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.sendPasswordResetEmail(eAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            progressBar.setVisibility(View.GONE);

                            if (task.isSuccessful()) {
                                email.setVisibility(View.GONE);
                                sendMail.setVisibility(View.GONE);
                                textView.setVisibility(View.VISIBLE);
                                textView.setText(R.string.sentSuccessfully);
                            } else {
                                Snackbar.make(view, "Error While Sending Mail", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Snackbar.make(view, "Check Email Address", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
