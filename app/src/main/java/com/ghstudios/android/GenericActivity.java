package com.ghstudios.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ghstudios.android.GenericActionBarActivity;
import com.ghstudios.android.mhgendatabase.R;

/*
 * Any subclass needs to:
 *  - override onCreate() to set title
 *  - override createFragment() for detail fragments
 */

public abstract class GenericActivity extends GenericActionBarActivity {

    private Fragment detail;

	protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            detail = fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment)
                    .commit();
        }

        // Integrate Toolbar so sliding drawer can go over toolbar
        android.support.v7.widget.Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        super.setupDrawer(); // Needs to be called after setContentView
        // Disabled by request. Turns into BACK button
        //super.enableDrawerIndicator(); // Enable drawer toggle button
    }

    public Fragment getDetail() {
        return detail;
    }

    public void showFatalError() {
        // todo: implement
        // make it override the fragment with a fatal error message
    }
}
