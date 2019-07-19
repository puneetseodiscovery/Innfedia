package com.mandy.innfedia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mandy.innfedia.Activities.CustmerActivity;
import com.mandy.innfedia.Activities.LoginActivity;
import com.mandy.innfedia.fragment.HomeFragment;
import com.mandy.innfedia.fragment.MyCartFragment;
import com.mandy.innfedia.fragment.OrderListFragment;
import com.mandy.innfedia.fragment.ProductDetailsFragment;
import com.mandy.innfedia.fragment.ProfileFragment;
import com.mandy.innfedia.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ImageView toolbarSearch, toolbarCart;
    TextView txtcartNumber;
    ActionBarDrawerToggle mToggle;
    FragmentManager manager;
    FragmentTransaction transaction;
    public static TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // find all id
        init();

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(mToggle);

        mToggle.syncState();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView.setText("Infedia");


        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        LayoutInflater.from(this).inflate(R.layout.header, navigationView);

        transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();

        onNavigationClick();

        toolbarSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction1 = manager.beginTransaction();
                transaction1.replace(R.id.framelayout, new SearchFragment());
                transaction1.addToBackStack(null);
                transaction1.commit();
            }
        });


        toolbarCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction1 = manager.beginTransaction();
                transaction1.replace(R.id.framelayout, new MyCartFragment());
                transaction1.addToBackStack(null);
                transaction1.commit();
            }
        });


    }

    //find all id
    private void init() {

        navigationView = (NavigationView) findViewById(R.id.Drawer_navigation);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerNavigation);
        toolbar = (Toolbar) findViewById(R.id.tooolbar);
        toolbarSearch = (ImageView) findViewById(R.id.toolbarSearch);
        toolbarCart = (ImageView) findViewById(R.id.toolbarCart);
        txtcartNumber = (TextView) findViewById(R.id.toolbarCartNumber);
        textView = (TextView) findViewById(R.id.txtToolbar);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // navigation item click
    private void onNavigationClick() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id) {

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.myorderlist:
                        drawerLayout.closeDrawers();
                        transaction = manager.beginTransaction();
                        transaction.replace(R.id.framelayout, new OrderListFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case R.id.cart:
                        drawerLayout.closeDrawers();
                        transaction = manager.beginTransaction();
                        transaction.replace(R.id.framelayout, new MyCartFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case R.id.my_profile:
                        drawerLayout.closeDrawers();
                        transaction = manager.beginTransaction();
                        transaction.replace(R.id.framelayout, new ProfileFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;

                    case R.id.my_logout:
                        drawerLayout.closeDrawers();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();

                        break;

                    case R.id.customer:
                        drawerLayout.closeDrawers();
                        startActivity(new Intent(getApplicationContext(), CustmerActivity.class));

                        break;
                }

                return false;
            }
        });
    }


}
