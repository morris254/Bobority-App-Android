package android.bobority;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Created by CarlDemolder on 1/10/2015.
 */
public class Generator extends ActionBarActivity
{
    private Button generateButton;
    public String barcode;
    public ImageView barcodeimage;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generator);
        generateButton = (Button) findViewById(R.id.generateButton);
        barcodeimage = (ImageView) findViewById(R.id.barcodeiamge);
        barcode="125678";
        generateButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                generateQRCode(barcode);
            }
        });
    }

    public void generateQRCode(String value)
    {
        QRCodeWriter writer = new QRCodeWriter();
        try
        {
            BitMatrix matrix = writer.encode(value, BarcodeFormat.QR_CODE, 700, 700);
            displayBarCode(convertBitMatrix(matrix));
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
    }
    public Bitmap convertBitMatrix(BitMatrix matrix)
    {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                bmp.setPixel(x, y, matrix.get(x,y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }
    public void displayBarCode(Bitmap bmp)
    {
        barcodeimage.setImageBitmap(bmp);
    }
}