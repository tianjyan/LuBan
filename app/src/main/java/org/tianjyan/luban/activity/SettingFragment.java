package org.tianjyan.luban.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

import org.tianjyan.luban.R;
import org.tianjyan.luban.model.SettingKey;

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
