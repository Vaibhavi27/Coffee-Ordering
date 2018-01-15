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

import org.w3c.dom.Text;

import java.text.NumberFormat;

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


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.name_field);
        String name = nameEditText.getText().toString();
        Log.v("MainActivity","Name : " +name);

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        Log.v("MainActivity","Has Whipped Cream :" +hasWhippedCream);

        CheckBox chocolateCreamCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolateCream = chocolateCreamCheckbox.isChecked();
        Log.v("MainActivity" ,"Has Chocolate Cream :" +hasChocolateCream);

        int price = calculatePrice(hasWhippedCream, hasChocolateCream);
        String priceMessage = createOrderSummary(name,price,hasWhippedCream,hasChocolateCream);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order for :" +name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCopffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.textView2);
        quantityTextView.setText("" + numberOfCopffees);
    }
    public int calculatePrice(boolean addWhippedCream, boolean addChocolateCream)
    {
        int basePrice = 5;
        if(addWhippedCream)
        {
            basePrice += 1;
        }
        if(addChocolateCream)
        {
            basePrice += 2;
        }

        return basePrice * quantity;
    }

    private String createOrderSummary(String names,int price, boolean addWhippedCream,boolean addChocolateCream)
    {
        String priceMessage = "Name :" +names;
        priceMessage +="\nHas Whipped Cream ?" + addWhippedCream;
        priceMessage +="\nHas Chocolate Cream ?" + addChocolateCream;
        priceMessage +="\nQuantity : " + quantity ;
        priceMessage +="\nTotal : $" + price;
        priceMessage +="\nThank You!!";
        return priceMessage;

    }

    public void increment(View view) {
        if(quantity ==100)
        {
            Toast.makeText(this, "You cannot have more than 100 cup of coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    public void decrement(View view) {
        if(quantity ==1)
        {
            Toast.makeText(this, "You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
}