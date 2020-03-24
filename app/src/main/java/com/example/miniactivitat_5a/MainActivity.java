package com.example.miniactivitat_5a;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ConnectivityManager ConManager;
    NetworkInfo activeNetwork;
    TextView twInfo, twState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetworkState();
    }

    public void checkNetworkState(){
        twInfo = findViewById(R.id.textView1);
        ConManager =(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(ConManager != null){                                                                     //Comprovem que el ConnectivityManager sigui diferent de null
            activeNetwork = ConManager.getActiveNetworkInfo();                                      //Mosrem la informació de la xarxa en el textView1
            twInfo.setText(activeNetwork.toString());
            new CheckConnectivityTask().execute();
        }
    }

    private class CheckConnectivityTask extends AsyncTask<Activity, Void, String> {

        @Override
        protected String doInBackground(Activity... activity) {
            String info = "";

            if (activeNetwork != null && activeNetwork.isConnected()) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {                     //Comprovem quin dels estats de la xarx s'esta executant
                    info = (String) getText(R.string.WifiConnection);                               //I afegim aquesta informació a l'string info
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    info = (String) getText(R.string.MobileConnected);
                }
            }else{
                info = (String) getText(R.string.NoConnection);
            }
            return info;                                                                            //Retornem aquest string per tal de mostrarlo en el textView2
        }

        @Override
        protected void onPostExecute(String networkState){      //Una vegada executat el CheckConnectivityTask
            twState = findViewById(R.id.textView2);             //mostrem en el textView2 el missatge corresponent a l'estat de la xarxa
            twState.setText(networkState);
        }
    }
}

