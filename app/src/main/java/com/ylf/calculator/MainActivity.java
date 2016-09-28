package com.ylf.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Stack;

/**
 * 9+（3-1）*3+10/2
 */
public class MainActivity extends AppCompatActivity {
    private TextView infixTV;
    private TextView contentTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infixTV = (TextView) findViewById(R.id.infix);
        contentTV = (TextView) findViewById(R.id.content);

        String infixExpression = "9+(3-1)*3+10/2";
        String suffixExpression = tranInfix2Suffix(infixExpression);


        infixTV.setText(infixExpression);
        contentTV.setText(suffixExpression);
    }

    /**
     * 规则：从左到右遍历中缀表达式的每个数字和符号，若是数字就输出，即成为后缀表达式的一部分；
     * 若是符号，则判断其与栈顶符号的优先级，是右括号或优先级低于栈顶符号（乘除优先加减）则栈顶元素依次出栈并输出，
     * 并将当前符号进栈，一直到最终输出后缀表达式为止。
     *
     * 9+（3-1）*3+10/2
     *
     * @param infixExpression
     * @return
     */
    private String tranInfix2Suffix(String infixExpression){
        Stack<Character> operatorStack = new Stack<>();
        StringBuffer buffer = new StringBuffer();

        char[] operatorArray = infixExpression.toCharArray();
        for(char c : operatorArray){
            if(c == '9' || c == '3' || c == '1' || c == '0' || c == '2'){
                buffer.append(c);
            }else if(c == '+' || c == '-'){
                if(operatorStack.empty()){
                    operatorStack.push(c);
                }else{
                    char top = operatorStack.peek();
                    if(getPriority(c) < getPriority(top)){//优先级低于栈顶符号（乘除优先加减）
                        buffer.append(operatorStack.pop());
                        buffer.append(c);
                    }else{
                        operatorStack.push(c);
                    }
                }
            }else if(c == '*' || c == '/'){
                if(operatorStack.empty()){
                    operatorStack.push(c);
                }else{
                    char top = operatorStack.peek();
                    if(getPriority(c) < getPriority(top)){//优先级低于栈顶符号（乘除优先加减）
                        buffer.append(operatorStack.pop());
                        operatorStack.push(c);
                    }else{
                        operatorStack.push(c);
                    }
                }
            }else if(c == '('){
                operatorStack.push(c);
            }else if(c == ')'){
                char top = operatorStack.pop();
                while(top != '('){
                    buffer.append(top);
                    top = operatorStack.pop();
                }
            }
        }
        while (!operatorStack.empty()){
            buffer.append(operatorStack.pop());
        }

        return buffer.toString();
    }

    private int getPriority(char c){
        int p = 0;
        switch (c){
            case '+':{
                p = 2;
                break;
            }
            case '-':{
                p = 2;
                break;
            }case '*':{
                p = 3;
                break;
            }
            case '/':{
                p = 3;
                break;
            }

        }
        return p;
    }


}
