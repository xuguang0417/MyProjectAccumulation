package com.example.xuguang0417.myprojectaccumulation.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.example.xuguang0417.myprojectaccumulation.R;
import com.example.xuguang0417.myprojectaccumulation.app.AppManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xuguang on 16/7/5.
 * activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    private static Boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setTheme();
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this); // 绑定注解
        initView();
        initData();
        setListener();
    }

    public void setTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.toolbar_color));
        }
    }

    public abstract int getLayoutId();

    public void init(Bundle savedInstanceState) {
    }

    public void initView() {
    }

    public void initData() {
    }

    public void setListener() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
