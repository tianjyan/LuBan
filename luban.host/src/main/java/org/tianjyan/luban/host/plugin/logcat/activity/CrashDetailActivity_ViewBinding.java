// Generated code from Butter Knife. Do not modify!
package org.tianjyan.luban.host.plugin.logcat.activity;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;

import org.tianjyan.luban.host.R;

import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CrashDetailActivity_ViewBinding implements Unbinder {
  private CrashDetailActivity target;

  @UiThread
  public CrashDetailActivity_ViewBinding(CrashDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CrashDetailActivity_ViewBinding(CrashDetailActivity target, View source) {
    this.target = target;

    target.textView = Utils.findRequiredViewAsType(source, R.id.crash_detail, "field 'textView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CrashDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textView = null;
  }
}
