package org.tianjyan.luban.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.tianjyan.luban.R;
import org.tianjyan.luban.model.OnFunctionSelected;
import org.tianjyan.luban.model.SettingKey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements OnFunctionSelected {
    private static boolean active = false;
    private List<String> functions;

    @BindView(R.id.main_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.main_navigation_drawer) View mDrawerView;
    private ActionBarDrawerToggle mDrawerToggle;

    private MainMenuFragment mainMenuFragment;
    private OutParaFragment outParaFragment;
    private InParaFragment inParaFragment;

    public static boolean isActive() {
        return active;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        active = true;
        functions = new ArrayList<>();
        functions.add(getString(R.string.function_out_para));
        functions.add(getString(R.string.function_in_para));
        initDrawer();
        initFragment();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mDrawerLayout.isDrawerOpen(mDrawerView)) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        switch (id) {
            case R.id.about:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

    private void initDrawer() {
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                String itemSelected = getSetting(SettingKey.LAST_SHOW_ITEM, "");
                if ("".equals(itemSelected))
                    itemSelected = getString(R.string.app_name);
                getSupportActionBar().setTitle(itemSelected);
            }
        };

        mDrawerLayout.post(() -> mDrawerToggle.syncState());
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        if ("false".equals(getSetting(SettingKey.IS_SHOWED_DRAWER, "false"))) {
            putSetting(SettingKey.IS_SHOWED_DRAWER, "true");
            mDrawerLayout.openDrawer(mDrawerView);
        } else {
            getSupportActionBar().setTitle(R.string.function_out_para);
        }
    }

    private void initFragment()  {
        FragmentManager fragmentManager = getFragmentManager();

        mainMenuFragment = (MainMenuFragment) fragmentManager.findFragmentByTag("MainMenuFragment");
        if (mainMenuFragment == null) {
            mainMenuFragment = new MainMenuFragment();
            mainMenuFragment.setOnFunctionSelected(this);
            mainMenuFragment.setDataSource(functions);
        }


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_navigation_drawer, mainMenuFragment, "MainMenuFragment");
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onFunctionSelected(String functionName) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (outParaFragment != null) transaction.hide(outParaFragment);
        if (inParaFragment != null) transaction.hide(inParaFragment);

        if (functionName.equals(getResources().getString(R.string.function_out_para))) {
            if (outParaFragment == null) {
                outParaFragment = new OutParaFragment();
                transaction.add(R.id.main_container, outParaFragment, "OutParaFragment");
            } else {
                transaction.show(outParaFragment);
            }
        } else if (functionName.equals(getResources().getString(R.string.function_in_para))) {
            if (inParaFragment == null) {
                inParaFragment = new InParaFragment();
                transaction.add(R.id.main_container, inParaFragment, "InParaFragment");
            } else {
                transaction.show(inParaFragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }
}
