package fxjwsq.taiji.com.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import fxjwsq.taiji.com.sample.Fragments.BannerFgt;

public class AdvLoopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_loop);

        ArrayList<String> mList = new ArrayList<>();
        mList.add("http://img3.imgtn.bdimg.com/it/u=762492323,13541956&fm=23&gp=0.jpg");
        mList.add("http://img5.imgtn.bdimg.com/it/u=3108519965,2573780878&fm=23&gp=0.jpg");
        mList.add("http://s4.mogujie.cn/b2/pic/111108/1ja9v_kqywursfkrbegv3wgfjeg5sckzsew_510x329.jpg_320x999.jpg");
        mList.add("http://img1.imgtn.bdimg.com/it/u=3242985389,2051307072&fm=21&gp=0.jpg");
        mList.add("http://img3.imgtn.bdimg.com/it/u=2062958349,2721437566&fm=23&gp=0.jpg");
        mList.add("http://img3.imgtn.bdimg.com/it/u=3092837035,4091327364&fm=23&gp=0.jpg");
        mList.add("http://img1.imgtn.bdimg.com/it/u=1191454288,687905211&fm=23&gp=0.jpg");

        //在实际的项目因为很多地方会用到banner广告栏，所以这里封装起来，
        //我们统一使用一个fragment来代替，
        //使用的时候只需要在布局中写好UI给的尺寸大小，并且在Bannder中setArgment中传入一个banner的集合就可以了
        BannerFgt bannerFgt = new BannerFgt();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("mList", mList);
        bannerFgt.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_banner, bannerFgt).commit();
    }
}
