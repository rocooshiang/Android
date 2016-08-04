package rocoo.networkconnection;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    final private String getUrlString = "https://httpbin.org/get";
    final private String postUrlString = "https://httpbin.org/post";
    final private String tag = "rocoo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Networking connection - Get **/
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Global.isConnected(MainActivity.this)) {
                    try {
                        String result = NetworkingConnectionGet.fetchDataFromGet(getUrlString);
                        Log.i(tag, "Get result data: " + result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        /** Networking connection - Post **/
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Global.isConnected(MainActivity.this)) {
                    try {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("Account", "Rocoo");
                        map.put("Password", "Test");
                        String result = NetworkingConnectionPost.fetchDataFromPost(postUrlString, map);
                        Log.i(tag, "Post result data: " + result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        /** Networking connection - Soap **/
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Global.isConnected(MainActivity.this)) {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

}


