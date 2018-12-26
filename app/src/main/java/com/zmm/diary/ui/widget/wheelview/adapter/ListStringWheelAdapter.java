package com.zmm.diary.ui.widget.wheelview.adapter;

import android.content.Context;

import java.util.List;

/**
 * The simple Array wheel adapter
 */
public class ListStringWheelAdapter extends AbstractWheelTextAdapter {

    private List<String> list;

    public ListStringWheelAdapter(Context context, List<String> list) {
        super(context);
        this.list = list;
    }
    
    @Override
    public String getItemText(int index) {

        if(list != null && list.size() > 0){
            return list.get(index);
        }

        return null;
    }

    @Override
    public int getItemsCount() {

        if(list != null && list.size() > 0){
            return list.size();
        }else {
            return 0;
        }

    }
}
