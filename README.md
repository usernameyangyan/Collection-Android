## Collection

![Travis](https://img.shields.io/badge/release-1.3.8-green.svg)
![Travis](https://img.shields.io/badge/llicense-MIT-green.svg)
![Travis](https://img.shields.io/badge/build-passing-green.svg)


Collection聚合了项目搭建的一些基本模块，节约开发者时间，协助项目的快速搭建,RecyclerView+Adapter+Retrofit+RxJava+MVP+DataManager+基本Base,能够满足一个项目的基本实现。


#### 更多交流请加微信公众号
![](https://upload-images.jianshu.io/upload_images/4361802-88c89753c38ddf70.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>#### 推荐文章

>  适配Android x库的Collection-kotlin:[https://juejin.im/post/5e59d0eef265da57315b0b0e](https://juejin.im/post/5e59d0eef265da57315b0b0e)

>  Collection-iOS库:[https://juejin.im/post/5e423d4ef265da572a0cec9f](https://juejin.im/post/5e423d4ef265da572a0cec9f)



>###### 简书地址：https://www.jianshu.com/p/0a8c27bc8457
>###### 掘金地址：https://juejin.im/post/5ab9987451882555635e5401




###  更新说明

####   v1.3.8
> 1.DialogFragment替换AlertDialog
> 2.SharePreference统一初始化
> 3.简化Fragment的跳转逻辑
> 4.DataManager.DataForHttp增加文件下载

####   v1.3.4
> 1.修复SQLite没有创建表查询异常
> 2.SQLite增加按条件查询List
> 3.增加PopupWindow显示位置设置
> 4.解决SQLit内容为null报错
>  5.状态栏修改：增加设置状态栏透明+黑色字体


####   v1.2.9
>  1.去掉Relam数据模块,安装包大小减少
>  2.对原生SQLite数据库进行封装，使用更加方便
>  3.对DataManager的使用进行修整
>  4.增加AutoLineLayout/TagView
>  5.增加LinkedMultiValueMap
>  6.增加RxJavaUtils,可进行子/主线程数据处理切换

####   v1.2.8
>  1.更新Realm数据库依赖。
>  2.更新RxJava、rxandroid、retrofit、converter-gson、adapter-rxjava2依赖。
>  3.封装好Fragment之间的交互，项目中可以选择使用一个Activity来作为跟容器，其它实现页面统一使用fragment来实现。
>  4.collectionLibary中的Config配置类增加json字段过滤、网络请求超时设置、网络请求头设置（全局请求头）。
>  5.增加自动换行布局。
>  6.Realm增加按数据字段查询和删除接口。
>  7.网络请求类型HttpType增加json类型请求参数。
>  8.网络请求增加个别接口请求头设置。
>  9.增加适配不同手机像素。

####   v1.2.7
>  1.增加自定义控件TabLayout。

####   v1.2.6
>  1.RxJava的依赖更新。
>  2.修正RecyclerView头部布局不能铺满问题。
>  3.PopupWindow的使用。
>  4.DisplayUtils工具类对状态栏的修改。

####   v1.2.5
>  1.修正Retrofit DEFAULT_POST请求方式指向错误。  
>  2.Retrofit 数据解析兼容没有公用been类，可以指定公用been类和不指定公用been类、或者混合使用。
>  3.Realm增加数据迁移（数据库字段增加或移除）。
>  4.增加几种通用的Dialog弹窗，提供方法自定义。
>  5.提供几种比较常用的Utils工具类

####   v1.2.4
>  1.增加DataManager用来统一管理数据请求，包括Retrofit的请求、SharePreference以及Realm的数据请求。
> 2.Retrofit的请求的整合。
> 3.PullToRefreshRecyclerView的空布局bug修改。




###  框架的引入
>  **implementation 'com.youngman:collectionlibrary:1.3.8'**

> Error:Could not find com.android.support:appcompat-v7:27.x.x.
因为library的Support Repository是27.x.x,可能跟项目有所冲突，如果sdk已经装了27还是会出现同样的错误。
解决办法：在项目根build.gradle中加入  maven { url "https://maven.google.com" }


###   一、框架整体模块

![效果图](https://upload-images.jianshu.io/upload_images/4361802-5a07258d6dba41ba.gif?imageMogr2/auto-orient/strip)




###   二、PullToRefreshRecyclerView的使用

| 属性 | 作用 |
| :-----| :---- |
|addHeaderView | 增加头部布局， 暂时只能添加一个头布局|
|setEmptyView | 设置自定义的加载布局和空布局|
|setRefreshView | 自定义刷新View|
|setDefaultLoadingMoreNoDataMessage | 设置默认没有数据的内容|
|setLoadMoreView | 自定义加载更多View|
|setNoMoreDate | 显示没有更多数据|
|setAutoRefresh | 自动刷新|
|refreshComplete | 刷新数据完成|
|loadMoreComplete | 加载更多数据完成|
|setPullRefreshEnabled | 是否允许刷新|
|setLoadMoreEnabled | 是否允许加载更多|
|setRefreshTimeVisible |显示加载更新时间|
|isLoading | 是否正在loading数据|
|isRefreshing | 正在refreshing数据|
|setRefreshAndLoadMoreListener |刷新和加载更多回调|
|destroy |内存回收|

####  1.框架默认下拉刷新、上拉加载更多样式
![效果图](https://upload-images.jianshu.io/upload_images/4361802-fc42ed065848f334.gif?imageMogr2/auto-orient/strip)

##### （1）布局文件

     <com.youngmanster.collection_kotlin.recyclerview.PullToRefreshRecyclerView
		android:id="@+id/recycler_rv"
		android:layout_width="match_parent"
		android:layout_height="match_parent" />

#####  （2）代码设置

     recycler_rv.setPullRefreshEnabled(true);
     recycler_rv.setLoadMoreEnabled(true);


####   2、自定义下拉刷新、上拉加载更多样式

![效果图](https://upload-images.jianshu.io/upload_images/4361802-b41235448956a34c.gif?imageMogr2/auto-orient/strip)



 #####  刷新几种状态：
| 属性 | 作用 |
| :-----| :---- |
| STATE_PULL_DOWN | 拉的状态（还没到下拉到固定的高度时）|
| STATE_RELEASE_REFRESH | 下拉到固定高度提示释放刷新的状态|
| STATE_REFRESHING | 正在刷新状态|
| STATE_DONE | 刷新完成|

 #####  加载更多几种状态：

| 属性 | 作用 |
| :-----| :---- |
| STATE_LOADING | 正在加载|
| STATE_COMPLETE |加 载完成|
| STATE_NODATA | 没有数据|

##### （1）代码设置

    recycler_rv.setPullRefreshEnabled(true);
    recycler_rv.setLoadMoreEnabled(true);
    recycler_rv.setRefreshAndLoadMoreListener(this);
    recycler_rv.setRefreshView(new DefinitionAnimationRefreshHeaderView(getActivity()));
    recycler_rv.setLoadMoreView(new DefinitionAnimationLoadMoreView(getActivity()));

##### 自定义刷新的步骤：

######  ①自定义View继承BasePullToRefreshView，重写initView()、setRefreshTimeVisible(boolean show)、destroy()方法:

1.在initView()做自定义布局、相关动画的初始化，最后在initView()方法的最后面添加以下代码即可。

        mContainer = LayoutInflater.from(context).inflate(R.layout.collection_library_layout_default_arrow_refresh, null);
       //把刷新头部的高度初始化为0
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 0);
		this.setLayoutParams(lp);
		this.setPadding(0, 0, 0, 0);
		addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
		setGravity(Gravity.BOTTOM);
        //测量高度
		measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mMeasuredHeight = getMeasuredHeight();



2.setRefreshTimeVisible(boolean show)是用来设置是否显示刷新时间控件，在默认刷新样式中通过mRecyclerView.setRefreshTimeVisible(false)即可隐藏刷新时间，如果在自定义的布局中没有这项这个方法就可以忽略。

3.destroy()是用来关掉改页面时把刷新View的一些动画等释放，防止内存泄漏。


######  ②实现BasePullToRefreshView.OnStateChangeListener监听(重点，主要是进行状态切换后的相关操作逻辑）

1.在构造函数中设置 onStateChangeListener=this
2.onStateChange的模板样式

    @Override
    public void onStateChange(int state) {
        //下拉时状态相同不做继续保持原有的状态
        if (state == mState) return ;
        //根据状态进行动画显示
        switch (state){
            case STATE_PULL_DOWN:
                clearAnim();
                startAnim();
                break;
            case STATE_RELEASE_REFRESH:
                break;
            case STATE_REFRESHING:
                clearAnim();
                startAnim();
                scrollTo(mMeasuredHeight);
                break;
            case STATE_DONE:
                break;
        }
        mState = state;
    }


#####  自定义加载更多的步骤(包括没有更多数据显示的操作)：
######  ①自定义View继承BaseLoadMoreView，重写initView()、setState()、destroy()方法:

1.在initView()做自定义布局、相关动画的初始化，最后在initView()方法的最后面添加以下代码即可

       mContainer = LayoutInflater.from(context)
            .inflate(R.layout.layout_definition_animation_loading_more, null)
        addView(mContainer)
        gravity = Gravity.CENTER

   2.destroy()是用来关掉改页面时把刷新View的一些动画等释放，防止内存泄漏。
3.在setState()进行状态切换后的相关操作逻辑，模板样式


    @Override
	public void setState(int state) {

		if(isDestroy){
			return;
		}
		switch (state){
			case STATE_LOADING:
				loadMore_Ll.setVisibility(VISIBLE);
				noDataTv.setVisibility(INVISIBLE);
				animationDrawable= (AnimationDrawable) loadingIv.getDrawable();
				animationDrawable.start();
				this.setVisibility(VISIBLE);
				break;
			case STATE_COMPLETE:
				if(animationDrawable!=null){
					animationDrawable.stop();
				}
				this.setVisibility(GONE);
				break;
			case STATE_NODATA:
				loadMore_Ll.setVisibility(INVISIBLE);
				noDataTv.setVisibility(VISIBLE);
				animationDrawable= (AnimationDrawable) loadingIv.getDrawable();
				animationDrawable.start();
				this.setVisibility(VISIBLE);
				break;
		}

		mState = state;

	}

4.注意：在自定义加载更多样式时，如果需要有没有更多加载更多数据提示同样需要在布局中写好，然后在onSatae中根据状态对加载和没有跟多显示提示进行显示隐藏操作。

#### 3、上拉加载更多配合SwipeRefreshLayout使用

![效果图](https://upload-images.jianshu.io/upload_images/4361802-61e0148999343a50.gif?imageMogr2/auto-orient/strip)


##### （1）布局文件
     <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
        android:id="@+id/swipeRefreshLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

         <com.youngmanster.collection_kotlin.recyclerview.PullToRefreshRecyclerView
            android:id="@+id/recycler_rv"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>

    </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>


##### （2）代码设置

    recycler_rv.setLoadMoreEnabled(true);
    recycler_rv.setRefreshAndLoadMoreListener(this);
    recycler_rv.setLoadMoreView(DefinitionAnimationLoadMoreView(activity));
    swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    swipeRefreshLayout.setOnRefreshListener(this);

#####   (3)注意的问题
> 由于PullToRefreshRecyclerView的下拉刷新和下拉加载更多完成时会自动刷新Adapter,而SwipeRefreshLayout刷新完成时需要手动进行notifyDataSetChanged刷新适配器。

###   4、RecyclerView添加头部、空布局
![效果图](https://upload-images.jianshu.io/upload_images/4361802-d44f5743f3b59c19.gif?imageMogr2/auto-orient/strip)

![效果图](https://upload-images.jianshu.io/upload_images/4361802-e1e385df413bedf9.gif?imageMogr2/auto-orient/strip)



    View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty,null);
		mRecyclerView.setEmptyView(emptyView);
		emptyView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for(int i=0;i<10;i++){
					mDatas.add("item"+i);
				}
				definitionRefreshAdapter.notifyDataSetChanged();
			}
		});

####   5、上拉加载更多实现NoMoreData、自动刷新

![效果图](https://upload-images.jianshu.io/upload_images/4361802-86255e77683f8322.gif?imageMogr2/auto-orient/strip)




##### （1）上拉加载更多数据的布局设置在上面的自定义LoadingMoreView中有介绍，如果要显示没有更多数据提示只需要在LoadMore返回数据之后设置：

    mRecyclerView.setNoMoreDate(true)


##### （2）自动刷新需要列表已经填充了数据之后再做自动刷新操作才会生效：

    mRecyclerView.setAutoRefresh()


####   6.Collection库的刷新加载文本已经适配了中文、英文、繁体，如果对默认刷新加载显示文本进行修改，通过LoadingTextConfig设置，可在Application全局设置


    LoadingTextConfig textConfig=LoadingTextConfig.getInstance(getApplicationContext());
    textConfig
				.setCollectionLoadingMore("加载更多")
				.setCollectionLastRefreshTimeTip("更新时间：")
				.setCollectionNoMoreData("没有更多数据")
				.setCollectionPullReleaseText("释放刷新")
				.setCollectionRefreshing("正在刷新")
				.setCollectionPullDownRefreshText("下拉刷新")
				.setCollectionRefreshDone("加载完成");
    PullToRefreshRecyclerViewUtils.loadingTextConfig=textConfig;



####   7、PullToRefreshRecyclerView的其他使用以及注意问题

1.下面是下拉刷新上拉加载更多的一些操作模板

    public void refreshUI() {
		if (defaultRefreshAdapter == null) {
			defaultRefreshAdapter = new DefaultRecyclerAdapter(getActivity(), mDatas, mRecyclerView);
			mRecyclerView.setAdapter(defaultRefreshAdapter);
		} else {
			if (mRecyclerView != null) {
				if (mRecyclerView.isLoading()) {
					mRecyclerView.loadMoreComplete();
				} else if (mRecyclerView.isRefreshing()) {
					mRecyclerView.refreshComplete();
				}
			}
		}
	}

2.注意问题

######  ①在设置RecyclerView是要设LayoutManager
######  ②如果使用PullToRefreshRecyclerView在Activty/Fragment中的onDestroy（）调用mRecyclerView.destroy()防止内存泄漏。
    @Override
	public void onDestroy() {
		super.onDestroy();
		mRecyclerView.destroy()
	}


######  ③设置refreshRv.setLoadMoreEnabled(true)，当填充的数据的列表size为0的同时还通过RecyclerView设置分割线底部就会出现一个空白的item，这个item就是加载更多显示的Item。
######   解决办法：不通过RecyclerView设置分割线，直接在布局自定义分割线。


###  三、BaseRecyclerViewAdapter的使用

####  1.BaseRecyclerViewAdapter的比原始Adapter的代码量减小

在BaseRecyclerViewAdapter中的BaseViewHolder进行布局转化，同时定义了一些比较基本的View操作，使用简单。

##### （1）使用代码：

    public class DefinitionRecyclerAdapter extends BaseRecyclerViewAdapter<String> {

      public DefinitionRecyclerAdapter(Context mContext, List<String> mDatas, PullToRefreshRecyclerView pullToRefreshRecyclerView) {
        super(mContext, R.layout.item_pull_refresh, mDatas, pullToRefreshRecyclerView);
      }

      @Override
      protected void convert(BaseViewHolder baseViewHolder, String s) {
          baseViewHolder.setText(R.id.title,s);
      }
    }

######  ①使用者需要在继承BaseRecyclerViewAdapter时传入一个数据实体类型，具体的操作在convert()方法中操作。
######  ②BaseViewHolder提供了一些常用View的基本操作，通过baseViewHolder.getView()可得到布局中的控件。


##### （2）BaseRecyclerViewAdapter提供了两个构造函数

	public BaseRecyclerViewAdapter(Context mContext, int mLayoutResId, List<T> mDatas, PullToRefreshRecyclerView pullToRefreshRecyclerView) {
		this.mContext = mContext;
		this.mLayoutResId = mLayoutResId;
		this.mDatas = mDatas;
		this.mRecyclerView=pullToRefreshRecyclerView;
	}

	public BaseRecyclerViewAdapter(Context mContext, int mLayoutResId, List<T> mDatas) {
		this.mContext = mContext;
		this.mLayoutResId = mLayoutResId;
		this.mDatas = mDatas;
	}

######  主要是对PullToRefreshRecyclerView和RecyclerView的适配，使用时适配器根据需要使用对应的构造函数。

####   2.添加Item的点击和长按事件

![效果图](https://upload-images.jianshu.io/upload_images/4361802-f984608c1191a05f.gif?imageMogr2/auto-orient/strip)

##### （1） Item点击事件实现

    itemClickAdapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.onItemLongClickListener() {
		@Override
		public boolean onItemLongClick(View view, int position) {
			showToast("进行长按操作");
			return true;
		}
	});


####   3.多布局的使用

![效果图](https://upload-images.jianshu.io/upload_images/4361802-30e8e453a9f11439.gif?imageMogr2/auto-orient/strip)




##### BaseRecyclerViewAdapter的多布局实现需要注意的四步：

######  ①自定义Adapter需要继承BaseRecyclerViewMultiItemAdapter。
######  ② 数据实体类需要继承BaseMultiItemEntity，在getItemViewType()返回布局类型。
######  ③ 在自定义Adapter中的构造函数中通过addItemType()传入不同类型对应的布局。
######   ④在自定义Adapter中的convert进行类型判断，做相对应的操作。


	public class MultipleAdapter extends BaseRecyclerViewMultiItemAdapter<MultiItem> {

		private int mHeight;

		public MultipleAdapter(Context mContext, List<MultiItem> mDatas) {
			super(mContext, mDatas);
			mHeight = DisplayUtil.dip2px(mContext, 100);
			addItemType(MultiItem.TYPE_TEXT, R.layout.item_main);
			addItemType(MultiItem.TYPE_IMG, R.layout.item_img);
			addItemType(MultiItem.TYPE_TEXT_IMG, R.layout.item_click);
		}

		@Override
		protected void convert(BaseViewHolder baseViewHolder, MultiItem multiItem) {
			switch (baseViewHolder.getItemViewType()) {
				case MultiItem.TYPE_TEXT:
					baseViewHolder.getView(R.id.card_view).getLayoutParams().height = mHeight;
					baseViewHolder.setText(R.id.title, multiItem.getTitle());
					break;
				case MultiItem.TYPE_IMG:
					baseViewHolder.setImageResource(R.id.ivImg, multiItem.getRes());
					break;
				case MultiItem.TYPE_TEXT_IMG:
					baseViewHolder.setImageResource(R.id.ivImg, multiItem.getRes());
					baseViewHolder.setText(R.id.titleTv, multiItem.getTitle());
					break;

			}

		}

####   4.添加拖拽、滑动删除
![效果图](https://upload-images.jianshu.io/upload_images/4361802-e1c914c19f375bfc.gif?imageMogr2/auto-orient/strip)


###### 局限：只针对RecyclerView，对本框架封装的PullToRefreshRecyclerView会出现混乱。

######   ①BaseRecyclerViewAdapter和BaseRecyclerViewMultiItemAdapter都已经封装支持拖拽、滑动，适配器只需要根据需求继承其中一个即可。
######   ②框架提供了一个BaseRecycleItemTouchHelper，对于普通的左右滑动删除、拖拽已经实现，如果想自定义可以继承BaseRecycleItemTouchHelper类，再重写相对应的方法进行实现。
######  ④在Activity/Fragment中需要实现以下代码：

	ItemTouchHelper.Callback callback=new BaseRecycleItemTouchHelper(dragAndDeleteAdapter);
	ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
	itemTouchHelper.attachToRecyclerView(mRecyclerView);

###### ⑤BaseRecyclerViewAdapter.OnDragAndDeleteListener进行操作动作完成之后的回调。


	@Override
	public void onMoveComplete() {
		ToastUtils.showToast(this, "移动操作完成");
	}

	@Override
	public void onDeleteComplete() {
		ToastUtils.showToast(this, "删除操作完成");
	}


###  四、MVP+RxJava+Retrofit的封装使用

![效果图](https://upload-images.jianshu.io/upload_images/4361802-f25084b0cbbf3835.gif?imageMogr2/auto-orient/strip)



####  1.在使用Retrofit请求网络之前需要进行配置，在框架中提供了了Config配置类

| 属性 | 作用 |
| :-----| :---- |
|DEBUG | 是否为BuildConfig.DEBUG,日志输出需要|
|CONTEXT | 设置Context,必填项|
|URL_DOMAIN | 网络请求的域名，需要以“/”结尾|
|URL_CACHE | 网络缓存地址，需要设置缓存才可以成功|
|MAX_CACHE_SECONDS | 设置OkHttp的缓存机制的最大缓存时间,默认为一天|
|MAX_MEMORY_SIZE | 缓存最大的内存,默认为10M|
|MClASS | 设置网络请求json通用解析类|
|EXPOSEPARAM | Json数据某些字段在没有数据是会不返回来，可通过这个属性设置过滤|
|USER_CONFIG | SharePreference保存的名称|
|CONNECT_TIMEOUT_SECONDS | 请求接口超时设定|
|READ_TIMEOUT_SECONDS | 请求接口超时设定|
|HEADERS | 设置Http全局请求头|
|SQLITE_DB_NAME | 数据库名称|
|SQLITE_DB_VERSION | 数据库版本名|


######  在项目中需要根据项目需要进行配置，在Application中设置


    private void config(){
		//基本配置
		Config.DEBUG= BuildConfig.DEBUG;
		Config.CONTEXT=this;
		//Retrofit配置
		Config.URL_CACHE=AppConfig.URL_CACHE;
		Config.MClASS= Result.class;
		Config.URL_DOMAIN="https://api.apiopen.top/";
		//SharePreference配置
		Config.USER_CONFIG="Collection_User";
		Config.SQLITE_DB_VERSION=0;
	}


######  根据项目需要定义一个通用的数据实体类，这是本例通用实体类，这个类需要设置到Applicatin中

	public class Result<T> implements Serializable {

		private int code;
		private String msg;
		private T newslist;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public T getNewslist() {
			return newslist;
		}

		public void setNewslist(T newslist) {
			this.newslist = newslist;
		}
	}


######  注意：由于每个项目返回来的json数据格式有所不同，如果Result中代表的字段例如newslist没有内容返回来的时候这个字段需要后台控制不返回，如果不做处理会报解析错误,可以通过设置Config.EXPOSEPARAM属性过滤字段。

####   2.RxJava+Retrofit+OkHttp

（1）RequestBuilder的设置（网络请求的配置）

| 属性 | 作用 |
| :-----| :---- |
| ReqType |数据处理的方式，默认DEFAULT_CACHE_LIST,使用到OkHttp缓存的需要需要设置Config.URL_CACHE  |
| NO_CACHE_MODEL | 不设置缓存，返回model|
| NO_CACHE_LIST | 不设置缓存,返回list|
| DEFAULT_CACHE_MODEL | 使用Okttp默认缓存，返回model|
| DEFAULT_CACHE_LIST | 使用Okttp默认缓存，返回list|
| DISK_CACHE_LIST_LIMIT_TIME | 限时使用自定义磁盘缓存，返回List|
| DISK_CACHE_MODEL_LIMIT_TIME | 限时使用自定义磁盘缓存，返回model|
| DISK_CACHE_NO_NETWORK_LIST | 自定义磁盘缓存，没有网络返回磁盘缓存，返回List|
| DISK_CACHE_NO_NETWORK_MODEL | /自定义磁盘缓存，没有网络返回磁盘缓存，返回Model|
|DOWNLOAD_FILE_MODEL|文件下载模式，返回Model|
| HttpType |网络请求方式,默认DEFAULT_GET |
| DEFAULT_GET |GET请求|
| DEFAULT_POST |POST请求 |
| FIELDMAP_POST |如果请求URL出现中文乱码，可选择这个 |
| JSON_PARAM_POST |json格式请求参数 |
| ONE_MULTIPART_POST |上传一张图片 |
| MULTIPLE_MULTIPART_POST |上传多张图片 |
|DOWNLOAD_FILE_GET|下载文件|
| ReqMode |请求模式，默认ASYNCHRONOUS|
| ASYNCHRONOUS |异步请求|
| SYNCHRONIZATION |同步请求|
| 其它参数 | |
| setTransformClass |设置请求转化Class |
| setUrl |设置请求url,如果不设置完全连接则会使用Config.URL_DOMIN进行拼接 |
| setFilePathAndFileName |设置自定义缓存时的路径和文件名 |
| setLimtHours |设置自定义缓存的有效时间 |
| setHeader |设置请求头 |
 | setHeaders |设置请求头集合 |
| setHttpTypeAndReqType |设置请求数据类型和请求方式 |
| setImagePath |设置上传图片路径 |
| setImagePaths |设置多张图片路径 |
| isUserCommonClass |设置是否使用公用类转化 |
| setReqMode |设置同步异步 |

（2）使用模块


    RequestBuilder<Result<List<WeChatNews>>> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<Result<List<WeChatNews>>>(mView) {
			@Override
			public void onNext(Result<List<WeChatNews>> result) {
				mView.refreshUI(result.getResult());
			}
		});

		resultRequestBuilder
				.setUrl(ApiUrl.URL_WETCHAT_FEATURED)
				.setTransformClass(WeChatNews.class)
				.setParam("page",page)
				.setParam("type","video")
				.setParam("count",num);


	rxManager.addObserver(DataManager.DataForHttp.httpRequest(resultRequestBuilder));


##### （3）Retrofit的扩展
######   如果存在DataManager提供的方法满足不了的请求可以通过RetrofitManager提供的getNoCacheApiService（）和getApiService（）获得不缓存和缓存的Retrofit，然后通过RxSubscriber进行回调。

    Observable<WeChatAccessToken> observable = RetrofitManager.getNoCacheApiService(ApiService.class)
				.getWeChatStr(ApiUrl.URL_WECHAT_HOST + ApiUrl.ACCESS_TOKEN, reqParams);

		DisposableObserver<WeChatAccessToken> observer = observable
				.compose(RxSchedulers.<WeChatAccessToken>io_main())
				.subscribeWith(new RxSubscriber<WeChatAccessToken>() {
					@Override
					public void _onNext(WeChatAccessToken weChatAccessToken) {
						getUserInfo(weChatAccessToken);
					}

					@Override
					public void _onError(NetWorkCodeException.ResponseThrowable responseThrowable) {
						showToast(R.string.wx_LoginResultEmpty);
						hideLoadingDialog();
						finish();
					}

					@Override
					public void _onComplete() {

					}
				});

		rxManager.addObserver(observer);


###### 定义一个ApiService类

	public interface ApiService {
		/**
	 	* 微信精选
	 	* @param url
	 	* @param map
	 	* @return
	 	*/
		@GET
		Observable<Result<List<WeChatNews>>> getWeChatFeaturedNews(@Url String url, @QueryMap Map<String,Object> map);
	}



#####   注意：

######   （1）RxObservableListener有三个回调方法
    void onNext(T result);
    void onComplete();
    void onError(NetWorkCodeException.ResponseThrowable e);
######   只会重写onNext方法，其它两个方法可以自行选择重写。
######   （2）RxObservableListener提供两个构造函数
    protected RxObservableListener(BaseView view){
	    this.mView = view;
    }

    protected RxObservableListener(BaseView view, String errorMsg){
	     this.mView = view;
         this.mErrorMsg = errorMsg;
    }


######  这两个构造函数主要主要是为了统一处理onError的，如果要自定义错误提醒，则可以选择第二个构造函数。

######   （3）通过DataManager的网络请求方式会返回来一个DisposableObserver，需要把它通过rxManager.addObserver()添加进CompositeDisposable才能正常执行,方便对网络请求的销毁管理。

######   （4）如果项目没有统一的解析been类，那么Config的公用类就不用设置了，在Retrofit请求的时候直接setTransformClass指定一个解析类就可以了

######  （5）如果项目想两种方式共存，那么在请求的时候需要通过setUserCommonClass（false）设置才能不使用统一解析类进行解析


######    (6)请求的域名已经在Application设置好了，setUrl不需要填完整的url
######    ②要区分清楚接口返回的数据时List还是Model，从而选择对应的ReqType
######    (7)setRequestParam可以设置参数集合，setParam可以单个设置
######    (8)使用DISK_CACHE_LIST_LIMIT_TIME/DISK_CACHE_MODEL_LIMIT_TIME这两个显示限时缓存时需要通过setFilePathAndFileName（）设置保存路径setLimtHours（）设置缓存时间（单位为：小时）
######   (9)如果要上传单张图片需要用到HttpType.ONE_MULTIPART_POST的请求方式，同时通过RequestBuilder设置MultipartBody.Part




####   3.MVP

1.写一个Contract类对Presenter和View进行统一管理(View需要实现BaseView,Presenter需要实现BasePresenter<T>

    public interface WeChatFeaturedContract {

	  interface View extends BaseView {
		void refreshUI(List<WeChatNews> newsList);
	  }

	  abstract class Presenter extends BasePresenter<View> {
		public abstract void requestFeaturedNews(int page, int num);
	  }

    }

2.写一个具体的Presenter类实现WeChatChinaNewsContract.Presenter,在里面做具体的逻辑处理，处理完成再通过mView进行View的处理

3.Activity/Fragment实现IBaseActivity<T extends BasePresenter>/IBaseFragment<T extends BasePresenter>以及定义好的WeChatChinaNewsContract.View

4.缺陷：View在使用时需要转化成在具体的子类才能调用相关方法。

5.具体使用可以参照demo


###  五、DataManager的使用(Http、Sharepreference、SQLite)

![效果图](https://upload-images.jianshu.io/upload_images/4361802-0be3986f8fab6242.gif?imageMogr2/auto-orient/strip)



（1）DataManager基本属性

| 属性 | 作用 |
| :-----| :---- |
| DataForSqlite |数据库模块  |
| insert | 插入bean数据|
| insertList | 插入List数据|
| insertListBySync | 异步插入List数据|
| queryByFirstByWhere | 根据条件查询|
| queryAll | 查询某个bean类的全部数据|
| queryAllBySync | 异步查询某个bean类的全部数据|
| queryByFirst | 查询某个bean类的第一条数据|
| delete |根据条件删除数据|
| deleteAll | 删除某个bean类的所有数据|
| deleteTable | 删除数据表|
| update | 更新某个bean类的|
| queryOfPageByWhere |根据条件分页查询，实体类必须包含PrimaryKey|
| queryOfPage | 分页查询，实体类必须包含PrimaryKey|
| updateTable | 更新数据表，用于增加字段|
| execQuerySQL | Sql语句查询|
| DataForHttp |Http模块  |
| httpRequest | 网络请求，传入RequestBuilder|
| DataForSharePreferences | SharePreference模块|
| saveObject | 保存基本类型数据|
| getObject | 获取基本类型数据|


（2）DataForSqlite
1.插入一条数据

    user=new User();
    user.setId(0);
    user.setName("Young1");
    user.setAge(14);
    user.setAddress("山东省");
    user.setStr("eeeee");
    DataManager.DataForSqlite.insert(user);
   showToast("保存成功");

2.查询数据

    user=DataManager.DataForSqlite.queryByFirst(User.class);
    String showContent="用户Id:"+user.getId()+"\n"+"用户姓名："+user.getName()+"\n"+"用户年龄："+user.getAge()+"\n"+"用户地址："+user.getAddress();
    showToast(showContent);

3.批量插入数据（可同步可异步）


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

4.bean类的定义

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

其中可以通过Column进行注解定义（isPrimaryKey、isNull、isUnique）

5.数据表格变化（只支持增加字段）

* 修改数据库版本号Config.SQLITE_DB_VERSION，往上递增
* 在Application中对版本号进行监听，并对数据表进行更新

      SQLiteVersionMigrate sqLiteVersionMigrate=new SQLiteVersionMigrate();
		sqLiteVersionMigrate.setMigrateListener(new SQLiteVersionMigrate.MigrateListener() {
			@Override
			public void onMigrate(int oldVersion, int newVersion) {
				for (int i=oldVersion;i<=newVersion;i++){
					if(i==2){

					}

				}
			}
		});




（3）DataForHttp


    RequestBuilder<Result<List<WeChatNews>>> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<Result<List<WeChatNews>>>(mView) {
			@Override
			public void onNext(Result<List<WeChatNews>> result) {
				mView.refreshUI(result.getResult());
			}
		});

		resultRequestBuilder
				.setUrl(ApiUrl.URL_WETCHAT_FEATURED)
				.setTransformClass(WeChatNews.class)
				.setParam("page",page)
				.setParam("type","video")
				.setParam("count",num);


	rxManager.addObserver(DataManager.DataForHttp.httpRequest(resultRequestBuilder));



（4）DataForSharePreferences

1.插入基本数据

    DataManager.DataForSharePreferences.saveObject("user","这是一条测试的内容")
    ToastUtils.showToast(activity,"保存成功")

2.查询基本类型数据

    val con=DataManager.DataForSharePreferences.getObject("user","")
    ToastUtils.showToast(activity,con)


###  六.Base的使用

#####  1.为了方便Activity/Fragment设置顶部菜单栏，继承IBaseActivity/IBaseFragment即可显示一个简单的顶部菜单，IBaseFragment的顶部菜单默认隐藏，下面以IBaseActivity的顶部菜单作为例子

| 属性 | 作用 |
| :-----| :---- |
| isShowSystemActionBar | 重写该方法设置实现显示系统ActionBar |
| isShowCustomActionBar | 重写该方法设置显示自定义Bar|
| setCustomActionBar | 重写该方法设置自定义Bar|


* 如果使用默认自定义Bar可通过DefaultDefineActionBarConfig进行相关设置

| 属性 | 作用 |
| :-----| :---- |
| hideBackBtn | 隐藏返回按钮|
| setBarBackground| 设置Bar的背景颜色|
| setBarHeight | 设置Bar的高度|
| setTitleColor | 设置标题颜色|
| setTitle | 设置标题|
| setBackClick | 设置返回按钮监听|

- 代码使用


    defineActionBarConfig
           .setTitleSize(20f)
           .setBarHeight(DisplayUtils.dip2px(this,60f))
           .setBarBackground(this,R.color.driver_font)
           .setTitle(getString(R.string.tab_Indicator_title))



#####  2.使用Fragment实现交互

![](https://upload-images.jianshu.io/upload_images/4361802-d7b7fd711990e145.gif?imageMogr2/auto-orient/strip)


| 属性 | 作用 |
| :-----| :---- |
| fragmentLayoutId |设置显示Fragment的根布局id,在Activity中设置|
| startFragmentForResult(...) |和回调结果跳转|
| onFragmentResult(....) |Fragment的结果回调|
| setResult(...) |onFragmentResult回调的结果设置 |
| startFragment(...) |普通跳转，具体使用查看IBaseActivity|
|isRootFragment（）|判断是否是根Fragment|

#####  3.StateView（数据加载页面）

![效果图](https://upload-images.jianshu.io/upload_images/4361802-aac6a842c41a5948.gif?imageMogr2/auto-orient/strip)


| 属性 | 作用 |
| :-----| :---- |
| STATE_NO_DATA | 不显示加载框状态码 |
| STATE_LOADING | 加载数据显示状态码|
| STATE_EMPTY | 没有数据显示状态码|
| STATE_DISCONNECT | 没有网络状态码|
| setOnDisConnectViewListener |点击没有网络图标回调|
| setOnEmptyViewListener | 点击没有没有数据图标回调|
| showViewByState | 设置显示状态|
| getmEmptyView |获取无数据状态View|
|布局可设置参数 ||
|loadingViewAnimation |设置加载的drawable动画|
|loadingText |加载时的文本|
|emptyImage |空布局显示的图片|
|emptyText |空布局文本|
|emptyViewRes |设置自定义空布局|
|disConnectImage |设置断网显示的图片|
|disConnectText |设置断网显示的文本|
|tipTextSize |文本字体大小|
|tipTextColor |文本字体颜色|


######  （1）定义一个通用布局


    <com.youngmanster.collection_kotlin.base.stateview.StateView xmlns:android="http://schemas.android.com/apk/res/android"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:layout_centerInParent="true"
	    android:id="@+id/state_view">

    </com.youngmanster.collection_kotlin.base.stateview.StateView >

######  (2)添加到Ui页面的layout中

	<include layout="@layout/layout_state"/>

###### 注意：上面的语句添加的layout最外层最好是LinearLayout以及设置为android:orientation="vertical"


######  (3)通过以下语句进行状态切换

	stateView.showViewByState(StateView.STATE_LOADING);
	stateView.showViewByState(StateView.STATE_EMPTY);
	stateView.showViewByState(StateView.STATE_NO_DATA);
	stateView.showViewByState(StateView.STATE_DISCONNECT);

####   3.三步实现Permission(权限)设置

![效果图](https://upload-images.jianshu.io/upload_images/4361802-c1768156ccaf5e37.gif?imageMogr2/auto-orient/strip)


    // 项目的必须权限，没有这些权限会影响项目的正常运行
	private static final String[] PERMISSIONS = new String[]{
			Manifest.permission.READ_SMS,
			Manifest.permission.RECEIVE_WAP_PUSH,
			Manifest.permission.READ_CONTACTS
	};

##### （2）权限通过PermissionManager管理

    permissionManager=PermissionManager.with(this).
				setNecessaryPermissions(PERMISSIONS);

    permissionManager.requestPermissions();

#####  （3）页面重写onRequestPermissionsResult

    //重写
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == PermissionManager.PERMISSION_REQUEST_CODE) {//PERMISSION_REQUEST_CODE为请求权限的请求值
			//有必须权限选择了禁止
			if (permissionManager.getShouldShowRequestPermissionsCode() == PermissionManager.EXIST_NECESSARY_PERMISSIONS_PROHIBTED) {
				ToastUtils.showToast(PermissionActivity.this,"可以在这里设置重新跳出权限请求提示框");
			} //有必须权限选择了禁止不提醒
			else if (permissionManager.getShouldShowRequestPermissionsCode() == PermissionManager.EXIST_NECESSARY_PERMISSIONS_PROHIBTED_NOT_REMIND) {
				ToastUtils.showToast(PermissionActivity.this,"可以在这里弹出提示框提示去应用设置页开启权限");
				permissionManager.startAppSettings();
			}
		}
	}


######   注意：如果有需求先判断是否所有权限都已经允许之后再进入主页面可以通过permissionManager.isLackPermission()进行判断，如果返回true则进行权限请求，如果返回false则进入主页面。


- 多个权限请求如果其中某一个被禁止提醒，会先把没有禁止提醒的权限处理完之后再进行处理。
- 如果是必要权限被禁止而没有选择禁止提醒退出之后下次会重新请求权限。
- 如果必要权限被禁止和选择了禁止提醒重新进入页面在onRequestPermissionsResult会重新回调方法。
- 使用者可以根据onRequestPermissionsResult（）方法中返回来的标志PermissionManager.EXIST_NECESSARY_PERMISSIONS_PROHIBTED和PermissionManager.EXIST_NECESSARY_PERMISSIONS_PROHIBTED_NOT_REMIND做出对应的显示和操作（例如弹框提示跳转到设置页面或者toat提示）。

####   4.提供几种比较常用的Dialog弹框

![效果图](https://upload-images.jianshu.io/upload_images/4361802-c5a27e85bf8a4209.gif?imageMogr2/auto-orient/strip)


#####   （1）提供的常用的CommonDialog

| 属性 | 作用 |
| :-----| :---- |
| DIALOG_TEXT_TWO_BUTTON_DEFAULT | 默认弹出按钮提示 |
| DIALOG_TEXT_TWO_BUTTON_CUSTOMIZE | 自定义弹出按钮提示 |
| DIALOG_LOADING_PROGRASSBAR | 默认加载弹框|
| DIALOG_CHOICE_ITEM | 没有数据显示状态码|

根据不同的构造函数设置不同的参数

#####   （2）自定义Dialog样式

- BaseDialogFragment

| 属性 | 作用 |
| :-----| :---- |
|  setContentView | 设置弹框布局样式 |
| onViewCreated | 初始化完成后的回调，可在此做一些初始化 |
| show(...) | 显示弹框 |
| dismiss | 弹框销毁|
| setAllCancelable| 点击返回键和外部不可取消|
| setOnlyBackPressDialogCancel | 点击返回键可以取消|
| setDialogInterval| 设置弹框和屏幕两边的间距|
| setDialogHeight | 设置弹框高度|
| setOnDismissListener | 弹框销毁回调|

- 继承BaseDialogFragment，通过setContentView(R.layout.dialog_list)设置弹窗布局。
- 在提供的onViewCreated方法中进行相应的逻辑设置


####   5.自定义PopupWindow弹框

![效果图](https://upload-images.jianshu.io/upload_images/4361802-9487fc0856a988fa.gif?imageMogr2/auto-orient/strip)

- BasePopupWindow

| 属性 | 作用 |
| :-----| :---- |
| BasePopupWindow(Context context) |调用该构造函数默认弹出框铺满全屏 |
| BasePopupWindow(Context context, int w, int h) | 自定义弹出框高宽 |
| showPopup |在屏幕中央显示弹框|
| showPopupAsDropDown | 在指定控件底部显示弹框|
| showPopup |在屏幕中央显示弹框|
| showPopupAsDropDown | 在指定控件底部显示弹框|
| setShowMaskView | 设置是否显示遮层|
| dismiss|销毁弹出框|
| getPopupLayoutRes | 自定义弹出框的布局文件|
| getPopupAnimationStyleRes | 自定义弹出框的动画文件，不设置动画返回0|

- 继承BasePopupWindow。
- 通过getPopupLayoutRes(R.layout.xxx)设置弹窗布局。
- 通过getPopupAnimationStyleRes(R.style.xxx)设置弹窗动画，不需要动画可以设置为0。
- 如果需要显示遮层，在构造函数通过setShowMaskView(true)设置。

###  七、CustomView的使用

![效果图](https://upload-images.jianshu.io/upload_images/4361802-b2efedccefa70103.gif?imageMogr2/auto-orient/strip)


####   1.CommonTabLayout的使用
| 属性 | 作用 |
| :-----| :---- |
| tab_tabIndicatorWidth |设置下滑线的长度|
| tab_tabIndicatorHeight | 设置下滑线的高度 |
| tab_tabIndicatorColor |下滑线颜色|
| tab_indicator_marginLeft | 设置下滑线外边距|
| tab_indicator_marginRight |设置下滑线外边距|
| tab_indicator_marginTop | 设置下滑线外边距|
| tab_indicator_marginBottom | 设置下滑线外边距|
| tab_tabTextColor|没选中字体颜色|
| tab_tabTextSize | 字体大小|
| tab_tabSelectedTextColor | 选中字体颜色|
| tab_padding | 下滑线内边距，block样式时可以通过该属性设置距离|
| tab_tabBackground |Tab 的背景颜色|
| tab_indicator_corner|下滑线的圆角大小|
| tab_indicator_gravity（bottom、top | 设置下滑线显示的位置，只针对line和triangle|
| tab_tabMode（scrollable、fixed） | Tab的显示模式|
| tab_indicator_style（line、triangle、block） | 下滑线的样式|

#####   具体可参照例子使用。

####   2.OutSideFrameTabLayout的使用

![效果图](https://upload-images.jianshu.io/upload_images/4361802-aeae3839f397dece.gif?imageMogr2/auto-orient/strip)


| 属性 | 作用 |
| :-----| :---- |
|  tab_tabIndicatorColor |设置Tab颜色|
| tab_indicator_corner | 圆角大小 |
| tab_indicator_marginLeft |下滑线外边距|
| tab_indicator_marginRight | 下滑线外边距|
| tab_indicator_marginTop |下滑线外边距|
| tab_indicator_marginBottom | 下滑线外边距|
| tab_tabTextColor | 没选中字体颜色|
| tab_tabSelectedTextColor|选中字体颜色|
| tab_tabTextSize | 字体大小|
| tab_tabSelectedTextColor | 选中字体颜色|
| tab_padding | 内边距|
|  tab_bar_color |bar的背景颜色|
| tab_bar_stroke_color|外框的颜色|
| tab_bar_stroke_width | 外框的大小|
| tab_width |bar的长度|

#####   具体可参照例子使用。

####   3.AutoLineLayout的使用


![效果图](https://upload-images.jianshu.io/upload_images/4361802-4d30d8dad1106c41.gif?imageMogr2/auto-orient/strip)


- 在外层布局使用AutoLineLayout


      <com.youngmanster.collection_kotlin.base.customview.wraplayout.AutoLineLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

      </com.youngmanster.collection_kotlin.base.customview.wraplayout.AutoLineLayout>

####   4.TagView的使用

![效果图](https://upload-images.jianshu.io/upload_images/4361802-6aed872b5736a86c.gif?imageMogr2/auto-orient/strip)

######   TagViewConfigBuilder

| 属性 | 作用 |
| :-----| :---- |
| setTitles |设置TagItem内容 |
| setTextSize | 设置TagItem字体大小 |
| setTextColor |设置TagItem字体颜色|
| setTextSelectColor | 设置TagItem选择字体颜色|
| setPaddingLeftAndRight |设置TagIttem左右内边距|
| setPaddingTopAndBottom | 设置TagIttem上下内边距|
| setMarginAndTopBottom | 设置TagItem上下外边距|
| setMarginLeftAndRight|设置TagItem左右外边距|
| setackgroudRes | 设置background Drawable|
| setTagViewAlign| 设置整体TagItem的Align(LEFT,RIGHT,CENTER) |

######  1.布局

     <com.youngmanster.collection_kotlin.base.customview.tagview.TagView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/tagView" />

#####  2.代码设置

    String[] list={"werwrw","4545465","金浩","风和日丽",
                "一只蜜蜂叮在挂历上","阳光","灿烂","1+1","浏览器","玲珑骰子安红豆，入骨相思知不知"};

        TagViewConfigBuilder builder=new TagViewConfigBuilder()
                .setTitles(list);

        tagView.create(builder, new TagView.TagViewPressListener() {
            @Override
            public void onPress(View view, String title, int position) {
                ToastUtils.showToast(TagViewActivity.this,title);
            }
        });

###  八、工具类的使用

#####  1.Density（适配不同手机像素）
 * 在Applicaton的onCreate中设置 Density.setDensity(this, 375f)
*  375f代表设计稿的宽度，以dp为单位，后面需要以f（浮点型）

#####  2.DisplayUtils

![效果图](https://upload-images.jianshu.io/upload_images/4361802-d6c96f712f603d92.gif?imageMogr2/auto-orient/strip)


| 属性 | 作用 |
| :-----| :---- |
| px2dip |px值转换为dip或dp值，保证尺寸大小不变（有精度损失）|
| px2dipByFloat |px值转换为dip或dp值，保证尺寸大小不变（无精度损失 |
| dip2px |dip或dp值转换为px值，保证尺寸大小不变（有精度损失），类似Context.getDimensionPixelSize方法（四舍五入|
| dip2pxByFloat | dip或dp值转换为px值，保证尺寸大小不变（无精度损失），类似Context.getDimension方法|
| px2sp |px值转换为sp值，保证文字大小不变|
| sp2px | sp值转换为px值，保证文字大小不变|
| getScreenWidthPixels | 屏幕宽度|
| getScreenHeightPixels|屏幕高度|
| getDisplayInfo | 获取设备信息|
| setStatusBarBlackFontBgColor | 设置黑色字体状态的背景颜色|
| setStatusBarColor |设置状态栏背景颜色|
| setStatusBarFullTranslucent | 设置状态栏透明|
| getStatusBarHeight |获取状态栏高度|
| getActionBarHeight|获取ActionBar高度|

#####  3.ColorUtils

| 属性 | 作用 |
| :-----| :---- |
| createColorStateList |获取ColorStateList|

#####  4.FileUtils

| 属性 | 作用 |
| :-----| :---- |
| WriterTxtFile |写文件，其中append可设置是否添加在原内容的后边|
| ReadTxtFile |读取文本文件中的内容，strFilePath代表文件详细路径|
| isCacheDataFailure |判断缓存是否失效|
| checkFileExists |检查文件是否存在|
| checkSaveLocationExists |检查是否安装SD卡|
| deleteDirectory |删除目录(包括：目录里的所有文件)|
| deleteFile |删除文件|
| getFileOrFilesSize |获取文件指定文件的指定单位的大小，其中sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB|
| getFileSize|获取指定文件大小|


#####  5.GetPermissionsUtils

| 属性 | 作用 |
| :-----| :---- |
| getAllPermissons |获取应用用到的所有权限|

#####  6.GlideUtils

| 属性 | 作用 |
| :-----| :---- |
| loadImg |加载图片|
| loadImgBlur |Glide实现高斯模糊|
| loadImgBlur |Glide实现高斯模糊，可设置模糊的程度|

#####  7 .ThreadPoolManager：线程池管理类

#####  8.LogUtils：日记工具类

#####  9.NetworkUtils：网络工具类

#####  10.SoftInputUtils：键盘工具类

#####  11.ToastUtils：Toast工具类

#####  12.RxJavaUtil：主/子线程的切换


#### 本文章会根据需要持续更新，建议点赞收藏，便于查看。也欢迎大家提出更多建议。