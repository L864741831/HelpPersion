package com.tianjistar.helppersion.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.db.DataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> channelList;
    private boolean[] isCheck;
    ViewHolder holder = null;
    //数据库
    DataBase dataBase;

    public GridViewAdapter(Context context, List<String> channelList) {
        this.mContext = context;
        this.channelList = channelList;
        dataBase = new DataBase(context);
        //给数组设置大小,并且全部赋值为false
        if (channelList != null) {
            isCheck = new boolean[channelList.size()];
            for (int i = 0; i < channelList.size(); i++) {
                isCheck[i] = false;
            }
        }
    }

    @Override
    public int getCount() {
        return channelList != null ? channelList.size() : null;
    }

    @Override
    public Object getItem(int position) {
        return channelList != null ? channelList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return channelList != null ? position : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.gridview_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.text);
            holder.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll_competitions);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //给item赋值
        holder.textView.setText(channelList.get(position));
        /*
        数组里面放置的是所有的position的是否选中的状态
        如果当前position的状态是true就是选中状态
        如果当前position的状态是false就不是选中状态
         */
        if (isCheck[position]) {
            holder.mLinearLayout.setBackgroundResource(R.drawable.grid_shap_two);
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.mLinearLayout.setBackgroundResource(R.drawable.grid_shap_one);
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        /**
         * 刚进去就去拿数据库里面以前选中的数据
         * 如果数据库拿到的数据和集合里面取出来的数据一样,说明当前position处于选中状态,
         * 并且数组里面的当前position也要赋值true
         */
//            List<String> choiceChannel = getData();
//            for (int j = 0; j < choiceChannel.size(); j++) {
//                if (channelList.get(position).equals(choiceChannel.get(j))) {
//                    holder.mLinearLayout.setBackgroundResource(R.color.black);
////                holder.mLinearLayout.setBackgroundResource(R.drawable.grid_shap_two);
//                    holder.textView.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
//                    isCheck[position] = true;
//                }
//
//            }

        return convertView;
    }

    /**
     * 改变某一个选项的状态
     * @param post
     */
    public void choiceState(int post) {
        /**
         *  传递过来所点击的position,如果是本身已经是选中状态,就让他变成不是选中状态,
         *  如果本身不是选中状态,就让他变成选中状态
         */
        isCheck[post] = isCheck[post] == true ? false : true;
        if (isCheck[post]) {
            //如果当前position是选中状态的的position也就是当前position是true,就把集合当前position的值存入数据库
            dataBase.insert(channelList.get(post));
        } else {
            //反之则从数据库里面删除
            dataBase.delete(channelList.get(post));
        }
        this.notifyDataSetChanged();
    }

    /**
     * 改变所有的选项框的状态,也就是全选和反选
     * @param type
     */
    public void changeState(int type) {
        if (type == 1) {//反选
            //把数组里面的值全部便利一遍,如果是选中,就变成不选中,如果没选中就变成选中
            for (int i = 0; i < channelList.size(); i++) {
                isCheck[i] = isCheck[i] == true ? false : true;
                if (isCheck[i]) {
                    dataBase.insert(channelList.get(i));
                } else {
                    dataBase.delete(channelList.get(i));
                }
            }
        }
        else if (type == 2) {//全选
            //全部变成选中状态
            for (int i = 0; i < channelList.size(); i++) {
                isCheck[i] = true;
                if (isCheck[i]) {
                    dataBase.insert(channelList.get(i));
                } else {
                    dataBase.delete(channelList.get(i));
                }
            }
        }

        notifyDataSetChanged();

    }

    static class ViewHolder {
        TextView textView;
        LinearLayout mLinearLayout;
    }

    /**
     * 通过查找数据库,拿到里面的数据
     */
    private List<String> getData() {
        List<String> list = new ArrayList<>();
        Cursor query = DataBase.getInstances(mContext).query();
        if (query.moveToFirst()) {
            do {
                String channel = query.getString(query.getColumnIndex("channel"));
                list.add(channel);
            } while (query.moveToNext());
        }
        //关闭查询游标
        query.close();
        return list;
    }

}