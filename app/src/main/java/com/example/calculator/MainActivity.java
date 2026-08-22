package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private double firstValue = Double.NaN;
    private String currentInput = "";
    private String fullExpression = "";
    private String pendingAction = "";

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

        tvDisplay = findViewById(R.id.tvDisplay);

        setNumericButtonListeners();
        setOperatorButtonListeners();
    }

    private void setNumericButtonListeners() {
        int[] numericButtons = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        View.OnClickListener listener = view -> {
            Button button = (Button) view;
            String val = button.getText().toString();

            // If we just finished an expression (e.g. "4+4=8"), clear and start new
            if (fullExpression.contains("=")) {
                clear();
            }

            currentInput += val;
            tvDisplay.setText(fullExpression + currentInput);
        };

        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorButtonListeners() {
        findViewById(R.id.btnAdd).setOnClickListener(v -> prepareCompute("+"));
        findViewById(R.id.btnSub).setOnClickListener(v -> prepareCompute("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> prepareCompute("x"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> prepareCompute("÷"));
        findViewById(R.id.btnEqual).setOnClickListener(v -> finalizeCompute());
        findViewById(R.id.btnC).setOnClickListener(v -> clear());
    }

    private void clear() {
        firstValue = Double.NaN;
        currentInput = "";
        fullExpression = "";
        pendingAction = "";
        tvDisplay.setText("0");
    }

    private void prepareCompute(String action) {
        if (currentInput.isEmpty()) return;

        double val = Double.parseDouble(currentInput);

        if (!Double.isNaN(firstValue)) {
            switch (pendingAction) {
                case "+": firstValue += val; break;
                case "-": firstValue -= val; break;
                case "x": firstValue *= val; break;
                case "÷": if (val != 0) firstValue /= val; break;
            }
        } else {
            firstValue = val;
        }

        pendingAction = action;
        fullExpression += currentInput + action;
        currentInput = "";
        tvDisplay.setText(fullExpression);
    }

    private void finalizeCompute() {
        if (currentInput.isEmpty() || Double.isNaN(firstValue)) return;

        double secondValue = Double.parseDouble(currentInput);
        double result = 0;

        switch (pendingAction) {
            case "+": result = firstValue + secondValue; break;
            case "-": result = firstValue - secondValue; break;
            case "x": result = firstValue * secondValue; break;
            case "÷":
                if (secondValue != 0) result = firstValue / secondValue;
                break;
        }

        // Format result to remove .0 if it's an integer
        String resultStr = (result == (long) result) ? String.valueOf((long) result) : String.valueOf(result);

        tvDisplay.setText(fullExpression + currentInput + "=" + resultStr);

        // Prepare for next operation or clear
        fullExpression = tvDisplay.getText().toString();
        currentInput = "";
        firstValue = Double.NaN;
    }
}
