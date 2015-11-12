package touch.enyeinteractive.com.touchpressure;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import touch.enyeinteractive.com.touchpressure.PairUtility.GenericDevice;
import touch.enyeinteractive.com.touchpressure.PairUtility.OnScanFinished;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    private TextView mTouchSpecs;
    private TouchViewGroup mTouchArea;
    private AlertDialog mScanningAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTouchSpecs = (TextView) findViewById(R.id.touch_details);
        mTouchArea = (TouchViewGroup) findViewById(R.id.touch_area);
        View v = findViewById(R.id.action_pair);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mScanningAlert != null)
                    return;
                //setup pairing
                AlertDialog.Builder b = new Builder(MainActivity.this);
                b.setMessage("Scanning...");
                mScanningAlert = b.show();
                startPairing();
            }
        });
    }


    private void startPairing() {
        Intent i = new Intent(this, ResultActivity.class);
        startActivity(i);
    }
}

