package com.example.xuguang0417.myprojectaccumulation.app;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * activity堆栈式管理
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {}

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 获取堆栈中activity的size
     */
    public int getActivitySize(){
        return  null == activityStack ? 0 : activityStack.size();
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if(activityStack.size() != 0){
            return  activityStack.lastElement();
        }
        return null;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishTopActivity() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                activityStack.pop().finish();
            }
        }
    }

    public void finishSecondActivity() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (i < size - 2) {
                activityStack.pop().finish();
            }
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 判断当前activity 是否在堆栈中
     * @param activity
     * @return
     */
    public boolean ActivityIsExist(Activity activity){
        return activityStack.contains(activity);
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
       for (int i = 0; i<activityStack.size();i++){
           activityStack.get(i).finish();
       }
        activityStack.clear();
    }

    /**
     * 获取指定的Activity
     * 
     * @author kymjs
     */
    public Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}