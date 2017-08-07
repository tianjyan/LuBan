package org.tianjyan.luban.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.greenrobot.eventbus.EventBus;
import org.tianjyan.luban.R;
import org.tianjyan.luban.aidl.InPara;
import org.tianjyan.luban.bridge.UIInParaBridge;
import org.tianjyan.luban.event.SetInParaEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InParaDetailActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private InPara inPara;
    private String currentCheckedValue;
    @BindView(R.id.radioGroup) RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_para_detail);
        ButterKnife.bind(this);

        String paraName = getIntent().getStringExtra("paraName");
        String pkgName = getIntent().getStringExtra("pkgName");
        inPara = UIInParaBridge.getInstance().getInPara(paraName, pkgName);

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
        //actionBar.setTitle(String.format("%s -- %s", pkgName, paraName));
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
                EventBus.getDefault().post(new SetInParaEvent(inPara));
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
