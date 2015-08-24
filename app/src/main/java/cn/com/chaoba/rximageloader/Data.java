package cn.com.chaoba.rximageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * class store the url and bitmap
 * Created by Liyanshun on 2015/8/24.
 */
public class Data {
    public Bitmap bitmap;
    public String url;
    private String message;

    public Data(Bitmap bitmap, String url) {
        this.bitmap = bitmap;
        this.url = url;
    }

    public Data(File f, String url) {
        if (f != null && f.exists()) {
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
                this.url = url;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isAvailable() {
        Boolean available = url != null && bitmap != null;
        Logger.d("data is available? " + available);
        return available;
    }
}
