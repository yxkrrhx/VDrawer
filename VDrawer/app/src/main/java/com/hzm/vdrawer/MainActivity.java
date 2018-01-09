package com.hzm.vdrawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected TextView open;
    protected TextView close;
    protected VDrawerLayout vd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_app);
        initView();
    }

    private void initView() {

        vd = (VDrawerLayout) findViewById(R.id.vd);
        open = (TextView) findViewById(R.id.open);
        close = (TextView) findViewById(R.id.close);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vd.openDrawer();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vd.closeDrawer();
            }
        });


    }
}
