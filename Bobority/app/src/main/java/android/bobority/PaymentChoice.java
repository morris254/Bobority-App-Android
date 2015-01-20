package android.bobority;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import org.json.JSONException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PaymentChoice extends Activity
{
    private static final String TAG = "paymentExample";

    private ImageButton CardButton;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentchoice);

        CardButton = (ImageButton)findViewById(R.id.cardButton);
        CardButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(PaymentChoice.this, EnterCard.class);
                startActivity(intent);
            }
        });
    }


    /*
     * Enable retrieval of shipping addresses from buyer's PayPal account
     */
    /*private void enableShippingAddressRetrieval(PayPalPayment paypalPayment, boolean enable)
    {
        paypalPayment.enablePayPalShippingAddressesRetrieval(enable);
    }

    @Override
    public void onDestroy()
    {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }*/
}

