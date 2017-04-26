package com.cpssd.organizr.other;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.cpssd.organizr.adapters.LectureAdapter;


public class LectureTouchHelper extends ItemTouchHelper.SimpleCallback {
    private LectureAdapter mLectureAdapter;

    public LectureTouchHelper(LectureAdapter lectures){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mLectureAdapter = lectures;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(direction == ItemTouchHelper.RIGHT) {
            mLectureAdapter.remove(viewHolder.getAdapterPosition());
        }
        else{

        }
    }
}
