
/*
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /*
     * This method is called when the order button is clicked.
     */


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkBox);
        boolean WhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox ChocolateCheckbox = findViewById(R.id.chocolate_checkBox);
        boolean hasChocolate = ChocolateCheckbox.isChecked();

        EditText Name_editText = findViewById(R.id.Name_editText);
        String name = Name_editText.getText().toString();

        int price = calculatePrice(WhippedCream, hasChocolate);

        String message = createOrderSummary(price, WhippedCream, hasChocolate, name);

        String subject = getString(R.string.Just_java_order_for)  + name;

        composeEmail( subject, message);
    }

    public void composeEmail(String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");

        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean WhippedCream, boolean hasChocolate) {
        int price;
        if (WhippedCream && (!hasChocolate)) {
            price = quantity * (5 + 1);
        } else if (!WhippedCream && hasChocolate) {
            price = quantity * (5 + 2);
        } else if (WhippedCream) {
            price = quantity * (5+2+1);
        } else {
            price = quantity * 5 ;
        }

        return price;
    }

    public String createOrderSummary(int price, boolean hasWhippedCream, boolean Chocolate, String name) {
        return getString(R.string.names) + name + "\n" +
                getString(R.string.add_whipped_cream) + hasWhippedCream + "\n" +
                getString(R.string.add_chocolate) + Chocolate + "\n"+
                getString(R.string.quan) + quantity + "\n"+
                getString(R.string.total_dollar) + price + "\n"+getString(R.string.thanks);
    }

    public void Increment(View view) {
        if (quantity == 100) {
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void Decrement(View view) {
        if (quantity == 0) {
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

//        display(quantity);

    /*
     * This method displays the given quantity value on the screen.
     */
    @SuppressLint("SetTextI18n")
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(getString(R.string.space) + number);
    }
}

//    /*
//     * This method displays the given text on the screen.
//     * @parammessage
//     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
//}