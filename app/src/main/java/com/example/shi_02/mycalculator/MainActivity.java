package com.example.shi_02.mycalculator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements OperateFragment.OnOperateListener {

    private EditText resultEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        resultEdit = (EditText) findViewById(R.id.result_edit);
        resultEdit.setMovementMethod(ScrollingMovementMethod.getInstance());
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(resultEdit.getWindowToken(), 0);

    }

    @Override
    public void numListener(String numStr) {

        int index = Math.max(resultEdit.getSelectionStart(), 0);
        resultEdit.getText().insert(index, numStr);
    }


    @Override
    public void operateListener(String operateStr) {
        int index = Math.max(resultEdit.getSelectionStart(), 0);
        resultEdit.getText().insert(index, operateStr);
    }

    @Override
    public void resetListener() {
        resultEdit.setText(null);
    }

    @Override
    public void deleteListener() {
        int index = resultEdit.getSelectionStart();
        if (!TextUtils.isEmpty(resultEdit.getText())) {
            resultEdit.getText().delete(index - 1, index);
        }

    }

    @Override
    public void equalListener(String s) {
        resultEdit.setText(resultEdit.getText().toString() + s + "\n");
    }
}