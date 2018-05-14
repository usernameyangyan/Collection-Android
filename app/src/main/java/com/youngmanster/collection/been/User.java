package com.youngmanster.collection.been;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yangyan
 * on 2018/4/16.
 */

public class User extends RealmObject {
	@PrimaryKey
	private String id;
	private String name;
	private int age;
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
