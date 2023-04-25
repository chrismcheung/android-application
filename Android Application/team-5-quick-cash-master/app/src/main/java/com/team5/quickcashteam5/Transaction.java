package com.team5.quickcashteam5;

import android.app.Activity;
import android.content.Intent;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

import static androidx.core.app.ActivityCompat.startActivityForResult;


public class Transaction {

    //code adapted from PayPal tutorial by TAs

    private static final String PAYPAL_USER_ID = "AQrQxP1U3uCcJswI9ZLK2b98BNi9wLYDI3KN5C6TtfMrsN5OWoisXWNT303mosgRsz3th1ROamfDUcaS";
    private static final int PAYPAL_REQUEST_CODE = (int) (Math.random() * 1000); //generates a random 3 digit number
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_USER_ID);

    Activity activity;
    double amount;
    Intent serviceIntent;

    public Transaction(Activity activity, double amount){
        this.activity = activity;
        this.amount = amount;
        serviceIntent = new Intent(activity, PayPalService.class);
        serviceIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        activity.startService(serviceIntent);
    }



    public void ProcessPayment() {
        Intent paymentIntent = new Intent(activity, PaymentActivity.class);
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(amount),"CAD",
                "Task Complete",PayPalPayment.PAYMENT_INTENT_SALE);
        paymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        paymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(activity, paymentIntent, PAYPAL_REQUEST_CODE, null);
    }

    public void close(){
        activity.stopService(serviceIntent);
    }

}
