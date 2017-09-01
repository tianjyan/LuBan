package org.tianjyan.luban.infrastructure.abs.ui;

import android.app.Activity;
import android.app.Fragment;

import dagger.android.AndroidInjection;

public abstract class AbsFragment extends Fragment{
    @Override
    public void onAttach(Activity activity) {
        AndroidInjection.inject(this);
        super.onAttach(activity);
    }
}
