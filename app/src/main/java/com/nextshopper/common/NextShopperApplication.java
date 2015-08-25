package com.nextshopper.common;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.nextshopper.rest.BitmapWorkerTask;
import com.nextshopper.rest.beans.Product;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by siyiliu on 8/20/15.
 */
public class NextShopperApplication extends Application{
    private Map<String, Product> productMap;
    private LruCache<String, Bitmap> memoryCache;

    @Override
    public void onCreate()
    {
        super.onCreate();
        productMap =  new HashMap<>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    public Map<String, Product> getProductMap() {
        return productMap;
    }

    public LruCache<String, Bitmap> getCache(){
        return memoryCache;
    }

    public void loadBitmaps(String imageUrl, ImageView imageView, boolean toRound, int height) {
        try {
            imageView.setTag(imageUrl);
            Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
            if (bitmap == null) {
                BitmapWorkerTask task = new BitmapWorkerTask(imageView, toRound, height);
                //task.execute(imageUrl);
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imageUrl);
            } else {
                if (imageView != null && bitmap != null && imageView.getTag().equals(imageUrl)) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Bitmap getBitmapFromMemoryCache(String key) {
        return memoryCache.get(key);
    }

    void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }
}
