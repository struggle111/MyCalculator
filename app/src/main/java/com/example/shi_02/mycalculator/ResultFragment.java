package com.example.shi_02.mycalculator;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shi-02 on 2015/5/16.
 */
public class ResultFragment extends Fragment {

    private View view;

    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.result_layout,container,false);

        initView();

        return view;
    }

    private void initView(){
        context = getActivity();
    }
}
