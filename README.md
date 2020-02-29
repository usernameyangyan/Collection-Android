## Collection

![Travis](https://img.shields.io/badge/release-1.2.9-green.svg)
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
>  **implementation 'com.youngman:collectionlibrary:1.2.9**

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
            .inflate(R.layout.layout_definition_anim
