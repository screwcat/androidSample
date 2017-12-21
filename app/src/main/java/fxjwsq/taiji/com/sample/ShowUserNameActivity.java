package fxjwsq.taiji.com.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import layout.GuideGridLayout;

public class ShowUserNameActivity extends AppCompatActivity {

    private TextView textview1;
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_name);
        textview1 = findViewById(R.id.tvUserName);
        btn2 = findViewById(R.id.btn2);
        String userName = getIntent().getStringExtra("userName");
        textview1.setText("你好，" + userName);
//        Intent intent = new Intent();
//        intent.putExtra("returnMsg", textview1.getText());
//        setResult(RESULT_OK, intent);
//        this.finish();
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
