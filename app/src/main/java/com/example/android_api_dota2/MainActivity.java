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

import java.sql.Array;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerFragCB {
    private FragmentManager manager;
    protected RecyclerFrag heroesList;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navbar;
    private ActionBarDrawerToggle drawerToggle;
    private SharedPreferences sharedPreferences;
    protected RecyclerFrag[] fragmentsCalled;
    private int currentFrag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialisation si necessaire
        if (savedInstanceState == null) {
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
            setContentView(R.layout.activity_main);
            fragmentsCalled = new RecyclerFrag[navbar.getMenu().size()];
            currentFrag = 0;
            showNewFragment(R.id.nav_first_fragment);
        } else {
            // affichage des fragments deja visibles
            for (int i = 0; i < fragmentsCalled.length; i++) {
                try {
                    fragmentsCalled[i].isVisible();
                } catch (Exception e) {}
            }
        }
    }

    // association des fonctions liees au menu
    private void initNavbarContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        handleMenuItemSelection(menuItem);
                        return true;
                    }
                });
    }

    // selon le type du parametre on va faire un appel a l'api ou plus
    public void handleMenuItemSelection(MenuItem menuItem) {
        // Highlight the selected item has been done by NavigationView and Set action bar title
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        showNewFragment(menuItem.getItemId());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    public void showNewFragment(int idFragment) {
        // chaque nouveau fragment est obligatoirement un recyclerfrag dans cette methode
        int fragTagID = 0;
        int fragIdentifier = 0;
        int fragSearchOptions = 0;
        boolean requestReady = true;
        // initialisation du fragment desire en se basant sur les donnees de string.xml
        switch(idFragment) {
            case R.id.nav_first_fragment:
                fragTagID = R.string.frag_hero_list_tag;
                fragSearchOptions = R.string.frag_search_abilities;
                fragIdentifier = R.id.heroes_content;
                break;
            case R.id.nav_second_fragment:
                fragTagID = R.string.frag_community_tag;
                fragSearchOptions = R.string.frag_search_community;
                fragIdentifier = R.id.community_content;
                // specifier lorsque le fragment choisi ne doit pas encore lancer d'appel api ici
                requestReady = false;
                break;
        }

        // regarder si le fragment est deja charge en memoire
        String fragTag = getResources().getString(fragTagID);
        System.out.println(fragmentsCalled[currentFrag]);
        fragmentsCalled[currentFrag] = (RecyclerFrag) manager.findFragmentByTag(fragTag);
        if (fragmentsCalled[currentFrag] == null) {
            // instanciation du fragment
            fragmentsCalled[currentFrag] = new RecyclerFrag();
            System.out.println(fragmentsCalled[currentFrag]);
            fragmentsCalled[currentFrag].fragSearchTagID = fragSearchOptions;
            manager.beginTransaction().replace(fragIdentifier, fragmentsCalled[currentFrag], fragTag).commit();
        } else {
            // restauration du fragment
            manager.popBackStackImmediate(fragTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().show(fragmentsCalled[currentFrag]).commit();
        }
        if (requestReady) launchResponse(fragTagID, "");
        currentFrag += 1;
    }

    protected void launchResponse(int fragTagID, String searchedData){
        ControllerAPI response = new ControllerAPI(this, sharedPreferences, fragTagID);
        response.searchedData = searchedData;
        response.start();
    }

    protected void updateRecyclerFrag (List<Heroes> heroesSortedList) {
        fragmentsCalled[currentFrag].data = heroesSortedList;
        fragmentsCalled[currentFrag].adaptater = new HerosAdapter(fragmentsCalled[currentFrag], heroesSortedList);
        fragmentsCalled[currentFrag].list_items.swapAdapter(fragmentsCalled[currentFrag].adaptater, false);
        fragmentsCalled[currentFrag] = null;
    }

    // callback du recyclerview de recyclerfrag pour voir ses details
    @Override
    public void watchDetails(Object object) {
        switch (object.getClass().getSimpleName()) {
            case "Heroes":
                PictureHero details = new PictureHero();
                details.hero = (Heroes) object;
                // mise en backstack du fragment actuel avant de le remplacer par la vue en details
                manager.beginTransaction().replace(R.id.heroes_content, details)
                        .addToBackStack(getResources().getString(R.string.frag_hero_list_tag)).commit();
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