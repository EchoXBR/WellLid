package com.speedata.welllid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.testpic.Bimp;
import com.example.testpic.FileUtils;
import com.example.testpic.PublishedActivity;
import com.johdan.paint.R;

import java.io.IOException;

/**
 * Created by echo on 2017/7/26.
 */
@SuppressLint("HandlerLeak")
public class GridAdapter extends BaseAdapter {
    private Context publishedActivity;
    private LayoutInflater inflater; // 视图容器
    private int selectedPosition = -1;// 选中的位置
    private boolean shape;

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public GridAdapter(Context publishedActivity, Context context) {
        this.publishedActivity = publishedActivity;
        inflater = LayoutInflater.from(context);
    }



    public int getCount() {
        return (Bimp.bmp.size() + 1);
    }

    public Object getItem(int arg0) {

        return null;
    }

    public long getItemId(int arg0) {

        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * ListView Item设置
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        final int coord = position;
        ViewHolder holder = null;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_published_grida,
                    parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView
                    .findViewById(R.id.item_grida_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == Bimp.bmp.size()) {
            holder.image.setImageBitmap(BitmapFactory.decodeResource(
                    publishedActivity.getResources(), R.drawable.icon_addpic_unfocused));
            if (position == 9) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            holder.image.setImageBitmap(Bimp.bmp.get(position));
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }


}
