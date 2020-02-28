package com.youngmanster.collection.been;


import com.youngmanster.collectionlibrary.data.database.Column;

/**
 * Created by yangyan
 * on 2018/4/16.
 */

public class User {
	@Column(isPrimaryKey =true)
	private int id;
	private String name;
	private int age;
	private String address;
	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
