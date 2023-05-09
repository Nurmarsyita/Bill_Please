package com.example.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

EditText amount;
EditText numOfPax;
ToggleButton svs;
ToggleButton gst;
EditText discount;
RadioGroup paymentMethod;
Button split;
Button reset;
TextView totalBill;
TextView eachPays;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.editTextAmount);
        numOfPax = findViewById(R.id.editTextPax);
        svs = findViewById(R.id.toggleButtonSVS);
        gst = findViewById(R.id.toggleButtonGST);
        discount = findViewById(R.id.editTextDiscount);
        paymentMethod = findViewById(R.id.radioGroupPaymentMethod);
        split = findViewById(R.id.buttonSplit);
        reset = findViewById(R.id.buttonReset);
        totalBill = findViewById(R.id.displayBill);
        eachPays = findViewById(R.id.displayPays);

        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double totalAmt = 0.0;
                int getAmt = amount.getText().toString().trim().length();
                int getNum = numOfPax.getText().toString().trim().length();
                int getDisc = discount.getText().toString().trim().length();

                if (getAmt != 0 && getNum != 0) {
                    if (!svs.isChecked() && (!gst.isChecked())) {
                        totalAmt = Double.parseDouble(amount.getText().toString());
                    } else if ((!svs.isChecked() && (gst.isChecked()))) {
                        totalAmt = Double.parseDouble(amount.getText().toString()) * 1.07;
                    } else if (svs.isChecked() && (!gst.isChecked())) {
                        totalAmt = Double.parseDouble(amount.getText().toString()) * 1.10;
                    } else if (svs.isChecked() && gst.isChecked()) {
                        totalAmt = Double.parseDouble(amount.getText().toString()) * 1.17;
                    }
                }

                if (getDisc != 0)
                {
                    double disc = Double.parseDouble(discount.getText().toString()) * totalAmt;
                    totalAmt = totalAmt - (disc/100);
                }

                String newAmt = String.format("%.2f", totalAmt);
                int getPax = Integer.parseInt(numOfPax.getText().toString());

                if (getPax != 1)
                {
                    int checkedRadioButtonId = paymentMethod.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == R.id.radioButtonCash)
                    {
                        totalBill.setText("Total Bill: $" + newAmt);
                        eachPays.setText("Each Pays: $" + String.format("%.2f in cash", totalAmt/getPax));
                    } else {
                        totalBill.setText("Total Bill: $" + newAmt);
                        eachPays.setText("Each Pays: $" + String.format("%.2f via Paynow to 912345678", totalAmt/getPax));
                    }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
                numOfPax.setText("");
                svs.setChecked(false);
                gst.setChecked(false);
                discount.setText("");
                totalBill.setText("");
                eachPays.setText("");
            }
        });
    }
}