package android.bobority;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 2015/01/09.
 */
public class Home extends Activity {
    private final int REQUEST_CAPTURE_IMAGE = 1;
    private final int REQUEST_LOAD_IMAGE = 2;
    private final int PIC_CROP = 3;
    private final String[] purchaseTypes = {"Buy", "Borrow", "Rent"};
    private final String[] categories = {"All", "Bikes", "Books", "Cloths", "Furniture", "Electronics", "Other"};

    private TabHost tabHost;
    private EditText searchEditText_tab1;
    private EditText priceEditText_tab2, titleEditText_tab2, descriptionEditText_tab2, depositEditText_tab2;
    private Button categoryButton_tab1, purchaseTypeButton_tab1, searchButton_tab1;
    private Button categoryButton_tab2, purchaseTypeButton_tab2, postButton_tab2;
    private ImageButton uploadPicImageButton_tab2;
    private TextView searchConditionTextView_tab1, hitCountTextView_tab1;
    private ListView itemListView;
    private ItemListAdapter itemListAdapter;
    private Bitmap picBitmap;
    byte[] profilePictureByteArray;
    private TableRow depositTableRow_tab2;

    /*** setting default value ***/
    private String selectedCategory_tab1 = "";
    private String selectedCategory_tab2 = "";
    protected ArrayList<String> selectedPurchaseTypes_tab1 = new ArrayList<String>();
    private String selectedPurchaseType_tab2 = "";
    private int hitCount = 0;
    private ArrayList<Item> itemList = new ArrayList<Item>();
    private ArrayList<Item> hitItemList = new ArrayList<Item>();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /***???::***/
        createDummyData();
        findViews();
        setTabs();
        setListeners();

        Intent intent = getIntent();
        profilePictureByteArray = intent.getByteArrayExtra("picBitmap");

