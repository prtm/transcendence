package ritsolutions.preetam.transcendence;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ritsolutions.preetam.getter.InformationMessage;

public class Messages extends Fragment {
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
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("feedback");
        FirebaseRecyclerAdapter<InformationMessage, MyHolder> adapter = new FirebaseRecyclerAdapter<InformationMessage, MyHolder>(
                InformationMessage.class,
                R.layout.custom_messages,
                MyHolder.class,
                ref) {
            @Override
            protected void populateViewHolder(MyHolder viewHolder, InformationMessage model, int position) {

                viewHolder.name.setText(model.getName());
                viewHolder.number.setText(model.getNumber());
                viewHolder.email.setText(model.getEmail());
                viewHolder.course.setText(model.getCourse());
                viewHolder.message.setText(model.getCourse());
                viewHolder.address.setText(model.getAddress());

            }
        };
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Communicator communicator = (Communicator) getActivity();
                    communicator.stopProgressBar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private TextView email, number, name, message, address, course;
        private LinearLayout llemail, llnumber;

        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.mobile);
            email = (TextView) itemView.findViewById(R.id.email);
            message = (TextView) itemView.findViewById(R.id.message);
            course = (TextView) itemView.findViewById(R.id.course);
            address = (TextView) itemView.findViewById(R.id.address);
            llemail = (LinearLayout) itemView.findViewById(R.id.llemail);
            llnumber = (LinearLayout) itemView.findViewById(R.id.llnumber);

            llemail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, email.getText().toString());
                    try {
                        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    } catch (Exception Ignored) {

                    }
                }
            });

            llnumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse(number.getText().toString()));
                    try {
                        context.startActivity(in);
                    } catch (Exception Ignored) {

                    }
                }
            });


        }
    }
}

