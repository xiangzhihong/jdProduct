package com.itau.jingdong.ui;

import java.io.File;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.itau.jingdong.R;
import com.itau.jingdong.ui.base.BaseActivity;
import com.itau.jingdong.utils.CommonTools;
import com.itau.jingdong.utils.ExitView;
import com.itau.jingdong.utils.FileUtil;
import com.itau.jingdong.utils.Utils;
import com.itau.jingdong.widgets.CustomScrollView;


public class PersonalActivity extends BaseActivity implements OnClickListener {

	private String[] items = new String[] { "选择本地图片", "拍照" };
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0x000;
	private static final int CAMERA_REQUEST_CODE = 0x001;
	private static final int RESULT_REQUEST_CODE = 0x002;
	private static final String IMAGE_FILE_NAME = Environment
			.getExternalStorageDirectory() + File.separator + "avert.jpg";
	private ImageView mBackgroundImageView = null;
	private Button mLoginButton,mExitButton;
	private CustomScrollView mScrollView = null;
	private Intent mIntent=null;
	private ExitView exit;
	private ImageView user_avert;
	private Bitmap avertBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);
		initView();
	}


	@Override
	protected void initView() {
		mBackgroundImageView = (ImageView) findViewById(R.id.personal_background_image);
		mLoginButton = (Button) findViewById(R.id.personal_login_button);
		mScrollView = (CustomScrollView) findViewById(R.id.personal_scrollView);
		mLoginButton=(Button)this.findViewById(R.id.login_button);
		mExitButton=(Button)this.findViewById(R.id.personal_exit);
		user_avert=(ImageView) findViewById(R.id.user_avert);
		mScrollView.setImageView(mBackgroundImageView);
		
		mLoginButton.setOnClickListener(this);
		mLoginButton.setOnClickListener(this);
		mExitButton.setOnClickListener(this);
		user_avert.setOnClickListener(this);
		try {
			avertBitmap=FileUtil.loadFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null==avertBitmap){
			user_avert.setBackgroundDrawable(getResources().getDrawable(R.drawable.pic));
		}else{
			Drawable drawable = new BitmapDrawable(avertBitmap);
			user_avert.setImageDrawable(drawable);
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			mIntent=new Intent(PersonalActivity.this, LoginActivity.class);
			startActivity(mIntent);
			break;
			
		case R.id.personal_exit:
			exit = new ExitView(PersonalActivity.this, itemsOnClick);
			exit.showAtLocation(PersonalActivity.this.findViewById(R.id.layout_personal), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
			break;
	  //頭像
		case R.id.user_avert:
			showDialog();
			break;
		default:
			break;
		}
		
	}
	
	//为弹出窗口实现监听类
	private OnClickListener  itemsOnClick = new OnClickListener(){
		
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.btn_exit:
				CommonTools.showShortToast(PersonalActivity.this, "退出程序");
				
				break;
			case R.id.btn_cancel:
				PersonalActivity.this.dismissDialog(R.id.btn_cancel);
				
				break;
			default:
				break;
			}
		}
	};
	private void showDialog() {

		new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // 设置文件类型
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1:

							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 判断存储卡是否可以用，可用进行存储
							if (Utils.hasSdcard()) {

								//intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME)));
								intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(IMAGE_FILE_NAME)));
							}
							startActivityForResult(intentFromCapture,
									CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (Utils.hasSdcard()) {
					//File tempFile = new File(Environment.getExternalStorageDirectory()+ IMAGE_FILE_NAME);
					File tempFile = new File(IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					System.out.println("照片照完，但是找不到照片，无法切图！");
					Toast.makeText(this, "未找到存储卡，无法存储照片！",
							Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
	};
	
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			user_avert.setImageDrawable(drawable);
			
			//保存圖片到sd卡
			saveAvertToSdcard(photo);
		}
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}
	/**
	 * 保存图像
	 */
	private void saveAvertToSdcard(Bitmap avert) {
        try {
			FileUtil.saveFile(avert);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
