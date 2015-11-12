package touch.enyeinteractive.com.touchpressure;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class PairUtility {
    private static final String TAG = PairUtility.class.getSimpleName();

    Context mContext;
    List<GenericDevice> devices = new ArrayList<>();
    private final BluetoothAdapter mAdapter;


    public PairUtility(Context context) {
        mContext = context;
        BluetoothManager m = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mAdapter = m.getAdapter();
//        m.openGattServer(context, new StylusGattServer());
//        List<ScanFilter> f = new ArrayList<>();
    }

    public void scan(final OnScanFinished callback) {

        mAdapter.getBluetoothLeScanner().startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                BluetoothDevice d = result.getDevice();
                int rssi = result.getRssi();
                ScanRecord r = result.getScanRecord();
                GenericDevice genericDevice = new GenericDevice(d,rssi,r);
                devices.add(genericDevice);
            }


            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
            }


            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.getBluetoothLeScanner().stopScan(new ScanCallback() {
                    @Override
                    public void onScanResult(int callbackType, ScanResult result) {
                        super.onScanResult(callbackType, result);
                        Log.v(TAG, "onScanResult");
                    }
                });
                callback.onFinished(devices);
            }
        }, TimeUnit.SECONDS.toMillis(10));
    }

    public static class GenericDevice {
        BluetoothDevice device;
        int rssi;
        ScanRecord scanRecord;


        public GenericDevice(BluetoothDevice d, int rssi, ScanRecord r) {
            device = d;
            this.rssi = rssi;
            scanRecord = r;
        }
    }

    public class StylusGattServer extends BluetoothGattServerCallback {

    }

    public interface OnScanFinished {
        public void onFinished(List<GenericDevice> devices);
    }
}

