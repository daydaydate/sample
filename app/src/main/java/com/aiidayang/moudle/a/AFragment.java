package com.aiidayang.moudle.a;

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

public class AFragment extends BaseFragment {

    private View mView;

    public AFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_a, container, false);
        initView();
        return mView;
    }

    private void initView() {

    }


}
