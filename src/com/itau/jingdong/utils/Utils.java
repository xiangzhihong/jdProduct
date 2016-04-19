package com.itau.jingdong.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Handler;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.text.ClipboardManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {

	private static Toast toast;
	private static int width,height;

	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	public static DisplayMetrics getDisplayMetrics(Context context){
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		return mDisplayMetrics;
			}
	/**
	 * 获取屏幕宽度的像素
	 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高度像素
	 */
	public static int getScreenHeight(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, int dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, int pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static void hideKeyBoard(Activity context) {
		if (context != null && context.getCurrentFocus() != null) {
			((InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(context.getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static String changeUrl(String path) {
		String url = path;
		if (!Constant.DEBUG) {
			return url;
		}

		return url;
	}

	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return sdDir.toString();
		} else {
			return null;
		}
	}
	public static int getScreenWidth(Activity context){
		if(width!=0){
			return width;
		}
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		return width;
	}
	
	public static int getScreenHeight(Activity context){
		if(height!=0){
			return height;
		}
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		height = dm.heightPixels;
		return height;
	}
	


	// 复制到剪贴板
	@SuppressWarnings("deprecation")
	public static void copy(String content, Context context) {
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
	}

	// 粘贴
	@SuppressWarnings("deprecation")
	public static String paste(Context context) {
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return cmb.getText().toString().trim();
	}

	public static boolean isEmpty(String s) {
		if (null == s)
			return true;
		if (s.length() == 0)
			return true;
		if (s.trim().length() == 0)
			return true;
		return false;
	}

	// 启动动画
	public void delayAnim(final ImageView iv, final boolean visable,
			final Animation animation, long delayMillis) {

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (visable) {
					iv.setVisibility(View.VISIBLE);
					iv.startAnimation(animation);
				} else {
					iv.setVisibility(View.GONE);
				}
			}
		}, delayMillis);
	}

	public void delayView(final View v, final boolean visable, long delayMillis) {

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (visable) {
					v.setVisibility(View.VISIBLE);
				} else {
					v.setVisibility(View.GONE);
				}
			}
		}, delayMillis);
	}

	public static float getTextHeight(Paint paint, String text) {
		Rect rect = new Rect();
		paint.getTextBounds(text, 0, text.length(), rect);
		return rect.height();
	}

	public static float getAppVer(Activity a, String packageName) {
		float ver = 4.0f;
		try {
			PackageInfo packageInfo = a.getPackageManager().getPackageInfo(
					packageName, 0);
			if (packageInfo.packageName.equals(packageName)) {
				String version = packageInfo.versionName.trim();
				if (!TextUtils.isEmpty(version) && version.length() >= 2) {
					ver = Float.parseFloat(version.substring(0, 1));
				}
			}
		} catch (Exception e) {
			ver = 4.0f;
		}
		return ver;
	}
	
	public static Date toDate(String str) {
		String pattern = "yyyy.MM.dd";
		SimpleDateFormat format = null;
		try {
			if (str.contains(".")) {
				if (str.contains(" ") && str.contains(":"))
					pattern = "yyyy.MM.dd HH:mm:ss";
				else
					pattern = "yyyy.MM.dd";
			} else if (str.contains("-")) {
				if (str.contains(" ") && str.contains(":"))
					pattern = "yyyy-MM-dd HH:mm:ss";
				else
					pattern = "yyyy-MM-dd";
			} else if (str.contains("/")) {
				if (str.contains(" ") && str.contains(":"))
					pattern = "yyyy/MM/dd HH:mm:ss";
				else
					pattern = "yyyy/MM/dd";
			} else {
				long milliseconds = Long.parseLong(str);
				return new Date(milliseconds * 1000);
			}
			format = new SimpleDateFormat(pattern);
			return format.parse(str);
		} catch (Exception e) {
			return new Date();
		}
	}
	/**
	 * 设置下划线
	 * 
	 * @param view
	 * @param isCenterFlag
	 *            true表明是中下划线，false表明是底部下划线
	 */
	public static void setTextViewPaintFlag(TextView view, boolean isCenterFlag) {
		TextPaint mPaint = view.getPaint();
		if (isCenterFlag) {
			mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
			mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.UNDERLINE_TEXT_FLAG);
		}
	}
	
	// 隐藏手机号码中间四位
		public static String hideMobileNum(String phonestr) {
			if (!TextUtils.isEmpty(phonestr) && phonestr.length() == 11) {
				phonestr = phonestr.substring(0, 3) + "****"
						+ phonestr.substring(7, phonestr.length());
			}
			return phonestr;
		}
		
		
		// 获取联系人姓名
		public static String getPhoneContacts(Context context, String contactId) {
			String name = "";
			Cursor cursor = null;
			try {
				cursor = context.getContentResolver().query(
						ContactsContract.Contacts.CONTENT_URI, null,
						BaseColumns._ID + "=?", new String[] { contactId }, null);
				if (cursor.moveToFirst()) {
					name = cursor
							.getString(cursor
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				}
			} catch (Exception e) {
				name = "";
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
			return name;
		}

		// 获取联系人手机号码
		public static String getPhoneNumber(Context context, String contactId) {
			String number = "";
			Cursor cursor = null;
			try {
				cursor = context.getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
								+ contactId, null, null);
				if (cursor.moveToFirst()) {
					do {
						number = cursor
								.getString(cursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						if (!TextUtils.isEmpty(number)) {
							return number;
						}
					} while (cursor.moveToNext());
				}
			} catch (Exception e) {
				number = "";
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
			return number;
		}
		
		public static String orderWaitTiex(int countSencond) {
			int minute = countSencond / 60;
			if (countSencond > 60 * 60) {
				int hour = countSencond / (60 * 60);
				int tmp = countSencond - hour * 3600;
				int min = tmp / 60;
				int sec = tmp % 60;
				String tmpHour = hour + "";
				String tmpMin = min + "";
				String tmpSec = sec + "";
				if (hour < 10) {
					tmpHour = "0" + tmpHour;
				}
				if (min < 10) {
					tmpMin = "0" + tmpMin;
				}
				if (sec < 10) {
					tmpSec = "0" + tmpSec;
				}
				return tmpHour + ":" + tmpMin + ":" + tmpSec;
			}
			int sencond = countSencond % 60;
			StringBuffer buf = new StringBuffer();
			if (minute >= 10) {
				buf.append(minute + ":");
			} else {
				buf.append("0" + minute + ":");
			}
			if (sencond >= 10) {
				buf.append(sencond + "");
			} else {
				buf.append("0" + sencond + "");
			}
			return buf.toString();
		}
}
