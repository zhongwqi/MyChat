package com.example.irving.lini;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.irving.lini.adapter.MyListAdapter;
import com.example.irving.lini.bean.ChatMessage;
import com.example.irving.lini.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private EditText ed_msg_send;
    private Button btn_msg_send;
    private ListView listView;
    private List<ChatMessage> mChatMessages;
    private MyListAdapter mAdapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChatMessage chatMessage = (ChatMessage) msg.obj;
            mChatMessages.add(chatMessage);
            mAdapter.notifyDataSetChanged();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题 5.0以下
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //实现全屏 5.0以下
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_main);

        initView();
        initDate();
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        btn_msg_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String toMsg = ed_msg_send.getText().toString();
                if(TextUtils.isEmpty(toMsg)){
                    Toast.makeText(MainActivity.this,"不能发送空的消息",Toast.LENGTH_SHORT).show();
                    return;
                }

                ChatMessage meChatMessage = new ChatMessage();
                meChatMessage.setMsg(toMsg);
                meChatMessage.setDate(new Date());
                meChatMessage.setType(ChatMessage.Type.OUTCOMING);
                mChatMessages.add(meChatMessage);
                mAdapter.notifyDataSetChanged();

                ed_msg_send.setText("");

                //子线程中接收数据
                new Thread(){
                    @Override
                    public void run() {
                        ChatMessage friendChatMessage = HttpUtils.sendMessage(toMsg);
                        Message message = Message.obtain();
                        message.obj = friendChatMessage;
                        handler.sendMessage(message);
                    }
                }.start();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        mChatMessages = new ArrayList<ChatMessage>();
        //测试
//        mChatMessages.add(new ChatMessage("你好",new Date(), ChatMessage.Type.INCOMING));
//        mChatMessages.add(new ChatMessage("你好哦",new Date(), ChatMessage.Type.OUTCOMING));
        mChatMessages.add(new ChatMessage("你好,小慕为你服务！",new Date(), ChatMessage.Type.INCOMING));
//        View viewHead = View.inflate(this,R.layout.chat_title,null);
//        listView.addHeaderView(viewHead);
//        View viewBottom = View.inflate(this,R.layout.chat_bottom,null);
//        listView.addFooterView(viewBottom);
        mAdapter = new MyListAdapter(this,mChatMessages);
        listView.setAdapter(mAdapter);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        ed_msg_send = (EditText) findViewById(R.id.ed_msg_send);
        btn_msg_send = (Button) findViewById(R.id.btn_msg_send);
        listView = (ListView) findViewById(R.id.listView);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("您确定要退出吗")
                    .setNegativeButton("取消",null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    }).show();
        }

        return super.onKeyDown(keyCode, event);
    }
}