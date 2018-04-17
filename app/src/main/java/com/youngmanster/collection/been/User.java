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
}
