package utils;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * 广告栏的适配器
 */
public class BannerVpAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> mDatas;
    private final ViewGroup.LayoutParams lp;

    public BannerVpAdapter(Context mContext, ArrayList<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = mDatas.get(position % mDatas.size());

        ImageView img = new ImageView(mContext);
        img.setLayoutParams(lp);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(url).into(img);

        container.addView(img);

        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
