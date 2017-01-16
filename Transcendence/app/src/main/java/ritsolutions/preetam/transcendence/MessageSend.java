package ritsolutions.preetam.transcendence;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MessageSend extends AppCompatActivity {
    public static final String courseClass = "course_name";
    public static String sub_or_course = "course";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send);
        final String cClass = getIntent().getExtras().getString(courseClass);
        boolean sboolean = getIntent().getExtras().getBoolean(sub_or_course);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference().child("feedback");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Intitalization
        final EditText name = (EditText) findViewById(R.id.name);
        EditText courseClass = (EditText) findViewById(R.id.courseClass);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText mNumber = (EditText) findViewById(R.id.mNumber);
        final EditText Address = (EditText) findViewById(R.id.address);
        final EditText message = (EditText) findViewById(R.id.message);
        final TextView invisibleTv = (TextView) findViewById(R.id.invisibleTv);
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        final String hint;
        if (sboolean) {
            hint = "Course";
            courseClass.setHint(hint);
        } else {
            hint = "Class";
            courseClass.setHint(hint);
        }
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        email.setText(firebaseAuth.getCurrentUser().getEmail());
        email.setEnabled(false);
        courseClass.setText(cClass);
        courseClass.setEnabled(false);
        Button send = (Button) findViewById(R.id.send);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectivityReceiver.isConnected()) {
                    String nm = name.getText().toString();
                    String num = mNumber.getText().toString();
                    String add = Address.getText().toString();
                    String msg = message.getText().toString();
                    if (TextUtils.isEmpty(nm) || nm.length() < 2) {
                        getSnackBar(view, "Check Name");
                        return;
                    }
                    if (TextUtils.isEmpty(num) || num.length() != 10) {
                        getSnackBar(view, "Number must of 10 digits");
                        return;
                    }
                    if (TextUtils.isEmpty(add)) {
                        getSnackBar(view, "Address must not be Empty");
                        return;
                    }
                    if (TextUtils.isEmpty(msg)) {
                        getSnackBar(view, "Message must not be Empty");
                        return;
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    //el=el.replaceAll("[.#$]",",");


                    Map<String, String> map = new HashMap<>();
                    map.put("Name", nm);
                    map.put(hint, cClass);
                    map.put("Email", firebaseAuth.getCurrentUser().getEmail());
                    map.put("Number", num);
                    map.put("Address", add);
                    map.put("Message", msg);

                    reference.push().setValue(map);

                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.GONE);
                    invisibleTv.setVisibility(View.VISIBLE);
                } else {
                    Snackbar.make(view, "Network Error", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
