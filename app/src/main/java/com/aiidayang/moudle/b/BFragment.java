package com.aiidayang.moudle.b;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.aiidayang.R;
import com.aiidayang.base.BaseFragment;
import com.aiidayang.moudle.constant.IConstant;
import com.base.untils.NetUtils;

public class BFragment extends BaseFragment {

    private View mView;

    public BFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_b, container, false);
        initView();
        return mView;
    }

    private void initView() {

    }



}
