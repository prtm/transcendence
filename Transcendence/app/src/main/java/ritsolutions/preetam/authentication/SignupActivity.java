package ritsolutions.preetam.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import ritsolutions.preetam.transcendence.ConnectivityReceiver;
import ritsolutions.preetam.transcendence.DashBoard;
import ritsolutions.preetam.transcendence.R;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Intialization
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText passwd = (EditText) findViewById(R.id.password);
        final EditText mNumber = (EditText) findViewById(R.id.mNumber);
        final Button signup = (Button) findViewById(R.id.signup);
        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        final CheckBox checkbox = (CheckBox) findViewById(R.id.termsCheck);
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference();
        final DatabaseReference newref = ref.child("Users");
        CheckBox tutorcheck = (CheckBox) findViewById(R.id.tutorcheck);
        final EditText qualification = (EditText) findViewById(R.id.tutorQualification);

        tutorcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    qualification.setVisibility(View.VISIBLE);
                } else {
                    qualification.setVisibility(View.GONE);
                }
            }
        });


        //onClicklistener
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (ConnectivityReceiver.isConnected()) {

                    final String eml, pwd, num, nm;
                    pwd = passwd.getText().toString().trim();
                    num = mNumber.getText().toString().trim();
                    eml = email.getText().toString().trim();
                    nm = name.getText().toString().trim();


                    if (TextUtils.isEmpty(nm)) {
                        Snackbar.make(view, "Enter the Name!", Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(eml)) {
                        Snackbar.make(view, "Enter the Email!", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (!eml.contains("@")) {
                        Snackbar.make(view, "Enter the Correct Email!", Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    if (pwd.length() < 8) {
                        Snackbar.make(view, "Password too short, Enter Minimum 8 characters!", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (num.length() < 10) {
                        Snackbar.make(view, "Enter 10 digit Mobile Number!", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (!checkbox.isChecked()) {
                        Snackbar.make(view, "Please Accept the Terms and Conditions", Snackbar.LENGTH_SHORT).show();
                    }

                    String tutor = "No";
                    String Qualification = "No";
                    if (qualification.getVisibility() == View.VISIBLE) {
                        String s = qualification.getText().toString();
                        if (TextUtils.isEmpty(s)) {
                            Snackbar.make(view, "Enter Qualification", Snackbar.LENGTH_SHORT).show();
                            return;
                        } else {
                            tutor = "Yes";
                            Qualification = s;
                        }
                    }

                    progressbar.setVisibility(View.VISIBLE);

                    final String finalTutor = tutor;
                    final String finalQualification = Qualification;
                    firebaseAuth.createUserWithEmailAndPassword(eml, pwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressbar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Map<String, String> k = new HashMap<>();
                                k.put("Name", nm);
                                k.put("Number", num);
                                k.put("Email", eml);
                                k.put("Tutor", finalTutor);
                                k.put("Qualification", finalQualification);
                                newref.push().setValue(k);

                                Snackbar.make(view, "Account Successfully Created", Snackbar.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, DashBoard.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Snackbar.make(view, "Failed to Create Account", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                    });

                } else {
                    Snackbar.make(view, "Network Error", Snackbar.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        progressbar.setVisibility(View.GONE);
    }

    public void openConditions(View view) {
        //startActivity(new Intent(SignupActivity.this, Terms.class));
    }
}
