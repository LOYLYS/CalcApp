package com.example.vhlee.calculator;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Stack;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public static final String NUM_ZERO = "0";
    public static final String NUM_ONE = "1";
    public static final String NUM_TWO = "2";
    public static final String NUM_THREE = "3";
    public static final String NUM_FOUR = "4";
    public static final String NUM_FIVE = "5";
    public static final String NUM_SIX = "6";
    public static final String NUM_SEVEN = "7";
    public static final String NUM_EIGHT = "8";
    public static final String NUM_NINE = "9";
    public static final char CHAR_ADD = '+';
    public static final char CHAR_SUB = '-';
    public static final char CHAR_MUL = '×';
    public static final char CHAR_DIV = '÷';
    public static final char CHAR_LEFT = '(';
    public static final char CHAR_RIGHT = ')';
    public static final String CHAR_SPACE = " ";
    public static final String CHAR_EMPT = "";
    public static final String CHAR_DOT = ".";

    private TextView mTextNotice;
    private EditText mEtextInput, mEtextHistory;
    private int[] mListId = {R.id.button_key_zero, R.id.button_key_one, R.id.button_key_two, R.id.button_key_three,
            R.id.button_key_four, R.id.button_key_five, R.id.button_key_six, R.id.button_key_seven,
            R.id.button_key_eight, R.id.button_key_nine,
            R.id.button_key_add, R.id.button_key_sub, R.id.button_key_mul, R.id.button_key_div,
            R.id.button_key_result, R.id.button_key_ac, R.id.button_key_dot, R.id.button_key_delete};
    private String mExp = CHAR_EMPT, mResult = CHAR_EMPT;

    public MainFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        return view;
    }
    public void init(View view) {

        mTextNotice = (TextView) view.findViewById(R.id.text_notice);
        mEtextInput = (EditText) view.findViewById(R.id.etext_input);
        mEtextHistory = (EditText) view.findViewById(R.id.etext_history);
        for (int mId : mListId) {
            Button button = (Button) view.findViewById(mId);
            button.setOnClickListener(onClickButton);
        }
    }

    public String[] formatExp(String mExp) {
        String strTemp = CHAR_EMPT, elementMath[] = null;
        for (int i=0; i<mExp.length(); i++){
            char element = mExp.charAt(i);
            if (!isOperator(element))
                strTemp = strTemp + element;
            else strTemp = strTemp + CHAR_SPACE + element + CHAR_SPACE;
        }
        strTemp = strTemp.trim();
        elementMath = strTemp.split(CHAR_SPACE);
        return elementMath;
    }
    public int priority(char operator){
        if (operator == CHAR_ADD || operator == CHAR_SUB) return 1;
        else if (operator == CHAR_MUL || operator == CHAR_DIV) return 2;
        else return 0;
    }
    public boolean isOperator(char operator){
        char listOperator[] = { CHAR_ADD , CHAR_SUB , CHAR_MUL , CHAR_DIV , CHAR_LEFT , CHAR_RIGHT };
        Arrays.sort(listOperator);
        if (Arrays.binarySearch(listOperator, operator) > -1)
            return true;
        else return false;
    }
    public String[] convertExp(String[] formatExp) {
        String strTemp = CHAR_EMPT, convertedExp[];
        Stack<String> stackElement = new Stack <String>();
        for (int i=0; i<formatExp.length; i++){
            char element = formatExp[i].charAt(0);

            if (!isOperator(element))
                strTemp = strTemp + CHAR_SPACE + formatExp[i];
            else{
                while (!stackElement.isEmpty() && priority(stackElement.peek().charAt(0)) >= priority(element)){
                    strTemp = strTemp + CHAR_SPACE + stackElement.peek();
                    stackElement.pop();
                }
                stackElement.push(formatExp[i]);
            }
        }
        while (!stackElement.isEmpty()){
            strTemp = strTemp + CHAR_SPACE + stackElement.peek();
            stackElement.pop();
        }
        convertedExp = strTemp.split(CHAR_SPACE);
        return convertedExp;
    }
    public String caculatorExp(String[] convertedExp) {
        Stack <String> stackElement = new Stack<String>();
        for (int i=1; i<convertedExp.length; i++){
            char element = convertedExp[i].charAt(0);
            if (!isOperator(element)) stackElement.push(convertedExp[i]);
            else{
                double num1 = Float.parseFloat(stackElement.pop());
                double num2 = Float.parseFloat(stackElement.pop());
                double num = processEvaluate(element, num1, num2);
                stackElement.push(Double.toString(num));
            }
        }
        return stackElement.pop();
    }
    public double processEvaluate(char element, double num1, double num2) {
        double num = 0f;
        switch (element) {
            case CHAR_ADD :
                num = num2 + num1;
                break;
            case CHAR_SUB :
                num = num2 - num1;
                break;
            case CHAR_MUL :
                num = num2 * num1;
                break;
            case CHAR_DIV :

                num = num2 / num1;
                break;
            default:
                break;
        }
        return num;
    }
    public String convertResult(String mResult) {
        double doubleNumber = Double.parseDouble(mResult);
        int intNumber = (int)doubleNumber;
        if(doubleNumber-intNumber==0) return String.valueOf(intNumber);
        return String.valueOf(doubleNumber);
    }
    View.OnClickListener onClickButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_key_zero: {
                    mExp += NUM_ZERO;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_one: {
                    mExp += NUM_ONE;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_two: {
                    mExp += NUM_TWO;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_three: {
                    mExp += NUM_THREE;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_four: {
                    mExp += NUM_FOUR;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_five: {
                    mExp += NUM_FIVE;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_six: {
                    mExp += NUM_SIX;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_seven: {
                    mExp += NUM_SEVEN;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_eight: {
                    mExp += NUM_EIGHT;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_nine: {
                    mExp += NUM_NINE;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_add: {
                    mExp += CHAR_ADD;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_sub: {
                    mExp += CHAR_SUB;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_mul: {
                    mExp += CHAR_MUL;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_div: {
                    mExp += CHAR_DIV;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_dot: {
                    mExp += CHAR_DOT;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_delete: {
                    mExp = mExp.substring(0,mExp.length()-1);
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_ac: {
                    mExp = CHAR_EMPT;
                    mEtextInput.setText(mExp);
                    break;
                }
                case R.id.button_key_result: {
                    mEtextHistory.setText(mExp);
                    String[] mTemp = convertExp(formatExp(mExp));
                    mResult = caculatorExp(mTemp);
                    mResult = convertResult(mResult);
                    mEtextInput.setText(mResult);
                    mResult = CHAR_EMPT;
                    mExp = CHAR_EMPT;
                    break;
                }
            }
        }
    };
}
