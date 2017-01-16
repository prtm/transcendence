package ritsolutions.preetam.transcendence;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainNotification extends AppCompatActivity implements Communicator, ConnectivityReceiver.ConnectivityReceiverListener {
    private ProgressBar progressBar;
    private boolean isLoad = false;
    private Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.toolbar_progress_bar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        networkCheck();

        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void networkCheck() {
        if (!ConnectivityReceiver.isConnected()) {
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Messages(), "Messages");
        adapter.addFragment(new Users(), "Users");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void stopProgressBar() {
        progressBar.setVisibility(View.GONE);
        isLoad = true;
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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

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
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
