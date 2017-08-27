package cn.com.chaoba.rximageloader;

import android.content.Context;
import android.widget.ImageView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

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


    private static final Map<Integer, String> cacheKeysMap
            = Collections.synchronizedMap(new HashMap<Integer, String>());

    /**
     * get the observable that load img and
     * set it to the given ImageView
     *
     * @param img the ImageView to show this img
     * @param url the url for the img
     * @return the observable to load img
     */
    public static Observable<Data> loadImage(final ImageView img,
                                             final String url) {
        if (img != null) {
            cacheKeysMap.put(img.hashCode(), url);
        }
        // Create our sequence for querying best available data
        Observable<Data> source = Observable.concat(
                sources.memory(url),
                sources.disk(url),
                sources.network(url))
                .first(new Func1<Data, Boolean>() {
                    @Override
                    public Boolean call(Data data) {
                        return data != null
                                && data.isAvailable()
                                && url.equals(data.url);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        return source.doOnNext(new Action1<Data>() {
            @Override
            public void call(Data data) {
                int hashcode = img.hashCode();
                String cachedUrl = cacheKeysMap.get(hashcode);
                if (img != null && url.equals(cachedUrl)) {
                    img.setImageBitmap(data.bitmap);
                }
            }
        });
    }
}
