package com.a706_4_6.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;
import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bt_0,bt_1,bt_2,bt_3,bt_4,bt_5,bt_6,bt_7,bt_8,bt_9,bt_pt,bt_AC,bt_bckspce,bt_pls,bt_mns,bt_mul,bt_div,bt_eq;
    TextView tv_frml,tv_rslt;
    boolean clear_flag,point_flag;       //都默认为false
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
        bt_AC = (Button) findViewById(R.id.button_AC);
        bt_bckspce = (Button) findViewById(R.id.button_bckspce);
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
        bt_AC.setOnClickListener(this);
        bt_bckspce.setOnClickListener(this);
        bt_pls.setOnClickListener(this);
        bt_mns.setOnClickListener(this);
        bt_mul.setOnClickListener(this);
        bt_div.setOnClickListener(this);
        bt_eq.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String str = tv_frml.getText().toString();
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
                if(clear_flag)
                {
                    clear_flag=false;
                    str="";
                    tv_frml.setText("");
                }
                tv_frml.setText(str + ((Button) v).getText());
                break;
            case R.id.button_pt:
                if(clear_flag)
                {
                    clear_flag=false;
                    str="";
                    tv_frml.setText("");
                }
                if (point_flag)break;   //若该数字存在小数点，则点击无效
                tv_frml.setText(str + ((Button) v).getText());
                point_flag=true;
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
                if(judge(str.charAt(str.length()-1)))break;   //最后一个字符是符号时输入符号不起作用
                tv_frml.setText(str + ((Button) v).getText());
                point_flag=false;
                break;
            case R.id.button_AC:
                if(clear_flag)
                {
                    clear_flag=false;
                    str="";
                    tv_frml.setText("");
                }
                point_flag=false;       //小数点标志置无
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
                    if(str.charAt(str.length()-1)=='.') point_flag=false;   //如果删除的是小数点，则小数点标志置无
                    tv_frml.setText(str.substring(0, str.length() - 1));
                }
                break;
            case R.id.button_eq:
                getresult();
                break;
        }
    }

    private void getresult() {
        String exp = tv_frml.getText().toString();

        if (exp == null || exp.equals("")) {
            return;
        }
        if (clear_flag) {
            clear_flag = false;
            return;
        }
        clear_flag = true;

        try {
            tv_rslt.setText(operation(exp));
        } catch (Exception e) {
            tv_rslt.setText("输入格式非法");    //向用户报错
        }
    }

    public String operation(String s) {
        Stack <String> stack_num = new Stack<>();//数字栈
        Stack <Character> stack_opr = new Stack<>();//符号栈
        String number="";//数字临时存放
        for (int i = 0; i <s.length(); i++) {
            if(judge(s.charAt(i)))
            {   //如果是符号
                if(stack_opr.isEmpty()) {//先判断符号栈是否栈空，不然比较时会出错
                    stack_opr.push(s.charAt(i));
                    stack_num.push(number);
                    number = "";
                }
                else if(prior(s.charAt(i))>prior(stack_opr.peek())) {             //当是符号时，与符号栈顶比较优先级，比符号栈顶优先级高则进栈
                    stack_opr.push(s.charAt(i));
                    stack_num.push(number);
                    number = "";
                }
                else {                      //比栈顶符号优先级相同或低时，对前两操作数和符号栈顶符号进行运算，并将结果压操作数栈
                    stack_num.push(number);//这里其实算多余操作...只是为了逻辑上统一
                    number = "";
                    String r_number=stack_num.pop();
                    String l_number=stack_num.pop();
                    char operator=stack_opr.pop();
                    stack_num.push(calculate(l_number,r_number,operator));
                    stack_opr.push(s.charAt(i));         //同时别忘了把新符号入栈，否则下次循环不读该符号了
                }
            }
            else{                                                                   //如果是数字
                number=number.concat(Character.toString(s.charAt(i)));
            }
        }
        stack_num.push(number);//最后补进栈的数字
        //number="";//中间值清空

        while(!stack_opr.isEmpty()){        //需要的东西全部入栈后，回头依次算优先级最低的符号，直到符号栈空
            String r_number=stack_num.pop();
            String l_number=stack_num.pop();
            char operator=stack_opr.pop();
            stack_num.push(calculate(l_number,r_number,operator));   //结果压操作数栈
        }
        String result=stack_num.pop();

        return result;   //含小数点则以浮点型输出
    }

    String calculate(String l_number,String r_number,char operator) {
        BigDecimal left = new BigDecimal(l_number);
        BigDecimal right = new BigDecimal(r_number);

        switch (operator) {
            case '＋':
                return (left.add(right)).toString();
            case '－':
                return (left.subtract(right)).toString();
            case '×':
                return (left.multiply(right)).toString();
            case '÷':
                return (left.divide(right, 10, BigDecimal.ROUND_HALF_DOWN)).toString();
        }
        return "";
    }

    public boolean judge(Character c){  //判断是否为运算符
        if(c=='＋'||c=='－'||c=='×'||c=='÷'){
            return true;
        }
        else
            return false;
    }

    int prior(char operator)    //运算优先级的设置
    {
        if (operator == '＋' || operator == '－')
            return 1;
        if (operator == '×' || operator == '÷')
            return 2;
        return 0;
    }
}

