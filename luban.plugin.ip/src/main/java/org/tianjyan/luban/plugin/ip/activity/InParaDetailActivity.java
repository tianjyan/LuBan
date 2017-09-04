package org.tianjyan.luban.plugin.ip.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.plugin.common.consts.AliasName;
import org.tianjyan.luban.plugin.ip.R2;
import org.tianjyan.luban.plugin.ip.R;
import org.tianjyan.luban.plugin.ip.bridge.UIInParaBridge;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class InParaDetailActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    @Inject @Named(AliasName.IN_PARA_BRIDGE) UIInParaBridge inParaBridge;
    private InPara inPara;
    private String currentCheckedValue;
    @BindView(R2.id.radioGroup) RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_para_detail);
        ButterKnife.bind(this);
        String paraName = getIntent().getStringExtra("paraName");
        String pkgName = getIntent().getStringExtra("pkgName");
        inPara = inParaBridge.getInPara(paraName, pkgName);

        for (String value : inPara.getValues()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(value);
            radioGroup.addView(radioButton);
            radioGroup.setOnCheckedChangeListener(this);
            if (value.equals(inPara.getSelectedValue())) {
                radioGroup.check(radioButton.getId());
            }
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(paraName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.para_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_done) {
            if (!inPara.getSelectedValue().equals(currentCheckedValue)) {
                inPara.setSelectedValue(currentCheckedValue);
                inParaBridge.setInPara(inPara);
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
        currentCheckedValue = radioButton.getText().toString();
    }
}
