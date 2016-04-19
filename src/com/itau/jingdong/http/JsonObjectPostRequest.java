package com.itau.jingdong.http;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
/**
 * 返回一个hashmap
 * @author xiangzhihong
 *
 */
public class JsonObjectPostRequest extends Request<JSONObject> {

	 private Map<String,String> mMap;
     private Listener<JSONObject>  mListener;

	 public JsonObjectPostRequest(String url,Listener<JSONObject> listener, ErrorListener errorListener,Map map) {
         super(Request.Method.POST, url, errorListener);
         this.mListener=listener;
         this.mMap=map;
         
 }

	 @Override
	protected Map<String, String> getParams() throws AuthFailureError {
		// TODO Auto-generated method stub
		return mMap;
	}
	 
	@Override
	protected void deliverResponse(JSONObject response) {
		// TODO Auto-generated method stub
		mListener.onResponse(response);
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		// TODO Auto-generated method stub
		 try {
             String jsonString =
                 new String(response.data, HttpHeaderParser.parseCharset(response.headers));
             return Response.success(new JSONObject(jsonString),
                     HttpHeaderParser.parseCacheHeaders(response));
         } catch (UnsupportedEncodingException e) {
        	 System.out.println(e.getMessage());
             return Response.error(new ParseError(e));
         } catch (JSONException je) {
        	 System.out.println(je.getMessage());
             return Response.error(new ParseError(je));
         }
	}

}
