package fxjwsq.taiji.com.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import fxjwsq.taiji.com.sample.Fragments.Tab1Fragment;
import fxjwsq.taiji.com.sample.Fragments.Tab2Fragment;
import fxjwsq.taiji.com.sample.Fragments.Tab3Fragment;

public class TABLabelActivity extends AppCompatActivity {

    private LinearLayout tab1Layout, tab2Layout, tab3Layout;
    private int index = 1;
    private FragmentManager fragmentManager;
    private Fragment tab1Fragment, tab2Fragment, tab3Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablabel);

        fragmentManager = getSupportFragmentManager();

        init();

    }

    private void init() {
        tab1Layout = (LinearLayout) findViewById(R.id.tab1_layout);
        tab2Layout = (LinearLayout) findViewById(R.id.tab2_layout);
        tab3Layout = (LinearLayout) findViewById(R.id.tab3_layout);

        tab1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearStatus();
                if (tab1Fragment == null) {
                    tab1Fragment = new Tab1Fragment();
                }
                replaceFragment(tab1Fragment);
                tab1Layout.setBackgroundColor(getResources().getColor(R.color.blue));
                index = 1;
            }
        });
        tab2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearStatus();
                if (tab2Fragment == null) {
                    tab2Fragment = new Tab2Fragment();
                }
                replaceFragment(tab2Fragment);
                tab2Layout.setBackgroundColor(getResources().getColor(R.color.blue));
                index = 2;
            }
        });
        tab3Layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clearStatus();
                if (tab3Fragment == null) {
                    tab3Fragment = new Tab3Fragment();
                }
                replaceFragment(tab3Fragment);
                tab3Layout.setBackgroundColor(getResources().getColor(R.color.blue));
                index = 3;
            }
        });
        setDefaultFragment();
    }


    private void setDefaultFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        tab1Fragment = new Tab1Fragment();
        transaction.replace(R.id.content_layout, tab1Fragment);
        transaction.commit();
    }


    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!newFragment.isAdded()) {
            transaction.replace(R.id.content_layout, newFragment);
            transaction.commit();
        } else {
            transaction.show(newFragment);
        }
    }


    private void clearStatus() {
        if (index == 1) {
            tab1Layout.setBackgroundColor(getResources().getColor(R.color.gray));
        } else if (index == 2) {
            tab2Layout.setBackgroundColor(getResources().getColor(R.color.gray));
        } else if (index == 3) {
            tab3Layout.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break ;
        }
        return true;
    }
}
