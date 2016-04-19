package com.itau.jingdong.entity;

/**
 * 商品类
 * 
 * @author Cool
 * 
 */
public class CartProduct {
	/**
	 * 是否被选中
	 */
	private boolean isCheck;
	/**
	 * 商品数量
	 */
	private int count;
	/**
	 * 商品总价
	 */
	private String sum;
	/**
	 * 提示
	 */
	private String remind;
	/**
	 * 产品类别
	 */
	private String fcategory;
	/**
	 * 编码
	 */
	private String fnumber;
	/**
	 * 名称
	 */
	private String fname;
	/**
	 * 规格
	 */
	private String fmodel;
	/**
	 * 包重
	 */
	private String fbagweight;
	/**
	 * 单价
	 */
	private String fprice;
	/**
	 * 所有者
	 */
	private String fowner;
	/**
	 * 备注
	 */
	private String fremark;
	/**
	 * 图片URL
	 */
	private String imgsrc;
	/**
	 * 推荐
	 */
	private String tag;

	public CartProduct() {
	}

	/**
	 * 
	 * @param isCheck 是否选中
	 * @param count 商品数量
	 * @param sum 商品金额
	 * @param remind 提示
	 * @param fcategory 商品类别
	 * @param fnumber 商品编码
	 * @param fname 商品名称
	 * @param fmodel 商品规格
	 * @param fbagweight 商品包重
	 * @param fprice 商品单价
	 * @param fowner 商品拥有者
	 * @param fremark 商品备注
	 * @param imgsrc 商品图片
	 * @param tag 
	 */
	public CartProduct(Boolean isCheck,int count,String sum,String remind,String fcategory, String fnumber, String fname,
			String fmodel, String fbagweight, String fprice, String fowner,
			String fremark, String imgsrc, String tag) {
		super();
		this.isCheck = isCheck;
		this.count =count;
		this.sum =sum;
		this.remind = remind;
		this.fcategory = fcategory;
		this.fnumber = fnumber;
		this.fname = fname;
		this.fmodel = fmodel;
		this.fbagweight = fbagweight;
		this.fprice = fprice;
		this.fowner = fowner;
		this.fremark = fremark;
		this.imgsrc = imgsrc;
		this.tag = tag;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getFcategory() {
		return fcategory;
	}

	public void setFcategory(String fcategory) {
		this.fcategory = fcategory;
	}

	public String getFnumber() {
		return fnumber;
	}

	public void setFnumber(String fnumber) {
		this.fnumber = fnumber;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFmodel() {
		return fmodel;
	}

	public void setFmodel(String fmodel) {
		this.fmodel = fmodel;
	}

	public String getFbagweight() {
		return fbagweight;
	}

	public void setFbagweight(String fbagweight) {
		this.fbagweight = fbagweight;
	}

	public String getFprice() {
		return fprice;
	}

	public void setFprice(String fprice) {
		this.fprice = fprice;
	}

	public String getFowner() {
		return fowner;
	}

	public void setFowner(String fowner) {
		this.fowner = fowner;
	}

	public String getFremark() {
		return fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}

	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
