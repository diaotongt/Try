package com.atguigu.mtime.selectcity;

/**
 * 功能：城市类
 * @author DTT
 *
 */
public class CityEntity implements Comparable<CityEntity> {

	private int count;
	private int id;
	private String n;
	private String pinyinFull;
	private String pinyinShort;

	public CityEntity(int count, int id, String n, String pinyinFull, String pinyinShort) {
		super();
		this.count = count;
		this.id = id;
		this.n = n;
		this.pinyinFull = pinyinFull;
		this.pinyinShort = pinyinShort;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getPinyinFull() {
		return pinyinFull;
	}

	public void setPinyinFull(String pinyinFull) {
		this.pinyinFull = pinyinFull;
	}

	public String getPinyinShort() {
		return pinyinShort;
	}

	public void setPinyinShort(String pinyinShort) {
		this.pinyinShort = pinyinShort;
	}

	@Override
	public int compareTo(CityEntity another) {
		return pinyinFull.substring(0, 1).compareTo(another.getPinyinFull().substring(0, 1));
	}

}
