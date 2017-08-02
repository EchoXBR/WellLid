package com.speedata.welllid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.johdan.paint.R;
import com.speedata.ui.adapter.CommonAdapter;
import com.speedata.ui.adapter.ViewHolder;
import com.speedata.welllid.bean.DialogShowMsg;
import com.speedata.welllid.bean.JgDevice;
import com.speedata.welllid.bean.JgTrouble;
import com.speedata.welllid.bean.Result;
import com.speedata.welllid.bean.User;
import com.speedata.welllid.callback.ResultInter;
import com.speedata.welllid.net.Global;
import com.speedata.welllid.net.NetManager;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ResultInter {

    private CommonAdapter<JgTrouble> adapter;
    private List<JgTrouble> jgTroubles = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tvTitle = findViewById(R.id.textView);
        User user = AppSpd.getInstance().getUser();
        tvTitle.setText("北京市政  |  " + user.getCompanyName() + "  |  " + user.getLoginName());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InitDevActivity.class));
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        listView = findViewById(R.id.list_item);
        adapter = new CommonAdapter<JgTrouble>(this, jgTroubles, R.layout.adapter_jgtroubles) {
            @Override
            public void convert(ViewHolder helper, JgTrouble item) {
                helper.setText(R.id.tv_1, item.getTroubleCode());
                helper.setText(R.id.tv_2, item.getAddress());
                helper.setText(R.id.tv_3, item.getCreateTime().toString());
                helper.setText(R.id.tv_4, item.getDescription());
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                JgTrouble trouble = jgTroubles.get(i);
               AppSpd.getInstance().setTrouble(trouble);
                startActivity(new Intent(MainActivity.this, MalfunctionDetialActivity.class));
            }
        });
        NetManager.getWaitRepair(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getResult(Result loginResult) {
        if (loginResult.isSuccess()) {
//String listdata=loginResult.get
            Map<String, Object> data = loginResult.getData();
            Object list = (data.get("list"));
            System.out.println("===list=" + list);
            List<JgTrouble> jgDevice = JSON.parseArray(String.valueOf(list), JgTrouble.class);
            jgTroubles.clear();
            jgTroubles.addAll(jgDevice);
            adapter.notifyDataSetChanged();
        }


    }


}
