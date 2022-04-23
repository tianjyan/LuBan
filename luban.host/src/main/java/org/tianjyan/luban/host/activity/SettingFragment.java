package org.tianjyan.luban.host.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import androidx.annotation.Nullable;

import org.tianjyan.luban.host.R;
import org.tianjyan.luban.infrastructure.abs.SettingKey;

public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
    }

    @Override
    public void onResume() {
        super.onResume();
        final SwitchPreference autoSaveLogPref = (SwitchPreference) findPreference(getString(R.string.pref_auto_save_log));
        String allowAutoSave = ((SettingActivity) getActivity()).getSetting(SettingKey.AUTO_SAVE_LOG, "true");
        autoSaveLogPref.setChecked(allowAutoSave == "true" ? true : false);
        autoSaveLogPref.setOnPreferenceClickListener(preference -> {
            ((SettingActivity) getActivity()).putSetting(SettingKey.AUTO_SAVE_LOG, autoSaveLogPref.isChecked() ? "true" : "false");
            return true;
        });
    }
}
