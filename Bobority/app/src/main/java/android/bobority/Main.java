package android.bobority;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.Arrays;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class Main extends Activity implements FacebookLoginUtils.FacebookLoginStatus
{
    private ImageButton createaccount;
    private String TAG = this.getClass().getSimpleName();
    private FacebookLoginUtils fLogin;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fLogin = new FacebookLoginUtils(this, R.id.authButton);
        fLogin.setLoginStatus(this);
        fLogin.setEnable(true);

        createaccount = (ImageButton)findViewById(R.id.accountbutton);
        createaccount.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(Main.this, CreateAccount.class);
                startActivity(intent);
            }

        });
    }
    protected void onResume()
    {
        super.onResume();
        fLogin.onResume();
    };

    @Override
    protected void onPause()
    {
        super.onPause();
        fLogin.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        fLogin.onDestroy();
        //TextView txt = (TextView)findViewById(R.id.txt);

        Log.d("Home.java", "asdfasdfasdf");
//        Intent intent = new Intent(Main.this, Home.class);
//        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int responseCode,Intent intent)
    {
        fLogin.onActivityResult(requestCode, responseCode, intent);
    }

    @Override
    public void OnSuccessFacebookLogin(Bundle profile)
    {
        Log.i(TAG,profile.getString(FacebookLoginUtils.USERID));
        Log.i(TAG,profile.getString(FacebookLoginUtils.EMAIL));
        Log.i(TAG,profile.getString(FacebookLoginUtils.NAME));
        Log.i(TAG,profile.getString(FacebookLoginUtils.FIRST_NAME));
        Log.i(TAG,profile.getString(FacebookLoginUtils.LAST_NAME));
    }

}
