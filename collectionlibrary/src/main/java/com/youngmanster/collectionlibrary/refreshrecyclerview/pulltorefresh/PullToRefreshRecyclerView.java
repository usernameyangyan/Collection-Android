package com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.youngmanster.collectionlibrary.R;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.refreshview.BaseLoadMoreView;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.refreshview.BasePullToRefreshView;
import com.youngmanster.collectionlibrary.refreshrecyclerview.defaultview.DefaultArrowRefreshHeaderView;
import com.youngmanster.collectionlibrary.refreshrecyclerview.defaultview.DefaultLoadMoreView;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义下拉刷新、加载更多
 * 增加Header
 * Created by yangyan
 * on 2018/3/9.
 */

public class PullToRefreshRecyclerView extends RecyclerView {

	//是否允许刷新
	private boolean isAllowRefresh = false;
	//是否允许加载更多
	private boolean isAllowLoadMore = false;
	//是否正在加载数据
	private boolean isLoadingData = false;
	//是否没有更多数据
	private boolean isNoMoreDate = false;
	//是否为空布局
	private boolean isEmptyView = false;
	//头部布局列表
	private ArrayList<View> mHeaderViews = new ArrayList<>();
	//每个header必须有不同的type,不然滚动的时候顺序会变化
	private static List<Integer> headerTypes = new ArrayList<>();
	//设置一个很大的数字
	private static final int TYPE_REFRESH_HEADER = 20000;
	private static final int TYPE_LOADMORE_FOOTER = 20001;
	private static final int TYPE_EMPTY_VIEW = 20002;
	private static final int HEADER_INIT_INDEX = 20003;
	//刷新加载更多监听
	private OnRefreshAndLoadMoreListener mLoadingListener;
	//设置头部底部View的适配器
	public HeaderAndFooterAdapter mHeaderAndFooterAdapter;
	private Adapter insideAdapter;
	private AdapterDataObserver mDataObserver = new HeaderAndFooterAdapterDataObserver();

	//默认下拉刷新头布局、空布局
	private BasePullToRefreshView headerRefreshView;
	private BaseLoadMoreView loadMoreView;
	private View emptyView;

	//触摸参数初始化
	private float mLastY = -1;
	private static final float DRAG_RATE = 1.5f;

	public PullToRefreshRecyclerView(Context context) {
		super(context);
		init(context);
	}

	public PullToRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PullToRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	/**
	 * 初始化下拉刷新、上拉加载更多的状态显示View
	 */
	private void init(Context context) {
		//初始化头部刷新布局
		headerRefreshView = new DefaultArrowRefreshHeaderView(context);
		headerRefreshView.setRefreshTimeVisible(true);
		loadMoreView = new DefaultLoadMoreView(context);
	}


