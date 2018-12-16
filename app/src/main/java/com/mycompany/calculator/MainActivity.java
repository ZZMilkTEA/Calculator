package com.mycompany.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private Button btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9;
    private Button btn_point,btn_AC,btn_backspace,btn_add,btn_sub,btn_mul,btn_div,btn_equ;
    private TextView tv_input;
    private TextView tv_result;
    private static String input="";//运算公式字符串
    private static String result="";//结果字符串
    private static int count_operator=0;//运算符计数器，控制去重，1为前面已有运算符
    private static int count_point=0;//小数点计数器，去重，1为前面已有小数点
    //初始化按钮
    public void initButton() {
        btn_0=findViewById(R.id.btn_0);
        btn_1=findViewById(R.id.btn_1);
        btn_2=findViewById(R.id.btn_2);
        btn_3=findViewById(R.id.btn_3);
        btn_4=findViewById(R.id.btn_4);
        btn_5=findViewById(R.id.btn_5);
        btn_6=findViewById(R.id.btn_6);
        btn_7=findViewById(R.id.btn_7);
        btn_8=findViewById(R.id.btn_8);
        btn_9=findViewById(R.id.btn_9);
        btn_point=findViewById(R.id.btn_point);
        btn_AC=findViewById(R.id.btn_AC);
        btn_backspace=findViewById(R.id.btn_backspace);
        btn_add=findViewById(R.id.btn_add);
        btn_sub=findViewById(R.id.btn_sub);
        btn_mul=findViewById(R.id.btn_mul);
        btn_div=findViewById(R.id.btn_div);
        btn_equ=findViewById(R.id.btn_equ);
        tv_input=findViewById(R.id.tv_input);
        tv_result=findViewById(R.id.tv_result);
    }

    //判断是否为运算符
    public int judge(Character c){
        if(c=='＋'||c=='－'||c=='×'||c=='÷'){
            return 1;
        }
        else
            return 0;
    }
    //二元运算，用BigDecimal防止精度丢失
    String calculate(String l_number,String r_number,char operator){
        BigDecimal left=new BigDecimal(l_number);
        BigDecimal right=new BigDecimal(r_number);

        switch (operator){
            case '＋':return (left.add(right)).toString();
            case '－':return (left.subtract(right)).toString();
            case '×':return (left.multiply(right)).toString();
            case '÷':return (left.divide(right,10,BigDecimal.ROUND_HALF_DOWN)).toString();
        }
        return "";
    }

    //运算优先级的设置
    int prior(char operator)
    {
        if (operator == '＋' || operator == '－')
            return 1;
        if (operator == '×' || operator == '÷')
            return 2;
        else
            return 0;
    }

    //从字符串运算公式得到结果，并存入字符串
    public String operation(String s) {
        Stack <String> stack_num = new Stack<>();//数字栈
        Stack <Character> stack_opr = new Stack<>();//符号栈
        String number="";//数字临时存放
        for (int i = 0; i <s.length(); i++) {
            if(judge(s.charAt(i))==1)
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButton();
        btn_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input+="0";
                count_operator=0;
                tv_input.setText(input);
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input+="1";
                count_operator=0;
                tv_input.setText(input);
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input+="2";
                count_operator=0;
                tv_input.setText(input);
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input+="3";
                count_operator=0;
                tv_input.setText(input);
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input+="4";
                count_operator=0;
                tv_input.setText(input);
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input+="5";
                count_operator=0;
                tv_input.setText(input);
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input+="6";
                count_operator=0;
                tv_input.setText(input);
            }
        });
        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input+="7";
                count_operator=0;
                tv_input.setText(input);
            }
        });
        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input+="8";
                count_operator=0;
                tv_input.setText(input);
            }
        });
        btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input+="9";
                count_operator=0;
                tv_input.setText(input);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!input.equals("")&&count_operator==0){
                    input+="＋";
                    count_operator=1;
                    count_point=0;
                }
                tv_input.setText(input);
            }
        });
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!input.equals("")&&count_operator==0){
                    input+="－";
                    count_operator=1;
                    count_point=0;
                }
                tv_input.setText(input);
            }
        });
        btn_mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!input.equals("")&&count_operator==0){
                    input+="×";
                    count_operator=1;
                    count_point=0;
                }
                tv_input.setText(input);
            }
        });
        btn_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!input.equals("")&&count_operator==0){
                    input+="÷";
                    count_operator=1;
                    count_point=0;
                }

                tv_input.setText(input);
            }
        });
        btn_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.equals("")||(count_operator==1&&count_point==0)){
                    input+="0";
                    count_operator=0;
                    count_point=0;
                }
                if(!input.equals("")&&count_point==0){
                    input+=".";
                    count_operator=1;
                    count_point=1;
                }
                tv_input.setText(input);
            }
        });
        btn_backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断退格后最后一个字符是什么
                if(input.length()==1){
                    input="";
                    count_point=0;
                    count_operator=0;
                }
                else if(input.length()>1){
                    input=input.substring(0,input.length()-1);
                    if(count_operator==1){
                        count_operator=0;
                    }
                    if (input.substring(input.length()-1,input.length()).equals(".")){
                        count_point=1;
                        count_operator=1;
                    }
                }
                tv_input.setText(input);
            }
        });
        btn_AC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input="";
                result="";
                count_operator=0;
                count_point=0;
                tv_input.setText(input);
                tv_result.setText(result);
            }
        });
        btn_equ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count_operator ==0){
                    try{
                        result=operation(input);
                        tv_result.setText(result);
                    }
                    catch (Exception e){
                        tv_result.setText(e.getMessage());
                    }
                }
            }
        });

    }
}
