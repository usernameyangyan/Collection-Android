package com.youngmanster.collectionlibrary.refreshrecyclerview.base.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * 拖拽的Base，自定义可以继承该类，然后重写相对应的方法，写自己的逻辑
 * Created by yangyan.
 */
public class BaseRecycleItemTouchHelper extends ItemTouchHelper.Callback{
    public ItemTouchHelperCallback helperCallback;

    public BaseRecycleItemTouchHelper(ItemTouchHelperCallback helperCallback) {
        this.helperCallback = helperCallback;
    }

    /**
     * 设置滑动类型标记
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //START  右向左 END左向右 LEFT  向左 RIGHT向右  UP向上
        //如果某个值传0，表示不触发该操作
        return makeMovementFlags(ItemTouchHelper.UP| ItemTouchHelper.DOWN|ItemTouchHelper.START|ItemTouchHelper.END, ItemTouchHelper.START|ItemTouchHelper.END);
    }
    /**
     * Item是否支持长按拖动
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }
    /**
     * Item是否支持滑动
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }
    /**
     * 拖拽切换Item的回调
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        helperCallback.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }
    /**
     * 滑动Item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        helperCallback.onItemDelete(viewHolder.getAdapterPosition());
    }

    /**
     * Item被选中时候回调
     *
     * @param viewHolder
     * @param actionState
     *          当前Item的状态
     *          ItemTouchHelper.ACTION_STATE_IDLE   闲置状态
     *          ItemTouchHelper.ACTION_STATE_SWIPE  滑动中状态
     *          ItemTouchHelper#ACTION_STATE_DRAG   拖拽中状态
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState==ItemTouchHelper.ACTION_STATE_DRAG){//点击可拖拽时的的背景色
            helperCallback.onItemSelected();
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        helperCallback.onItemFinish();
//        viewHolder.itemView.setBackgroundColor(0);
    }


    /**
     * 移动过程中绘制Item
     *
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     *          X轴移动的距离
     * @param dY
     *          Y轴移动的距离
     * @param actionState
     *          当前Item的状态
     * @param isCurrentlyActive
     *          如果当前被用户操作为true，反之为false
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public interface ItemTouchHelperCallback{
        void onItemDelete(int position);
        void onItemMove(int fromPosition, int toPosition);
        void onItemSelected();
        void onItemFinish();
    }
}
