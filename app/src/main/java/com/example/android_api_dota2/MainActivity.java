package com.example.android_api_dota2;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private NavigationView navbar;
    private ActionBarDrawerToggle drawerToggle;
    private SharedPreferences sharedPreferences;
    private String heroesListTag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialisation si necessaire
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_main);
            // initialisation des tags
            heroesListTag = getResources().getString(R.string.frag_hero_list_tag);
            // pour les appels a l'api
            sharedPreferences = getBaseContext().getSharedPreferences("DotaAppli", MODE_PRIVATE);
            // pour la gestion des fragments
            manager = getSupportFragmentManager();
            // Le drawer permet de gerer l'affichage du toolbar/navbar
            mDrawer = findViewById(R.id.drawer_layout);
            // initialisation du toolbar
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // attachement du drawer et du toolbar pour pouvoir afficher une icone "hamburger" (avec animations)
            drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.syncState();
            mDrawer.addDrawerListener(drawerToggle);

            // initialisation du navbar et lancement du fragment voulu
            navbar = findViewById(R.id.navbar);
            initNavbarContent(navbar);
            showNewFragment(navbar.getMenu().getItem(0));
        } else {
            // affichage des fragments deja visibles
            try{heroesList.isVisible();} catch (Exception e){}
        }
    }

    // association des fonctions liees au menu
    private void initNavbarContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        showNewFragment(menuItem);
                        return true;
                    }
                });
    }

    public void showNewFragment(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                // initialisation du fragment de la liste des heros
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
        } catch (Exception e) {e.printStackTrace();}
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.heroes_content, fragment).commit();*/

        // Highlight the selected item has been done by NavigationView and Set action bar title
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    protected void initHeroList() {
        // regarder si le fragment est deja charge en memoire
        heroesList = (RecyclerFrag) manager.findFragmentByTag(heroesListTag);
        if (heroesList == null) {
            ControllerAPI response  = new ControllerAPI(this, sharedPreferences, heroesListTag);
            response.start();
            heroesList = new RecyclerFrag();
            manager.beginTransaction().add(R.id.heroes_content, heroesList, heroesListTag).commit();
        } else {
            manager.popBackStackImmediate(heroesListTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().show(heroesList).commit();
        }
    }

    // callback du recyclerview de recyclerfrag pour voir ses details
    @Override
    public void watchDetails(Object hero) {
        switch (hero.getClass().getSimpleName()) {
            case "Heroes":
                PictureHero details = new PictureHero();
                details.hero = (Heroes) hero;
                // mise en backstack du fragment actuel avant de le remplacer par la vue en details
                manager.beginTransaction().replace(R.id.heroes_content, details)
                        .addToBackStack(heroesListTag).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // affichage du menu lorsque l'on appuie sur l'icone prevue a cet effet en prenant les evenements du toolbar
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ces deux surcharges permettent de restaurer la vue correctement meme si l'on a bascule l'ecran entre-temps
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }
}