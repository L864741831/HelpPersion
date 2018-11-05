package com.tianjistar.helppersion.view;

/**
 * Created by Administrator on 2018/3/21.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoViewHolder {
    //现在对于int作为键的官方推荐用SparseArray替代HashMap
    private final SparseArray<View> views;
    private View convertView;
    private Context context;

    private PhotoViewHolder(Context context, ViewGroup parent, int itemLayoutId, int position) {
        this.context = context;
        this.views = new SparseArray<>();
        this.convertView = LayoutInflater.from(context).inflate(itemLayoutId,parent,false);
        convertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     */
    public static PhotoViewHolder get(Context context,View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new PhotoViewHolder(context,parent, layoutId, position);
        }
        return (PhotoViewHolder) convertView.getTag();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 设置字符串
     */
    public PhotoViewHolder setText(int viewId,String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public PhotoViewHolder setText(int viewId,CharSequence text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }


    /**
     * 设置图片
     */
    public PhotoViewHolder setImageResource(int viewId,int drawableId){
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public PhotoViewHolder setImageDrawable(int viewId, Drawable drawable){
        ImageView iv = getView(viewId);
        iv.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置图片
     */
    public PhotoViewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }


    /**
     * 设置文本颜色
     */
    public PhotoViewHolder setTextColor(int viewId,int color){
        TextView textView = getView(viewId);
        textView.setTextColor(color);
        return this;
    }
}
