package com.yptake.yplearnproject.mvp.ui.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;


import com.yptake.yplearnproject.R;

import java.util.Objects;

import butterknife.ButterKnife;

/**
 * 弹窗~
 */
public abstract class BaseDialogFragment extends AppCompatDialogFragment {


    private static final String TAG = "BaseDialogFragment";

    private static final float DEFAULT_DIM = 0.5f;

    @LayoutRes
    public abstract int getResId();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_fragment_style);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(getCancelOutside());
        View view = inflater.inflate(getResId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        super.onActivityCreated(savedInstanceState);
        //设置背景为透明
        if (window != null) {
            window.setBackgroundDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.color.transparent));
            //设置弹窗大小为会屏
            window.setLayout(getWidth(), getHeight());
            //去除阴影
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = getDimAmount();
            layoutParams.gravity = getGravity();
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(layoutParams);
        }
    }


    public int getGravity() {
        return Gravity.CENTER;
    }

    public int getWidth() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public float getDimAmount() {
        return DEFAULT_DIM;
    }

    public boolean getCancelOutside() {
        return true;
    }

    public String getFragmentTag() {
        return TAG;
    }

    public void showDialog(FragmentManager manager) {
        show(manager, getFragmentTag());
    }

    public void showDialog(FragmentManager manager, String tag) {
        show(manager, tag);
    }

    public void dismissDialog() {
        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }

}
