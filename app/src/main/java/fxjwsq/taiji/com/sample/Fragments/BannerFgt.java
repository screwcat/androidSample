package fxjwsq.taiji.com.sample.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import java.util.ArrayList;

import fxjwsq.taiji.com.sample.R;
import utils.BannerVpAdapter;
import utils.UiUtils;


/**
 * 广告轮播的Fragment，这个是写好的，不需要改任务地方
 */
public class BannerFgt extends Fragment implements ViewPager.OnPageChangeListener, View.OnTouchListener {
    private final int MSG_CHANG_POS = 101;

    private View view;
    private ViewPager vp_banner;
    private LinearLayout linear_dot;

    private ArrayList<String> mList;

    //轮播的间隔时间
    private long mChangeTime = 3000;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CHANG_POS:
                    vp_banner.setCurrentItem(vp_banner.getCurrentItem() + 1);
                    mHandler.sendEmptyMessageDelayed(MSG_CHANG_POS, mChangeTime);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = UiUtils.inflateXml(getActivity(), R.layout.fgt_banner);

        initView();

        return view;
    }

    private void initView() {
        vp_banner = (ViewPager) view.findViewById(R.id.vp_banner);
        linear_dot = (LinearLayout) view.findViewById(R.id.linear_dot);

        mList = getArguments().getStringArrayList("mList");

        BannerVpAdapter mVpAdapter = new BannerVpAdapter(getActivity(), mList);
        vp_banner.setAdapter(mVpAdapter);

        initVpIndexDot();
        updateIndexDotState();

        vp_banner.setOnPageChangeListener(this);
        vp_banner.setOnTouchListener(this);

    }

    /**
     * 初始化索引的圆点
     */
    private void initVpIndexDot() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(UiUtils.getDimens(R.dimen.x40), UiUtils.getDimens(R.dimen.y40));

        for (int x = 0; x < mList.size(); x++) {
            View view = new View(getActivity());
            //view.setBackground(UiUtils.getDrawable(R.drawable.selector_banner_index_dot));
            view.setBackground(getResources().getDrawable(R.drawable.selector_banner_index_dot));

            if (x != 0) {
                lp.leftMargin = UiUtils.getDimens(R.dimen.x20);
            }
            view.setLayoutParams(lp);
            linear_dot.addView(view);
        }

        //这里设置vp的当前位置是，Integer最大值的中间位置,达到向左向后都可以伪循环
        int middlePos = Integer.MAX_VALUE / 2;
        int pos = middlePos % mList.size();
        vp_banner.setCurrentItem(middlePos - pos);
    }

    /**
     * 让当前位置上的圆点显示常亮
     */
    private void updateIndexDotState() {
        int currentItem = vp_banner.getCurrentItem() % mList.size();

        for (int x = 0; x < linear_dot.getChildCount(); x++) {
            linear_dot.getChildAt(x).setEnabled(currentItem == x);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateIndexDotState();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 当按下某位置图片的时候我们需要停止自动轮播
     * 抬起时重新开始轮播
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHandler.removeMessages(MSG_CHANG_POS);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mHandler.sendEmptyMessageDelayed(MSG_CHANG_POS, mChangeTime);
                break;
        }
        return false;
    }
}
