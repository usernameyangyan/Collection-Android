package com.youngmanster.collection.db.fragment;
import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseFragment;
import com.youngmanster.collectionlibrary.db.DataManager;
import butterknife.OnClick;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class FragmentSharePreference extends BaseFragment{

	@Override
	public int getLayoutId() {
		return R.layout.fragment_sharepreference;
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
				DataManager.getInstance(DataManager.DataType.SHAREPREFERENCE).saveByKeyWithSP("user","这是一条测试的内容");
				showToast("保存成功");
				break;
			case R.id.queryBtn:
				String user=DataManager.getInstance(DataManager.DataType.SHAREPREFERENCE).queryByKeyWithSP("user",String.class,"");
				showToast(user);
				break;
		}
	}

}
