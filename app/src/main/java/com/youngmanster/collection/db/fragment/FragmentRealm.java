package com.youngmanster.collection.db.fragment;

import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseFragment;
import com.youngmanster.collection.been.User;
import com.youngmanster.collectionlibrary.db.DataManager;

import butterknife.OnClick;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class FragmentRealm extends BaseFragment{

	private User user;

	@Override
	public int getLayoutId() {
		return R.layout.fragment_realm;
	}

	@Override
	public void init() {
	}

	@Override
	public void requestData() {
	}


	@OnClick({R.id.saveBtn,R.id.queryBtn})
	public void onMenuClick(View view){
		switch (view.getId()){
			case R.id.saveBtn:
				user=new User();
				user.setId("132323");
				user.setName("Young1");
				user.setAge(14);
				user.setAddress("广州市");
				DataManager.getInstance(DataManager.DataType.REALM).saveOrUpdateWithPKByRealm(user);
				showToast("保存成功");
				break;
			case R.id.queryBtn:
				user= (User) DataManager.getInstance(DataManager.DataType.REALM).queryFirstByRealm(User.class);
				String showContent="用户Id:"+user.getId()+"\n"+"用户姓名："+user.getName()+"\n"+"用户年龄："+user.getAge()+"\n"+"用户地址："+user.getAddress();
				showToast(showContent);
				break;
		}
	}

}
