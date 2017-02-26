package ritsolutions.preetam.transcendence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ritsolutions.preetam.getter.InformationUsers;

public class Users extends Fragment {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recyclerview, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        context = getActivity();
        getData();
        return view;
    }

    public void getData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        FirebaseRecyclerAdapter<InformationUsers, MyHolder> adapter = new FirebaseRecyclerAdapter<InformationUsers, MyHolder>(
                InformationUsers.class,
                R.layout.custom_users,
                MyHolder.class,
                ref) {
            @Override
            protected void populateViewHolder(MyHolder viewHolder, final InformationUsers model, int position) {

                viewHolder.name.setText(model.getName());
                viewHolder.number.setText(model.getNumber());
                viewHolder.email.setText(model.getEmail());
                if (model.getTutor().equals("No")) {
                    viewHolder.lltutor.setVisibility(View.GONE);
                    viewHolder.llqualification.setVisibility(View.GONE);
                } else {
                    viewHolder.tutor.setText(model.getTutor());
                    viewHolder.qualification.setText(model.getQualification());
                }
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        };
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDestroy() {
        context = null;
        super.onDestroy();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private TextView email, number, name, tutor, qualification;
        private LinearLayout llemail, llnumber, lltutor, llqualification;

        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.mobile);
            email = (TextView) itemView.findViewById(R.id.email);
            llemail = (LinearLayout) itemView.findViewById(R.id.llemail);
            llnumber = (LinearLayout) itemView.findViewById(R.id.llnumber);
            lltutor = (LinearLayout) itemView.findViewById(R.id.tutorLayout);
            llqualification = (LinearLayout) itemView.findViewById(R.id.qualificationLayout);
            tutor = (TextView) itemView.findViewById(R.id.tutor);
            qualification = (TextView) itemView.findViewById(R.id.qualification);


            llemail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, email.getText().toString());
                    try {
                        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    } catch (Exception ignored) {

                    }
                }
            });

            llnumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse(number.getText().toString()));

                    try {
                        context.startActivity(in);
                    } catch (Exception ignored) {

                    }
                }
            });
        }
    }
}

