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
    private boolean isAvailable;

    public Data(Bitmap bitmap, String url) {
        this.bitmap = bitmap;
        this.url = url;
//        if (bitmap != null) {
//            isAvailable = true;
//        }
    }

    public Data(File f, String url) {
        if (f != null && f.exists()) {
//            isAvailable = true;
            this.url = url;
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isAvailable() {
        isAvailable = url != null && bitmap != null;
        Logger.i("data is available? " + isAvailable);
        return isAvailable;
    }
}
