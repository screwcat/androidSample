package utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import utils.BaseApplication;


/**
 * ui操作的工具类
 */
public class UiUtils {

    /**
     * 获取尺寸
     */
    public static int getDimens(int id) {

        return (int) BaseApplication.getInstance().getResources().getDimension(id);
    }

    /**
     * 获取Drawable
     */
    public static Drawable getDrawable(int id) {

        return BaseApplication.getInstance().getResources().getDrawable(id);
    }

    /**
     * 解析一个布局文件
     */
    public static View inflateXml(Context context, int layoutId) {
        return View.inflate(context, layoutId, null);
    }
}
