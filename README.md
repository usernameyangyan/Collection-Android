## Collection

![Travis](https://img.shields.io/badge/release-1.2.8-green.svg)
![Travis](https://img.shields.io/badge/llicense-MIT-green.svg)
![Travis](https://img.shields.io/badge/build-passing-green.svg)


Collection聚合了项目搭建的一些基本模块，节约开发者时间，协助项目的快速搭建,RecyclerView+Adapter+Retrofit+RxJava+MVP+DataManager+基本Base,能够满足一个项目的基本实现。


#### 更多交流请加微信公众号
![](https://upload-images.jianshu.io/upload_images/4361802-88c89753c38ddf70.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


>###### 简书地址：https://www.jianshu.com/p/0a8c27bc8457
>###### 掘金地址：https://juejin.im/post/5ab9987451882555635e5401

## 框架的引入

>implementation 'com.youngman:collectionlibrary:1.2.8'

>Error:Could not find com.android.support:appcompat-v7:27.x.x.
因为library的Support Repository是27.x.x,可能跟项目有所冲突，如果sdk已经装了27还是会出现同样的错误。
解决办法：在项目根build.gradle中加入  maven { url "https://maven.google.com" }


### 更新说明


####  v1.2.8
> 1.更新Realm数据库依赖。  
> 2.更新RxJava、rxandroid、retrofit、converter-gson、adapter-rxjava2依赖。  
> 3.封装好Fragment之间的交互，项目中可以选择使用一个Activity来作为跟容器，其它实现页面统一使用fragment来实现。  
> 4.collectionLibary中的Config配置类增加json字段过滤、网络请求超时设置、网络请求头设置（全局请求头）。    
> 5.增加自动换行布局。  
> 6.Realm增加按数据字段查询和删除接口。  
> 7.网络请求类型HttpType增加json类型请求参数。  
> 8.网络请求增加个别接口请求头设置。  
> 9.增加适配不同手机像素。

####  v1.2.7
> 1.增加自定义控件TabLayout。

####  v1.2.6
> 1.RxJava的依赖更新。
> 2.修正RecyclerView头部布局不能铺满问题。
> 3.PopupWindow的使用。
> 4.DisplayUtils工具类对状态栏的修改。


####  v1.2.5
> 1.修正Retrofit DEFAULT_POST请求方式指向错误。      
> 2.Retrofit 数据解析兼容没有公用been类，可以指定公用been类和不指定公用been类、或者混合使用。   
> 3.Realm增加数据迁移（数据库字段增加或移除）。   
> 4.增加几种通用的Dialog弹窗，提供方法自定义。   
> 5.提供几种比较常用的Utils工具类。   

####  v1.2.4
> 1.增加DataManager用来统一管理数据请求，包括Retrofit的请求、SharePreference以及Realm的数据请求。   
> 2.Retrofit的请求的整合。
> 3.PullToRefreshRecyclerView的空布局bug修改。


## 项目介绍
### 文章目录

**1.PullToRefreshRecyclerView的使用** 

- 框架默认下拉刷新、上拉加载更多样式  
- 自定义下拉刷新、上拉加载更多样式  
- 上拉加载更多结合SwipeRefreshLayout使用  
- RecyclerView添加头部、空布局  
- 上拉加载更多实现NoMoreData、自动刷新   
 
  
**2.BaseRecyclerViewAdapter的使用**

- BaseRecyclerViewAdapter比原始Adapter代码量减少
- 添加Item的点击事件  
- 添加Item的长按事件  
- 多布局的使用  
- 添加拖拽、滑动删除  

**3.MVP+RxJava+Retrofit的封装使用**  

- 框架中的Retrofit+RxJava封装的了解
- 使用框架在项目需要做的操作
- MVP+RxJava+Retrofit+OkHttp的缓存机制 
- MVP+RxJava+Retrofit+自定义磁盘缓存机制  


**4.DataManager的使用**  

- DataManager的Retrofit请求
- DataManager的SharePreference的使用
-  DataManager的Realm的使用

**5.Base的使用**   

- Base封装了MVP和项目的基类   
- UI状态控制StateView的使用  
- 三步实现Permission(权限)设置
- 提供几种比较常用的Dialog弹框
- 提供几种比较常用的PopupWindow弹框
- 使用DisplayUtils修改状态栏
- 提供几种比较常用的Utils工具类


**6.CustomView的使用**
-  CommonTabLayout的使用
-  OutSideFrameTabLayout的使用


###  一、框架整体模块
![](https://upload-images.jianshu.io/upload_images/4361802-fb6a3fc709e0e235.gif?imageMogr2/auto-orient/strip)


### 二 、PullToRefreshRecyclerView的使用

#### 1.框架默认下拉刷新、上拉加载更多样式  
![](https://upload-images.jianshu.io/upload_images/4361802-919e993cd5c7b044.gif?imageMogr2/auto-orient/strip)

##### （1）布局文件    

	 <com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView
        android:id="@+id/recycler_rv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
 

##### （2）代码设置

	mRecyclerView.setPullRefreshEnabled(true);  
	mRecyclerView.setLoadMoreEnabled(true);

####  2、自定义下拉刷新、上拉加载更多样式
![](https://upload-images.jianshu.io/upload_images/4361802-af42500b827165f8.gif?imageMogr2/auto-orient/strip)

##### （1）代码设置

    mRecyclerView.setPullRefreshEnabled(true);
    mRecyclerView.setLoadMoreEnabled(true);
    mRecyclerView.setRefreshView(new DefinitionAnimationRefreshHeaderView(getActivity()));
    mRecyclerView.setLoadMoreView(new DefinitionAnimationLoadMoreView(getActivity()));

##### （2）自定义刷新和加载更多样式

 ##### 在进行自定义View之前先来了解刷新和加载更多的几种状态：

###### ① BasePullToRefreshView刷新：

	/***
	 * 下拉刷新分为4个状态
	 */
	//下拉的状态（还没到下拉到固定的高度时）
	public static final int STATE_PULL_DOWN=0;//
	//下拉到固定高度提示释放刷新的状态
	public static final int STATE_RELEASE_REFRESH=1;
	//刷新状态
	public static final int STATE_REFRESHING=2;
	//刷新完成
	public static final int STATE_DONE=3;



###### ②BaseLoadMoreView加载更多：

	/***
	 * 加载更多分为3个状态
	 */
	//正在加载
	public final static int STATE_LOADING = 0;
	//加载完成
	public final static int STATE_COMPLETE = 1;
	//没有数据
	public final static int STATE_NODATA= 2;

##### 自定义刷新的步骤：

###### ①自定义View继承BasePullToRefreshView，重写initView()、setRefreshTimeVisible(boolean show)、destroy()方法: 

 在initView()做自定义布局、相关动画的初始化，最后在initView()方法的最后面添加以下代码即可。   

	//mContainer =LayoutInflater.from(context).inflate(R.layout.layout_default_arrow_refresh, null);  

 	//把刷新头部的高度初始化为0
    LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    lp.setMargins(0, 0, 0, 0);
    this.setLayoutParams(lp);
    this.setPadding(0, 0, 0, 0);
    addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));//把刷新布局添加进去
    setGravity(Gravity.BOTTOM);

    //测量高度
    measure(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    mMeasuredHeight = getMeasuredHeight();

setRefreshTimeVisible(boolean show)是用来设置是否显示刷新时间控件，在默认刷新样式中通过mRecyclerView.setRefreshTimeVisible(false)即可隐藏刷新时间，如果在自定义的布局中没有这项这个方法就可以忽略。 
          
destroy()是用来关掉改页面时把刷新View的一些动画等释放，防止内存泄漏。

###### ②实现BasePullToRefreshView.OnStateChangeListener监听(重点，主要是进行状态切换后的相关操作逻辑） 

在构造函数中

    onStateChangeListener=this;

 onStateChange的模板样式
 	
    @Override
    public void onStateChange(int state) {
        //下拉时状态相同不做继续保持原有的状态
        if (state == mState) return ;
        //根据状态进行动画显示
        switch (state){
            case STATE_PULL_DOWN://跟随手指下拉的状态
                 //clearAnim();
                 //startAnim();
                 break;
            case STATE_RELEASE_REFRESH://下拉释放
                 break;
            case STATE_REFRESHING://正在进行刷新
                 //clearAnim();
                 //startAnim();
                 scrollTo(mMeasuredHeight);//这段代码需要添加
                 break;
             case STATE_DONE://刷新完成
                 break;
         }
         mState = state;//状态的更新
     }


#####  自定义加载更多的步骤(包括没有没有更多数据显示的操作)：
 
###### ①自定义View继承BaseLoadMoreView，重写initView()、setState()、destroy()方法:

在initView()做自定义布局、相关动画的初始化，最后在initView()方法的最后面添加以下代码即可。   


    //mContainer = LayoutInflater.from(context).inflate(R.layout.layout_definition_animation_loading_more, null);
    addView(mContainer);
    setGravity(Gravity.CENTER);

destroy()是用来关掉改页面时把刷新View的一些动画等释放，防止内存泄漏。  

在setState()进行状态切换后的相关操作逻辑，模板样式：

	@Override
	public void setState(int state) {
		switch (state){
			case STATE_LOADING://正在加载
				//loadMore_Ll.setVisibility(VISIBLE);
				//noDataTv.setVisibility(INVISIBLE);
				//animationDrawable= (AnimationDrawable) loadingIv.getDrawable();
				//animationDrawable.start();
				this.setVisibility(VISIBLE);//这段代码需要添加
				break;
			case STATE_COMPLETE:
				//if(animationDrawable!=null){
				//	animationDrawable.stop();
				//}
				this.setVisibility(GONE);//这段代码需要添加
				break;
			case STATE_NODATA:
				//loadMore_Ll.setVisibility(INVISIBLE);
				//noDataTv.setVisibility(VISIBLE);
				//animationDrawable= (AnimationDrawable) loadingIv.getDrawable();
				//animationDrawable.start();
				this.setVisibility(VISIBLE);//这段代码需要添加
				break;
		}
		mState = state;//状态的更新
	}


###### ②注意：在自定义加载更多样式时，如果需要有没有更多加载更多数据提示同样需要在布局中写好，然后在onSatae中根据状态对加载和没有跟多显示提示进行显示隐藏操作。

#### 3、上拉加载更多配合SwipeRefreshLayout使用  
![](https://upload-images.jianshu.io/upload_images/4361802-83d4abf47975d8ee.gif?imageMogr2/auto-orient/strip)

##### （1）布局文件    

	<android.support.v4.widget.SwipeRefreshLayout
       android:id="@+id/swipeRefreshLayout"
       android:layout_width="match_parent"
       android:layout_height="match_parent">

       <com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView
           android:id="@+id/recycler_rv"
           android:layout_width="match_parent"
           android:layout_height="match_parent"/>

    </android.support.v4.widget.SwipeRefreshLayout>
 

##### （2）代码设置

	mRecyclerView.setLoadMoreEnabled(true);
    mRecyclerView.setLoadMoreView(new DefinitionAnimationLoadMoreView(getActivity()));
    swl_Refresh.setColorSchemeResources(R.color.colorAccent);
    swl_Refresh.setOnRefreshListener(this);

##### (3)注意的问题
>由于PullToRefreshRecyclerView的下拉刷新和下拉加载更多完成时会自动刷新Adapter,而SwipeRefreshLayout刷新完成时需要手动进行notifyDataSetChanged刷新适配器。

### 4、RecyclerView添加头部、空布局

![](https://upload-images.jianshu.io/upload_images/4361802-43963d6039c41bb2.gif?imageMogr2/auto-orient/strip)

##### （1）代码设置

	View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty,null);
	mRecyclerView.setEmptyView(emptyView);

#### 5、上拉加载更多实现NoMoreData、自动刷新

![](https://upload-images.jianshu.io/upload_images/4361802-631dfbc16e8fe42d.gif?imageMogr2/auto-orient/strip)


##### （1）上拉加载更多数据的布局设置在上面的自定义LoadingMoreView中有介绍，如果要显示没有更多数据提示只需要在LoadMore返回数据之后设置：

    mRecyclerView.setNoMoreDate(true);

##### （2）自动刷新需要列表已经填充了数据之后再做自动刷新操作才会生效：

    mRecyclerView.setAutoRefresh();



####  6、PullToRefreshRecyclerView的其他使用

##### （1）提供的使用方法

    mRecyclerView.isLoading()  //是否正在加载更多
    mRecyclerView.loadMoreComplete()  //加载更多完成
    mRecyclerView.isRefreshing()  //是否正在刷新
    mRecyclerView.refreshComplete();  //刷新数据完成

##### （2）刷新、加载更多接口回调，PullToRefreshRecyclerView.OnRefreshAndLoadMoreListener提供一下两个方法：

    onRecyclerViewRefresh()
    onRecyclerViewLoadMore()
    
###### ①在设置RecyclerView是要设LayoutManager
###### ②如果使用PullToRefreshRecyclerView在Activty/Fragment中的onDestroy（）调用mRecyclerView.destroy()防止内存泄漏。
    @Override
	public void onDestroy() {
		super.onDestroy();
		if(mRecyclerView!=null){
			mRecyclerView.destroy();
		}
	}


### 三、BaseRecyclerViewAdapter的使用

#### 1.BaseRecyclerViewAdapter的比原始Adapter的代码量减小

在BaseRecyclerViewAdapter中的BaseViewHolder进行布局转化，同时定义了一些比较基本的View操作，使用简单。
##### （1）使用代码：

	public class PullToRecyclerViewAdapter extends BaseRecyclerViewAdapter<String> {

    	public PullToRecyclerViewAdapter(Context mContext, List<String> mDatas, PullToRefreshRecyclerView pullToRefreshRecyclerView) {
        	super(mContext, R.layout.item_pull_refresh, mDatas, pullToRefreshRecyclerView);
    	}

    	@Override
    	protected void convert(BaseViewHolder baseViewHolder, String s) {
        	baseViewHolder.setText(R.id.title,s);
    	}
	}
###### ①使用者需要在继承BaseRecyclerViewAdapter时传入一个数据实体类型，具体的操作在convert()方法中操作。
###### ②BaseViewHolder提供了一些常用View的基本操作，通过baseViewHolder.getView()可得到布局中的控件。


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


###### 主要是对PullToRefreshRecyclerView和RecyclerView的适配，使用时适配器根据需要使用对应的构造函数。

####   2.添加Item的点击和长按事件  

![](https://upload-images.jianshu.io/upload_images/4361802-3c8ff91f4e0e802a.gif?imageMogr2/auto-orient/strip)


##### （1） Item点击事件实现


	itemClickAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
		@Override
		public void onItemClick(View view, int position) {
			showToast(mDatas.get(position).getTitle());
		}
	});


##### （2）Item长按事件实现


	itemClickAdapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.onItemLongClickListener() {
		@Override
		public boolean onItemLongClick(View view, int position) {
			showToast("进行长按操作");
			return true;
		}
	});


##### （3）也可以实现BaseRecyclerViewAdapter.OnItemClickListener和BaseRecyclerViewAdapter.onItemLongClickListener

	//事件监听
	itemClickAdapter.setOnItemClickListener(this);
	itemClickAdapter.setOnItemLongClickListener(this);

	//点击实现
	@Override
	public void onItemClick(View view, int position) {
		showToast(mDatas.get(position).getTitle());
	}

	@Override
	public boolean onItemLongClick(View view, int position) {
		showToast("进行长按操作");
		return true;
	}

#### 3.多布局的使用 

![](https://upload-images.jianshu.io/upload_images/4361802-2a6c0f6834039c2a.gif?imageMogr2/auto-orient/strip)

##### BaseRecyclerViewAdapter的多布局实现需要注意的四步：

###### ①自定义Adapter需要继承BaseRecyclerViewMultiItemAdapter。
###### ② 数据实体类需要继承BaseMultiItemEntity，在getItemViewType()返回布局类型。
###### ③ 在自定义Adapter中的构造函数中通过addItemType()传入不同类型对应的布局。
###### ④在自定义Adapter中的convert进行类型判断，做相对应的操作。




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

#### 4.添加拖拽、滑动删除  

![](https://upload-images.jianshu.io/upload_images/4361802-110f412fd3c759e0.gif?imageMogr2/auto-orient/strip)


###### 局限：只针对RecyclerView，对本框架封装的PullToRefreshRecyclerView会出现混乱。

###### ①BaseRecyclerViewAdapter和BaseRecyclerViewMultiItemAdapter都已经封装支持拖拽、滑动，适配器只需要根据需求继承其中一个即可。
###### ②框架提供了一个BaseRecycleItemTouchHelper，对于普通的左右滑动删除、拖拽已经实现，如果想自定义可以继承BaseRecycleItemTouchHelper类，再重写相对应的方法进行实现。
###### ④在Activity/Fragment中需要实现以下代码：

	ItemTouchHelper.Callback callback=new BaseRecycleItemTouchHelper(dragAndDeleteAdapter);
	ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
	itemTouchHelper.attachToRecyclerView(mRecyclerView);

###### ⑤BaseRecyclerViewAdapter.OnDragAndDeleteListener进行操作动作完成之后的回调。


	@Override
	public void onDragAndDeleteFinished() {

		mRecyclerView.postDelayed(new Runnable() {
			@Override
			public void run() {
				dragAndDeleteAdapter.notifyDataSetChanged();
				showToast("操作完成");
			}
		},300);
	}
  ###### 注意：需要延时再进行逻辑操作，不然会出现数据混乱。
  
  
  ###  四、MVP+RxJava+Retrofit的封装使用

###### 由于Retrofit已经封装在DataManager中，在DataManager中有详细的介绍，这里只是提供一个例子让大家了解如何使用MVP+RxJava+Retrofit。



#### 1.在使用Retrofit请求网络之前需要进行配置，在框架中提供了了Config配置类

###### 框架中的Config总览如下：

	public class Config {
          /**必传参数**/
          //是否为BuildConfig.DEBUG,日志输出需要
          public static boolean DEBUG;
          //设置Context
          public static Context CONTEXT;
          /**Retrofit**/
          //网络请求的域名
          public static String URL_DOMAIN;
          //网络缓存地址
          public static String URL_CACHE;
          //设置OkHttp的缓存机制的最大缓存时间,默认为一天
          public static long MAX_CACHE_SECONDS= 60 * 60 * 24;
          //缓存最大的内存,默认为10M
          public static long MAX_MEMORY_SIZE=10 * 1024 * 1024;
          //设置网络请求json通用解析类
          public static Class MClASS;
		  //设置该参数可以去掉json某个字段不解析，比如EXPOSEPARAM=“data”,json的data字段内容不被解析
		  public static String EXPOSEPARAM;
          /**SharePreference**/
          public static String USER_CONFIG;
          /**Realm**/
          public static RealmMigration realmMigration;
          public static int realmVersion=0;
          public static String realmName="myRealm.realm";

          /***请求接口超时设定**/
          public static int CONNECT_TIMEOUT_SECONDS=60;
          public static int READ_TIMEOUT_SECONDS=60;
          public static int WRITE_TIMEOUT_SECONDS=60;

		  /***设置全局请求头***/
          public static Map<String,String> HEADERS;

    }

###### 在项目中需要根据项目需要进行配置，在Application中设置

	private void config(){
		Config.DEBUG= BuildConfig.DEBUG;//这个如果是测试时，日志输出，网络请求相关信息输出
		Config.URL_CACHE=AppConfig.URL_CACHE;//OkHttp缓存地址
		Config.CONTEXT=this;//这个是必传
		Config.MClASS= Result.class;//如果项目的json数据格式统一可以设置一个统一的been类
		Config.URL_DOMAIN="http://api.tianapi.com/";//网络请求域名
	}


###### 根据项目需要定义一个通用的数据实体类，这是本例通用实体类，这个类需要设置到Applicatin中

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


######  温馨提醒：由于每个项目返回来的json数据格式有所不同，如果Result中代表的字段例如newslist没有内容返回来的时候这个字段需要后台控制不返回，如果不做处理会报解析错误。

####  3.MVP+RxJava+Retrofit+OkHttp的缓存机制

![](https://upload-images.jianshu.io/upload_images/4361802-e0f0294088db24bd.gif?imageMogr2/auto-orient/strip)


###### 上面的缓存配置完成之后通过以下代码即可：

    public class WeChatWorldNewsPresenter extends WeChatWorldNewsContract.Presenter {
        @Override
         public void requestWorldNews(int page, int num) {

         RequestBuilder<Result<List<WeChatNews>>> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<Result<List<WeChatNews>>>(mView) {
            @Override
            public void onNext(Result<List<WeChatNews>> result) {
                mView.refreshUI(result.getNewslist());
            }
        });

        resultRequestBuilder
                .setUrl(ApiUrl.URL_WETCHAT_WORLD_NEWS)
                .setTransformClass(WeChatNews.class)
                .setRequestParam(ApiClient.getRequiredBaseParam())
                .setHttpTypeAndReqType(RequestBuilder.HttpType.DEFAULT_GET, RequestBuilder.ReqType.DEFAULT_CACHE_LIST)
                .setParam("page",page)
                .setParam("num",num);

        rxManager.addObserver(DataManager.getInstance(DataManager.DataType.RETROFIT).httpRequest(resultRequestBuilder));
      }
    }

#### 3.MVP+RxJava+Retrofit+OkHttp的缓存机制

[效果图](https://upload-images.jianshu.io/upload_images/4361802-e0f0294088db24bd.gif?imageMogr2/auto-orient/strip)


###### 上面的缓存配置完成之后通过以下代码即可：

    public class WeChatWorldNewsPresenter extends WeChatWorldNewsContract.Presenter {
        @Override
         public void requestWorldNews(int page, int num) {

         RequestBuilder<Result<List<WeChatNews>>> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<Result<List<WeChatNews>>>(mView) {
            @Override
            public void onNext(Result<List<WeChatNews>> result) {
                mView.refreshUI(result.getNewslist());
            }
        });

        resultRequestBuilder
                .setUrl(ApiUrl.URL_WETCHAT_WORLD_NEWS)
                .setTransformClass(WeChatNews.class)
                .setRequestParam(ApiClient.getRequiredBaseParam())
                .setHttpTypeAndReqType(RequestBuilder.HttpType.DEFAULT_GET, RequestBuilder.ReqType.DEFAULT_CACHE_LIST)
                .setParam("page",page)
                .setParam("num",num);

        rxManager.addObserver(DataManager.getInstance(DataManager.DataType.RETROFIT).httpRequest(resultRequestBuilder));
      }
    }


####  4.MVP+RxJava+Retrofit+自定义磁盘缓存机制

[效果图](https://upload-images.jianshu.io/upload_images/4361802-04e2322fc5f515ee.gif?imageMogr2/auto-orient/strip)

  public class WeChatChinaNewsDefinitionPresenter extends WeChatChinaNewsContract.Presenter {
	    @Override
	    public void requestChinaNews(int page, int num) {
		    String filePath = AppConfig.STORAGE_DIR + "wechat/china";
		    String fileName = "limttime.t";

		    RequestBuilder resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<Result<List<WeChatNews>>>(mView) {
			    @Override
			    public void onNext(Result<List<WeChatNews>> result) {
				    mView.refreshUI(result.getNewslist());
			    }
		    }).setFilePathAndFileName(filePath, fileName)
				.setTransformClass(WeChatNews.class)
				.setUrl(ApiUrl.URL_WETCHAT_CHINA_NEWS)
				.setRequestParam(ApiClient.getRequiredBaseParam())
				.setHttpTypeAndReqType(RequestBuilder.HttpType.DEFAULT_GET,RequestBuilder.ReqType.DISK_CACHE_LIST_LIMIT_TIME)
				.setParam("page", page)
				.setParam("num", num);

		    rxManager.addObserver(DataManager.getInstance(DataManager.DataType.RETROFIT).httpRequest(resultRequestBuilder));
	    }
    }


##### 新增json请求参数以及针对接口设置的请求头设置

 	resultRequestBuilder.setUrl(ApiUrl.URL_ABOUT_US_RULE)
                .setTransformClass(ContractUsInfo.class)
                .setParam("code","contact_us")
                .setHeader("Accept-Language",MultiLanguageUtils.getInstance().getRequestLanguage())
                .setHttpTypeAndReqType(RequestBuilder.HttpType.JSON_PARAM_POST, RequestBuilder.ReqType.NO_CACHE_MODEL);

        rxManager.addObserver(DataManager.getInstance(DataManager.DataType.RETROFIT).httpRequest(resultRequestBuilder));

#####  注意：

######  ①RxObservableListener有三个回调方法
    void onNext(T result);
    void onComplete();
    void onError(NetWorkCodeException.ResponseThrowable e);
######  只会重写onNext方法，其它两个方法可以自行选择重写。
######  ②RxObservableListener提供两个构造函数
    protected RxObservableListener(BaseView view){
	    this.mView = view;
    }

    protected RxObservableListener(BaseView view, String errorMsg){
	     this.mView = view;
         this.mErrorMsg = errorMsg;
    }

###### 这两个构造函数主要主要是为了统一处理onError的，如果要自定义错误提醒，则可以选择第二个构造函数。

######  ③通过DataManager的网络请求方式会返回来一个DisposableObserver，需要把它通过rxManager.addObserver()添加进CompositeDisposable才能正常执行。


###  五、DataManager的使用（DataManager封装了三种数据请求方式，包括Retroift、SharePreference和Realm）

####   1.DataManager的了解 

**提供了三种方式**

    public enum DataType {
		RETROFIT, REALM, SHAREPREFERENCE
    }

**通过DataManager.getInstance(DataManager.DataType.XXX)可获得对应的请求方式。**

####  1.DataManager的Retrofit请求 
##### （1）配置
需要在项目的Application初始化Retrofit的一些参数

        //基本配置
		Config.DEBUG= BuildConfig.DEBUG;
		Config.CONTEXT=this;
		//Retrofit配置
		Config.URL_CACHE=AppConfig.URL_CACHE;
		Config.MClASS= Result.class;//如果项目的json数据格式统一可以设置一个统一的been类
		Config.URL_DOMAIN="http://api.tianapi.com/";	



##### （2）使用统一解析类、不使用统一解析类、混合使用

![](https://user-gold-cdn.xitu.io/2018/5/14/1635d7e3a50ad4b2?w=368&h=654&f=gif&s=1584805)

###### ① 如果项目如果项目的json数据格式统一可以设置一个统一的been类，例如上面的例子的Result类,同时要在Config类设置（下面例子都是有统一解析类）：

    RequestBuilder<Result<List<WeChatNews>>> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<Result<List<WeChatNews>>>(mView) {
			@Override
			public void onNext(Result<List<WeChatNews>> result) {
				mView.refreshUI(result.getNewslist());
			}
		});

		resultRequestBuilder
				.setUrl(ApiUrl.URL_WETCHAT_FEATURED)
				.setTransformClass(WeChatNews.class)
				.setRequestParam(ApiClient.getRequiredBaseParam())
				.setParam("page",page)
				.setParam("num",num);

		rxManager.addObserver(DataManager.getInstance(DataManager.DataType.RETROFIT).httpRequest(resultRequestBuilder));


######  ②如果项目没有统一的解析been类，那么Config类就不用设置了，在Retrofit请求的时候直接指定一个解析类就可以了：

     RequestBuilder<WeChatNewsResult> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<WeChatNewsResult>(mView) {
			@Override
			public void onNext(WeChatNewsResult result) {
				mView.refreshUI(result.getNewslist());
			}
		});

		resultRequestBuilder
				.setUrl(ApiUrl.URL_WETCHAT_FEATURED)
                            .setTransformClass(WeChatNewsResult.class)
				.setRequestParam(ApiClient.getRequiredBaseParam())
				.setParam("page",page)
				.setParam("num",num);

		rxManager.addObserver(DataManager.getInstance(DataManager.DataType.RETROFIT).httpRequest(resultRequestBuilder));


######  ③如果项目想两种方式共存，那么在请求的时候需要通过setUserCommonClass（false）设置才能不使用统一解析类进行解析：

    RequestBuilder<WeChatNewsResult> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<WeChatNewsResult>(mView) {
			@Override
			public void onNext(WeChatNewsResult result) {
				mView.refreshUI(result.getNewslist());
			}
		});

		resultRequestBuilder
				.setUrl(ApiUrl.URL_WETCHAT_FEATURED)
                            .setTransformClass(WeChatNewsResult.class)
				.setUserCommonClass(false)
				.setRequestParam(ApiClient.getRequiredBaseParam())
				.setParam("page",page)
				.setParam("num",num);

		rxManager.addObserver(DataManager.getInstance(DataManager.DataType.RETROFIT).httpRequest(resultRequestBuilder));


##### 注意：DISK_CACHE_LIST_LIMIT_TIME和DISK_CACHE_MODEL_LIMIT_TIME这两种限时使用缓存的请求方式不统一一种解析方式会出现页面没有数据显示，因为在限定的时间内如果突然转用另外一个解析实体类去解析会解析失败，只能等过限定时间或者清除本地缓存去解决这一问题。


##### （2）RequestBuilder的设置（网络请求的配置）
######  ①数据处理的方式
    public enum ReqType {
        //没有缓存
        NO_CACHE_MODEL,
        No_CACHE_LIST,
        //默认Retrofit缓存
        DEFAULT_CACHE_MODEL,
        DEFAULT_CACHE_LIST,
        //自定义磁盘缓存，返回List
        DISK_CACHE_LIST_LIMIT_TIME,
        //自定义磁盘缓存，返回Model
        DISK_CACHE_MODEL_LIMIT_TIME,
        //自定义磁盘缓存，没有网络返回磁盘缓存，返回List
        DISK_CACHE_NO_NETWORK_LIST,
        //自定义磁盘缓存，没有网络返回磁盘缓存，返回Model
        DISK_CACHE_NO_NETWORK_MODEL,
        //保存网络数据到本地磁盘，可以设定网络请求是否返回数据
        DISK_CACHE_NETWORK_SAVE_RETURN_MODEL,
        DISK_CACHE_NETWORK_SAVE_RETURN_LIST,
     }

######  ②网络请求方式
    public enum HttpType {
        //GET请求
        DEFAULT_GET,
        //POST请求
        DEFAULT_POST,
        //如果请求URL出现中文乱码，可选择这个
        FIELDMAP_POST,
	    //json格式请求参数
		JSON_PARAM_POST,
        //上传一张图片
        ONE_MULTIPART_POST

    }


######  ③RequestBuilder的填充

     RequestBuilder<Result<List<WeChatNews>>> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<Result<List<WeChatNews>>>(mView) {
            @Override
            public void onNext(Result<List<WeChatNews>> result) {
                mView.refreshUI(result.getNewslist());
            }
        });

        resultRequestBuilder
                .setUrl(ApiUrl.URL_WETCHAT_WORLD_NEWS)
                .setTransformClass(WeChatNews.class)
                .setHttpTypeAndReqType(RequestBuilder.HttpType.DEFAULT_GET, RequestBuilder.ReqType.DEFAULT_CACHE_LIST)
                .setRequestParam(ApiClient.getRequiredBaseParam())
                .setParam("page",page)
                .setParam("num",num);

######  ④DataManager提供Retrofit请求的方法

     <T> DisposableObserver<ResponseBody> httpRequest(RequestBuilder<T> requestBuilder);


##### （3）Retrofit的扩展
######  如果存在DataManager提供的方法满足不了的请求可以通过RetrofitManager提供的getNoCacheApiService（）和getApiService（）获得不缓存和缓存的Retrofit，然后通过RxSubscriber进行回调。

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


##### （4）注意的问题
######   ①请求的域名已经在Application设置好了，setUrl不需要填完整的url
######   ②要区分清楚接口返回的数据时List还是Model，从而选择对应的ReqType
######   ③setRequestParam可以设置参数集合，setParam可以单个设置
######   ④使用DISK_CACHE_LIST_LIMIT_TIME/DISK_CACHE_MODEL_LIMIT_TIME这两个显示限时缓存时需要通过setFilePathAndFileName（）设置保存路径setLimtHours（）设置缓存时间（单位为：小时）
######   ⑤如果要上传单张图片需要用到HttpType.ONE_MULTIPART_POST的请求方式，同时通过RequestBuilder设置MultipartBody.Part


 #### 2.DataManager的SharePreference的使用
##### （1）配置
需要在项目的Application初始化SharePreference的一些参数

    //SharePreference配置
    Config.USER_CONFIG="Collection_User";

##### （2）使用方法
    DataManager.getInstance(DataManager.DataType.SHAREPREFERENCE).saveByKeyWithSP("user","这

    String user=DataManager.getInstance(DataManager.DataType.SHAREPREFERENCE).queryByKeyWithSP("user",String.class);是一条测试的内容");

##### （3）DataManager提供SharePreference请求的方法

    //自定义保存的配置文件名、key
    void saveByNameAndKeyWithSP(String name, String key, Object object);
     //使用在Application配置的保存文件名
    void saveByKeyWithSP(String key,Object object);
     //查询保存在自定义的配置文件的内容
    <T> T queryByNameAndKeyWithSP(String name, String key, Class<T> clazz);
    //查询保存在Application设置的文件的内容
    <T> T queryByKeyWithSP(String key, Class<T> clazz);

####  3.DataManager的Realm的使用

 #####  （1）配置
######   ①需要在项目的Application初始化Realm的一些参数

    //Realm的配置
	Config.realmVersion=0;
	Config.realmName="realm.realm";
	Config.realmMigration=customMigration;//数据库数据迁移（been类字段增加移除）

######   ②在Project 的build.gradle中的dependencies加入

    classpath "io.realm:realm-gradle-plugin:5.0.0"

######   ③在项目 的build.gradle中的顶部加入

    apply plugin: 'realm-android'

##### （2）使用方法

    DataManager.getInstance(DataManager.DataType.REALM).saveOrUpdateWithPKByRealm(user);
    user= (User) DataManager.getInstance(DataManager.DataType.REALM).queryFirstByRealm(User.class);


##### （3）DataManager提供Realm请求的方法

        /**
	 * 保存操作
	 */
	void saveOrUpdateWithPKByRealm(final RealmObject bean);
	void saveOrUpdateWithPKByRealm(final List<? extends RealmObject> beans);
	void saveWithoutPKByRealm(final RealmObject bean);
	void saveWithoutPKByRealm(final List<? extends RealmObject> beans);

	/**
	 * 查询操作
	 */
	RealmObject queryFirstByRealm(Class<? extends RealmObject> clazz);
	RealmObject queryAllWithFieldByRealm(Class<? extends RealmObject> clazz, String fieldName, String value);
	RealmObject queryWithFieldByRealm(Class<? extends RealmObject> clazz, String fieldName, String value) 
	List<? extends RealmObject> queryAllByRealm(Class<? extends RealmObject> clazz);
	List<? extends RealmObject> queryAllWithSortByRealm(Class<? extends RealmObject> clazz, String fieldName,Boolean isAscendOrDescend);

	/**
	 * 修改操作
	 */
	void updateParamWithPKByRealm(Class<? extends RealmObject> clazz, String primaryKeyName, Object primaryKeyValue, String fieldName,Object newValue);

	/**
	 * 删除操作
	 */
	void deleteFirstByRealm(Class<? extends RealmObject> clazz);
	void deleteAllByRealm(Class<? extends RealmObject> clazz);
	void deleteAllWithFieldByRealm(Class<? extends RealmObject> clazz, String fieldName, String value)

##### （4）Realm数据迁移（been类字段操作，更多参照Realm文档）

随着app版本的迭代，数据库的字段可能会增加或者移除这时候就需要用到Realm提供的RealmMigration进行设置。

    public class CustomMigration implements RealmMigration {
    	@Override
    	public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        	RealmSchema schema = realm.getSchema();

        		for(int i = (int) (oldVersion+1);i<=newVersion;i++){
            		if (i == 1) {
                		RealmObjectSchema personSchema = schema.get("BleLabelInfo");

                		RealmObjectSchema multilanguageSchema = schema.create("MultiLanguage");
                		multilanguageSchema.addField("zhHk", String.class);
                		multilanguageSchema.addField("zhCn", String.class);
                		multilanguageSchema.addField("en", String.class);

                		personSchema
                        	.addRealmObjectField("multilingualism", multilanguageSchema);
            		} else if (i == 2) {
                		RealmObjectSchema personSchema = schema.get("User");
                		personSchema
                        	.addField("id", String.class);


                		RealmObjectSchema multilanguageSchema = schema.create("LabelRecord");
                		multilanguageSchema.addField("major_minor", String.class);
                		multilanguageSchema.addPrimaryKey("major_minor");
                		multilanguageSchema.addField("major", String.class);
                		multilanguageSchema.addField("minor", String.class);
                		multilanguageSchema.addField("timeMillis", long.class);
                		multilanguageSchema.addField("threshold",int.class);
            		}else if(i==3){
                		RealmObjectSchema personSchema = schema.get("User");
                		personSchema
                        	.addField("authType", boolean.class);
            		}
        		}


    	}
   	}

######  步骤：
-  **自定义RealmMigration，在migrate方法中进行字段的增加或者移除。**
-  **在Application中升Realm的版本号Config.realmVersion往上增加。**
-  **在Application设置RealmMigration，Config.realmMigration=customMigration。**

##### （5）注意的问题

-  **自定义Realm的保存文件文成的时候需要以.realm为后缀。**


###  六、 Base的使用

####  1.Base封装了MVP和项目的基类   

##### （1）MVP

- BaseModel
- BaseView
- BasePresenter

##### （2）在项目中的网络请求+MVP的完整实现

###### ①定义一个contract类，内部分别继承上面的MVP base类，在这里定义操作。

	public interface WeChatChinaNewsContract {

		interface Model extends BaseModel{
			Observable<Result<List<WeChatNews>>> loadChinaNews(int page, int num);
		}

		interface View extends BaseView{
			void refreshUI(List<WeChatNews> weChatNews);
		}

		abstract class Presenter extends BasePresenter<Model,View>{
			public abstract void requestChinaNews(int page,int num);
		}
	}


###### ②Presenter的具体执行类。

	public class WeChatChinaNewsPresenter extends WeChatChinaNewsContract.Presenter {
    	@Override
    	public void requestChinaNews(int page, int num) {

 			rxManager.addObserver(RequestManager.loadOnlyNetWork(mModel.loadChinaNews(page, num),
                new RxObservableListener<Result<List<WeChatNews>>>(mView) {
                    @Override
                    public void onNext(Result<List<WeChatNews>> result) {
                        mView.refreshUI(result.getNewslist());
                    }
                }));

    	}
	}

###### ③Model的具体执行类。
 
	public class WeChatChinaNewsModel implements WeChatChinaNewsContract.Model {
		@Override
		public Observable<Result<List<WeChatNews>>> loadChinaNews(int page, int num) {

			Map<String,Object> map= ApiClient.getRequiredBaseParam();
			map.put("page",page);
			map.put("num",num);
			return RetrofitManager.getApiService(ApiService.class)
				.getWeChatChinaNews(ApiUrl.URL_WETCHAT_CHINA_NEWS,map);
		
		}
	}

###### ④UI

	public class FragmentChinaNews extends BaseFragment<WeChatChinaNewsModel,WeChatChinaNewsPresenter> implements WeChatChinaNewsContract.View{

		@Override
		public void init() {
		}

		@Override
		public void requestData() {
			((WeChatChinaNewsPresenter)mPresenter).requestChinaNews(pageSize,PAGE_SIZE);
		}

		@Override
		public void refreshUI(List<WeChatNews> newsList) {

		}

		@Override
		public void onError(String errorMsg) {
		}
	}

##### 2.UI Base
###### （1）IBaseActivity
- IBaseActivity:主要提供了一个页面的基本方法、处理了MVP之间的关联、使用者可以直接继承该类使用、也可以继承该类实现扩展。
- IBaseActivity<T extends BaseModel, E extends BasePresenter>已经进行MVP之间的传递和关联。
 - 处理好页面销毁之后Observables 和 Subscribers的解绑。
 - getLayoutId()设置布局、init()数据初始化、requestData()请求数据，执行顺序已经在IBaseActivity做好处理。
 - 可以继承IBaseActivity进行扩展。


	public abstract class BaseActivity<T extends BaseModel,E extends BasePresenter> extends 	IBaseActivity{
    	private Unbinder unbinder;

    	@Override
   	 	protected void onCreate(@Nullable Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
       	   	unbinder= ButterKnife.bind(this);
    	}
	}

 

###### 缺陷：如果对IBaseActivity进行扩展，在具体调用时需要类型才能调用相关方法。

	@Override
	public void requestData() {
		((WeChatFeaturedPresenter) mPresenter).requestFeaturedNews(pageSize, PAGE_SIZE);
	}


##### （2）IBaseFragment
- IBaseFragment:主要提供一个页面的基本方法，处理了MVP之间的关联，该类已经加入了懒人加载的控制方式、使用者可以直接继承该类使用、也可以继承该类实现扩展。

 - IBaseFragment<T extends BaseModel,E extends BasePresenter>已经进行MVP之间的传递和关联。
 - 处理好页面销毁之后Observables 和 Subscribers的解绑。
 - 加入了懒人加载方式，只有页面显示才会调用requestData()请求数据，并只会调用一次。
 - getLayoutId()设置布局、init()数据初始化、requestData()请求数据，执行顺序已经在IBaseFragment做好处理。
 - 可以继承IBaseFragment进行扩展。


		public abstract class BaseFragment<T extends BaseModel,E extends BasePresenter> extends IBaseFragment {

    		private Unbinder unbinder;

    		@Nullable
    		@Override
    		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        		super.onCreateView(inflater, container, savedInstanceState);
        		unbinder= ButterKnife.bind(this,mainView);
        		return mainView;
    		}
		}

###### 缺陷：如果对IBaseActivity进行扩展，在具体调用时需要类型才能调用相关方法。

	@Override
	public void requestData() {
		((WeChatChinaNewsPresenter)mPresenter).requestChinaNews(pageSize,PAGE_SIZE);
	}

##### （3）项目中的页面大多数都是使用Fragment实现交互，只有几个Activity作为跟容器的实现方式：
- Activity继承IBaseActivity或者IBaseActivity的子类
- 通过loadRootFragment加载根Fragment
- 重写onCreateFragmentAnimator进行设定Fragment之间的跳转动画，分别可以设置为DefaultHorizontalAnimator、DefaultVerticalAnimator，可以自定义
- 部分常用方法
	- onSupportVisible()/onSupportInvisible()页面的显示/隐藏
	- onBackPressedSupport()点击返回按钮回调，替换掉onBackPress方法
	- onFragmentResult类似Activity的onActivityResult
	- onNewBundle类似Activity的onNewBundle
	- isRootFragment判断是否是跟Fragment
	- loadRootFragment加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
	- loadMultipleRootFragment:加载多个同级根Fragment
	- showHideFragment:show一个Fragment,hide其他同栈所有Fragment
	- showHideFragment: show一个Fragment,hide一个Fragment
	- start/startForResult/startWithPop/startWithPopTo
	- 其它方法可以具体看IBaseFragment类

![](https://i.imgur.com/BvnJ9SY.gif)


##### （4）适配不同手机像素
- 在Applicaton的onCreate中设置 Density.setDensity(this, 375f);
- 375f代表设计稿的宽度，以dp为单位，后面需要以f（浮点型）

#### 3.UI状态控制StateView的使用 

##### （1）StateView的四种状态：

	//不显示
	public static final int STATE_NO_DATA = 0;
	//正在加载
	public static final int STATE_LOADING = 1;
	//空数据
	public static final int STATE_EMPTY = 2;
	//没有网络
	public static final int STATE_DISCONNECT=3;


##### （2）StateView的使用：

![](https://upload-images.jianshu.io/upload_images/4361802-62462c76bfc5f750.gif?imageMogr2/auto-orient/strip)


###### ①定义一个通用布局

	<?xml version="1.0" encoding="utf-8"?>
	<com.youngmanster.collectionlibrary.base.StateView
		xmlns:android="http://schemas.android.com/apk/res/android"
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		android:layout_centerInParent="true"
		android:id="@+id/state_view">

	</com.youngmanster.collectionlibrary.base.StateView >


###### ②添加到Ui页面的layout中

	<include layout="@layout/layout_state"/>

###### 注意：上面的语句添加的layout最外层最好是LinearLayout以及设置为android:orientation="vertical"

###### ③通过以下语句进行状态切换
 
	stateView.showViewByState(StateView.STATE_LOADING);
	stateView.showViewByState(StateView.STATE_EMPTY);
	stateView.showViewByState(StateView.STATE_NO_DATA);
	stateView.showViewByState(StateView.STATE_DISCONNECT);

###### ④通过以下语句可以修改不同布局的内容以及样式


	app:emptyText=""//设置空数据的文字提示
	app:emptyImage=""//设置空数据显示的图片
	app:disConnectImage=""//设置无网络的显示图片
	app:disConnectText=""//设置无网络的文字提示
	app:loadingText=""//设置loading的文字提示
	app:loadingViewAnimation=""//设置loading的动画文件，只是ImageView
	app:tipTextColor=""//设置文字显示颜色
	app:tipTextSize=""//设置文字的大小


#### 3.三步实现Permission(权限)设置

![](https://upload-images.jianshu.io/upload_images/4361802-e9f4d0a7f127faed.gif?imageMogr2/auto-orient/strip)


##### （1）设置好要请求的权限

	// 项目的必须权限，没有这些权限会影响项目的正常运行
	private static final String[] PERMISSIONS = new String[]{
			Manifest.permission.READ_SMS,
			Manifest.permission.RECEIVE_WAP_PUSH,
	};


##### （2）权限通过PermissionManager管理

	PermissionManager permissionManager=PermissionManager.with(this).
				//必须权限
				setNecessaryPermissions(PERMISSIONS);
    //通过以下语句进行请求
    permissionManager.requestPermissions();

##### （3）重写页面onRequestPermissionsResult


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == PermissionManager.PERMISSION_REQUEST_CODE) {//PERMISSION_REQUEST_CODE为请求权限的请求值
			//有必须权限选择了禁止
			if (permissionManager.getShouldShowRequestPermissionsCode() == PermissionManager.EXIST_NECESSARY_PERMISSIONS_PROHIBTED) {
				showToast("可以在这里设置重新跳出权限请求提示框");
			} //有必须权限选择了禁止不提醒
			else if (permissionManager.getShouldShowRequestPermissionsCode() == PermissionManager.EXIST_NECESSARY_PERMISSIONS_PROHIBTED_NOT_REMIND) {
				showToast("可以在这里弹出提示框提示去应用设置页开启权限");
				permissionManager.startAppSettings();
			}
		}
	}


######  注意：如果有需求先判断是否所有权限都已经允许之后再进入主页面可以通过permissionManager.isLackPermission()进行判断，如果返回true则进行权限请求，如果返回false则进入主页面。



- 多个权限请求如果其中某一个被禁止提醒，会先把没有禁止提醒的权限处理完之后再进行处理。
- 如果是必要权限被禁止而没有选择禁止提醒退出之后下次会重新请求权限。
- 如果必要权限被禁止和选择了禁止提醒重新进入页面在onRequestPermissionsResult会重新回调方法。
- 使用者可以根据onRequestPermissionsResult（）方法中返回来的标志PermissionManager.EXIST_NECESSARY_PERMISSIONS_PROHIBTED和PermissionManager.EXIST_NECESSARY_PERMISSIONS_PROHIBTED_NOT_REMIND做出对应的显示和操作（例如弹框提示跳转到设置页面或者toat提示）。

####  4.提供几种比较常用的Dialog弹框
![](https://upload-images.jianshu.io/upload_images/4361802-607bfea3f81b09b6.gif?imageMogr2/auto-orient/strip)

#####  ①提供的Dialog
- DIALOG_TEXT_TWO_BUTTON_DEFAULT：默认弹窗样式。
- DIALOG_TEXT_TWO_BUTTON_CUSTOMIZE：自定义弹出按钮提示。
- DIALOG_LOADING_PROGRASSBAR：默认加载弹框。
- DIALOG_DISPLAY_ADVERTISING：显示广告图的弹框样式。
- DIALOG_CHOICE_ITEM：单项选择弹框样式。

#####  ②自定义Dialog样式
- 继承BaseDialog，通过setContentView(R.layout.dialog_list);设置弹窗布局。
- 在提供的initUI（）方法中进行相应的逻辑设置。


#####  ③BaseDialog提供的方法
- setContentView（）：设置弹框布局样式。
- show（）：显示弹框。
- isShowing（）：判断弹框是否显示。
- dismiss（）：弹框销毁。
- setCancelable（）：点击返回键和外部不可取消。
- setDialogCancel（）：点击返回键可以取消。

#### 5.提供几种比较常用的PopupWindow弹框

![](https://upload-images.jianshu.io/upload_images/4361802-feb5a3664b8c8ddb.gif?imageMogr2/auto-orient/strip)

#####  ①BasePopupWindow提供的方法
- BasePopupWindow(Context context) :调用该构造函数默认弹出框铺满全屏。
- BasePopupWindow(Context context, int w, int h)：调用该构造函数可指定弹出框大小。
-  showPopup（）：在屏幕中央显示弹框。
- showPopupAsDropDown(View anchor):在指定控件底部显示弹框。
- setShowMaskView(boolean isShowMaskView):设置是否显示遮层。
- dismiss():销毁弹出框。
- getPopupLayoutRes():自定义弹出框的布局文件。
- getPopupAnimationStyleRes():自定义弹出框的动画文件。

#####  ②自定义PopupWindow
- 继承BasePopupWindow。
- 通过getPopupLayoutRes(R.layout.xxx)设置弹窗布局。
- 通过getPopupAnimationStyleRes(R.style.xxx)设置弹窗动画，不需要动画可以忽略不设置。

      <style name="animation_scale" parent="android:Animation.Dialog">
		<item name="android:windowEnterAnimation">@anim/scale_tip_in</item>
		<item name="android:windowExitAnimation">@anim/scale_tip_out</item>
      </style>

- 如果需要显示遮层，在构造函数通过setShowMaskView(true)设置。

#### 6.使用DisplayUtils修改状态栏

![](https://upload-images.jianshu.io/upload_images/4361802-8fe97b79628f38a2.gif?imageMogr2/auto-orient/strip)

- setStatusBarFullTranslucentWithBlackFont（Activity act）：状态栏透明黑字。
- setStatusBarBlackFontBgColor(Activity activity,int bgColor)：修改状态栏颜色同时字体变为黑色。
- setStatusBarFullTranslucent(Activity act)：状态栏透明。
- setStatusBarColor(Activity activity, int colorResId)：改变状态栏颜色。

####  7.提供几种比较常用的Utils工具类
- DisplayUtils：px和dp的转换、获取屏幕高宽、状态栏白底黑字、设置状态栏颜色、设置状态栏全屏透明、获取状态栏的高度、获取ActionBar的高度。
- FileUtils：写文件、读取文本文件中的内容、判断缓存是否失效、检查文件是否存在、删除目录、检查是否安装SD卡、删除文件。
- GlideUtils：Glide显示网络图片、Glide实现高斯模糊。
- LogUtils：日志工具类。
- NetworkUtils：网络工具类。
- ToastUtils：Toast提示类。


### 七、 CustomView的使用

####  1.CommonTabLayout的使用
![](https://upload-images.jianshu.io/upload_images/4361802-eed89d4cf407dbbf.gif?imageMogr2/auto-orient/strip)

#####  ①属性：
- tab_tabIndicatorWidth：设置下滑线的长度。
- tab_tabIndicatorHeight：设置下滑线的高度。
- tab_tabIndicatorColor:下滑线颜色。
- tab_indicator_marginLeft/tab_indicator_marginRight/tab_indicator_marginTop/tab_indicator_marginBottom：设置下滑线外边距。
- tab_tabTextColor：没选中字体颜色。
- tab_tabTextSize：字体大小。
- tab_tabSelectedTextColor:选中字体颜色。
- tab_padding:下滑线内边距，block样式时可以通过该属性设置距离。
- tab_tabBackground:Tab的背景颜色。
- tab_indicator_corner:下滑线的圆角大小。
- tab_indicator_gravity（bottom、top）:设置下滑线显示的位置，只针对line和triangle。
- tab_tabMode（scrollable、fixed）:Tab的显示模式。
- tab_indicator_style（line、triangle、block）:下滑线的样式。

#####  ②具体用户可参照例子使用。

####  2.OutSideFrameTabLayout的使用

![](https://upload-images.jianshu.io/upload_images/4361802-c221ec93b552c6bd.gif?imageMogr2/auto-orient/strip)

#####  ①属性：
- tab_tabIndicatorColor：设置Tab颜色。
- tab_indicator_corner：圆角大小
- tab_indicator_marginLeft/tab_indicator_marginRight/tab_indicator_marginTop/tab_indicator_marginBottom：设置下滑线外边距。
- tab_tabTextColor：没选中字体颜色。
- tab_tabTextSize：字体大小。
- tab_tabSelectedTextColor：选中字体颜色。
- tab_padding：内边距。
- tab_bar_color：bar的背景颜色。
- tab_bar_stroke_color：外框的颜色。
- tab_bar_stroke_width：外框的大小。
- tab_width：bar的长度。

#####  ②具体用户可参照例子使用。

#### 3.自动布局AutoLinefeedLayout的使用
	<com.youngmanster.collectionlibrary.customview.wraplayout.AutoLinefeedLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
     
	//填充的内容
     </com.youngmanster.collectionlibrary.customview.wraplayout.AutoLinefeedLayout>

![](https://i.imgur.com/3z5qQgn.gif)
#### 本文章会根据需要持续更新，建议star收藏，便于查看。也欢迎大家提出更多建议。

