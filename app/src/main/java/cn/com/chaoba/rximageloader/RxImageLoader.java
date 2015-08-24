package cn.com.chaoba.rximageloader;

import android.content.Context;
import android.widget.ImageView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Liyanshun on 2015/8/24.
 */
public class RxImageLoader {
    static Sources sources;

    public static void init(Context mContext) {
        sources = new Sources(mContext);
    }


//    static WeakHashMap<WeakReference<ImageView>, String> map = new WeakHashMap();

    public static Observable<Data> getLoaderObservalve(ImageView img, String url) {

        // Create our sequence for querying best available data
        Observable<Data> source = Observable.concat(sources.memory(url), sources.disk(url), sources.network(url))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .first(data -> data != null && url.equals(data.url) && data.bitmap != null);

        return source.doOnNext(data -> {
            img.setImageBitmap(data.bitmap);
        });
    }
}
