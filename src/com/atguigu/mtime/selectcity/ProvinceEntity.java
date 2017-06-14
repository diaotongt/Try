package com.atguigu.mtime.selectcity;

import java.util.List;

/**
 * 省会类,就是每一类的首字母
 * @author DTT
 *
 */
public class ProvinceEntity {

	private String name;
	private List<CityEntity> cities;

	public ProvinceEntity(String name, List<CityEntity> cities) {
		super();
		this.name = name;
		this.cities = cities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CityEntity> getCities() {
		return cities;
	}

	public void setCities(List<CityEntity> cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return "ProvinceEntity [name=" + name + ", cities=" + cities + "]";
	}

}
