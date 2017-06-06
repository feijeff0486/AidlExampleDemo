package com.jeff.aidlexample.startaddremote;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.aidlexampledemo.IAddInterface;

public class MainActivity extends AppCompatActivity {

    private IAddInterface mService;
    private EditText etX;
    private EditText etY;
    private TextView tvResult;
    private Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent intent = new Intent();
        intent.setAction("com.aidlexample.add");
        intent.setPackage("com.jeff.aidlexampledemo");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        etX = (EditText) findViewById(R.id.et_numX);
        etY = (EditText) findViewById(R.id.et_numY);
        tvResult = (TextView) findViewById(R.id.tv_result);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(etX.getText().toString());
                int y = Integer.parseInt(etY.getText().toString());
                if (etX.getText()!=null && etY.getText()!= null) {
                    int result = 0;
                    try {
                        result = mService.add(x, y);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    tvResult.setText("相加结果为：" + result);
                } else {
                    Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IAddInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };
}
