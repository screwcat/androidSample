package fxjwsq.taiji.com.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private HashMap<String, Object> item = new HashMap<String, Object>();
    private List list = new ArrayList<HashMap<String, Object>>();
    private SimpleAdapter simpleAdapter;
    private ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView1 = findViewById(R.id.listView1);
        for (int i = 0; i < 20; i++) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("number", i + "");
            item.put("name", "姓名" + i);
            item.put("content", java.util.UUID.randomUUID().toString());
            list.add(item);
        }
        simpleAdapter = new SimpleAdapter(ListActivity.this, list, R.layout.keypersonnellistview_item, new String[]{"number", "name", "content"}, new int[]{R.id.objectName, R.id.documentNumber, R.id.cityCodeAddress});
        listView1.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
    }
}