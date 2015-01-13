package android.bobority;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by CarlDemolder on 1/10/2015.
 */
public class Scanner extends ActionBarActivity
{
    private Button scanButton;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner);
        scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                IntentIntegrator integrator = new IntentIntegrator(Scanner.this);
                integrator.initiateScan();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null)
        {
            String re = scanResult.getContents();
            Log.d("code", re);
            Intent intent1 = new Intent(Scanner.this, CheckMark.class);
            startActivity(intent1);
        }
    }
}