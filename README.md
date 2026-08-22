# EX:NO:05:Develop a program to create a simple calculator using android studio.

## AIM:

To create and design an android application for a simple calculator using android studio.


## EQUIPMENTS REQUIRED:

Latest Version Android Studio

## ALGORITHM:

Step 1: Open Android Stdio and then click on File -> New -> New project.

Step 2: Then type the Application name as HelloWorld and click Next. 

Step 3: Then select the Minimum SDK as shown below and click Next.

Step 4: Then select the Empty Activity and click Next. Finally click Finish.

Step 5: Design layout in activity_main.xml.

Step 6: Display message give in MainActivity file.

Step 7: Save and run the application.

## PROGRAM:

Program to create and design an android application simple calculator using Intent.

Developed by: Siddarth A S

Registeration Number : 212224040316

### AndroidManifest.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Calculator">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```
### MainActivity.java
```java
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
```

### activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/bg_charcoal"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/tvDisplay"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1.5"
        android:background="@color/display_bg"
        android:gravity="bottom|end"
        android:padding="24dp"
        android:text="0"
        android:textColor="@color/white"
        android:textSize="48sp" />

    <TableLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="3"
        android:stretchColumns="*">

        <TableRow
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1">

            <Button
                android:id="@+id/btnC"
                style="@style/CalcButton.Action"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="C" />

            <Button
                android:id="@+id/btnDiv"
                style="@style/CalcButton.Operator"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="÷" />
        </TableRow>

        <TableRow
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1">

            <Button
                android:id="@+id/btn7"
                style="@style/CalcButton.Numeric"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="7" />

            <Button
                android:id="@+id/btn8"
                style="@style/CalcButton.Numeric"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="8" />

            <Button
                android:id="@+id/btn9"
                style="@style/CalcButton.Numeric"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="9" />

            <Button
                android:id="@+id/btnMul"
                style="@style/CalcButton.Operator"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="x" />
        </TableRow>

        <TableRow
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1">

            <Button
                android:id="@+id/btn4"
                style="@style/CalcButton.Numeric"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="4" />

            <Button
                android:id="@+id/btn5"
                style="@style/CalcButton.Numeric"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="5" />

            <Button
                android:id="@+id/btn6"
                style="@style/CalcButton.Numeric"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="6" />

            <Button
                android:id="@+id/btnSub"
                style="@style/CalcButton.Operator"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="-" />
        </TableRow>

        <TableRow
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1">

            <Button
                android:id="@+id/btn1"
                style="@style/CalcButton.Numeric"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="1" />

            <Button
                android:id="@+id/btn2"
                style="@style/CalcButton.Numeric"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="2" />

            <Button
                android:id="@+id/btn3"
                style="@style/CalcButton.Numeric"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="3" />

            <Button
                android:id="@+id/btnAdd"
                style="@style/CalcButton.Operator"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="+" />
        </TableRow>

        <TableRow
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1">

            <Button
                android:id="@+id/btn0"
                style="@style/CalcButton.Numeric"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="3"
                android:layout_span="3"
                android:text="0" />

            <Button
                android:id="@+id/btnEqual"
                style="@style/CalcButton.Action"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:text="=" />
        </TableRow>

    </TableLayout>

</LinearLayout>


```

### themes.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- Base application theme. -->
    <style name="Theme.Calculator" parent="Theme.AppCompat.NoActionBar">
        <!-- Primary brand color. -->
        <item name="colorPrimary">@color/bg_charcoal</item>
        <item name="colorPrimaryDark">@color/black</item>
        <item name="colorAccent">@color/btn_amber</item>
        <item name="android:windowBackground">@color/bg_charcoal</item>
    </style>

    <!-- Numeric Button Style -->
    <style name="CalcButton.Numeric" parent="Widget.AppCompat.Button">
        <item name="android:backgroundTint">@color/btn_grey</item>
        <item name="android:textColor">@color/white</item>
        <item name="android:textSize">24sp</item>
        <item name="android:layout_margin">4dp</item>
    </style>

    <!-- Operator Button Style -->
    <style name="CalcButton.Operator" parent="Widget.AppCompat.Button">
        <item name="android:backgroundTint">@color/btn_amber</item>
        <item name="android:textColor">@color/white</item>
        <item name="android:textSize">24sp</item>
        <item name="android:layout_margin">4dp</item>
    </style>

    <!-- Action Button Style (C, =) -->
    <style name="CalcButton.Action" parent="Widget.AppCompat.Button">
        <item name="android:backgroundTint">@color/btn_orange</item>
        <item name="android:textColor">@color/white</item>
        <item name="android:textSize">24sp</item>
        <item name="android:layout_margin">4dp</item>
    </style>
</resources>

```

### colors.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- Basic Palette -->
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>

    <!-- Calculator Theme Colors -->
    <color name="bg_charcoal">#121212</color>
    <color name="btn_grey">#333333</color>
    <color name="btn_amber">#FF9800</color>
    <color name="btn_orange">#F4511E</color>
    <color name="display_bg">#000000</color>
</resources>

```

## OUTPUT


<img width="1918" height="1138" alt="Screenshot 2026-08-22 084009" src="https://github.com/user-attachments/assets/020c87a8-76d5-4b93-95ad-dfab9d7d9493" />
<img width="1917" height="1143" alt="Screenshot 2026-08-22 083943" src="https://github.com/user-attachments/assets/bab23cac-5c4e-4de7-9e68-ea6b7aa81f4a" />
<img width="1918" height="1143" alt="Screenshot 2026-08-22 084029" src="https://github.com/user-attachments/assets/5928ed05-d861-48ab-b32b-3bd0b4edcf74" />
<img width="1918" height="1141" alt="Screenshot 2026-08-22 084105" src="https://github.com/user-attachments/assets/c3091f3a-fb8f-4600-af4e-5ee1406c0240" />



## RESULT

Thus a Simple Android Application create a simple calculator using Android Studio is developed and executed successfully.