	/**
	 * 增加头部布局
	 * 暂时只能添加一个头布局
	 *
	 * @param view
	 */
	public void addHeaderView(View view) {
		if (mHeaderViews == null || headerTypes == null)
			return;
		if (mHeaderViews.size() == 1)
			return;
		headerTypes.add(HEADER_INIT_INDEX + mHeaderViews.size());
		mHeaderViews.add(view);
		if (mHeaderAndFooterAdapter != null) {
			mHeaderAndFooterAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 设置自定义的加载布局和空布局
	 *
	 * @param emptyView
	 */
	public void setEmptyView(View emptyView) {
		this.emptyView = emptyView;
		//空布局
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.emptyView.setLayoutParams(lp);
	}

	/**
	 * 自定义刷新View
	 *
	 * @param refreshView
	 */
	public void setRefreshView(BasePullToRefreshView refreshView) {
		this.headerRefreshView = refreshView;
	}

	/**
	 * 自定义加载View
	 *
	 * @param loadMoreView
	 */
	public void setLoadMoreView(BaseLoadMoreView loadMoreView) {
		this.loadMoreView = loadMoreView;
	}

	/**
	 * 没有更多数据可加载
	 */
	public void setNoMoreDate(boolean noMore) {
		isLoadingData = false;
		isNoMoreDate = noMore;
		loadMoreView.setState(isNoMoreDate ? BaseLoadMoreView.STATE_NODATA : BaseLoadMoreView.STATE_COMPLETE);
		if (noMore) {
			insideAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 自动加载
	 */
	public void setAutoRefresh() {
		if (isAllowRefresh && mLoadingListener != null) {
			if(!isLoading()&&!isRefreshing()){
				headerRefreshView.onStateChangeListener.onStateChange(BasePullToRefreshView.STATE_REFRESHING);
				mLoadingListener.onRecyclerViewRefresh();
				this.scrollToPosition(0);
			}
		}
	}

	/**
	 * 刷新数据完成
	 */
	public void refreshComplete() {
		if (headerRefreshView != null)
			headerRefreshView.refreshComplete();

		mDataObserver.onChanged();
		setNoMoreDate(false);
	}

	/**
	 * 加载数据完成
	 */
	public void loadMoreComplete() {
		isLoadingData = false;
		loadMoreView.setState(BaseLoadMoreView.STATE_COMPLETE);
		insideAdapter.notifyDataSetChanged();
	}

	/**
	 * 是否允许刷新
	 *
	 * @param enabled
	 */
	public void setPullRefreshEnabled(boolean enabled) {
		isAllowRefresh = enabled;
	}

	/**
	 * 是否允许加载更多
	 *
	 * @param enabled
	 */
	public void setLoadMoreEnabled(boolean enabled) {
		isAllowLoadMore = enabled;
		if (!enabled) {
			loadMoreView.setState(BaseLoadMoreView.STATE_COMPLETE);
		}
	}

	/**
	 * 显示加载更新时间
	 */
	public void setRefreshTimeVisible(boolean isShow) {
		if (headerRefreshView != null)
			headerRefreshView.setRefreshTimeVisible(isShow);
	}


	@Override
	public void setAdapter(Adapter adapter) {
		this.insideAdapter = adapter;
		mHeaderAndFooterAdapter = new HeaderAndFooterAdapter(adapter);
		super.setAdapter(mHeaderAndFooterAdapter);
		adapter.registerAdapterDataObserver(mDataObserver);
		mDataObserver.onChanged();
	}

	/**
	 * 保证GridLayoutManager加载更多和headView占一行
	 *
	 * @param layout
	 */
	@Override
	public void setLayoutManager(LayoutManager layout) {
		super.setLayoutManager(layout);
		if (mHeaderAndFooterAdapter != null) {
			if (layout instanceof GridLayoutManager) {
				final GridLayoutManager gridManager = ((GridLayoutManager) layout);
				gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
					@Override
					public int getSpanSize(int position) {
						return (mHeaderAndFooterAdapter.isHeader(position) || mHeaderAndFooterAdapter.isFooter(position) || mHeaderAndFooterAdapter.isRefreshHeader(position))
								? gridManager.getSpanCount() : 1;
					}
				});
			}
		}
	}

	/**
	 * =======================================================滚动监听 =======================================================
	 */
	@Override
	public void onScrollStateChanged(int state) {
		super.onScrollStateChanged(state);
		if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null && !isLoadingData && isAllowLoadMore && !isNoMoreDate) {
			LayoutManager layoutManager = getLayoutManager();
			int lastVisibleItemPosition;
			if (layoutManager instanceof GridLayoutManager) {
				lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
			} else if (layoutManager instanceof StaggeredGridLayoutManager) {
				int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
				((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
				lastVisibleItemPosition = findMax(into);
			} else {
				lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
			}

			int headerAndFooterAdapterItemCount = layoutManager.getItemCount() + getHeadersCount();
			int status = BasePullToRefreshView.STATE_DONE;

			if (headerRefreshView != null)
				status = headerRefreshView.getState();

			//是否满屏
			if (PullToRefreshRecyclerViewUtils.isFullPage(this, lastVisibleItemPosition)) {
				if (layoutManager.getChildCount() > 0 && lastVisibleItemPosition >= headerAndFooterAdapterItemCount - 2
						&& headerAndFooterAdapterItemCount >= layoutManager.getChildCount()
						&& status < BasePullToRefreshView.STATE_REFRESHING) {
					isLoadingData = true;
					loadMoreView.setState(BaseLoadMoreView.STATE_LOADING);
					mLoadingListener.onRecyclerViewLoadMore();
				}
			}
		}
	}


	private int findMax(int[] lastPositions) {
		int max = lastPositions[0];
		for (int value : lastPositions) {
			if (value > max) {
				max = value;
			}
		}
		return max;
	}


	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastY = ev.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				final float deltaY = ev.getRawY() - mLastY;
				mLastY = ev.getRawY();
				if (isOnTop() && isAllowRefresh) {
					if (headerRefreshView == null)
						break;
					if (loadMoreView.getState() != BaseLoadMoreView.STATE_LOADING) {
						headerRefreshView.onMove(deltaY / DRAG_RATE);
					}
					if (headerRefreshView.getVisibleHeight() > 0 && headerRefreshView.getState() < BasePullToRefreshView.STATE_REFRESHING) {
						return false;
					}
				}
				break;
			default:
				mLastY = -1; // reset
				if (isOnTop() && isAllowRefresh) {
					if (headerRefreshView != null && headerRefreshView.dealReleaseAction()) {
						if (mLoadingListener != null) {
							mLoadingListener.onRecyclerViewRefresh();
						}
					}
				}
				break;
		}

		int status = BasePullToRefreshView.STATE_DONE;
		if (headerRefreshView != null)
			status = headerRefreshView.getState();

		if (status == BasePullToRefreshView.STATE_REFRESHING) {
			return true;
		} else {
			return super.onTouchEvent(ev);
		}
	}

	private boolean isOnTop() {
		if (headerRefreshView == null)
			return false;
		if (headerRefreshView.getParent() != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否正在loading数据
	 *
	 * @return
	 */
	public boolean isLoading() {
		if (loadMoreView.getState() == BaseLoadMoreView.STATE_LOADING) {
			return true;
		}
		return false;
	}

	/**
	 * 正在refreshing数据
	 */
	public boolean isRefreshing() {
		if (headerRefreshView.getState() == BasePullToRefreshView.STATE_REFRESHING) {
			return true;
		}
		return false;
	}

	/**
	 * ======================================================================================================================================
	 */

	/**
	 * 根据header的ViewType判断是哪个header
	 */
	private View getHeaderViewByType(int itemType) {
		if (!isHeaderType(itemType)) {
			return null;
		}
		if (mHeaderViews == null)
			return null;
		return mHeaderViews.get(itemType - HEADER_INIT_INDEX);
	}

	/**
	 * 判断一个type是否为HeaderType
	 *
	 * @param itemViewType
	 * @return
	 */
	private boolean isHeaderType(int itemViewType) {
		if (mHeaderViews == null || headerTypes == null)
			return false;
		return mHeaderViews.size() > 0 && headerTypes.contains(itemViewType);
	}

	/**
	 * 判断内部adapter的type是否跟定义的头部和底部itemtype一致
	 *
	 * @param itemViewType
	 * @return
	 */
	private boolean isDefinitionWithSame(int itemViewType) {
		if (itemViewType == TYPE_REFRESH_HEADER || itemViewType == TYPE_LOADMORE_FOOTER
				|| itemViewType == TYPE_EMPTY_VIEW || headerTypes.contains(itemViewType)) {
			return true;
		} else {
			return false;
		}
	}


	/***
	 * 包装头部和底部的数据通知
	 */
	private class HeaderAndFooterAdapterDataObserver extends AdapterDataObserver {
		@Override
		public void onChanged() {
			if (mHeaderAndFooterAdapter != null) {
				mHeaderAndFooterAdapter.notifyDataSetChanged();
			}
			isShowEmptyView();
		}

		@Override
		public void onItemRangeInserted(int positionStart, int itemCount) {
			mHeaderAndFooterAdapter.notifyItemRangeInserted(positionStart, itemCount);
		}

		@Override
		public void onItemRangeChanged(int positionStart, int itemCount) {
			mHeaderAndFooterAdapter.notifyItemRangeChanged(positionStart, itemCount);
		}

		@Override
		public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
			mHeaderAndFooterAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
		}

		@Override
		public void onItemRangeRemoved(int positionStart, int itemCount) {
			mHeaderAndFooterAdapter.notifyItemRangeRemoved(positionStart, itemCount);
		}

		@Override
		public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
			mHeaderAndFooterAdapter.notifyItemMoved(fromPosition, toPosition);
		}
	}

	/**
	 * 配合BasePullToRefreshAdapter使用，用来获取除去头部View之后对应的item
	 *
	 * @return
	 */
	public int getRealItemCount() {
		int itemCount;
		if (isAllowRefresh) {
			itemCount = getHeadersCount() + 1;
		} else {
			itemCount = getHeadersCount();
		}
		return itemCount;
	}


	public int getHeadersCount() {
		if (mHeaderViews == null)
			return 0;
		return mHeaderViews.size();
	}

	private void isShowEmptyView() {
		int emptyCount;
		if (isAllowRefresh) {
			emptyCount = 1 + getHeadersCount();
		} else {
			emptyCount = getHeadersCount();
		}

		if (isAllowLoadMore) {
			emptyCount++;
		}

		if(emptyView!=null){
			if (mHeaderAndFooterAdapter.getItemCount() == emptyCount) {
				emptyView.setVisibility(VISIBLE);
				isEmptyView = true;
			} else {
				emptyView.setVisibility(GONE);
				isEmptyView = false;
			}
		}
	}

	/***
	 * 包装头部布局和顶部布局的外层adapter
	 */
	private class HeaderAndFooterAdapter extends Adapter<ViewHolder> {

		private Adapter adapter;

		public HeaderAndFooterAdapter(Adapter adapter) {
			this.adapter = adapter;
		}

		public boolean isHeader(int position) {
			if (mHeaderViews == null)
				return false;
			if (isAllowRefresh) {
				return position >= 1 && position < mHeaderViews.size() + 1;
			} else {
				return position >= 0 && position < mHeaderViews.size();
			}

		}

		public boolean isFooter(int position) {
			if (isAllowLoadMore) {
				return position == getItemCount() - 1;
			} else {
				return false;
			}
		}

		public boolean isRefreshHeader(int position) {
			if (isAllowRefresh) {
				return position == 0;
			} else {
				return false;
			}

		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

			if (viewType == TYPE_REFRESH_HEADER) {
				return new HeaderAndFooterViewHolder(headerRefreshView);
			} else if (isHeaderType(viewType)) {
				return new HeaderAndFooterViewHolder(getHeaderViewByType(viewType));
			} else if (viewType == TYPE_LOADMORE_FOOTER) {
				loadMoreView.setVisibility(GONE);
				return new HeaderAndFooterViewHolder(loadMoreView);
			} else if (viewType == TYPE_EMPTY_VIEW) {
				return new HeaderAndFooterViewHolder(emptyView);
			}

			return adapter.onCreateViewHolder(parent, viewType);
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			if (isEmptyView) {
				return;
			}

			if (isRefreshHeader(position)) {
				return;
			}
			if (isHeader(position)) {
				return;
			}
			int adjPosition = position - (getHeadersCount() + 1);

			int adapterCount;
			if (adapter != null) {
				adapterCount = adapter.getItemCount();
				if (adjPosition < adapterCount) {
					adapter.onBindViewHolder(holder, adjPosition);
				}
			}
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {

			if (isEmptyView) {
				return;
			}

			if (isHeader(position) || isRefreshHeader(position)) {
				return;
			}

			int adjPosition;

			if (isAllowRefresh) {
				adjPosition = position - (getHeadersCount() + 1);
			} else {
				adjPosition = position - getHeadersCount();
			}

			int adapterCount;
			if (adapter != null) {
				adapterCount = adapter.getItemCount();
				if (adjPosition < adapterCount) {
					if (payloads.isEmpty()) {
						adapter.onBindViewHolder(holder, adjPosition);
					} else {
						adapter.onBindViewHolder(holder, adjPosition, payloads);
					}
				}
			}
		}

		@Override
		public int getItemCount() {
			//这里是为了解决空布局的显示和点击事件，逻辑有点乱，需要重新考虑
			if ((!isAllowRefresh && !isAllowLoadMore && isEmptyView)) {
				return 1;
			} else if ((isAllowRefresh && isAllowLoadMore && isEmptyView)) {
				return 1;
			}


			int adjLen;
			if (isAllowRefresh) {
				adjLen = (isAllowLoadMore ? 2 : 1);
			} else {
				adjLen = (isAllowLoadMore ? 1 : 0);
			}

			if (adapter != null) {
				return getHeadersCount() + adapter.getItemCount() + adjLen;
			} else {
				return getHeadersCount() + adjLen;
			}
		}

		@Override
		public int getItemViewType(int position) {
			if (isEmptyView) {
				return TYPE_EMPTY_VIEW;
			}
			int adjPosition;
			if (isAllowRefresh) {
				adjPosition = position - (getHeadersCount() + 1);
			} else {
				adjPosition = position - getHeadersCount();
			}

			if (isRefreshHeader(position)) {
				return TYPE_REFRESH_HEADER;
			}
			if (isHeader(position)) {
				if (isAllowRefresh) {
					position = position - 1;
				}
				return headerTypes.get(position);
			}
			if (isFooter(position)) {
				return TYPE_LOADMORE_FOOTER;
			}
			int adapterCount;
			if (adapter != null) {
				adapterCount = adapter.getItemCount();
				if (adjPosition < adapterCount) {
					int type = adapter.getItemViewType(adjPosition);
					if (isDefinitionWithSame(type)) {
						throw new IllegalStateException("内部adapter的itemType是否跟定义的头部和底部itemType一致，造成报错!");
					}
					return type;
				}
			}
			return 0;
		}

		@Override
		public long getItemId(int position) {

			int count;
			if (isAllowRefresh) {
				count = getHeadersCount() + 1;
			} else {
				count = getHeadersCount();
			}
			if (adapter != null && position >= count) {
				int adjPosition = position - count;
				if (adjPosition < adapter.getItemCount()) {
					return adapter.getItemId(adjPosition);
				}
			}
			return -1;
		}

		@Override
		public void onAttachedToRecyclerView(RecyclerView recyclerView) {
			super.onAttachedToRecyclerView(recyclerView);
			LayoutManager manager = recyclerView.getLayoutManager();
			if (manager instanceof GridLayoutManager) {
				final GridLayoutManager gridManager = ((GridLayoutManager) manager);
				gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
					@Override
					public int getSpanSize(int position) {
						return (isHeader(position) || isFooter(position) || isRefreshHeader(position))
								? gridManager.getSpanCount() : 1;
					}
				});
			}
			adapter.onAttachedToRecyclerView(recyclerView);
		}

		@Override
		public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
			adapter.onDetachedFromRecyclerView(recyclerView);
		}

