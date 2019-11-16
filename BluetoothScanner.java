package com.example.max.ltproto;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BluetoothScanner {
     boolean discovery = false;
    IntentFilter Filter;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            switch (action){
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:{
                    discovery = true;
                    break;
                }
                case BluetoothDevice.ACTION_FOUND:{
                    BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String name = device.getName();
                    String address = device.getAddress();

                    String rss = Integer.toString(intent.getIntExtra(BluetoothDevice.EXTRA_NAME, Integer.MIN_VALUE));   //Needs to implement actualization
                    Tag nTag = new Tag(address, name, Tag.VISIBILITY.VISIBLE);
                    items.add(nTag);
                    break;
                }
                default:{
                    break;
                }
            }
        }
    };

    Context context;

    List<Tag> items;
    BluetoothAdapter bluetoothAdapter;

    public List<Tag> getSurroundingTags(){
        discovery = true;
        bluetoothAdapter.startDiscovery();
        while (discovery){ }
        return this.items;
    }

    public BluetoothScanner(Context context){
        this.context = context;
       items = new ArrayList<Tag>();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!bluetoothAdapter.isEnabled())
            bluetoothAdapter.enable();

        Filter = new IntentFilter();
        Filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        Filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        Filter.addAction(BluetoothDevice.ACTION_FOUND);
        Filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        context.registerReceiver(receiver, Filter);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        context.unregisterReceiver(receiver);
    }
}

