package com.jeffhanke.rackm8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BottomNavigationView mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        mBottomNav.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        int x = 1;
                        switch (item.getItemId()) {
                            case R.id.menu_search:
                                selectedFragment = Building.newInstance();
                                x = 0;
                                break;
                            case R.id.menu_map:
                                selectedFragment = Map.newInstance();
                                x = 1;
                                break;
                            case R.id.menu_nearby:
                                selectedFragment = Nearby.newInstance();
                                x = 2;
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, selectedFragment);
                        transaction.commit();
                        mBottomNav.getMenu().getItem(0).setChecked(false);
                        mBottomNav.getMenu().getItem(1).setChecked(false);
                        mBottomNav.getMenu().getItem(2).setChecked(false);
                        mBottomNav.getMenu().getItem(x).setChecked(true);

                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, Map.newInstance());
        transaction.commit();

        //Used to select an item programmatically
        mBottomNav.getMenu().getItem(0).setChecked(false);
        mBottomNav.getMenu().getItem(1).setChecked(true);

    }

}
