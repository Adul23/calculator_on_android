package com.example.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private TextView inputDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inputDisplay = findViewById(R.id.textView);
        Button button1 = findViewById(R.id.button_1);
        Button button2 = findViewById(R.id.button_2);
        Button button3 = findViewById(R.id.button_3);
        Button button4 = findViewById(R.id.button_4);
        Button button5 = findViewById(R.id.button_5);
        Button button6 = findViewById(R.id.button_6);
        Button button7 = findViewById(R.id.button_7);
        Button button8 = findViewById(R.id.button_8);
        Button button9 = findViewById(R.id.button_9);
        Button button0 = findViewById(R.id.button_0);
        Button plus = findViewById(R.id.plus);
        Button equal = findViewById(R.id.equal);
        Button eraser = findViewById(R.id.eraser);
        Button minus = findViewById(R.id.minus);
        Button multiply = findViewById(R.id.multiply);
        Button division = findViewById(R.id.divide);
        Button scopeleft = findViewById(R.id.scopeleft);
        Button scoperight = findViewById(R.id.scoperight);
        Button dot = findViewById(R.id.dot);
        Button ces = findViewById(R.id.C);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("1");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("2");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("3");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("4");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("5");
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("6");
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("7");
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("8");
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("9");
            }
        });
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("0");
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("+");
            }
        });
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double k = evaluateExpression(inputDisplay.getText().toString());
                inputDisplay.setText(String.valueOf(k));
            }
        });
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletingThing();
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("-");
            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("*");
            }
        });
        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("/");
            }
        });
        scopeleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay("(");
            }
        });
        scoperight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay(")");
            }
        });
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInputDisplay(".");
            }
        });
        ces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDisplay.setText("");
            }
        });
    }

    private void appendToInputDisplay(String text) {
        String currentText = inputDisplay.getText().toString();
        inputDisplay.setText(currentText + text);
    }



    public static double
    evaluateExpression(String expression)
    {
        char[] tokens = expression.toCharArray();

        // Stacks to store operands and operators
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        // Iterate through each character in the expression
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;

            // If the character is a digit or a decimal
            // point, parse the number
            if ((tokens[i] >= '0' && tokens[i] <= '9')
                    || tokens[i] == '.') {
                StringBuilder sb = new StringBuilder();
                // Continue collecting digits and the
                // decimal point to form a number
                while (i < tokens.length
                        && (Character.isDigit(tokens[i])
                        || tokens[i] == '.')) {
                    sb.append(tokens[i]);
                    i++;
                }
                // Parse the collected number and push it to
                // the values stack
                values.push(
                        Double.parseDouble(sb.toString()));
                i--; // Decrement i to account for the extra
                // increment in the loop
            }
            else if (tokens[i] == '(') {
                // If the character is '(', push it to the
                // operators stack
                operators.push(tokens[i]);
            }
            else if (tokens[i] == ')') {
                // If the character is ')', pop and apply
                // operators until '(' is encountered
                while (operators.peek() != '(') {
                    values.push(applyOperator(
                            operators.pop(), values.pop(),
                            values.pop()));
                }
                operators.pop(); // Pop the '('
            }
            else if (tokens[i] == '+' || tokens[i] == '-'
                    || tokens[i] == '*'
                    || tokens[i] == '/') {
                // If the character is an operator, pop and
                // apply operators with higher precedence
                while (!operators.isEmpty()
                        && hasPrecedence(tokens[i],
                        operators.peek())) {
                    values.push(applyOperator(
                            operators.pop(), values.pop(),
                            values.pop()));
                }
                // Push the current operator to the
                // operators stack
                operators.push(tokens[i]);
            }
        }

        // Process any remaining operators in the stack
        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(),
                    values.pop(),
                    values.pop()));
        }

        // The result is the only remaining element in the
        // values stack
        return values.pop();
    }

    // Function to check if operator1 has higher precedence
    // than operator2
    private static boolean hasPrecedence(char operator1,
                                         char operator2)
    {
        if (operator2 == '(' || operator2 == ')')
            return false;
        return (operator1 != '*' && operator1 != '/')
                || (operator2 != '+' && operator2 != '-');
    }

    // Function to apply the operator to two operands
    private static double applyOperator(char operator,
                                        double b, double a)
    {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new ArithmeticException(
                            "Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
    private void deletingThing() {
        String currentText = inputDisplay.getText().toString();
        if (!currentText.isEmpty()) {
            currentText = currentText.substring(0, currentText.length() - 1);
            inputDisplay.setText(currentText);
        }
    }
}