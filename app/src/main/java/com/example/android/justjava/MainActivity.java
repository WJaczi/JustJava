package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean isWhippedCreamCheckBoxChecked = whippedCreamCheckBox.isChecked();
        Log.v("MainActivity.java","Has whipped cream" + isWhippedCreamCheckBoxChecked);

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean isChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(isWhippedCreamCheckBoxChecked,isChocolate);


        String summaryMessage = createOrderSummary(price, isWhippedCreamCheckBoxChecked,isChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, summaryMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order summary.");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


        /** displayMessage(summaryMessage); */


    }

    private int calculatePrice(boolean isWhippedCreamCheckBoxChecked,boolean isChocolate) {

        int chocolatePrice = 2;
        int whippedCreamPrice = 1;
        int price= 5;



        if (isWhippedCreamCheckBoxChecked) {

           price = price + whippedCreamPrice;
        }

        if (isChocolate) {

            price = price + chocolatePrice;
        }



        int totalPrice = quantity*price;
        return totalPrice;
    }

    private String createOrderSummary(int price,boolean isWhippedCreamCheckBoxChecked,boolean isChocolate, String name){


        String summaryMessage = getString(R.string.order_summary_name,name);
        summaryMessage = summaryMessage + "\nAdd whipped cream: " + isWhippedCreamCheckBoxChecked;
        summaryMessage = summaryMessage + "\nAdd chocolate: " + isChocolate;
        summaryMessage = summaryMessage + "\nQuantity: " + quantity;
        summaryMessage = summaryMessage + "\nTotal: " + price + "$ ";
        summaryMessage = summaryMessage + "\n" + getString(R.string.thank_you);
        return summaryMessage;


    }

    public void increment(View view) {

        if (quantity <100) {

            quantity = quantity + 1;
            display(quantity);
        }
        else {

            Toast.makeText(getApplicationContext(), "Maximum number of coffees is one hundred.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void decrement(View view) {

        if (quantity > 1){

            quantity = quantity - 1;
            display(quantity);
        }
        else {
            Toast.makeText(getApplicationContext(), "Minimum number of coffees is one.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//}
}