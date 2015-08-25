package cn.com.chaoba.rximageloader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import rx.Subscription;

public class MainActivity extends Activity {
    final String url = "http://att.x2.hiapk.com/forum/month_1008/100804175235a96c931557db2c.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxImageLoader.init(getApplicationContext());
        setContentView(R.layout.activity_main);
        ImageView img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(e -> startSubscribe(img));
        startSubscribe(img);

    }

    private Subscription startSubscribe(ImageView img) {
        return RxImageLoader.getLoaderObservable(img, url)
                .subscribe(data -> Logger.i("bitmap size:" + data.bitmap.getHeight() * data.bitmap.getWidth()));
    }

}
