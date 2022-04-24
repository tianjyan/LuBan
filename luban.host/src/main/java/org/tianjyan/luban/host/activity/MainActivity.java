package org.tianjyan.luban.host.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.tianjyan.luban.host.R;
import org.tianjyan.luban.host.model.OnFunctionSelected;
import org.tianjyan.luban.infrastructure.abs.plugin.ILogPlugin;
import org.tianjyan.luban.infrastructure.abs.SettingKey;
import org.tianjyan.luban.infrastructure.abs.plugin.IInParaPlugin;
import org.tianjyan.luban.infrastructure.abs.plugin.ILogcatPlugin;
import org.tianjyan.luban.infrastructure.abs.plugin.IOutParaPlugin;
import org.tianjyan.luban.infrastructure.abs.plugin.IPlugin;
import org.tianjyan.luban.plugin.common.AliasName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity implements OnFunctionSelected {
    private static boolean active = false;
    private static int OVERLAY_PERMISSION_REQ_CODE = 0x01;
    private MainMenuFragment mainMenuFragment;
    private Map<String, Fragment> menuItems = new HashMap<>();
    private Map<String, IPlugin> pluginItems = new HashMap<>();
    private List<String> menu = new ArrayList<>();

    @BindView(R.id.main_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_navigation_drawer) View mDrawerView;
    private ActionBarDrawerToggle mDrawerToggle;
    @Inject @Named(AliasName.OUT_PARA_PLUGIN) IOutParaPlugin outPlugin;
    @Inject @Named(AliasName.IN_PARA_PLUGIN) IInParaPlugin inPlugin;
    @Inject @Named(AliasName.LOG_PLUGIN) ILogPlugin logPlugin;
    @Inject @Named(AliasName.LOGCAT_PLUGIN) ILogcatPlugin logcatPlugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        active = true;

        initPlugin(outPlugin, inPlugin, logPlugin, logcatPlugin);
        initDrawer();
        initFragment();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestDrawOverLays();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
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
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.setting:
                Intent settingIntent = new Intent(this, SettingActivity.class);
                startActivity(settingIntent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestDrawOverLays() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        }
    }

    public static boolean isActive() {
        return active;
    }

    private void initPlugin(IPlugin... plugins) {
        for (IPlugin plugin : plugins) {
            menuItems.put(plugin.getPluginName(), null);
            pluginItems.put(plugin.getPluginName(), plugin);
            menu.add(plugin.getPluginName());
        }
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
            getSupportActionBar().setTitle(outPlugin.getPluginName());
        }
    }

    private void initFragment()  {
        FragmentManager fragmentManager = getSupportFragmentManager();

        mainMenuFragment = (MainMenuFragment) fragmentManager.findFragmentByTag("MainMenuFragment");
        if (mainMenuFragment == null) {
            mainMenuFragment = new MainMenuFragment();
            mainMenuFragment.setOnFunctionSelected(this);
            mainMenuFragment.setDataSource(menu);
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_navigation_drawer, mainMenuFragment, "MainMenuFragment");
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onFunctionSelected(String functionName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragment : menuItems.values()) {
            if (fragment != null) transaction.hide(fragment);
        }

        Fragment fragment = menuItems.get(functionName);
        if (fragment == null) {
            IPlugin plugin = pluginItems.get(functionName);
            fragment = plugin.getPluginFragment();
            menuItems.put(plugin.getPluginName(), fragment);
            transaction.add(R.id.main_container, fragment, fragment.getClass().getSimpleName());
        } else {
            transaction.show(fragment);
        }

        transaction.commitAllowingStateLoss();
        mDrawerLayout.closeDrawer(mDrawerView);
    }
}