		@Override
		public void onViewAttachedToWindow(ViewHolder holder) {
			super.onViewAttachedToWindow(holder);
			ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
			if (lp != null
					&& lp instanceof StaggeredGridLayoutManager.LayoutParams
					&& (isHeader(holder.getLayoutPosition()) || isRefreshHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
				StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
				p.setFullSpan(true);
			}
			adapter.onViewAttachedToWindow(holder);
		}

		@Override
		public void onViewDetachedFromWindow(ViewHolder holder) {
			adapter.onViewDetachedFromWindow(holder);
		}

		@Override
		public void onViewRecycled(ViewHolder holder) {
			adapter.onViewRecycled(holder);
		}

		@Override
		public boolean onFailedToRecycleView(ViewHolder holder) {
			return adapter.onFailedToRecycleView(holder);
		}

		@Override
		public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
			adapter.unregisterAdapterDataObserver(observer);
		}

		@Override
		public void registerAdapterDataObserver(AdapterDataObserver observer) {
			adapter.registerAdapterDataObserver(observer);
		}

		private class HeaderAndFooterViewHolder extends ViewHolder {
			public HeaderAndFooterViewHolder(View itemView) {
				super(itemView);
			}
		}
	}


	/**
	 * 设置加载更多监听
	 *
	 * @param listener
	 */
	public void setRefreshAndLoadMoreListener(OnRefreshAndLoadMoreListener listener) {
		mLoadingListener = listener;
	}

	public interface OnRefreshAndLoadMoreListener {

		void onRecyclerViewRefresh();

		void onRecyclerViewLoadMore();
	}

	/**
	 * 防止内存泄漏
	 */
	public void destroy() {
		if (mHeaderViews != null) {
			mHeaderViews.clear();
			mHeaderViews = null;
		}
		if (loadMoreView != null) {
			loadMoreView.destroy();
			loadMoreView = null;
		}
		if (headerRefreshView != null) {
			headerRefreshView.destroy();
			headerRefreshView = null;
		}
	}
}
