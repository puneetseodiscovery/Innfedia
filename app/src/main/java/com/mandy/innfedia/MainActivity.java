package com.mandy.innfedia;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.crashlytics.android.Crashlytics;
import com.mandy.innfedia.commonActivity.CustmerActivity;
import com.mandy.innfedia.commonActivity.NoInternetActivity;
import com.mandy.innfedia.myCart.MyCartActivity;
import com.mandy.innfedia.productDetails.GetAddToCart;
import com.mandy.innfedia.myProfile.ProfileActivity;
import com.mandy.innfedia.myProfile.ProfileApi;
import com.mandy.innfedia.myOrderList.MyOrderListActivity;
import com.mandy.innfedia.retrofit.ApiInterface;
import com.mandy.innfedia.retrofit.ServiceGenerator;
import com.mandy.innfedia.searchActivity.SearchActivity;
import com.mandy.innfedia.utils.CheckInternet;
import com.mandy.innfedia.utils.SharedToken;
import com.mandy.innfedia.utils.Snack;
import com.mandy.innfedia.utils.UtilDialog;
import com.mandy.innfedia.homeFragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static TextView textView;
    public static TextView txtcartNumber;
    public static ProfileApi.Data data;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ImageView toolbarSearch, toolbarCart;

    ActionBarDrawerToggle mToggle;
    FragmentManager manager;
    FragmentTransaction transaction;


    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(MainActivity.this, new Crashlytics());
        setContentView(R.layout.activity_main);


        // find all id
        init();


        if (CheckInternet.isInternetAvailable(MainActivity.this)) {
            getProfile();
            getCartNumber();
        } else {
            startActivity(new Intent(MainActivity.this, NoInternetActivity.class));
        }

        checkPermissions();

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(mToggle);

        mToggle.syncState();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView.setText("Innfedia");


        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

//        LayoutInflater.from(this).inflate(R.layout.header, navigationView);

        transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();

        onNavigationClick();

        toolbarSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


        toolbarCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myCartIntent = new Intent(MainActivity.this, MyCartActivity.class);
                startActivity(myCartIntent);
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
                        Intent intent = new Intent(MainActivity.this, MyOrderListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                        drawerLayout.closeDrawers();
                        Intent myCartIntent = new Intent(MainActivity.this, MyCartActivity.class);
                        startActivity(myCartIntent);
                        break;
                    case R.id.my_profile:
                        drawerLayout.closeDrawers();
                        Intent intent1 = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.my_logout:
                        drawerLayout.closeDrawers();
                        Dialog dialog = UtilDialog.dialog(MainActivity.this, getResources().getString(R.string.logout), "Logout");
                        dialog.show();
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


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissions) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied += "\n" + per;
                        }

                    }
                }
                return;
            }

        }
    }


    public void getProfile() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        SharedToken sharedToken = new SharedToken(this);
        String token = "Bearer " + sharedToken.getShared();
        Call<ProfileApi> call = apiInterface.getProfile(token);

        call.enqueue(new Callback<ProfileApi>() {
            @Override
            public void onResponse(Call<ProfileApi> call, Response<ProfileApi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(200)) {
                        data = response.body().getData();

                        View hView = navigationView.inflateHeaderView(R.layout.header);
                        ImageView imgvw = (ImageView) hView.findViewById(R.id.imageView);
                        TextView tv = (TextView) hView.findViewById(R.id.textView);
                        Glide.with(MainActivity.this).load(ServiceGenerator.BASE_API_PROFILE_IMAGE + data.getImage()).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).into(imgvw);

                        tv.setText("Hello " + data.getName());


                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "" + response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileApi> call, Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), "" + t.getMessage(), Snackbar.LENGTH_LONG).show();

            }
        });

    }

    private void getCartNumber() {
        SharedToken sharedToken = new SharedToken(MainActivity.this);
        ApiInterface apiInterfac = ServiceGenerator.createService(ApiInterface.class);
        Call<GetAddToCart> call = apiInterfac.getCartNumber("Bearer " + sharedToken.getShared());
        call.enqueue(new Callback<GetAddToCart>() {
            @Override
            public void onResponse(Call<GetAddToCart> call, Response<GetAddToCart> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(200)) {
                        txtcartNumber.setText(response.body().getData().getTotalCartProducts().toString());
                    } else {
                        Snack.snackbar(MainActivity.this, response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAddToCart> call, Throwable t) {

            }
        });
    }


}
