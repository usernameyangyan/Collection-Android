package com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import com.youngmanster.collectionlibrary.refreshrecyclerview.base.helper.BaseRecycleItemTouchHelper;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;

import java.util.Collections;
import java.util.List;

/**
 * 不同布局Adapter
 * 配了基本的RecyclerView和PullToRefreshRecyclerView
 * Created by yangyan
 * on 2018/3/14.
 */

public abstract class BaseRecyclerViewMultiItemAdapter<T extends BaseMultiItemEntity> extends RecyclerView.Adapter<BaseViewHolder> implements BaseRecycleItemTouchHelper.ItemTouchHelperCallback{

	private SparseIntArray layouts;

	protected Context mContext;
	protected List<T> mDatas;
	protected OnItemClickListener mOnItemClickListener;
	protected onItemLongClickListener onItemLongClickListener;
	protected OnDragAndDeleteListener onDragAndDeleteListener;
	protected PullToRefreshRecyclerView mRecyclerView;

	public BaseRecyclerViewMultiItemAdapter(Context mContext, List<T> mDatas, PullToRefreshRecyclerView pullToRefreshRecyclerView) {
		this.mContext = mContext;
		this.mDatas = mDatas;
		this.mRecyclerView=pullToRefreshRecyclerView;
	}

	public BaseRecyclerViewMultiItemAdapter(Context mContext, List<T> mDatas) {
		this.mContext = mContext;
		this.mDatas = mDatas;
	}

	@Override
	public int getItemViewType(int position) {
		if(!isAddItemView()) return super.getItemViewType(position);

		if(mDatas.get(position) instanceof BaseMultiItemEntity){
			return mDatas.get(position).getItemViewType();
		}else{
			return super.getItemViewType(position);
		}
	}

	@Override
	public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		int layoutId =layouts.get(viewType);
		BaseViewHolder baseViewHolder=BaseViewHolder.onCreateViewHolder(mContext,parent,layoutId);
		setItemClickListener(baseViewHolder);
		return baseViewHolder;
	}

	@Override
	public void onBindViewHolder(BaseViewHolder holder, int position) {
		convert(holder,mDatas.get(position));
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
	}

	protected boolean isAddItemView() {
		return layouts.size() > 0;
	}

	/**
	 * 添加不同布局
	 */
	protected void addItemType(int type,int layoutRes){
		if(this.layouts == null) {
			this.layouts = new SparseIntArray();
		}

		this.layouts.put(type, layoutRes);
	}


	/**
	 * 简化ViewHolder创建
	 * @param baseViewHolder
	 * @param t
	 */
	protected abstract void convert(BaseViewHolder baseViewHolder, T t);


	/**
	 * 获取data
	 * @return
	 */
	public List<T> getDatas(){
		return mDatas;
	}

	/**
	 * item点击事件
	 * @param baseViewHolder
	 */
	protected void setItemClickListener(final BaseViewHolder baseViewHolder){

		baseViewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mOnItemClickListener!=null){
					int position;
					if(mRecyclerView!=null){
						position=baseViewHolder.getAdapterPosition()-mRecyclerView.getRealItemCount();
					}else{
						position=baseViewHolder.getAdapterPosition();
					}

					mOnItemClickListener.onItemClick(v,position);
				}
			}
		});

		baseViewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {

				if(onItemLongClickListener!=null){
					int position;
					if(mRecyclerView!=null){
						position=baseViewHolder.getAdapterPosition()-mRecyclerView.getRealItemCount();
					}else{
						position=baseViewHolder.getAdapterPosition();
					}
					return onItemLongClickListener.onItemLongClick(v,position);
				}
				return false;
			}
		});
	}


	/**
	 * 点击事件监听
	 */
	public interface OnItemClickListener {
		void onItemClick(View view, int position);
	}

	public interface onItemLongClickListener{
		boolean onItemLongClick(View view,int position);
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	public void setOnItemLongClickListener(onItemLongClickListener onItemLongClickListener){
		this.onItemLongClickListener=onItemLongClickListener;
	}

	/**
	 * ========================================拖拽删除=======================================
	 */

	public interface OnDragAndDeleteListener {
		void onDragAndDeleteFinished();
	}

	public void setDragAndDeleteListener(OnDragAndDeleteListener onDragAndDeleteListener){
		this.onDragAndDeleteListener=onDragAndDeleteListener;
	}

	@Override
	public void onItemDelete(int position) {
		mDatas.remove(position);
		notifyItemRemoved(position);
	}

	@Override
	public void onItemMove(int fromPosition, int toPosition) {
		Collections.swap(mDatas,fromPosition,toPosition);//交换数据
		notifyItemMoved(fromPosition,toPosition);
	}

	@Override
	public void onItemSelected() {
	}

	@Override
	public void onItemFinish() {
		if(onDragAndDeleteListener!=null){
			onDragAndDeleteListener.onDragAndDeleteFinished();
		}
	}
}
