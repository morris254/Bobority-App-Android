package android.bobority;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by T on 2015/01/11.
 */
public class CheckMark extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmark);

        ImageButton checkMarkButton = (ImageButton)findViewById(R.id.btn_checkmark);
        checkMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckMark.this, Feedback.class);
                startActivity(intent);
            }
        });
    }
}