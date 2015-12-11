package com.example.shiozaki.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {


    public BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if(scanRecord.length > 30)
            {
                //iBeacon の場合 6 byte 目から、 9 byte 目はこの値に固定されている。
                if((scanRecord[5] == (byte)0x4c) && (scanRecord[6] == (byte)0x00) &&
                        (scanRecord[7] == (byte)0x02) && (scanRecord[8] == (byte)0x15))
                {
                    String uuid = IntToHex2(scanRecord[9] & 0xff)
                            + IntToHex2(scanRecord[10] & 0xff)
                            + IntToHex2(scanRecord[11] & 0xff)
                            + IntToHex2(scanRecord[12] & 0xff)
                            + "-"
                            + IntToHex2(scanRecord[13] & 0xff)
                            + IntToHex2(scanRecord[14] & 0xff)
                            + "-"
                            + IntToHex2(scanRecord[15] & 0xff)
                            + IntToHex2(scanRecord[16] & 0xff)
                            + "-"
                            + IntToHex2(scanRecord[17] & 0xff)
                            + IntToHex2(scanRecord[18] & 0xff)
                            + "-"
                            + IntToHex2(scanRecord[19] & 0xff)
                            + IntToHex2(scanRecord[20] & 0xff)
                            + IntToHex2(scanRecord[21] & 0xff)
                            + IntToHex2(scanRecord[22] & 0xff)
                            + IntToHex2(scanRecord[23] & 0xff)
                            + IntToHex2(scanRecord[24] & 0xff);

                    String major = IntToHex2(scanRecord[25] & 0xff) + IntToHex2(scanRecord[26] & 0xff);
                    String minor = IntToHex2(scanRecord[27] & 0xff) + IntToHex2(scanRecord[28] & 0xff);
                }
            }
        }
    };

    //intデータを 2桁16進数に変換するメソッド
    public String IntToHex2(int i) {
        char hex_2[] = {Character.forDigit((i>>4) & 0x0f,16),Character.forDigit(i&0x0f, 16)};
        String hex_2_str = new String(hex_2);
        return hex_2_str.toUpperCase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BluetoothManager bluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
        mBluetoothAdapter.startLeScan(mLeScanCallback);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
