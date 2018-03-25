package com.nyc.polymerse;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by Wayne Kellman on 3/25/18.
 */

public class MyDiffCallback extends DiffUtil.Callback {

    private List<User> oldUserList;
    private List<User> newUserList;

    public MyDiffCallback(List<User> oldUserList, List<User> newUserList) {
        this.oldUserList = oldUserList;
        this.newUserList = newUserList;
    }

    @Override
    public int getOldListSize() {
        return oldUserList.size();
    }

    @Override
    public int getNewListSize() {
        return newUserList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldUserList.get(oldItemPosition).getuID().equals(newUserList.get(newItemPosition).getuID());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldUserList.get(oldItemPosition).equals(newUserList.get(newItemPosition));
    }
}
