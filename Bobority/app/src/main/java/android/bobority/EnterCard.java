package android.bobority;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by CarlDemolder on 1/11/2015.
 */
public class EnterCard extends Activity
{
    private ImageButton saveButton;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entercard);


        saveButton = (ImageButton)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(EnterCard.this, VerifyAccount.class);
                startActivity(intent);
            }
        });
    }

}

