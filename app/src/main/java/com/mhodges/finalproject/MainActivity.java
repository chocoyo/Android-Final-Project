package com.mhodges.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int RC_SIGN_IN = 0;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get signed in user or make user sign in
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null)
        {
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }
        else  {
            FirebaseUser user = mAuth.getCurrentUser();
            setUpUIForUser(user);
        }

        //Configure Toolbar
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        //Configure NavDrawer
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Configure NavView
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Sets up UI when user is logged in
    private void setUpUIForUser(FirebaseUser user){
        NavigationView navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        View headerView = navigationView.getHeaderView(0);

        ((TextView) headerView.findViewById(R.id.tvUsername)).setText(user.getDisplayName());

        Uri photoURL = user.getPhotoUrl();
        if (photoURL != null)
        {
            ImageView imageView = (ImageView) headerView.findViewById(R.id.ivProfilePicture);
            Glide.with(this).load(photoURL.toString()).into(imageView);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                setUpUIForUser(user);
            } else {
                Toast.makeText(this, "Sign In Failed, Please Try Again Later", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    private void createNewList(ListOfListsFragment fragment){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New List");

        // Set up the input
        final EditText input = new EditText(this);
        input.setHint("Name Of List");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ItemList list = new ItemList(input.getText().toString(), user.getUid());
                FirebaseFirestore.getInstance().collection("lists").add(list);
                fragment.updateData();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void createNewItem(ListOfItemsFragment fragment){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Item");

        // Set up the input
        final EditText input = new EditText(this);
        input.setHint("Name Of Item");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Item item = new Item(input.getText().toString(), user.getUid());
                FirebaseFirestore.getInstance().collection("lists").document(fragment.list.getDocumentId()).collection("items").add(item);
                fragment.updateData();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.activity_main_drawer_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, ListOfListsFragment.newInstance()).commit();
                break;
            case R.id.activity_main_drawer_settings:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                finish();
                                startActivity(new Intent(MainActivity.this, MainActivity.this.getClass()));
                                Toast.makeText(getApplicationContext(), "Sign Out Successful", Toast.LENGTH_LONG).show();
                            }
                        });
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment test = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        boolean returnVal = false;
        switch (item.getItemId()) {
            case R.id.action_new:
                if (test instanceof ListOfItemsFragment){
                    createNewItem((ListOfItemsFragment) test);
                    returnVal = true;
                }
                else if (test instanceof ListOfListsFragment){
                    createNewList((ListOfListsFragment) test);
                    returnVal = true;
                }
                break;
        }
        return returnVal;
    }
}