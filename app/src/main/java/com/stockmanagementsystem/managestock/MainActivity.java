package com.stockmanagementsystem.managestock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.stockmanagementsystem.managestock.activity.AddMedicineActivity;
import com.stockmanagementsystem.managestock.activity.CustomersActivity;
import com.stockmanagementsystem.managestock.activity.LoginActivity;
import com.stockmanagementsystem.managestock.activity.RegisterActivity;
import com.stockmanagementsystem.managestock.activity.SalesActivity;
import com.stockmanagementsystem.managestock.fragments.AddFragment;
import com.stockmanagementsystem.managestock.fragments.HomeFragment;
import com.stockmanagementsystem.managestock.fragments.ReportsFragment;
import com.stockmanagementsystem.managestock.modelCLasses.UserPreferences;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Toolbar customToolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigationView;

    TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        readData();

        customToolbar = findViewById(R.id.custom_toolbar);

        ImageView iv = customToolbar.findViewById(R.id.appInfo);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        setSupportActionBar(customToolbar);

        setDrawerLayouts();

        setBottomNavigation();

        switchFragment(new HomeFragment());

        setDefaultFragment();


        UserPreferences u = new UserPreferences(MainActivity.this);

        headerText.setText(u.getUsername());

    }

    public void setDrawerLayouts() {
        drawerLayout = findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, customToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        headerText = headerView.findViewById(R.id.nav_user_name);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_item_1) {
                    finish();
                    Intent i = new Intent(MainActivity.this, AddMedicineActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_item_2) {
                    finish();
                    Intent i = new Intent(MainActivity.this, CustomersActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_item_3) {
                    finish();
                    Intent i = new Intent(MainActivity.this, SalesActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_item_4) {
                    logOutDialog();
                }
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    public void setBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_add:
                        selectedFragment = new AddFragment();
                        break;
                    case R.id.nav_reports:
                        selectedFragment = new ReportsFragment();
                        break;
                }
                replaceFragment(selectedFragment);
                return true;
            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment());
        transaction.commit();
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    public void logOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Dismiss the dialog and do nothing
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Dismiss the dialog and do nothing
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        ;
    }

    private void readData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("bill").document(uid).collection("data").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        // Retrieve the data
                        String name = documentSnapshot.getString("name");
                        String email = documentSnapshot.getString("email");
                        String phone = documentSnapshot.getString("phone");
                        String address = documentSnapshot.getString("address");

                        UserPreferences userPreferences = new UserPreferences(getApplicationContext());
                        userPreferences.saveUserData(name, uid, email);


                    }
                })
                .addOnFailureListener(e -> {
                    // Failed to retrieve data
                });

    }

    private void showCustomDialog() {
        // Create the dialog
        final Dialog dialog = new Dialog(MainActivity.this);

        // Set the layout for the dialog
        dialog.setContentView(R.layout.custom_dialog);

        // Set the background color of the dialog window
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }

        // Get the views in the dialog
        TextView textView = dialog.findViewById(R.id.text_view);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        // Set the text of the text view

        // Set a click listener on the OK button
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something when the OK button is clicked
                dialog.dismiss(); // Close the dialog
            }
        });

        // Set a click listener on the Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something when the Cancel button is clicked
                dialog.dismiss(); // Close the dialog
            }
        });

        // Show the dialog
        dialog.show();
    }


}