package ritsolutions.preetam.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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

import ritsolutions.preetam.transcendence.ConnectivityReceiver;
import ritsolutions.preetam.transcendence.DashBoard;
import ritsolutions.preetam.transcendence.R;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login_content);
        //Intialization
        username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        CheckBox checkBox = (CheckBox) findViewById(R.id.showPassword);


        //onClickListeners
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    //password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); after bitwise or
                    password.setInputType(129);
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (ConnectivityReceiver.isConnected()) {
                    String pwd = password.getText().toString().trim();
                    String eml = username.getText().toString().trim();
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
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signInWithEmailAndPassword(eml, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Snackbar.make(view, "Login Successfully", Snackbar.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, DashBoard.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Snackbar.make(view, "Authentication Failed!", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Snackbar.make(view, "Network Error", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void forgetPassword(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
        Bundle bundle = new Bundle();
        bundle.putString("email", username.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
