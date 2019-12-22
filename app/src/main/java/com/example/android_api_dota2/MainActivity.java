package com.example.android_api_dota2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

//rajouter mvvm observer voir aussi material design et revoir mise en cache avec get save
public class MainActivity extends AppCompatActivity implements RecyclerFragCB {
    private FragmentManager manager;
    protected RecyclerFrag heroesList;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialisation si necessaire
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_main);

            // Set a Toolbar to replace the ActionBar.
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            // This will display an Up icon (<-), we will replace it with hamburger later
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Find our drawer view
            mDrawer = findViewById(R.id.drawer_layout);
            // Find our drawer view
            nvDrawer = (NavigationView) findViewById(R.id.nvView);
            // Setup drawer view
            setupDrawerContent(nvDrawer);

            /*SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("DotaAppli", MODE_PRIVATE);
            ControllerAPI response  = new ControllerAPI(this, sharedPreferences, "HeroesData");
            response.start();
            manager = getSupportFragmentManager();
            initHeroList();*/
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("DotaAppli", MODE_PRIVATE);
                ControllerAPI response  = new ControllerAPI(this, sharedPreferences, "HeroesData");
                response.start();
                manager = getSupportFragmentManager();
                initHeroList();
                break;
            case R.id.nav_second_fragment:
                fragmentClass = RecyclerFrag.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = RecyclerFrag.class;
                break;
            default:
                fragmentClass = RecyclerFrag.class;
        }

        /*
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.heroes_content, fragment).commit();*/

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    protected void initHeroList() {
        // regarder si le fragment est deja charge en memoire
        heroesList = (RecyclerFrag) manager.findFragmentByTag("herolist");
        if (heroesList == null) {
            heroesList = new RecyclerFrag();
            heroesList.layout = R.layout.recycler_fragment;
            manager.beginTransaction().add(R.id.heroes_content, heroesList, "herolist").commit();
        } else {
            manager.popBackStackImmediate("herolist", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().show(heroesList).commit();
        }
    }

    @Override
    public void watchDetails(Heroes hero) {
        PictureHero details = new PictureHero();
        details.hero = hero;
        // mise en backstack du fragment actuel avant de le remplacer par la vue en details
        manager.beginTransaction().replace(R.id.heroes_content, details)
                .addToBackStack("herolist").commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // affichage du menu lorsque l'on appuie sur l'icone prevue a cet effet
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}