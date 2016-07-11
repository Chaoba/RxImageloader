package cn.com.chaoba.rximageloader;

import android.content.Context;
import android.widget.ImageView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;

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


    private static final Map<Integer, String> cacheKeysMap = Collections
            .synchronizedMap(new HashMap<>());

    /**
     * get the observable that load img and set it to the given ImageView
     *
     * @param img the ImageView to show this img
     * @param url the url for the img
     * @return the observable to load img
     */
    public static Observable<Data> getLoaderObservable(ImageView img, String url) {
        if (img != null) {
            cacheKeysMap.put(img.hashCode(), url);
        }
        // Create our sequence for querying best available data
        Observable<Data> source = Observable.concat(sources.memory(url), sources.disk(url), sources.network(url))
                .first(data -> data != null && data.isAvailable() && url.equals(data.url));

        return source.doOnNext(data -> {
            if (img != null && url.equals(cacheKeysMap.get(img.hashCode()))) {
                img.setImageBitmap(data.bitmap);
            }

        });
    }
}
