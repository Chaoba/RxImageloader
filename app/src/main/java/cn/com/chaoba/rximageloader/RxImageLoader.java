package cn.com.chaoba.rximageloader;

import android.content.Context;
import android.widget.ImageView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The RxImageLoader use Rxjava to load bitmap,please call
 * {@link  #init} mothod before use.
 * Created by Liyanshun on 2015/8/24.
 */
public class RxImageLoader {
    static Sources sources;

    public static void init(Context mContext) {
        sources = new Sources(mContext);
    }


//    static WeakHashMap<WeakReference<ImageView>, String> map = new WeakHashMap();

    /**
     * get the observable that load img and set it to the given ImageView
     * @param img the ImageView to show this img
     * @param url the url for the img
     * @return the observable to load img
     */
    public static Observable<Data> getLoaderObservable(ImageView img, String url) {
        // Create our sequence for querying best available data
        Observable<Data> source = Observable.concat(sources.memory(url), sources.disk(url), sources.network(url))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .first(data -> data != null && data.isAvailable() && url.equals(data.url));

        return source.doOnNext(data -> img.setImageBitmap(data.bitmap));
    }
}
