package com.youngmanster.collection.data.fragment;

import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseFragment;
import com.youngmanster.collection.been.User;
import com.youngmanster.collectionlibrary.data.DataManager;
import com.youngmanster.collectionlibrary.data.database.SQLiteDataBase;
import com.youngmanster.collectionlibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class FragmentSql extends BaseFragment{

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


	@OnClick({R.id.saveBtn,R.id.queryBtn,R.id.btn1,R.id.btn2})
	public void onMenuClick(View view){
		switch (view.getId()){
			case R.id.saveBtn:
				user=new User();
				user.setId(0);
				user.setName("Young1");
				user.setAge(14);
				user.setAddress("山东省");
				user.setStr("eeeee");
 			    DataManager.DataForSqlite.insert(user);
 			    showToast("保存成功");
				break;
			case R.id.queryBtn:
				user=DataManager.DataForSqlite.queryByFirst(User.class);
				String showContent="用户Id:"+user.getId()+"\n"+"用户姓名："+user.getName()+"\n"+"用户年龄："+user.getAge()+"\n"+"用户地址："+user.getAddress();
				showToast(showContent);
				break;
			case R.id.btn1:
				List<User> users=new ArrayList<>();
				for(int i=0;i<10000;i++){
					user=new User();
					user.setId(i);
					user.setName("Young1");
					user.setAge(14);
					user.setAddress("广州市");

					users.add(user);
				}


				DataManager.DataForSqlite.insertListBySync(User.class, users, new SQLiteDataBase.InsertDataCompleteListener() {
					@Override
					public void onInsertDataComplete(boolean isInsert) {
						if(isInsert){
							ToastUtils.showToast(getActivity(),"保存成功");
						}else{
							ToastUtils.showToast(getActivity(),"保存失败");
						}
					}
				});
				break;
			case R.id.btn2:

				DataManager.DataForSqlite.queryAllBySync(User.class, new SQLiteDataBase.QueryDataCompleteListener<User>() {
					@Override
					public void onQueryComplete(List<User> datas) {
						ToastUtils.showToast(getActivity(),"查询到"+datas.size()+"条数据");
					}
				});
				break;
		}
	}

}
