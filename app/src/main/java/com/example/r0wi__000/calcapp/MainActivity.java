package com.example.r0wi__000.calcapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class MainActivity extends AppCompatActivity {

    //Flags to check which button pressed lastly
    boolean dot_flag = false;
    boolean operator_flag = false;
    boolean number = true;
    boolean txt_has_result = false;

    //call when the user clicks all the numeric buttons
    public void numericOnClick(View view) {

        // Get a reference to the text view
        TextView result_txt = (TextView) findViewById(R.id.text_result);

        // Get a reference to the buttons view
        Button clicked_btn = (Button) view;
        String result_txt_string = String.valueOf(result_txt.getText());

        //Check if the result field has result or zero, exchange it with the clicked button text
        if (result_txt_string.equals("0") || txt_has_result)
            result_txt.setText(clicked_btn.getText());

            //If no result or zero. append the clicked buttons text to form equation
        else

            result_txt.append(clicked_btn.getText());

        //Update the flags
        number = true;
        txt_has_result = false;
        operator_flag = false;


    }

    //call when the user clicks the dot button
    public void dotOnClick(View view) {

        // Get a reference to the text view
        TextView result_txt = (TextView) findViewById(R.id.text_result);

        //After clearing the text or at the initialize append only decimal point
        if (!dot_flag && !operator_flag && !number)

            result_txt.append(".");

        //After adding an operator append 0 before the decimal point
        if (!dot_flag && operator_flag && !number)

            result_txt.append("0.");

        //Add only decimal point if a number was pressed
        if (!dot_flag && !operator_flag && number)
            result_txt.append(".");

        //If operator and number were pressed add only decimal point
        if (operator_flag && number)
            result_txt.append(".");

        //Update the flags
        dot_flag = true;
        number = false;
        operator_flag = false;
        txt_has_result = false;

    }

    //call when user clicks negate button (-/+)
    public void negateOnClick(View view) {

        // Get a reference to the text view
        TextView result_txt = (TextView) findViewById(R.id.text_result);
        String result_txt_string = String.valueOf(result_txt.getText());
        //Replace symbols with mathematical operations
        result_txt_string = result_txt_string.replaceAll("X", "*").replaceAll("÷", "/");
        //Build expression to make a mathematical equation
        Expression expression = new ExpressionBuilder(result_txt_string).build();
        double double_calculation_result = expression.evaluate();
        double double_negate_result = -1 * double_calculation_result;
        String[] result_string;
        String negate_result_string = String.valueOf(double_negate_result);
        // Split the result into a part left of decimal point and a part right of decimal point
        result_string = negate_result_string.split("\\.");

        //Check if the right of the decimal point is zero print only the left part
        if (result_string[1].equals("0")) {
            result_txt.setText(result_string[0]);
        } else {
            result_txt.setText(negate_result_string);
        }

        //Update the flags
        number = true;
        txt_has_result = true;
    }

    //Call when the user clicks the percent button
    public void percentOnClick(View view) {
        // Get a reference to the text view
        TextView result_txt = (TextView) findViewById(R.id.text_result);
        String result_txt_string = String.valueOf(result_txt.getText());
        result_txt_string = result_txt_string.replaceAll("X", "*").replaceAll("÷", "/");
        Expression expression = new ExpressionBuilder(result_txt_string).build();
        double percent_calculation_expression = expression.evaluate();
        double percent_result_txt = 0.01 * percent_calculation_expression;
        String percent_res = String.valueOf(percent_result_txt);
        result_txt.setText(percent_res);

        //Update the flags
        number = true;
        txt_has_result = true;

    }

    //call when the user clicks the clear button AC

    public void acOnClick(View view) {

        // Get a reference to the text view
        TextView result_txt = (TextView) findViewById(R.id.text_result);
        result_txt.setText("0");
        dot_flag = false;
        operator_flag = false;
        number = false;


    }

    //call when the user clicks all the operator buttons
    public void operatorOnClick(View view) {

        // Get a reference to the text view
        TextView result_txt = (TextView) findViewById(R.id.text_result);

        // Get a reference to the buttons view
        Button operator_clicked_button = (Button) view;
        if (!operator_flag && dot_flag && !number)
            result_txt.append("0" + operator_clicked_button.getText());
        if (!operator_flag && number)
            result_txt.append(operator_clicked_button.getText());
        operator_flag = true;
        dot_flag = false;
        number = false;
        txt_has_result = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void equalOnClick(View view) {

        // Get a reference to the text view
        TextView result_txt = (TextView) findViewById(R.id.text_result);
        try {

            String result_txt_string = String.valueOf(result_txt.getText());
            result_txt_string = result_txt_string.replaceAll("X", "*").replaceAll("÷", "/");
            Expression expression = new ExpressionBuilder(result_txt_string).build();
            double equal_calculation_expression = expression.evaluate();
            float float_equal_result = (float) equal_calculation_expression;
            String[] result_string;
            String equal_result = String.valueOf(float_equal_result);
            result_string = equal_result.split("\\.");

            if (result_string[1].equals("0")) {
                result_txt.setText(result_string[0]);
            } else {
                result_txt.setText(equal_result);
            }

        } catch (ArithmeticException exception) {
            result_txt.setText(R.string.exception_name);
        }
        operator_flag = false;
        dot_flag = false;
        number = true;
        txt_has_result = true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
