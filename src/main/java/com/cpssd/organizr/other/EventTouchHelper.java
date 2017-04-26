package com.cpssd.organizr.other;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.cpssd.organizr.adapters.EventAdapter;


public class EventTouchHelper extends ItemTouchHelper.SimpleCallback {
    private EventAdapter mLectureAdapter;

    public EventTouchHelper(EventAdapter events){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mLectureAdapter = events;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            mLectureAdapter.remove(viewHolder.getAdapterPosition());

    }
}
