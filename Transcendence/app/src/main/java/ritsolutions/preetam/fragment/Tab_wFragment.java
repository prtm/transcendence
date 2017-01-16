package ritsolutions.preetam.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;

import ritsolutions.preetam.adapter.RecyclerAdapter;
import ritsolutions.preetam.transcendence.DashBoard;
import ritsolutions.preetam.transcendence.R;


public class Tab_wFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private DatabaseReference ref;
    private RecyclerView rv1;
    private TextView tv1;

    public static Tab_wFragment newInstance(String param1) {
        Tab_wFragment fragment = new Tab_wFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_w, container, false);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference().child("Data");
        rv1 = (RecyclerView) view.findViewById(R.id.dRv1);
        tv1 = (TextView) view.findViewById(R.id.dName1);
        rv1.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            assert mParam1 != null;
            switch (mParam1) {
                case "one":
                    DatabaseReference child1 = ref.child("0");
                    final RecyclerAdapter fra1 = new RecyclerAdapter(getActivity(), new ArrayList<String>());
                    child1.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                            };
                            fra1.ls = dataSnapshot.getValue(t);
                            fra1.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                            };
                            fra1.ls = dataSnapshot.getValue(t);
                            fra1.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    rv1.setAdapter(fra1);
                    if (DashBoard.list != null && !DashBoard.list.isEmpty()) {
                        tv1.setText(DashBoard.list.get(0));
                    }


                    break;
                case "two":
                    DatabaseReference child2 = ref.child("1");
                    final RecyclerAdapter fra2 = new RecyclerAdapter(getActivity(), new ArrayList<String>());
                    child2.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                            };
                            fra2.ls = dataSnapshot.getValue(t);
                            fra2.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                            };
                            fra2.ls = dataSnapshot.getValue(t);
                            fra2.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    rv1.setAdapter(fra2);
                    if (DashBoard.list != null && !DashBoard.list.isEmpty()) {
                        tv1.setText(DashBoard.list.get(1));
                    }
                    break;
                case "three":
                    DatabaseReference child3 = ref.child("2");
                    final RecyclerAdapter fra3 = new RecyclerAdapter(getActivity(), new ArrayList<String>());
                    child3.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                            };
                            fra3.ls = dataSnapshot.getValue(t);
                            fra3.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                            };
                            fra3.ls = dataSnapshot.getValue(t);
                            fra3.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    rv1.setAdapter(fra3);
                    if (DashBoard.list != null && !DashBoard.list.isEmpty()) {
                        tv1.setText(DashBoard.list.get(2));
                    }
                    break;
                case "four":
                    DatabaseReference child4 = ref.child("3");
                    final RecyclerAdapter fra4 = new RecyclerAdapter(getActivity(), new ArrayList<String>());
                    child4.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                            };
                            fra4.ls = dataSnapshot.getValue(t);
                            fra4.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                            };
                            fra4.ls = dataSnapshot.getValue(t);
                            fra4.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    rv1.setAdapter(fra4);
                    if (DashBoard.list != null && !DashBoard.list.isEmpty()) {
                        tv1.setText(DashBoard.list.get(3));
                    }
                    break;
                case "five":
                    DatabaseReference child5 = ref.child("4");
                    final RecyclerAdapter fra5 = new RecyclerAdapter(getActivity(), new ArrayList<String>());
                    child5.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                            };
                            fra5.ls = dataSnapshot.getValue(t);
                            fra5.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                            };
                            fra5.ls = dataSnapshot.getValue(t);
                            fra5.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    rv1.setAdapter(fra5);
                    if (DashBoard.list != null && !DashBoard.list.isEmpty()) {
                        tv1.setText(DashBoard.list.get(4));
                    }
                    break;
            }


        }

    }
}
