package com.itau.jingdong.http;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * 
 * @author Doots
 *
 */
public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }

    @SuppressLint("NewApi")
	@Override
    protected int sizeOf(String key, Bitmap bitmap) {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}

