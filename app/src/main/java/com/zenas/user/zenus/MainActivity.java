package com.zenas.user.zenus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeMainFragment homeMainFragment = new HomeMainFragment();
                    FragmentTransaction fragmentHomeTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentHomeTransaction.replace(R.id.content, homeMainFragment);
                    fragmentHomeTransaction.commit();
                    return true;
                case R.id.navigation_chat:
                    ChatMainFragment chatMainFragment = new ChatMainFragment();
                    FragmentTransaction fragmentChatTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentChatTransaction.replace(R.id.content, chatMainFragment);
                    fragmentChatTransaction.commit();
                    return true;
                case R.id.navigation_more:
                    MoreMainFragment moreMainFragment = new MoreMainFragment();
                    FragmentTransaction fragmentMoreTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentMoreTransaction.replace(R.id.content, moreMainFragment);
                    fragmentMoreTransaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        HomeMainFragment homeMainFragment = new HomeMainFragment();
        FragmentTransaction fragmentHomeTransaction = getSupportFragmentManager().beginTransaction();
        fragmentHomeTransaction.replace(R.id.content, homeMainFragment);
        fragmentHomeTransaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
        {
            Intent startPageIntent = new Intent(MainActivity.this, StartPageActivity.class);
            startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(startPageIntent);
            finish();
        }
    }
}
