package com.anshi.lazyshopmall.utils;

import android.app.Activity;
import android.app.Dialog;

import com.kaopiz.kprogresshud.KProgressHUD;


public class DialogBuild {

    private static DialogBuild build;


    public interface OnConfirmListener {
        void onConfirm(Dialog dialog, boolean isConfirm);
    }

    private DialogBuild() {

    }

    public static DialogBuild getBuild() {
        if (build == null) {
            build = new DialogBuild();
        }
        return build;
    }




    public KProgressHUD createCommonLoadDialog(Activity activity,String message){
        return KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(message)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    public KProgressHUD createCommonProgressDialog(Activity activity,String message){
        return KProgressHUD.create(activity)
                .setCancellable(true)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setMaxProgress(100)
                .setLabel(message)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

}