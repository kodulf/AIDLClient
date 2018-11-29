package kodulf.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import kodulf.aidlserver.CalculateInterface;

public class MainActivity extends AppCompatActivity {


    private String TAG="kodulf-client";
    public CalculateInterface mService;
    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
             mService = CalculateInterface.Stub.asInterface(service);
            try {
                Log.d(TAG, "onCreate: "+mService.sum(1.0,2.0));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onServiceConnected:");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisConnected:");
            mService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent  = new Intent();
        intent.setAction("kodulf.aidlserver.CalculateInterface");
        intent.setPackage("kodulf.aidlserver");
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);


    }


}
