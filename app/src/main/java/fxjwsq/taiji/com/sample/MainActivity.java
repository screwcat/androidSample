package fxjwsq.taiji.com.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    private GridView gvMenu;
    private SimpleAdapter menuAdapter;
    private ArrayList<HashMap<String, Object>> iconItem = new ArrayList<HashMap<String, Object>>();
    int[] draws = {R.drawable.icon01, R.drawable.icon02, R.drawable.icon03, R.drawable.icon04, R.drawable.icon05, R.drawable.icon06, R.drawable.icon07, R.drawable.icon08, R.drawable.icon09, R.drawable.icon10, R.drawable.icon11, R.drawable.icon12};
    String[] titles = {"各种控件", "约束布局", "列表", "照片采集", "视频", "TAB标签", "离线地图", "登录", "首页","多选","广告轮播","HTML5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gvMenu = findViewById(R.id.gvMenu);

        for (int i = 0; i < draws.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", draws[i]);
            map.put("textImage", titles[i]);
            iconItem.add(map);
        }
        menuAdapter = new SimpleAdapter(this, iconItem, R.layout.griditem_addpic, new String[]{"itemImage", "textImage"}, new int[]{R.id.iconPic, R.id.iconName});
        gvMenu.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();
        gvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast toast = Toast.makeText(MainActivity.this, i+"", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent();
                switch(i){
                    case 0:{
                        intent.setClass(MainActivity.this, ControlsActivity.class);
                        break;
                    }
                    case 1:{
                        intent.setClass(MainActivity.this, LayoutsActivity.class);
                        break;
                    }
                    case 2:{
                        intent.setClass(MainActivity.this,ListActivity.class);
                        break;
                    }
                    case 3:{
                        intent.setClass(MainActivity.this, PhotoCollectionActivity.class);
                        break;
                    }
                    case 4:{
                        intent.setClass(MainActivity.this, VideoActivity.class);
                        break;
                    }
                    case 5:{
                        intent.setClass(MainActivity.this, TABLabelActivity.class);
                        break;
                    }
                    case 6:{
                        intent.setClass(MainActivity.this, BaiduMapActivity.class);
                        break;
                    }
                    case 7:{
                        intent.setClass(MainActivity.this, LoginActivity.class);
                        break;
                    }
                    case 8:{
                        intent.setClass(MainActivity.this, HomePageActivity.class);
                        break;
                    }
                    case 9:{
                        intent.setClass(MainActivity.this, MultiselectActivity.class);
                        break;
                    }
                    case 10:{
                        intent.setClass(MainActivity.this, AdvLoopActivity.class);
                        break;
                    }
                    case 11:{
                        intent.setClass(MainActivity.this, Html5Activity.class);
                        break;
                    }
                }
                startActivityForResult(intent, 0);
            }

        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Toast toast = Toast.makeText(MainActivity.this, "返回首页！！！！", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
