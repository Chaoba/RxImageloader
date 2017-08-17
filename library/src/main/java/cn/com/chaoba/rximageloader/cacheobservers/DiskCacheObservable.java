package cn.com.chaoba.rximageloader.cacheobservers;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import cn.com.chaoba.rximageloader.Data;
import cn.com.chaoba.rximageloader.Logger;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * DiskCacheObservable load dat from disk
 * Created by Liyanshun on 2015/8/24.
 */
public class DiskCacheObservable extends CacheObservable {
    Context mContext;
    File mCacheFile;

    public DiskCacheObservable(Context mContext) {
        this.mContext = mContext;
        mCacheFile = mContext.getCacheDir();
    }

    @Override
    public Observable<Data> getObservable(String url) {
        return Observable.just(url)
                .map(new Func1<String, Data>() {
                    @Override
                    public Data call(String s) {
                        Logger.i("read file from disk");
                        File f = getFile(url);
                        Data data = new Data(f, url);
                        return data;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private File getFile(String url) {
        url = url.replaceAll(File.separator, "-");
        return new File(mCacheFile, url);
    }

    /**
     * save pictures downloaded from net to disk
     *
     * @param data data to be saved
     */
    public void putData(Data data) {
        Observable.just(data)
                .map(new Func1<Data, Data>() {
                    @Override
                    public Data call(Data data) {
                        File f = getFile(data.url);
                        OutputStream out = null;
                        try {
                            out = new FileOutputStream(f);
                            Bitmap.CompressFormat format;
                            if (data.url.endsWith("png") || data.url.endsWith("PNG")) {
                                format = Bitmap.CompressFormat.PNG;
                            } else {
                                format = Bitmap.CompressFormat.JPEG;
                            }
                            data.bitmap.compress(format, 100, out);
                            out.flush();
                            out.close();
                        } catch (IOException e) {
                            throw Exceptions.propagate(e);
                        } finally {
                            if (out != null) {
                                try {
                                    out.close();
                                } catch (IOException e) {
                                    throw Exceptions.propagate(e);
                                }
                            }
                        }
                        return data;
                    }
                }).subscribeOn(Schedulers.io()).subscribe();
    }
}
