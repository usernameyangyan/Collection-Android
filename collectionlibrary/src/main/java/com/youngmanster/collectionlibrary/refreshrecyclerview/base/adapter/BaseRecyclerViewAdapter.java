package com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.youngmanster.collectionlibrary.refreshrecyclerview.base.helper.BaseRecycleItemTouchHelper;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;

import java.util.Collections;
import java.util.List;


/**
 * 基本的快速适配器
 * 适配了基本的RecyclerView和PullToRefreshRecyclerView
 * Created by yangyan
 * on 2018/3/14.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements BaseRecycleItemTouchHelper.ItemTouchHelperCallback{

	protected Context mContext;
	protected int mLayoutResId;
	protected List<T> mDatas;
	protected OnItemClickListener mOnItemClickListener;
	protected onItemLongClickListener onItemLongClickListener;
	protected OnDragAndDeleteListener onDragAndDeleteListener;
	protected PullToRefreshRecyclerView mRecyclerView;

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

	@Override
	public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		BaseViewHolder baseViewHolder=BaseViewHolder.onCreateViewHolder(mContext,parent,mLayoutResId);
		setItemClickListener(baseViewHolder);
		return baseViewHolder;
	}

	@Override
	public void onBindViewHolder(BaseViewHolder baseViewHolder, int position) {
		convert(baseViewHolder,mDatas.get(position));
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
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
		void onItemClick(View view,int position);
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
