package com.itau.jingdong.adapter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itau.jingdong.R;
import com.itau.jingdong.common.CartItemChangedListener;
import com.itau.jingdong.common.NumChangedListener;
import com.itau.jingdong.entity.CartProduct;
import com.itau.jingdong.widgets.NumEditText;

/**
 * 商品类别适配器
 * 
 * @author Cool
 * 
 */
@SuppressLint("HandlerLeak")
public class CartItemAdapter extends BaseAdapter {

	private Context context;
	private FinalBitmap fb;
	private List<CartProduct> cProducts = new ArrayList<CartProduct>();
	private CartItemChangedListener itemChangedListener;
	
	//对话框
	private Dialog dialog =null;
	private Button btn_ok = null;
	private Button btn_cancel = null;
	private TextView tv_price = null;
	private TextView tv_count = null;
	private NumEditText net_numedit = null;
	int count = 1;
	
	public void setCartItemChangedListener(CartItemChangedListener itemChangedListener){
		this.itemChangedListener = itemChangedListener;
	}
	
	public CartItemAdapter(Context context, List<CartProduct> cProducts) {
		this.context = context;
		this.cProducts = cProducts;
		fb = FinalBitmap.create(context);
	}

	@Override
	public int getCount() {
		return cProducts.size();
	}

	@Override
	public Object getItem(int position) {
		return cProducts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.view_cartlist_item,
					null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_cart_name);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.iv_cart_pic);
			holder.price = (TextView) convertView
					.findViewById(R.id.tv_cart_price);
			holder.fbagweight = (TextView) convertView
					.findViewById(R.id.tv_cart_bagweight);
			holder.fnumber = (TextView) convertView
					.findViewById(R.id.tv_cart_fnumber);
			holder.remind = (TextView) convertView
					.findViewById(R.id.tv_cart_remind);
			holder.remindlayout = (LinearLayout) convertView.findViewById(R.id.ll_cart_remind);
			holder.checked = (CheckBox) convertView
					.findViewById(R.id.cb_cart_item_check);
			holder.sum = (TextView) convertView
					.findViewById(R.id.tv_cart_item_sum);
			holder.count = (NumEditText) convertView
					.findViewById(R.id.net_product_count);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		

		holder.count.setNumChangedListener(new NumChangedListener() {

			@Override
			public void numchanged(int num) {
				cProducts.get(position).setCount(num);
				if (itemChangedListener != null) {
					itemChangedListener.itemNumChanged(position, num);
				}
				notifyDataSetChanged();
			}
		});
		
		holder.count.setNumEditClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
			}
		});
		
		holder.checked.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isCheck) {
				cProducts.get(position).setCheck(isCheck);
				if (itemChangedListener != null) {
					itemChangedListener.itemCheckChanged(position, isCheck);
				}
				notifyDataSetChanged();
			}
		});

		
		holder.name.setText(cProducts.get(position).getFcategory()
				+ cProducts.get(position).getFname()
				+ cProducts.get(position).getFmodel());
		holder.price.setText(cProducts.get(position).getFprice());
		holder.fbagweight.setText(cProducts.get(position).getFbagweight());
		holder.fnumber.setText(cProducts.get(position).getFnumber());
		if (cProducts.get(position).getRemind() == null||"".equals(cProducts.get(position).getRemind())) {
			holder.remindlayout.setVisibility(View.GONE);
		}else {
			holder.remindlayout.setVisibility(View.VISIBLE);
			holder.remind.setText(cProducts.get(position).getRemind());
		}
		holder.checked.setChecked(cProducts.get(position).isCheck());
		holder.sum.setText(cProducts.get(position).getSum());
		holder.count.setNum(cProducts.get(position).getCount());
		fb.display(holder.icon, cProducts.get(position).getImgsrc());
		
		return convertView;
	}
	
	
	static class ViewHolder {
		private ImageView icon;
		private TextView name;
		private TextView price;
		private TextView fbagweight;
		private TextView fnumber;
		private TextView remind;
		private LinearLayout remindlayout;
		private CheckBox checked;
		private TextView sum;
		private NumEditText count;
	}
}
