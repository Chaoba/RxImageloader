package cn.com.chaoba.rximageloader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity {
    final String url="http://att.x2.hiapk.com/forum/month_1008/100804175235a96c931557db2c.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img= (ImageView) findViewById(R.id.img);
        RxImageLoader.init(getApplicationContext());
        RxImageLoader.getLoaderObservalve(img,url)
                .subscribe(data -> Log.d("test","bitmap size:"+data.bitmap.getHeight()*data.bitmap.getWidth()));
    }

}
