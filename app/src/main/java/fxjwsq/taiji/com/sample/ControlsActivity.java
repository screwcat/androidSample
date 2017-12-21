package fxjwsq.taiji.com.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import utils.ImageUrlFullScreen;
import utils.StringUtil;

public class ControlsActivity extends AppCompatActivity {

    private Button btn1;
    private EditText text1;
    private TextView changeText;
    private ImageView imgClick;

    String imagePath;
    Uri imageUri;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_controls);
        btn1 = findViewById(R.id.btn1);
        text1 = findViewById(R.id.text1);
        changeText = findViewById(R.id.changeText);
        imgClick = findViewById(R.id.imgClick);

        verifyStoragePermissions(ControlsActivity.this);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setClass(ControlsActivity.this, ShowUserNameActivity.class);
                intent1.putExtra("userName", text1.getText().toString());
                startActivityForResult(intent1,1);
            }
        });
        text1.addTextChangedListener(showText);


        String releaseTask_jpg = "IMG_20171028_135636.jpg";
        imagePath = StringUtil.getKaoQinQianDaoPath(ControlsActivity.this) + "/" + releaseTask_jpg;
        imageUri = FileProvider.getUriForFile(ControlsActivity.this, "fxjwsq.taiji.com.sample.fileProvider", new File(StringUtil.getKaoQinQianDaoPath(ControlsActivity.this), releaseTask_jpg));
        Picasso.with(ControlsActivity.this).load(imageUri).into(imgClick);



        imgClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUrlFullScreen.smallImgClickFull(ControlsActivity.this,imagePath,imageUri);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast toast = Toast.makeText(ControlsActivity.this, data.getStringExtra(""), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private TextWatcher showText = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            changeText.setText(text1.getText());
        }
    };

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