        itemListAdapter = new ItemListAdapter(this, hitItemList);
        itemListView.setAdapter(itemListAdapter);
    }

    private void findViews() {
        /*** for search tab ***/
        tabHost = (TabHost)findViewById(R.id.tabHost);
        searchEditText_tab1 = (EditText)findViewById(R.id.edt_search_tab1);
        categoryButton_tab1 = (Button)findViewById(R.id.btn_category_tab1);
        purchaseTypeButton_tab1 = (Button)findViewById(R.id.btn_purchase_type_tab1);
        searchButton_tab1 = (Button)findViewById(R.id.btn_search_tab1);
        searchConditionTextView_tab1 = (TextView)findViewById(R.id.txv_search_condition_tab1);
        hitCountTextView_tab1 = (TextView)findViewById(R.id.txv_hit_count_tab1);
        itemListView = (ListView)findViewById(R.id.lstvew_tab1);

        /*** for post tab ***/
        uploadPicImageButton_tab2 = (ImageButton)findViewById(R.id.imgbtn_upload_pic_tab2);
        priceEditText_tab2 = (EditText)findViewById(R.id.edt_price_tab2);
        titleEditText_tab2 = (EditText)findViewById(R.id.edt_title_tab2);
        categoryButton_tab2 = (Button)findViewById(R.id.btn_category_tab2);
        descriptionEditText_tab2 = (EditText)findViewById(R.id.edt_description_tab2);
        purchaseTypeButton_tab2 = (Button)findViewById(R.id.btn_purchase_type_tab2);
        depositEditText_tab2 = (EditText)findViewById(R.id.edt_deposit_tab2);
        depositTableRow_tab2 = (TableRow)findViewById(R.id.tblrow_deposit);
        postButton_tab2 = (Button)findViewById(R.id.btn_post_tab2);
    }

    private void setTabs() {
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("search");
        tabSpec.setContent(R.id.search);
        tabSpec.setIndicator("Search");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("post");
        tabSpec.setContent(R.id.post);
        tabSpec.setIndicator("Post");
        tabHost.addTab(tabSpec);
    }

    private void setListeners() {
        /******************/
        /*** search tab ***/
        /******************/
        categoryButton_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog;
                dialog = new AlertDialog.Builder(Home.this);
                dialog.setSingleChoiceItems(categories, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                selectedCategory_tab1 = categories[which];
                            }
                        }
                );
                dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String str = "";
                        if (!selectedCategory_tab1.equals("")) {
                            Toast.makeText(Home.this, "You have selected "+selectedCategory_tab1, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.create().show();
            }
        });


        purchaseTypeButton_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPurchaseTypes_tab1 = new ArrayList<String>();
                boolean[] checkStatus = {false, false, false};

                AlertDialog.Builder dialog;
                dialog = new AlertDialog.Builder(Home.this);
                dialog.setMultiChoiceItems(purchaseTypes, checkStatus,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        public void onClick(DialogInterface dialog, int which, boolean flag) {
                            String str = purchaseTypes[which];

                            if(flag) {
                                selectedPurchaseTypes_tab1.add(str);
                            }
                            else {
                                selectedPurchaseTypes_tab1.remove(str);
                            }
                        }
                    }
                );
                dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (selectedPurchaseTypes_tab1.size() != 0) {
                            String str = appendStrings(selectedPurchaseTypes_tab1, ", ").toString();
                            Toast.makeText(Home.this, "You have selected "+str, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Home.this, "Select Purchase Type", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.create().show();
            }
        });

        searchButton_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadyToShow()) {
                    showSearchResult();
                }
            }
        });

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Home.this, TransactionSelector.class);
                startActivity(intent);
            }
        });

        /****************/
        /*** post tab ***/
        /****************/
        uploadPicImageButton_tab2.setOnClickListener(new View.OnClickListener() {
            String[] choices = {"Load from Gallery", "Take Now"};

            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog;
                dialog = new AlertDialog.Builder(Home.this);
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

        categoryButton_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog;
                dialog = new AlertDialog.Builder(Home.this);
                dialog.setSingleChoiceItems(categories, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                selectedCategory_tab2 = categories[which];
                            }
                        }
                );
                dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String str = "";
                        if (!selectedCategory_tab2.equals("")) {
                            Toast.makeText(Home.this, "You have selected "+selectedCategory_tab2, Toast.LENGTH_SHORT).show();
                            categoryButton_tab2.setText(selectedCategory_tab2);
                        }
                    }
                });
                dialog.create().show();
            }
        });

        purchaseTypeButton_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog;
                dialog = new AlertDialog.Builder(Home.this);
                dialog.setSingleChoiceItems(purchaseTypes, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                selectedPurchaseType_tab2 = purchaseTypes[which];
                                if (which == 0) {
                                    depositTableRow_tab2.setVisibility(View.GONE);
                                }
                                else if (which == 1) {
                                    depositTableRow_tab2.setVisibility(View.VISIBLE);
                                }
                                else if (which == 2) {
                                    depositTableRow_tab2.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                );
                dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String str = "";
                        if (!selectedPurchaseType_tab2.equals("")) {
                            Toast.makeText(Home.this, "You have selected "+selectedPurchaseType_tab2, Toast.LENGTH_SHORT).show();
                            purchaseTypeButton_tab2.setText(selectedPurchaseType_tab2);
                        }
                    }
                });
                dialog.create().show();
            }
        });

        postButton_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadyToPost()) {
                    AlertDialog.Builder dialog;
                    dialog = new AlertDialog.Builder(Home.this);
                    dialog.setTitle("Are you sure you want to post this item?");
                    if (selectedPurchaseType_tab2.equals("Buy")) {
                        dialog.setMessage(
                                "Price:"+"\n     "+priceEditText_tab2.getText().toString()+"\n"
                                        +"Title:"+"\n     "+titleEditText_tab2.getText().toString()+"\n"
                                        +"Category:"+"\n     "+categoryButton_tab2.getText().toString()+"\n"
                                        +"Description:"+"\n     "+descriptionEditText_tab2.getText().toString()+"\n"
                                        +"PurchaseType:"+"\n     "+purchaseTypeButton_tab2.getText().toString()
                        );
                    }
                    else { // if "Borrow" or "Rent"
                        dialog.setMessage(
                                "Price:"+"\n     "+priceEditText_tab2.getText().toString()+"\n"
                                        +"Title:"+"\n     "+titleEditText_tab2.getText().toString()+"\n"
                                        +"Category:"+"\n     "+categoryButton_tab2.getText().toString()+"\n"
                                        +"Description:"+"\n     "+descriptionEditText_tab2.getText().toString()+"\n"
                                        +"PurchaseType:"+"\n     "+purchaseTypeButton_tab2.getText().toString()+"\n"
                                        +"Deposit:"+"\n     "+depositEditText_tab2.getText().toString()
                        );
                    }
                    dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String str = "";
                            if (!selectedPurchaseType_tab2.equals("")) {
                                Toast.makeText(Home.this, "Your Post is Done", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(getIntent());
                            }
                        }
                    });
                    dialog.create().show();
                }
            }
        });
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
            picBitmap = Bitmap.createScaledBitmap(picBitmap, uploadPicImageButton_tab2.getWidth()-30, uploadPicImageButton_tab2.getHeight()-30, false);
            uploadPicImageButton_tab2.setImageBitmap(picBitmap);
        }
    }

    private boolean isReadyToShow() {
        if (selectedPurchaseTypes_tab1.size() == 0) {
            Toast.makeText(Home.this, "Select Purchase Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (selectedCategory_tab1.equals("")) {
            selectedCategory_tab1 = "All";
        }
        return true;
    }

    private boolean isReadyToPost() {
        if (picBitmap == null) {
            Toast.makeText(Home.this, "Upload Picture", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (priceEditText_tab2.getText().toString().equals("")) {
            Toast.makeText(Home.this, "Fill in Price Column", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (titleEditText_tab2.getText().toString().equals("")) {
            Toast.makeText(Home.this, "Fill in Title Column", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (selectedCategory_tab2.equals("")) {
            Toast.makeText(Home.this, "Select Category", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (descriptionEditText_tab2.getText().toString().equals("")) {
            Toast.makeText(Home.this, "Fill in Description Column", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (selectedPurchaseType_tab2.equals("")) {
            Toast.makeText(Home.this, "Select Purchase Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if ((selectedPurchaseType_tab2.equals("Borrow") || selectedPurchaseType_tab2.equals("Rent"))
                && depositEditText_tab2.getText().toString().equals("")) {
            Toast.makeText(Home.this, "Fill in Deposit Column", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private StringBuilder appendStrings(ArrayList<String> arrayList, String conjunction) {
        StringBuilder str = new StringBuilder();
        str.append(arrayList.get(0));
        for (int i = 1; i < arrayList.size(); i++) {
            str.append(conjunction + arrayList.get(i));
        }
        return str;
    }

    private void showSearchResult() {
        setSearchConditionText();
        doSearch();
        showImage();
    }

    private void setSearchConditionText() {
        searchConditionTextView_tab1.setText(
                "Your Search Condition is: \n"
                        + "   Category: "
                        + selectedCategory_tab1 + "\n"
                        + "   String: "
                        + searchEditText_tab1.getText().toString() + "\n"
                        + "   Purchase Type: "
                        + appendStrings(selectedPurchaseTypes_tab1, ", ").toString()
        );
    }

    private void createDummyData() {
        // picture, price, title, category, description, purchaseYype
        Item bike1 = new Item(BitmapFactory.decodeResource(Home.this.getResources(), R.drawable.bike1), 50, "Mountain", "Bikes", "It's good, dude", "Buy", 0);
        Item bike2 = new Item(BitmapFactory.decodeResource(Home.this.getResources(), R.drawable.bike2), 0, "Road", "Bikes", "Used, Pretty good", "Borrow", 70);
        Item bike3 = new Item(BitmapFactory.decodeResource(Home.this.getResources(), R.drawable.bike3), 210, "Road", "Bikes", "Brand new.", "Buy", 0);
        Item bike4 = new Item(BitmapFactory.decodeResource(Home.this.getResources(), R.drawable.bike4), 70, "Mountain", "Bikes", "Old. It's okay.", "Rent", 5);
        Item bike5 = new Item(BitmapFactory.decodeResource(Home.this.getResources(), R.drawable.bike5), 0, "City", "Bikes", "Okay quality", "Borrow", 50);

        itemList.add(bike1);
        itemList.add(bike2);
        itemList.add(bike3);
        itemList.add(bike4);
        itemList.add(bike5);
    }

    private void doSearch() {
        hitCount = 0;
        hitItemList.clear();
        for (int i=0; i<itemList.size(); i++) {
            if (selectedCategory_tab1.equals(itemList.get(i).getCategory()) || selectedCategory_tab1.equals("All"))
            {
                if (searchEditText_tab1.getText().toString().equalsIgnoreCase(itemList.get(i).getTitle())
                        || searchEditText_tab1.getText().toString().equals(""))
                {
                    for (int j = 0; j < (selectedPurchaseTypes_tab1.size()); j++) {
                        if (selectedPurchaseTypes_tab1.get(j).equals(itemList.get(i).getPurchaseType())) {
                            hitItemList.add(itemList.get(i));
                            hitCount++;
                        }
                    }
                }
            }
        }
    }

    private void showImage()
    {
        hitCountTextView_tab1.setText("Hit: " + hitCount);
    }
}

