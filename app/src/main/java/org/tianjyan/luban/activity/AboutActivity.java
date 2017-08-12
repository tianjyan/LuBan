package org.tianjyan.luban.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;

import org.tianjyan.luban.BuildConfig;
import org.tianjyan.luban.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.commit_id) TextView commitIdTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.about));

        commitIdTV.setText(String.format("Build Commit: %s", BuildConfig.GIT_COMMIT_SHA));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.author_homepage)
    public void openAuthorHomePage() {
        openLink("https://youngytj.github.io");
    }

    @OnClick(R.id.project_site)
    public void openProjectSite() {
        openLink("https://github.com/youngytj/LuBan");
    }

    @OnClick(R.id.commit_id)
    public void openCommitId() {
        openLink(String.format("https://github.com/youngytj/LuBan/commit/%s", BuildConfig.GIT_COMMIT_SHA));
    }

    private void openLink(String link) {
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
