package com.cdass.bluetooth02


import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cdass.bluetooth02.databinding.ActivityMainBinding


class MainActivity:AppCompatActivity(){

    lateinit var bluetoothManager: BluetoothManager
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var receiver : BluetoothReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as  BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        
        enableDisableBT()

        receiver = BluetoothReceiver()
        
        binding.btDiscoverability.setOnClickListener {

        }
    }

    @Suppress("DEPRECATION")
    private fun enableDisableBT() {
        when{
            ContextCompat.checkSelfPermission(this,android.Manifest.permission.BLUETOOTH_CONNECT)==PackageManager.PERMISSION_GRANTED->{

            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.BLUETOOTH_CONNECT)->{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT),101)
            }
        }
        binding.btOnOff.setOnClickListener {
            if (!bluetoothAdapter.isEnabled){
                bluetoothAdapter.enable()
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivity(intent)

                val intentFilter= IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
                registerReceiver(receiver,intentFilter)
            }
            if(bluetoothAdapter.isEnabled){
                bluetoothAdapter.disable()

                val intentFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
                registerReceiver(receiver,intentFilter)
            }
        }

    }


}