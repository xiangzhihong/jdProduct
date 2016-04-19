package com.itau.jingdong.http;

import java.io.UnsupportedEncodingException;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.itau.jingdong.BaseApplication;

/**
 * 
 * @author Doots
 *
 */
public class RequestManager {
	
    public static RequestQueue mRequestQueue = Volley.newRequestQueue(BaseApplication.getInstance());

    private RequestManager() {
        // no instances
    }

    /**
     * 
     * @param <T>
     * @param url
     * @param tag
     * @param listener
     */
    public static void get(String url, Object tag, RequestListener listener) {
    	get(url, tag, null, listener);
    }

    /**
     * 
     * @param url
     * @param tag
     * @param params
     * @param listener
     */
    public static void get(String url, Object tag, RequestParams params, RequestListener listener) {
    	ByteArrayRequest request = new ByteArrayRequest(Method.GET, url, null, responseListener(listener), responseError(listener));
    	addRequest(request , tag);
    }

    /**
     * 
     * @param url
     * @param tag
     * @param listener
     */
    public static void post(String url, Object tag, RequestListener listener) {
    	post(url, tag, null, listener);
    }
    
    /**
     * 
     * @param url
     * @param tag
     * @param params
     * @param listener
     */
    public static void post(String url, Object tag, RequestParams params, RequestListener listener) {
    	ByteArrayRequest request = new ByteArrayRequest(Method.POST, url, params, responseListener(listener), responseError(listener));
    	addRequest(request , tag);
    }
    
    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
    
    
    /**
     * 成功消息监听
     * @param l
     * @return
     */
    protected static Response.Listener<byte[]> responseListener(final RequestListener l) {
    	return new Response.Listener<byte[]>() {
			@Override
			public void onResponse(byte[] arg0) {
				String data = null;
				try {
					data = new String(arg0, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				l.requestSuccess(data);
			}
		};
    }
    
    /**
     * 返回错误监听
     * @param l
     * @return
     */
    protected static Response.ErrorListener responseError(final RequestListener l) {
    	return new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError e) {
				l.requestError(e);
			}
		};
    }
}
