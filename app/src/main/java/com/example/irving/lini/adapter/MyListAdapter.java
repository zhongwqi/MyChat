package com.example.irving.lini.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.irving.lini.R;
import com.example.irving.lini.bean.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Irving on 2018/5/3.
 */

public class MyListAdapter extends BaseAdapter {

    private List<ChatMessage> mChatMessages;
    private LayoutInflater inflater;
    public MyListAdapter(Context context,List<ChatMessage> mChatMessages){
        this.mChatMessages = mChatMessages;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mChatMessages.get(position);
        if(chatMessage.getType() == ChatMessage.Type.INCOMING){
            return 0;
        }else{
            return 1;
        }
    }

    /**
     * 返回视图的种类数目
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mChatMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return mChatMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatMessage chatMessage = mChatMessages.get(position);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder();
        if(convertView == null){
            if(chatMessage.getType() == ChatMessage.Type.INCOMING){
                convertView = inflater.inflate(R.layout.friend_chat_layout,parent,false);
                viewHolder.mDate = (TextView) convertView.findViewById(R.id.text_friend_date);
                viewHolder.mMsg = (TextView) convertView.findViewById(R.id.text_friend_msg);
            }else{
                convertView = inflater.inflate(R.layout.me_chat_layout,parent,false);
                viewHolder.mDate = (TextView) convertView.findViewById(R.id.text_me_date);
                viewHolder.mMsg = (TextView) convertView.findViewById(R.id.text_me_msg);
            }
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示数据
        viewHolder.mMsg.setText(chatMessage.getMsg());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
        viewHolder.mDate.setText( format.format(chatMessage.getDate()));
        return convertView;
    }

    static class ViewHolder{
        TextView mMsg;
        TextView mDate;
    }
}
