package android.bobority;

import android.graphics.Bitmap;

/**
 * Created by T on 2015/01/10.
 */
public class Item {
    private Bitmap picture;
    private int price;
    private String title;
    private String category;
    private String description;
    private String purchaseType;
    private int deposit;

    public Item(
            Bitmap picture,
            int price,
            String title,
            String category,
            String description,
            String purchaseType,
            int deposit) {
        this.picture = picture;
        this.price = price;
        this.title = title;
        this.category = category;
        this.description = description;
        this.purchaseType = purchaseType;
        this.deposit = deposit;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public int getDeposit() {
        return deposit;
    }
}

