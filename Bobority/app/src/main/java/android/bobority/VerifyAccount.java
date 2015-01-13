package android.bobority;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by CarlDemolder on 1/11/2015.
 */
public class VerifyAccount extends Activity
{
    private final int REQUEST_CAPTURE_IMAGE = 1;
    private final int REQUEST_LOAD_IMAGE = 2;
    private final int PIC_CROP = 3;
    private ImageButton saveButton, pictureButton;
    private Bitmap picBitmap;
    public User user1;
    public EditText dateText;
    public EditText monthText;
    public EditText yearText;
    public EditText firstText;
    public EditText lastText;
    public EditText street1Text;
    public EditText street2Text;
    public EditText cityText;
    public EditText stateText;
    public EditText zipcodeText;
    public EditText cardmonthText;
    public EditText cardnumText;
    public EditText cardyearText;
    public EditText cvv2Text;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifyaccount);

        User user1 = new User(
                "Carl",
                "Demolder",
                "2015/1/11",
                "UCSC",
                "1156 High Street",
                "Santa Cruz",
                "California",
                "95064"
        );

        pictureButton = (ImageButton)findViewById(R.id.addProPic);
        pictureButton.setOnClickListener(new View.OnClickListener()
        {
            String[] choices = {"Load from Gallery", "Take Now"};

            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog;
                dialog = new AlertDialog.Builder(VerifyAccount.this);
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

        //dateText= (EditText)findViewById(R.id.verifyBirthDate);
       // dateText.setText(user1.getBirthDate(), TextView.BufferType.EDITABLE);

        //monthText= (EditText)findViewById(R.id.verifyBirthMonth);
        //monthText.setText(user1.getBirthMonth(), TextView.BufferType.EDITABLE);

        //yearText= (EditText)findViewById(R.id.verifyBirthYear);
        //yearText.setText(user1.getBirthYear(), TextView.BufferType.EDITABLE);

        //firstText= (EditText)findViewById(R.id.verifyFirstName);
        //firstText.setText(user1.getFirstName(), TextView.BufferType.EDITABLE);

        //lastText= (EditText)findViewById(R.id.verifyLastName);
        //lastText.setText(user1.getLastName(), TextView.BufferType.EDITABLE);

        //street1Text= (EditText)findViewById(R.id.verifyStreet1);
        //street1Text.setText(user1.getStreet1(), TextView.BufferType.EDITABLE);

        //street2Text= (EditText)findViewById(R.id.verifyStreet2);
        //street2Text.setText(user1.getStreet2(), TextView.BufferType.EDITABLE);

        //cityText= (EditText)findViewById(R.id.verifyCity);
        //cityText.setText(user1.getCity(), TextView.BufferType.EDITABLE);

        //stateText= (EditText)findViewById(R.id.verifyState);
        //stateText.setText(user1.getState(), TextView.BufferType.EDITABLE);

        //zipcodeText= (EditText)findViewById(R.id.verifyZipcode);
        //zipcodeText.setText(user1.getZipcode(), TextView.BufferType.EDITABLE);

        //cvv2Text= (EditText)findViewById(R.id.verifycvv2);
        //cvv2Text.setText(user1.getcvv2(), TextView.BufferType.EDITABLE);

        //cardnumText= (EditText)findViewById(R.id.verifyCardNum);
       // cardnumText.setText(user1.getCardNum(), TextView.BufferType.EDITABLE);

        //cardmonthText= (EditText)findViewById(R.id.verifyCardMonth);
        //cardmonthText.setText(user1.getCardMonth(), TextView.BufferType.EDITABLE);

        //cardyearText= (EditText)findViewById(R.id.verifyCardYear);
        //cardyearText.setText(user1.getCardYear(), TextView.BufferType.EDITABLE);

        //dateText= (EditText)findViewById(R.id.verifyBirthDate);
        //dateText.setText(user1.getBirthDate(), TextView.BufferType.EDITABLE);

        saveButton = (ImageButton)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(VerifyAccount.this, Home.class);
                intent.putExtra("picBitmap", encode(picBitmap));
                startActivity(intent);
            }
        });
    }

    public static byte[] encode(Bitmap bitmap) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
