package com.atguigu.mtime.home.bean;

public class ShopInfo {

	private String background;
	private int goodsId;
	private String iconText;
	private String image;	//商品图片的地址
	private String longName;
	private int marketPrice;
	private int minSalePrice;
	private String name;
	private String url;		//商品对应的连接
	
	public ShopInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShopInfo(String background, int goodsId, String iconText, String image, String longName, int marketPrice,
			int minSalePrice, String name, String url) {
		super();
		this.background = background;
		this.goodsId = goodsId;
		this.iconText = iconText;
		this.image = image;
		this.longName = longName;
		this.marketPrice = marketPrice;
		this.minSalePrice = minSalePrice;
		this.name = name;
		this.url = url;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getIconText() {
		return iconText;
	}

	public void setIconText(String iconText) {
		this.iconText = iconText;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public int getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(int marketPrice) {
		this.marketPrice = marketPrice;
	}

	public int getMinSalePrice() {
		return minSalePrice;
	}

	public void setMinSalePrice(int minSalePrice) {
		this.minSalePrice = minSalePrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ShopInfo [background=" + background + ", goodsId=" + goodsId + ", iconText=" + iconText + ", image="
				+ image + ", longName=" + longName + ", marketPrice=" + marketPrice + ", minSalePrice=" + minSalePrice
				+ ", name=" + name + ", url=" + url + "]";
	}

}
