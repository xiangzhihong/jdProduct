package com.itau.jingdong.http;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.support.v4.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
/**
 * 返回一个arraylist
 * @author xiangzhihong
 *
 */
public class JsonArrayPostRequest extends Request<JSONArray> {
	
	 private Map<String,String> mMap;
     private Listener<JSONArray>  mListener;
     private LruCache lrucache;

	 public JsonArrayPostRequest(String url, Listener<JSONArray> listener, ErrorListener errorListener,Map map) {
		 super(Request.Method.POST, url, errorListener);
		this.mListener=listener;
		this.mMap=map;
	}

	 @Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mMap;
	}
	 
	@Override
	protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
		try {
            String jsonString =
                new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
	}

	@Override
	protected void deliverResponse(JSONArray response) {
		mListener.onResponse(response);
		
	}
	
	
}
