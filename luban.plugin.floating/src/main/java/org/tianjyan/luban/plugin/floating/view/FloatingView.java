package org.tianjyan.luban.plugin.floating.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.plugin.common.event.AddFloatingOutParaEvent;
import org.tianjyan.luban.plugin.common.event.RemoveFloatingOutParaEvent;
import org.tianjyan.luban.plugin.floating.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FloatingView {
    private Handler handler;
    private WindowManager windowManager;
    private WindowManager.LayoutParams logoLayoutParams;
    private WindowManager.LayoutParams detailLayoutParams;
    private FloatingAdapter floatingAdapter;
    private List<OutPara> floatingParas = Collections.synchronizedList(new ArrayList<>());
    private Context context;
    private LinearLayout logoLinearLayout;
    private LinearLayout detailLinearLayout;
    private View logo;
    private RecyclerView recyclerView;

    private static final int HIDE_TIMEOUT = 3000;
    private static final int HIDE = 1;
    private static final float ALPHA = 0.75f;
    private static final float DEFAULT_ALPHA = 1.0f;

    private DisplayMetrics mDisplayMetrics;
    private long lastTouchTimeMillis;
    private float offsetX, offsetY;
    private float downX, downY;
    private boolean isLogoShowing;
    private boolean isDetailShowing;
    private int mTouchSlop;

    public FloatingView(Context context) {
        this.context = context;
        EventBus.getDefault().register(this);
        windowManager = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
        mTouchSlop = ViewConfiguration.get(this.context).getScaledTouchSlop();
        mDisplayMetrics = this.context.getResources().getDisplayMetrics();
        floatingAdapter = new FloatingAdapter(context, floatingParas);
        handler = new Handler(FloatingView.this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case HIDE:
                        if (System.currentTimeMillis() - lastTouchTimeMillis >= HIDE_TIMEOUT) {
                            getLogoLayoutParams().alpha = ALPHA;
                            windowManager.updateViewLayout(getLogoContentView(), getLogoLayoutParams());
                        }
                        break;
                }
            }
        };
    }

    public void showLogo() {
        if (!permissionAllowed()) return;
        if (!isLogoShowing) {
            windowManager.addView(getLogoContentView(), getLogoLayoutParams());
            isLogoShowing = true;
            postHideMessage();
        }
    }

    public void hideLogo() {
        if (!permissionAllowed()) return;
        if (isLogoShowing) {
            windowManager.removeView(getLogoContentView());
            handler.removeMessages(HIDE);
            isLogoShowing = false;
        }
    }

    public void showDetail() {
        if (!permissionAllowed()) return;
        if (!isDetailShowing) {
            windowManager.addView(getDetailContentView(), getDetailLayoutParams());
            isDetailShowing = true;
        }
    }

    public void hideDetail() {
        if (!permissionAllowed()) return;
        if (isDetailShowing) {
            windowManager.removeView(getDetailContentView());
            isDetailShowing = false;
        }
    }

    private int getStatusBarHeight() {
        int height = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            height = context.getResources().getDimensionPixelSize(resId);
        }
        return height;
    }

    private WindowManager.LayoutParams getLogoLayoutParams() {
        if (logoLayoutParams == null) {
            logoLayoutParams = new WindowManager.LayoutParams();
            logoLayoutParams.flags = logoLayoutParams.flags
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            logoLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            logoLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            logoLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
            logoLayoutParams.height = (int) context.getResources().getDimension(R.dimen.floating_logo_size);
            logoLayoutParams.format = PixelFormat.RGBA_8888;
            logoLayoutParams.alpha = DEFAULT_ALPHA;
            logoLayoutParams.y = mDisplayMetrics.heightPixels / 4 - getStatusBarHeight();
        }
        return logoLayoutParams;
    }

    private LinearLayout getLogoContentView() {
        if (logoLinearLayout == null) {
            logoLinearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.floating_logo, null);

            logoLinearLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            offsetY = getStatusBarHeight() + logoLinearLayout.getMeasuredHeight() / 2;
            offsetX = logoLinearLayout.getMeasuredWidth() / 2;
            logo = logoLinearLayout.findViewById(R.id.logo);

            logoLinearLayout.setOnTouchListener(new LogoTouchListener());
            logo.setOnTouchListener(new LogoTouchListener());
        }
        return logoLinearLayout;
    }

    private void updateLocation(float x, float y, boolean offset) {
        if (getLogoContentView() != null) {
            getLogoLayoutParams().x = offset ? (int) (x - offsetX) : (int) x;
            getLogoLayoutParams().y = offset ? (int) (y - offsetY) : (int) y;
            windowManager.updateViewLayout(getLogoContentView(), getLogoLayoutParams());
        }
    }

    class LogoTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    down(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int deltaX = (int) (event.getRawX() - downX);
                    int deltaY = (int) (event.getRawY() - downY);
                    if (!(Math.abs(deltaX) < mTouchSlop && Math.abs(deltaY) < mTouchSlop)) {
                        move(event);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    up(event);
                    break;
                default:
                    break;
            }
            return false;
        }

        private void down(MotionEvent event) {
            downX = event.getRawX();
            downY = event.getRawY();
            getLogoLayoutParams().alpha = DEFAULT_ALPHA;
            lastTouchTimeMillis = System.currentTimeMillis();
            windowManager.updateViewLayout(getLogoContentView(), getLogoLayoutParams());
        }

        private void move(MotionEvent event) {
            lastTouchTimeMillis = System.currentTimeMillis();
            updateLocation(event.getRawX(), event.getRawY(), true);
        }

        private void up(MotionEvent event) {
            int deltaX = (int) (event.getRawX() - downX);
            int deltaY = (int) (event.getRawY() - downY);
            if (Math.abs(deltaX) < mTouchSlop && Math.abs(deltaY) < mTouchSlop) {
                if (isTouchTarget(event, logo)) {
                    hideLogo();
                    showDetail();
                }
            }
            // avoid post message if the view is removed from windows
            if (isLogoShowing) {
                postHideMessage();
            }
        }

        private boolean isTouchTarget(MotionEvent ev, View view) {
            if (view != null && view.getVisibility() == View.VISIBLE) {
                Rect bounds = new Rect();
                view.getGlobalVisibleRect(bounds);
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                if (bounds.contains(x, y)) {
                    return true;
                }
            }
            return false;
        }
    }

    private void postHideMessage() {
        handler.removeMessages(HIDE);
        handler.sendEmptyMessageDelayed(HIDE, HIDE_TIMEOUT);
    }

    private WindowManager.LayoutParams getDetailLayoutParams() {
        if (detailLayoutParams == null) {
            detailLayoutParams = new WindowManager.LayoutParams();
            detailLayoutParams.flags = detailLayoutParams.flags
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            detailLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            detailLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            detailLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            detailLayoutParams.gravity = Gravity.CENTER;
            detailLayoutParams.format = PixelFormat.RGBA_8888;
            detailLayoutParams.alpha = DEFAULT_ALPHA;
        }
        return detailLayoutParams;
    }

    private LinearLayout getDetailContentView() {
        if (detailLinearLayout == null) {
            detailLinearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.floating_detail, null);
            recyclerView = (RecyclerView) detailLinearLayout.findViewById(R.id.para_rv);
            recyclerView.setAdapter(floatingAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
            dividerLine.setSize(1);
            dividerLine.setColor(R.color.para_line_color);
            recyclerView.addItemDecoration(dividerLine);
            recyclerView.setOnTouchListener(new DetailTouchListener());
            detailLinearLayout.setOnTouchListener(new DetailTouchListener());
        }
        return detailLinearLayout;
    }

    class DetailTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                hideDetail();
                showLogo();
            }
            return false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddFloatingOutPara(AddFloatingOutParaEvent event) {
        floatingParas.add(event.getOutPara());
        floatingAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRemoveFloatingOutPara(RemoveFloatingOutParaEvent event) {
        floatingParas.remove(event.getOutPara());
        floatingAdapter.notifyDataSetChanged();
    }

    public void outParaValueUpdate(OutPara outPara, String value) {
        Observable.just(outPara)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(val -> {
                    floatingAdapter.notifyDataSetChanged();
                });
    }

    public void clientDisconnect(String pkgName) {
        Observable.just(pkgName)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(val -> {
                    floatingParas.removeIf(outPara -> outPara.getClient().equals(pkgName));
                    floatingAdapter.notifyDataSetChanged();
                });
    }

    private boolean permissionAllowed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }
}
