package fxjwsq.taiji.com.sample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import layout.GuideGridLayout;

public class MultiselectActivity extends AppCompatActivity {
    private GuideGridLayout gglayout;
    private String titles[] = {"治 安", "社 区", "刑 侦", "出入境", "禁 毒", "网 安", "巡 特", "经 侦", "食药侦", "技 侦", "内 保", "凌 河", "消 防", "维 稳", "指挥中心", "国 保"};
    private String jzIds[] = {"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiselect);
        gglayout = (GuideGridLayout) findViewById(R.id.listPoliceCategory);
        gglayout.setGridAdapter(new GuideGridLayout.GridAdatper() {
            @Override
            public View getView(int index) {
                View view = getLayoutInflater().inflate(R.layout.stress_object_realistic_item, null);
                TextView tvJzId = (TextView) view.findViewById(R.id.jzId);
                TextView tvpc = (TextView) view.findViewById(R.id.tvPoliceCategory);
                tvJzId.setText(jzIds[index]);
                tvpc.setText(titles[index]);
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
                TextView tvpc = (TextView) v.findViewById(R.id.tvPoliceCategory);
                if (tvpc.getCurrentTextColor() == -1) {
                    tvpc.setTextColor(Color.parseColor("#349afe"));
                    tvpc.setBackground(getResources().getDrawable(R.drawable.textview_border));
                } else {
                    tvpc.setTextColor(Color.parseColor("#ffffff"));
                    tvpc.setBackgroundColor(Color.parseColor("#349afe"));
                }
            }
        });
    }
}
