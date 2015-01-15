package android.bobority;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreateAccount extends ActionBarActivity
{
    private ImageButton save;
    private ImageButton proPicUpload, pictureButton;
    public User user1;
    EditText firstNameText;
    EditText lastNameText;
    EditText monthText;
    EditText dateText;
    EditText yearText;
    EditText street1Text;
    EditText street2Text;
    EditText cityText;
    EditText stateText;
    EditText zipcodeText;
    private Bitmap picBitmap;
    private final int REQUEST_CAPTURE_IMAGE = 1;
    private final int REQUEST_LOAD_IMAGE = 2;
    private final int PIC_CROP = 3;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        save = (ImageButton)findViewById(R.id.saveButton);
        proPicUpload = (ImageButton)findViewById(R.id.addProPic);


        dateText   = (EditText)findViewById(R.id.editDay);
        monthText   = (EditText)findViewById(R.id.editMonth);
        yearText   = (EditText)findViewById(R.id.editYear);
        street1Text   = (EditText)findViewById(R.id.editStreet1);
        street2Text   = (EditText)findViewById(R.id.editStreet2);
        cityText   = (EditText)findViewById(R.id.editCity);
        stateText   = (EditText)findViewById(R.id.editState);
        zipcodeText   = (EditText)findViewById(R.id.editZipcode);
        firstNameText   = (EditText)findViewById(R.id.firstName);
        lastNameText   = (EditText)findViewById(R.id.lastName);

        proPicUpload.setOnClickListener(new View.OnClickListener()
        {
            String[] choices = {"Load from Gallery", "Take Now"};

            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog;
                dialog = new AlertDialog.Builder(CreateAccount.this);
                dialog.setSingleChoiceItems(choices, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(intent, REQUEST_LOAD_IMAGE);
                                } else {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
                                }
                                dialog.dismiss();
                            }
                        }
                );
                dialog.create().show();
            }
        });

        firstNameText.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String temp = firstNameText.getText().toString();
                        //user1.readFirstName(temp);
                    }
                });
        lastNameText.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String temp = lastNameText.getText().toString();
                       // user1.readLastName(temp);
                    }
                });
        dateText.setOnClickListener(
            new View.OnClickListener()
            {
                 public void onClick(View view)
                 {
                    String temp = dateText.getText().toString();
                   // user1.readBirthDate(temp);
                 }
            });

        monthText.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String temp = dateText.getText().toString();
                       // user1.readBirthMonth(temp);
                    }
                });
        yearText.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String temp = dateText.getText().toString();
                        //user1.readBirthYear(temp);
                    }
                });
        street1Text.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String temp = dateText.getText().toString();
                        //user1.readStreet1(temp);
                    }
                });
        street2Text.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String temp = dateText.getText().toString();
                        //user1.readStreet2(temp);
                    }
                });
        cityText.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String temp = dateText.getText().toString();
                        //user1.readCity(temp);
                    }
                });
        stateText.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String temp = dateText.getText().toString();
                        //user1.readState(temp);
                    }
                });
        zipcodeText.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String temp = dateText.getText().toString();
                        //user1.readZipcode(temp);
                    }
                });

        save.setOnClickListener( new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccount.this, PaymentChoice.class);
                startActivity(intent);
            }

        });

        proPicUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccount.this, CreateAccount.class);
                startActivity(intent);
            }

        });
    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
    public static byte[] encode(Bitmap bitmap)
    {
        if(bitmap == null) {
            return null;
        }

        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        ByteArrayOutputStream out = new ByteArrayOutputStream(size);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        try {
            out.flush();
            out.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }


    private void doCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 200);
            cropIntent.putExtra("outputY", 200);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException e) {
            String errorMessage = "Error At: "+getCallingActivity().toString();
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex); // String picturePath contains the path of selected Image
            cursor.close();
            doCrop(selectedImage);
        }
        else if(requestCode == REQUEST_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            doCrop(selectedImage);
        }
        else if(requestCode == PIC_CROP && resultCode == Activity.RESULT_OK && null != data) {
            Bundle extras = data.getExtras();
            picBitmap = extras.getParcelable("data");
            picBitmap = Bitmap.createScaledBitmap(picBitmap, pictureButton.getWidth() - 30, pictureButton.getHeight() - 30, false);
            pictureButton.setImageBitmap(picBitmap);
        }
    }
}