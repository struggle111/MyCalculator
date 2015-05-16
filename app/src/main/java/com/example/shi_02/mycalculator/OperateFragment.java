package com.example.shi_02.mycalculator;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shi-02 on 2015/5/16.
 */
public class OperateFragment extends Fragment {

    private View view;

    private Context context;

    public interface OnOperateListener {
        public void numListener(String numStr);

        public void operateListener(String operateStr);

        //重置事件
        public void resetListener();

        //单个字符删除事件
        public void deleteListener();

        //等于的事件
        public void equalListener(String s);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.operator_layout, container, false);

        initView();

        return view;

    }

    private void initView() {

        context = getActivity();

        Resources resources = getResources();

        //数字事件
        for (int i = 0; i < 10; i++) {
            int id = resources.getIdentifier("text_" + i, "id", context.getPackageName());
            final TextView textView = (TextView) view.findViewById(id);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = textView.getText().toString();
                    ((OnOperateListener) context).numListener(num);
                }
            });
        }

        //四则运算事件
        for (int k = 0;k<7;k++){
            int operateId = resources.getIdentifier("operate_" + k, "id", context.getPackageName());
            final TextView operateText = (TextView) view.findViewById(operateId);
            operateText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String operateStr = operateText.getText().toString();
                    ((OnOperateListener) context).operateListener(operateStr);
                }
            });
        }

        view.findViewById(R.id.reset_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnOperateListener)context).resetListener();
            }
        });

        view.findViewById(R.id.delete_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnOperateListener)context).deleteListener();
            }
        });

        final TextView equalText = (TextView) view.findViewById(R.id.equal_text);
        equalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnOperateListener) context).equalListener(equalText.getText().toString());
            }
        });
    }
}
