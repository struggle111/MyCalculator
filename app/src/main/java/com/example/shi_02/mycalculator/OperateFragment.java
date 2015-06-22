package com.example.shi_02.mycalculator;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shi-02 on 2015/5/16.
 */
public class OperateFragment extends Fragment {

    //操作界面的View
    private View view;
    //上下文
    private Context context;

    private boolean eb = false;

    //定义接口，完成控件的点击事件
    public interface OnOperateListener {
        //数字按钮的点击事件
        public void numListener(String numStr);

        //操作按钮的点击事件
        public void operateListener(String operateStr);

        //重置事件
        public void resetListener();

        //单个字符删除事件
        public void deleteListener();

        //等于的事件
        public void equalListener(String s);

        //右括号的事件
        public void rightBracket(String r);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.operator_layout, container, false);
        //初始化
        initView();
        return view;

    }

    //完成控件的初始化
    private void initView() {
        context = getActivity();
        //构造控件的id,方便初始化控件
        Resources resources = getResources();
        //数字事件
        for (int i = 0; i < 10; i++) {
            int id = resources.getIdentifier("text_" + i, "id", context.getPackageName());
            final TextView textView = (TextView) view.findViewById(id);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearResultText();
                    String num = textView.getText().toString();
                    ((OnOperateListener) context).numListener(num);
                }
            });
        }

        //四则运算事件
        for (int k = 0; k < 4; k++) {
            int operateId = resources.getIdentifier("operate_" + k, "id", context.getPackageName());
            final TextView operateText = (TextView) view.findViewById(operateId);
            operateText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    operateBoolean[k] = false;
                    clearResultText();
                    String operateStr = operateText.getText().toString();
                    ((OnOperateListener) context).operateListener(operateStr);
                }
            });
        }
        final TextView right = (TextView) view.findViewById(R.id.operate_4);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearResultText();
                String r = right.getText().toString();
                ((OnOperateListener)context).rightBracket(r);
            }
        });

        final TextView left = (TextView) view.findViewById(R.id.operate_5);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearResultText();
                String l = left.getText().toString();
                ((OnOperateListener) context).operateListener(l);
            }
        });

        //点的事件
        final TextView pointText = (TextView) view.findViewById(R.id.operate_6);
        pointText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearResultText();
                String point = pointText.getText().toString();
                ((OnOperateListener) context).operateListener(point);
            }
        });
        //重置
        view.findViewById(R.id.reset_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnOperateListener) context).resetListener();
            }
        });
        //删除
        view.findViewById(R.id.delete_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearResultText();
                ((OnOperateListener) context).deleteListener();
            }
        });
        //等于
        final TextView equalText = (TextView) view.findViewById(R.id.equal_text);
        equalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eb) {
                    clearResultText();
                } else {
                    eb = true;
                    ((OnOperateListener) context).equalListener(equalText.getText().toString());
                }

            }
        });
    }

    private void clearResultText() {
        if (eb) {
            eb = false;
            ((OnOperateListener) context).resetListener();
        }
    }
}
