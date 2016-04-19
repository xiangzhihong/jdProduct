package com.itau.jingdong.entity;

public class Type {
     private int id;
     private String typename;
     private String typeiconurl;
     
     
     public Type(int id,String typename,String typeiconurl)
     {
    	 super();
    	 this.id=id;
    	 this.typename=typename;
    	 this.typeiconurl=typeiconurl;
     }
     
     
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getTypeiconurl() {
		return typeiconurl;
	}
	public void setTypeiconurl(String typeiconurl) {
		this.typeiconurl = typeiconurl;
	}
     
     
     
}
