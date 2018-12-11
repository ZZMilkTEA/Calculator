package com.a706_4_6.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.regex.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bt_0,bt_1,bt_2,bt_3,bt_4,bt_5,bt_6,bt_7,bt_8,bt_9,bt_pt,bt_prct,bt_AC,bt_bckspce,bt_plsmns,bt_pls,bt_mns,bt_mul,bt_div,bt_eq;
    TextView tv_frml,tv_rslt;
    boolean clear_flag,operator_flag,operator_exist_flag;         //clearflag默认为false
    String pattern = "(\\d+)(\\+|×|-|÷)(\\d+)";
    Pattern patrn = Pattern.compile(pattern);    // 创建 Pattern 对象，判断符号用
    Matcher m;// 创建 matcher 对象，输出判断
    //定义控件对象，通过接口方式实现事件的监听

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_0 = (Button) findViewById(R.id.button_0);                //各个控件的初始化
        bt_1 = (Button) findViewById(R.id.button_1);
        bt_2 = (Button) findViewById(R.id.button_2);
        bt_3 = (Button) findViewById(R.id.button_3);
        bt_4 = (Button) findViewById(R.id.button_4);
        bt_5 = (Button) findViewById(R.id.button_5);
        bt_6 = (Button) findViewById(R.id.button_6);
        bt_7 = (Button) findViewById(R.id.button_7);
        bt_8 = (Button) findViewById(R.id.button_8);
        bt_9 = (Button) findViewById(R.id.button_9);
        bt_pt = (Button) findViewById(R.id.button_pt);
        bt_prct = (Button) findViewById(R.id.button_prct);
        bt_AC = (Button) findViewById(R.id.button_AC);
        bt_bckspce = (Button) findViewById(R.id.button_bckspce);
        bt_plsmns = (Button) findViewById(R.id.button_plsmns);
        bt_pls = (Button) findViewById(R.id.button_pls);
        bt_mns = (Button) findViewById(R.id.button_mns);
        bt_mul = (Button) findViewById(R.id.button_mul);
        bt_div = (Button) findViewById(R.id.button_div);
        bt_eq = (Button) findViewById(R.id.button_eq);
        tv_frml = (TextView) findViewById(R.id.tv_frml);
        tv_rslt = (TextView) findViewById(R.id.tv_rslt);

        bt_0.setOnClickListener(this);        //设置按钮的点击事件
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
        bt_4.setOnClickListener(this);
        bt_5.setOnClickListener(this);
        bt_6.setOnClickListener(this);
        bt_7 .setOnClickListener(this);
        bt_8.setOnClickListener(this);
        bt_9.setOnClickListener(this);
        bt_pt.setOnClickListener(this);
        bt_prct.setOnClickListener(this);
        bt_AC.setOnClickListener(this);
        bt_bckspce.setOnClickListener(this);
        bt_plsmns.setOnClickListener(this);
        bt_pls.setOnClickListener(this);
        bt_mns.setOnClickListener(this);
        bt_mul.setOnClickListener(this);
        bt_div.setOnClickListener(this);
        bt_eq.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String str = tv_frml.getText().toString();
        if (Pattern.matches(pattern,str))operator_exist_flag=true;
        else operator_exist_flag=false;
        switch (v.getId()) {
            case R.id.button_0:
            case R.id.button_1:
            case R.id.button_2:
            case R.id.button_3:
            case R.id.button_4:
            case R.id.button_5:
            case R.id.button_6:
            case R.id.button_7:
            case R.id.button_8:
            case R.id.button_9:
            case R.id.button_pt:
                if(clear_flag)
                {
                    clear_flag=false;
                    str="";
                    tv_frml.setText("");
                }
                tv_frml.setText(str + ((Button) v).getText());
                break;
            case R.id.button_pls:
            case R.id.button_mul:
            case R.id.button_div:
            case R.id.button_mns:
                if(clear_flag)
                {
                    clear_flag=false;
                    str="";
                    tv_frml.setText("");
                }
                if(operator_exist_flag)break;
                //判断用
                tv_frml.setText(str + ((Button) v).getText());
                break;
            case R.id.button_AC:
                if(clear_flag)
                {
                    clear_flag=false;
                    str="";
                    tv_frml.setText("");
                }
                tv_frml.setText("");
                break;
            case R.id.button_bckspce:
                if(clear_flag)
                {
                    clear_flag=false;
                    str="";
                    tv_frml.setText("");
                }
                if (str != null && !str.equals("")) {
                    tv_frml.setText(str.substring(0, str.length() - 1));
                }
                break;
            case R.id.button_eq:
                getresult();
                break;
        }
    }

    private void getresult(){
        String exp = tv_frml.getText().toString();

        if (exp == null || exp.equals("")) {
                return;
            }
        if (clear_flag) {
                clear_flag = false;
                return;
            }
        clear_flag = true;
        double result = 0;
        m= patrn.matcher(exp);
       try {
           m.find();
           String s1 = m.group(1);
           String op = m.group(2);
           String s2 = m.group(3);

        if (!s1.equals("") && !s2.equals("")) {
            double d1 = Double.parseDouble(s1);
            double d2 = Double.parseDouble(s2);
            //强制类型转换

            if (op.equals("+")) {
                result = d1 + d2;
            } else if (op.equals("-")) {
                result = d1 - d2;
            } else if (op.equals("×")) {
                result = d1 * d2;
            } else if (op.equals("÷")) {
                if (d1 == 0) {
                    result = 0;
                } else result = d1 / d2;
            }

            if (!s1.contains(".") && !s2.contains(".") && !op.equals("÷")) {
                int r = (int) result;
                tv_rslt.setText(r + "");
            } else {
                tv_rslt.setText(result + "");
            }

        } else if (!s1.equals("") && s2.equals("")) {
            tv_rslt.setText(exp);
        } else if (s1.equals("") && !s2.equals("")) {
            double d2 = Double.parseDouble(s2);
            if (op.equals("+")) {
                result = 0 + d2;
            } else if (op.equals("-")) {
                result = 0 - d2;
            } else if (op.equals("×")) {
                result = 0 * d2;
            } else if (op.equals("÷")) {
                result = 0;
            }
        } else {
            tv_frml.setText("");
        }
       }catch(PatternSyntaxException e){
           tv_rslt.setText("输入格式非法");
       }
        }
}

