package com.itau.jingdong.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itau.jingdong.R;

public class MyAlertDialog extends MyDialog {
	private MyListener listener;
	private MyListenerAction myListenerAction;
	private Context context;
	private TextView bt_confirm;
	private TextView bt_cancel;
	private TextView title;
	private TextView content;
	private View margin_view;
	private String msg;
	private TextView safetext;// V5.0 ADD
	private ImageView img;
	private RelativeLayout edit_layout;
	private ProgressBar progressBar;
	private boolean isClickConfimAutoDismissDialog = true;

	public MyAlertDialog(Context context, String msg, MyListener listener) {
		super(context);
		this.msg = msg;
		this.listener = listener;
		this.context = context;
		super.createView();
		super.setCancelable(true);
		super.setCanceledOnTouchOutside(true);
	}

	public MyAlertDialog(Context context, int msgStrResId, MyListener listener) {
		this(context, context.getString(msgStrResId), listener);
	}

	public MyAlertDialog(Context context, String msg, MyListener listener,
			MyListenerAction myListenerAction) {
		this(context, msg, listener);
		this.isClickConfimAutoDismissDialog = false;
		this.myListenerAction = myListenerAction;
	}

	@Override
	protected View getView() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.dt_dialog_new_alert, null);
		title = (TextView) view.findViewById(R.id.title);
		content = (TextView) view.findViewById(R.id.content);
		if (!TextUtils.isEmpty(msg)) {
			content.setText(msg);
			content.setVisibility(View.VISIBLE);
		}
		bt_confirm = (TextView) view.findViewById(R.id.bt_confirm);
		bt_cancel = (TextView) view.findViewById(R.id.bt_cancel);
		margin_view = view.findViewById(R.id.margin_view);
		safetext = (TextView) view.findViewById(R.id.content_show);
		edit_layout = (RelativeLayout) view.findViewById(R.id.edit_layout);
		img = (ImageView) view.findViewById(R.id.img);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

		bt_confirm.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isClickConfimAutoDismissDialog) {
					MyAlertDialog.this.dismiss();
				}
				if (listener != null) {
					listener.onConfirm();
				}
			}
		});
		bt_cancel.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MyAlertDialog.this.dismiss();
				if (listener != null) {
					listener.onCancel();
				}
			}
		});
		img.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (myListenerAction != null) {
					myListenerAction.onAction();
				}
			}
		});
		return view;
	}

	public TextView getBt_confirm() {
		bt_confirm.setVisibility(View.VISIBLE);
		return bt_confirm;
	}

	public TextView getBt_cancel() {
		margin_view.setVisibility(View.VISIBLE);
		bt_cancel.setVisibility(View.VISIBLE);
		return bt_cancel;
	}

	public TextView getTitle() {
		title.setVisibility(View.VISIBLE);
		return title;
	}

	public TextView getContent() {
		content.setVisibility(View.VISIBLE);
		return content;
	}

	public View getMargin_view() {
		content.setVisibility(View.VISIBLE);
		return margin_view;
	}


	public ImageView getImg() {
		img.setVisibility(View.VISIBLE);
		return img;
	}

	public ProgressBar getProgressBar() {
		progressBar.setVisibility(View.VISIBLE);
		return progressBar;
	}

	public RelativeLayout getEdit_layout() {
		edit_layout.setVisibility(View.VISIBLE);
		return edit_layout;
	}

	public TextView getSafetext() {
		safetext.setVisibility(View.VISIBLE);
		return safetext;
	}

	public interface MyListener {
		public void onConfirm();

		public void onCancel();
	}

	public interface MyListenerAction {
		public void onAction();
	}

	public void mustClick() {
		this.setCancelable(false);
		this.setCanceledOnTouchOutside(false);
	}
}