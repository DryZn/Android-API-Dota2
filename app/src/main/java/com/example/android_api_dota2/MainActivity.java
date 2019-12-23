package com.example.android_api_dota2;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements RecyclerFragCB {
    private FragmentManager manager;
    protected RecyclerFrag heroesList;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navbar;
    private ActionBarDrawerToggle drawerToggle;
    private SharedPreferences sharedPreferences;
    private String heroesListTag;
    protected RecyclerFrag fragmentCalled;

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
        // chaque nouveau fragment est obligatoirement un recyclerfrag dans cette methode
        ControllerAPI response  = new ControllerAPI(this, sharedPreferences);
        String fragTag = "";
        int fragSearchOptions = 0;
        // initialisation du fragment desire
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragTag = heroesListTag;
                fragSearchOptions = R.string.frag_search_abilities;
                break;
            case R.id.nav_second_fragment:
                //fragmentClass = RecyclerFrag.class;
                break;
            case R.id.nav_third_fragment:
                //initHeroList();
                break;
        }

        // regarder si le fragment est deja charge en memoire
        fragmentCalled = (RecyclerFrag) manager.findFragmentByTag(fragTag);
        if (fragmentCalled == null) {
            // instanciation du fragment
            response.dataName = fragTag;
            response.start();
            fragmentCalled = new RecyclerFrag();
            fragmentCalled.fragSearchTag = getResources().getString(fragSearchOptions);
            manager.beginTransaction().add(R.id.heroes_content, fragmentCalled, fragTag).commit();
        } else {
            // restauration du fragment
            manager.popBackStackImmediate(fragTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().show(fragmentCalled).commit();
        }
        // Highlight the selected item has been done by NavigationView and Set action bar title
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
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