package org.tianjyan.luban.activity;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
    private LogFragment logFragment;
    private PerformanceFragment performanceFragment;

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
        functions.add(getString(R.string.function_log));
        functions.add(getString(R.string.function_performance));
        initDrawer();
        initFragment();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestDrawOverLays();
        }
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
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
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
        if (logFragment != null) transaction.hide(logFragment);
        if (performanceFragment != null) transaction.hide(performanceFragment);

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
        } else if (functionName.equals(R.string.function_log)) {
            if (logFragment == null) {
                logFragment = new LogFragment();
                transaction.add(R.id.main_container, logFragment, "LogFragment");
            } else {
                transaction.show(logFragment);
            }
        } else if (functionName.equals(R.string.function_performance)) {
            if (performanceFragment == null) {
                performanceFragment = new PerformanceFragment();
                transaction.add(R.id.main_container, logFragment, "PerformanceFragment");
            } else {
                transaction.show(performanceFragment);
            }
        }
        transaction.commitAllowingStateLoss();
        mDrawerLayout.closeDrawer(mDrawerView);
    }

    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;

    @TargetApi(Build.VERSION_CODES.M)
    public void requestDrawOverLays() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
            }
        }
    }
}
