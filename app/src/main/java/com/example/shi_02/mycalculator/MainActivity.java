package com.example.shi_02.mycalculator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MainActivity extends Activity implements OperateFragment.OnOperateListener {
    //显示结果的编辑框
    private EditText resultEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    //初始化控件
    private void initView() {
        resultEdit = (EditText) findViewById(R.id.result_edit);
        resultEdit.setMovementMethod(ScrollingMovementMethod.getInstance());
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(resultEdit.getWindowToken(), 0);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT <= 10) {
            // 点击EditText，屏蔽默认输入法
            resultEdit.setInputType(InputType.TYPE_NULL); // editText是声明的输入文本框。
        } else {
            // 点击EditText，隐藏系统输入法
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method method = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);// 4.0的是setShowSoftInputOnFocus，4.2的是setSoftInputShownOnFocus
                method.setAccessible(false);
                method.invoke(resultEdit, false); // editText是声明的输入文本框。
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void numListener(String numStr) {
        int index = resultEdit.getSelectionStart();
        resultEdit.getText().insert(index, numStr);
    }


    @Override
    public void operateListener(String operateStr) {
        String lastStr = resultEdit.getText().toString();
        if (lastStr.length() == 0) {
            return;
        }
        char c = lastStr.charAt(lastStr.length() - 1);
        char o = operateStr.charAt(0);

        if (o == '.') {
            switch (c) {
                case '+':
                case '-':
                case '/':
                case '*':
                case '(':
                case ')':
                case '.':
                    return;
                default:
                    break;

            }
        } else {
            //防止出现错误的表达式
            switch (c) {
                case '+':
                case '-':
                case '/':
                case '*':
                case '(':
                case '.':
                    return;
                default:
                    break;
            }
        }

        if (lastStr.length() != 0) {
            int index = Math.max(resultEdit.getSelectionStart(), 0);
            resultEdit.getText().insert(index, operateStr);
        }

    }

    @Override
    public void resetListener() {
        resultEdit.setText(null);
        resultEdit.setSelection(0);
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
        Calculator calculator = new Calculator();
        Double result = calculator.getResult(resultEdit.getText().toString());
        String r = String.valueOf(result);
        if (r.equals("Infinity")) {
            r = "0不能作为除数";
        }
        resultEdit.setText(resultEdit.getText().toString() + s + "\n" + r);
        resultEdit.setSelection(resultEdit.getText().toString().length());
    }

    @Override
    public void rightBracket(String r) {

        String lastStr = resultEdit.getText().toString();

        if (lastStr.length() > 0) {
            char c = lastStr.charAt(lastStr.length() - 1);
            char o = r.charAt(0);
            if (o == '(') {
                switch (c) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case ')':
                    case '.':
                        return;
                    default:
                        break;
                }
            }
        }
        int index = resultEdit.getSelectionStart();
        resultEdit.getText().insert(index, r);
    }
}