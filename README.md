## Collection

![Travis](https://img.shields.io/badge/release-1.1.5-green.svg)
![Travis](https://img.shields.io/badge/llicense-MIT-green.svg)
![Travis](https://img.shields.io/badge/build-passing-green.svg)


Collection聚合了项目搭建的一些基本模块，节约开发者时间，协助项目的快速搭建,RecyclerView+Adapter+网络请求+MVP+基本Base,能够满足一个项目的基本实现，框架中暂时还没有把数据库和SharePrefence添加进去，后续会把两者跟网络请求封装在一起的 。

#### 更多交流请加微信公众号
![](https://upload-images.jianshu.io/upload_images/4361802-88c89753c38ddf70.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


>###### 简书地址：
>###### 掘金地址：

## 项目介绍
### 文章目录

**1.框架的引入**

>implementation 'com.youngman:collectionlibrary:1.1.5'

>Error:Could not find com.android.support:appcompat-v7:27.0.2.
因为library的Support Repository是27.0.2,可能跟项目有所冲突，如果sdk已经装了27还是会出现同样的错误。
解决办法：在项目根build.gradle中加入  maven { url "https://maven.google.com" }

**2.PullToRefreshRecyclerView的使用** 

- 框架默认下拉刷新、上拉加载更多样式  
- 自定义下拉刷新、上拉加载更多样式  
- 上拉加载更多结合SwipeRefreshLayout使用  
- RecyclerView添加头部、空布局  
- 上拉加载更多实现NoMoreData、自动刷新   
 
  
**3.BaseRecyclerViewAdapter的使用**

- BaseRecyclerViewAdapter比原始Adapter代码量减少
- 添加Item的点击事件  
- 添加Item的长按事件  
- 多布局的使用  
- 添加拖拽、滑动删除  

**4.MVP+RxJava+Retrofit的封装使用**  

- 框架中的Retrofit+RxJava封装的了解
- 使用框架在项目需要做的操作
- MVP+RxJava+Retrofit+OkHttp的缓存机制 
- MVP+RxJava+Retrofit+自定义磁盘缓存机制  

**5.Base的使用**   

- Base封装了MVP和项目的基类   
- UI状态控制StateView的使用  
- 三步实现Permission(权限)设置



###  一、框架整体模块
![](https://upload-images.jianshu.io/upload_images/4361802-f0b81113274451b6.gif?imageMogr2/auto-orient/strip)


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
![](https://upload-images.jianshu.io/upload_images/4361802-310aaa634f712429.gif?imageMogr2/auto-orient/strip)

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
  
  
  ### 四、MVP+RxJava+Retrofit的封装使用

#### 1.框架中的Retrofit+RxJava封装的了解

###### （1）Retrofit:RetrofitManager提供getApiService()和getNoCacheApiService，分别可以获取到可以缓存的retrofit和没有缓存的retrofit,使用第一种在没有网络的时候可以根据缓存进行显示，缓存的相关配置下面会有介绍。


###### （2）RxJava：提供了RxManager、RxObservableListener、RxSchedulers、RxSubscriber。

- RxManager：对Observables 和 Subscribers管理。

- RxObservableListener：对结果回调。

- RxSchedulers：线程切换。

- RxSubscriber：Observer的处理事件。

###### （3）网络请求管理RequestManager：提供对应的请求操作。

- loadFormDiskResultListLimitTime():设置缓存时间，没超过设置的时间不请求网络，只返回缓存数据。结果为一个List。
- loadFormDiskModeLimitTime():设置缓存时间，没超过设置的时间不请求网络，只返回缓存数据。结果为一个Model。
- loadNoNetWorkWithCacheResultList():没有网络再请求缓存。结果为一个List。
- loadNoNetWorkWithCacheModel():没有网络再请求缓存。结果为一个Model。
- loadOnlyNetWorkSaveResult():把结果保存到本地，根据标志是否返回数据，如果本地存在则不需要下载。
- loadOnlyNetWork()：只通过网络返回数据。

###### 前面5种请求方式主要是和自定缓存磁盘关联起来，配合RetrofitManager.getNoCacheApiService()使用，loadOnlyNetWork()和RetrofitManager.getApiService()结合使用，这样就可以实现OkHttp缓存和自定义磁盘缓存。


####  2.使用框架在项目需要做的操作


###### 在使用Retrofit请求网络之前需要进行配置，在框架中提供了了Config配置类

	public class Config {
    	//是否为BuildConfig.DEBUG，日志输出需要
    	public static boolean DEBUG;
    	//网络请求的域名
    	public static String URL_DOMAIN;
    	//网络缓存地址
    	public static String URL_CACHE;
    	//设置Context
    	public static Context CONTEXT;
    	//设置OkHttp的缓存机制的最大缓存时间,默认为一天
    	public static long MAX_CACHE_SECONDS= 60 * 60 * 24;
    	//缓存最大的内存,默认为10M
    	public static long MAX_MEMORY_SIZE=10 * 1024 * 1024;
    	//设置网络请求json通用解析类
    	public static Class MClASS;

	}

###### 在项目中需要根据项目需要进行配置，在Application中设置

	private void config(){
		Config.DEBUG= BuildConfig.DEBUG;//这个如果是测试时，日志输出，网络请求相关信息输出
		Config.URL_CACHE=AppConfig.URL_CACHE;//OkHttp缓存地址
		Config.CONTEXT=this;//这个是必传
		Config.MClASS= Result.class;//主要是网络请求通用数据实体类，自定义磁盘缓存需要用到
		Config.URL_DOMAIN="http://api.tianapi.com/";//网络请求域名
	}


###### 根据项目需要定义一个通用的数据实体类，这是本例通用实体类

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
#### 3.MVP+RxJava+Retrofit+OkHttp的缓存机制

[效果图](https://upload-images.jianshu.io/upload_images/4361802-e0f0294088db24bd.gif?imageMogr2/auto-orient/strip)


###### RetrofitManager.getApiService()+RequestManager.loadOnlyNetWork()+缓存配置即可  


###### 上面的缓存配置完成之后通过以下代码即可：

    //通过RetrofitManager.getApiService（）可以进行缓存
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

    //通过RetrofitManager.getApiService（）配合RequestManager.loadOnlyNetWork
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


#### 4.MVP+RxJava+Retrofit+自定义磁盘缓存机制

[效果图](https://upload-images.jianshu.io/upload_images/4361802-04e2322fc5f515ee.gif?imageMogr2/auto-orient/strip)

###### RetrofitManager.getNoCacheApiService()+RequestManager提供的除了loadOnlyNetWork()的其他数据获取方式即可使用自定义磁盘缓存



    //通过RetrofitManager.getNoCacheApiService（）去掉OkHttp缓存
	public class WeChatChinaNewsDefinitionModel implements WeChatChinaNewsContract.Model {
		@Override
		public Observable<Result<List<WeChatNews>>> loadChinaNews(int page, int num) {
			Map<String,Object> map= ApiClient.getRequiredBaseParam();
			map.put("page",page);
			map.put("num",num);
			return RetrofitManager.getNoCacheApiService(ApiService.class)
				.getWeChatChinaNews(ApiUrl.URL_WETCHAT_CHINA_NEWS,map);
		}
	}


    //RetrofitManager.getNoCacheApiService（）
	public class WeChatChinaNewsDefinitionPresenter extends WeChatChinaNewsContract.Presenter {
    	@Override
    	public void requestChinaNews(int page, int num) {
        	String filePath = AppConfig.STORAGE_DIR + "wechat/china/";
        	String fileName = "limttime.t";

        	rxManager.addObserver(RequestManager.loadFormDiskResultListLimitTime(
                mModel.loadChinaNews(page, num), new RxObservableListener<Result<List<WeChatNews>>>(mView) {
                    @Override
                    public void onNext(Result<List<WeChatNews>> result) {
                        mView.refreshUI(result.getNewslist());
                    }
                }, WeChatNews.class, 1, filePath, fileName));
    	}
	}

#####  注意：
######  ①通过RequestManager提供的几种请求方式返回来一个DisposableObserver，需要把它通过rxManager.addObserver()添加进CompositeDisposable才能正常执行。
######  ②RxObservableListener有三个回调方法
    void onNext(T result);
    void onComplete();
    void onError(NetWorkCodeException.ResponseThrowable e);
######  只会重写onNext方法，其它两个方法可以自行选择重写。
######  ③RxObservableListener提供两个构造函数
    protected RxObservableListener(BaseView view){
		this.mView = view;
    }

    protected RxObservableListener(BaseView view, String errorMsg){
	 	 this.mView = view;
         this.mErrorMsg = errorMsg;
    }

###### 这两个构造函数主要主要是为了统一处理onError的，如果要自定义错误提醒，则可以选择第二个构造函数。


### 六、 Base的使用

#### 1.Base封装了MVP和项目的基类   

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


#### 本文章会根据需要持续更新，建议star收藏，便于查看。也欢迎大家提出更多建议。

