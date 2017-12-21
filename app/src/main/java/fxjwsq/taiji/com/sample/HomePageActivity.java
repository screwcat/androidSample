package fxjwsq.taiji.com.sample;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import layout.GuideGridLayout;

public class HomePageActivity extends AppCompatActivity {

    private GuideGridLayout gglayout, gglayout_bottom;
    private int[] draws = {R.drawable.guard_network, R.drawable.key_personnel, R.drawable.sjxf_manager, R.drawable.xyaq_manager, R.drawable.sjcj_manager, R.drawable.offline_baidu_map};
    private String[] titles = {"群防群治", "重点人员", "三级消防", "校园安全", "数据采集", "考勤签到"};
    private String[] backColors = {"#E7F8FF", "#FFBE26", "#0789BB", "#01AEF0", "#EF9A19", "#8ED400"};
    private String[] textColors = {"#3399FE", "#FFBE26", "#0789BB", "#01AEF0", "#EF9A19", "#8ED400"};
    private String titles_bottom[] = {"待办任务"};
    private TextView tvWaitProcessNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        gglayout = (GuideGridLayout) findViewById(R.id.list);
        gglayout_bottom=(GuideGridLayout) findViewById(R.id.list_bottom);
        gglayout.setGridAdapter(new GuideGridLayout.GridAdatper() {

            @Override
            public View getView(int index) {
                View view = getLayoutInflater().inflate(R.layout.guidegrid_item, null);
                ImageView iv = (ImageView) view.findViewById(R.id.iv);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                LinearLayout llIconBack = (LinearLayout) view.findViewById(R.id.llIconBack);
                iv.setImageResource(draws[index]);
                tv.setText(titles[index]);
                tv.setTextColor(Color.parseColor(textColors[index]));
                llIconBack.setBackgroundColor(Color.parseColor(backColors[index]));
                return view;
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });
        gglayout.setOnItemClickListener(new GuideGridLayout.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int index) {
                Toast toast = Toast.makeText(HomePageActivity.this, index+"", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        gglayout_bottom.setGridAdapter(new GuideGridLayout.GridAdatper() {
            @Override
            public View getView(int index) {
                View view = getLayoutInflater().inflate(R.layout.guidegrid_item_bottom, null);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                tvWaitProcessNum = (TextView) view.findViewById(R.id.tvWaitProcessNum);
                tv.setText(titles_bottom[index]);
                return view;
            }

            @Override
            public int getCount() {
                return titles_bottom.length;
            }
        });
        gglayout_bottom.setOnItemClickListener(new GuideGridLayout.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int index) {
                v.setAlpha(1f);
                Toast toast = Toast.makeText(HomePageActivity.this, "跳转到待办任务！！！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }
}
