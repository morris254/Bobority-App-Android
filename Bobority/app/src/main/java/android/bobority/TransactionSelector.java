package android.bobority;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by T on 2015/01/11.
 */
public class TransactionSelector extends Activity
{
    Button buyerButton;
    Button sellerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_selector);

        findViews();
        setListeners();
    }

    private void findViews() {
        buyerButton = (Button)findViewById(R.id.btn_buyer);
        sellerButton = (Button)findViewById(R.id.btn_seller);
    }

    private void setListeners() {
        buyerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionSelector.this, Scanner.class);
                startActivity(intent);
            }
        });

        sellerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (TransactionSelector.this, Generator.class);
                startActivity(intent);
            }
        });
    }
}