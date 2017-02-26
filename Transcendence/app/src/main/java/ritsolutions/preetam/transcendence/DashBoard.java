package ritsolutions.preetam.transcendence;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ritsolutions.preetam.authentication.LoginActivity;
import ritsolutions.preetam.contact.AboutUsActivity;
import ritsolutions.preetam.contact.ContactUsActivity;
import ritsolutions.preetam.fragment.TabWFragment;
import ritsolutions.preetam.interfacer.OnClickHandler;

public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnClickHandler, ConnectivityReceiver.ConnectivityReceiverListener {

    public static List<String> list;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref;
    private ProgressBar progressBar;
    private NavigationView navigationView;
    private boolean isLoad = false;
    private Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        list = new ArrayList<>();
        //Intialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ref = FirebaseDatabase.getInstance().getReference().child("Data");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        progressBar = (ProgressBar) findViewById(R.id.toolbar_progress_bar);

        ///////////////////////
        //Set up
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            list = null;
            super.onBackPressed();
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(TabWFragment.newInstance("one"), "Tut");
        adapter.addFragment(TabWFragment.newInstance("two"), "Tut");
        adapter.addFragment(TabWFragment.newInstance("three"), "Prep");
        adapter.addFragment(TabWFragment.newInstance("four"), "Class");
        adapter.addFragment(TabWFragment.newInstance("five"), "Class");


        progressBar.setVisibility(View.VISIBLE);
        if (!checkConnection()) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show();
        }
        networkCheck();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GenericTypeIndicator<Map<String, ArrayList<String>>> t = new GenericTypeIndicator<Map<String, ArrayList<String>>>() {
                };
                progressBar.setVisibility(View.VISIBLE);
                Map<String, ArrayList<String>> map = dataSnapshot.getValue(t);
                try {
                    DashBoard.list.add(new ArrayList<>(map.keySet()).get(0));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                isLoad = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        viewPager.setAdapter(adapter);
    }

    private void networkCheck() {
        if (!checkConnection()) {
            progressBar.setVisibility(View.GONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Device is Offline");
            builder.setTitle("Network Error");

            builder.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    networkCheck();
                }
            });

            d = builder.create();
            d.setCancelable(false);
            d.show();


        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            startActivity(new Intent(DashBoard.this, LoginActivity.class));
        } else if (id == R.id.nav_aboutUs) {
            startActivity(new Intent(DashBoard.this, AboutUsActivity.class));

        } else if (id == R.id.nav_contactUs) {
            startActivity(new Intent(DashBoard.this, ContactUsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dash_board_menu, menu);
        if (firebaseAuth.getCurrentUser() == null) {
            MenuItem item = menu.findItem(R.id.signout);
            item.setVisible(false);
            invalidateOptionsMenu();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.signout == item.getItemId()) {
            firebaseAuth.signOut();
            item.setVisible(false);
            invalidateOptionsMenu();
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
        }
    }

    @Override
    public void onClickEvent(String subject, boolean isCourse) {
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(DashBoard.this, LoginActivity.class));
        } else {
            Intent intent = new Intent(DashBoard.this, MessageSend.class);
            Bundle bundle = new Bundle();
            bundle.putString(MessageSend.courseClass, subject);
            bundle.putBoolean(MessageSend.sub_or_course, isCourse);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        d.dismiss();
        if (isConnected && !isLoad) {
            progressBar.setVisibility(View.VISIBLE);
        } else if (!isConnected && isLoad) {
            progressBar.setVisibility(View.GONE);
        } else {
            networkCheck();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }


    // Method to manually check connection status
    private boolean checkConnection() {
        return ConnectivityReceiver.isConnected();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> ListTitle = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            ListTitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ListTitle.get(position);
        }
    }

}
