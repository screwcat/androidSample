package fxjwsq.taiji.com.sample;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import utils.ImageCompressUtil;
import utils.StringUtil;

public class PhotoCollectionActivity extends AppCompatActivity {

    private GridView gvTaskPic;
    private ArrayList<HashMap<String, Object>> imageItem;
    private Bitmap bmp;
    private SimpleAdapter taskPicAdapter;
    private final int IMAGE_OPEN = 1;
    private String releaseTask_jpg;
    private Uri imageUri;
    private final int TAKE_PHOTO = 3;
    private String path;
    private final int SUBMIT_TASK = 4;
    private Switch switchCompress;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_collection);
        switchCompress = findViewById(R.id.switchCompress);

        gvTaskPic = (GridView) findViewById(R.id.gvTaskPic);
        initPicGrid();
        gvTaskPic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (imageItem.size() > 7) { //第一张为默认图片
                    Toast.makeText(PhotoCollectionActivity.this, "图片数7张已满", Toast.LENGTH_SHORT).show();
                    gvTaskPic.setMinimumHeight(gvTaskPic.getHeight() + 100);
                } else if (position == imageItem.size() - 1) { //点击图片位置为+ 0对应0张图片
                    AddImageDialog();
                }

            }
        });
        gvTaskPic.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteDialog(position);
                return true;
            }
        });

    }

    private void initPicGrid() {
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic); //加号
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> mapPlus = new HashMap<String, Object>();
        mapPlus.put("itemImage", bmp);
        mapPlus.put("pathImage", "add_pic");
        imageItem.add(mapPlus);
        taskPicAdapter = new SimpleAdapter(PhotoCollectionActivity.this, imageItem, R.layout.griditem_addpic, new String[]{"itemImage"}, new int[]{R.id.iconPic});
        taskPicAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView i = (ImageView) view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gvTaskPic.setAdapter(taskPicAdapter);
        taskPicAdapter.notifyDataSetChanged();
    }

    protected void AddImageDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PhotoCollectionActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        builder.setTitle("添加图片");
        builder.setCancelable(false); //不响应back按钮
        builder.setItems(new String[]{"本地相册选择", "手机相机添加", "取消"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: //本地相册
                                dialog.dismiss();
                                verifyStoragePermissions(PhotoCollectionActivity.this);
                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, IMAGE_OPEN);
                                //通过onResume()刷新数据
                                break;
                            case 1: //手机相机
                                dialog.dismiss();
                                /*File outputImage = new File(StringUtil.getKaoQinQianDaoPath(Tab2Fragment.this.getActivity()), releaseTask_jpg);
                                pathTakePhoto = outputImage.toString();
                                try {
                                    if (outputImage.exists()) {
                                        outputImage.delete();
                                    }
                                    outputImage.createNewFile();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }*/
                                releaseTask_jpg = System.currentTimeMillis() + ".jpg";
                                imageUri = FileProvider.getUriForFile(PhotoCollectionActivity.this, "fxjwsq.taiji.com.sample.fileProvider", new File(StringUtil.getKaoQinQianDaoPath(PhotoCollectionActivity.this), releaseTask_jpg));
                                Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //拍照
                                intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intentPhoto, TAKE_PHOTO);
                                break;
                            case 2: //取消添加
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                });
        //显示对话框
        builder.create().show();
    }

    protected void DeleteDialog(final int position) {
        if (position < imageItem.size() - 1) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PhotoCollectionActivity.this);
            builder.setMessage("确认移除已添加图片吗？");
            builder.setTitle("提示");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    imageItem.remove(position);
                    taskPicAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //打开图片
        if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {
            Uri uri = data.getData();
            //selectPic.setImageURI(uri);
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                //查询选择图片
                Cursor cursor = this.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                //返回 没找到选择图片
                if (null == cursor) {
                    return;
                }
                //光标移动至开头 获取图片路径
                cursor.moveToFirst();
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }
        //拍照
        else if (resultCode == RESULT_OK && requestCode == TAKE_PHOTO) {
            path = StringUtil.getKaoQinQianDaoPath(this) + "/" + releaseTask_jpg;
        } else if (resultCode == RESULT_OK && requestCode == SUBMIT_TASK) {
            //noticeTitle.setText("");
            //noticeContent.setText("");
            initPicGrid();
            return;
        } else {
            return;
        }
        if (switchCompress.isChecked()) {
            try {
                ImageCompressUtil.compressImageByPixel(path);
            } catch (Exception e) {
                Toast toast = Toast.makeText(this, "压缩图片失败：" + e.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }


        Bitmap addbmp = BitmapFactory.decodeFile(path);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", addbmp);
        map.put("pathImage", path);
        imageItem.add(imageItem.size() - 1, map);
        taskPicAdapter = new SimpleAdapter(this, imageItem, R.layout.griditem_addpic, new String[]{"itemImage"}, new int[]{R.id.iconPic});
        //接口载入图片
        taskPicAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView i = (ImageView) view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gvTaskPic.setAdapter(taskPicAdapter);
        taskPicAdapter.notifyDataSetChanged();
//        double rows = imageItem.size() / 4;
//        rows = Math.ceil(rows);
//        gvTaskPic.setMinimumHeight((int) Math.ceil(imageItem.size() / 4) * 100);

        //ImageCompressUtil.compressImageByPixel(path);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
