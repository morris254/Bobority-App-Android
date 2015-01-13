package android.bobority;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 2015/01/11.
 */
public class ItemListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> itemList;

    public ItemListAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = null;
        Item item = itemList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.row_product_item, parent, false);
        } else {
            itemView = convertView;
        }

        if (item != null) {
            ImageView cardImageView = (ImageView) itemView.findViewById(R.id.img_item);
            TextView priceTextView = (TextView) itemView.findViewById(R.id.txv_price);

            cardImageView.setImageBitmap(item.getPicture());
            priceTextView.setText("$"+String.valueOf(item.getPrice()));
        }
        return itemView;
    }

}
