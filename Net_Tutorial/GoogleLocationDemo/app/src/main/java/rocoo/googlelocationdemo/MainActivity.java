package rocoo.googlelocationdemo;



import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.support.design.widget.Snackbar;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;


import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener{

    private String TAG = "Rocoo";

    private int REQUEST_ACCESS_FINE_LOCATION;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private View mLayout;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = findViewById(R.id.sample_output);

        REQUEST_ACCESS_FINE_LOCATION = 1;


        // 建立與GoogleApi的連接設定
        buildGoogleApiClient();

        // 建立位置更新的條件
        createLocationRequest();

        // 建立位置設定的需求(將上方的條件當做參數)
        buildLocationSettingsRequest();

    }

    // 畫面啟動時，開始連接 GoogleApiClient
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    // 小心手機直向橫向改變時，也會呼叫這
    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    // 退出畫面，取消連接 GoogleApiClient
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void createLocationRequest() {

        mLocationRequest = new LocationRequest();

        /** Note: 下面兩個設定更新頻率都會因為其他app的需求(它們也會設定更新頻率)而受到影響，所以必須設定
         * setFastestInterval來確保資料不會因為太快更新而有溢出的風險 **/

        /** 性能建議: 當app使用網路或是有需要一段時間的工作時，請將fastest interval調整到比較緩慢，
         * 這個調整可以防止app無法接收到更新的狀況，一旦該工作結束時，即可調整回來**/

        // 設定幾秒更新一次(單位：毫秒)
        mLocationRequest.setInterval(10000);

        // 設定最快能接收的更新頻率(單位：毫秒)
        mLocationRequest.setFastestInterval(5000);

        // 設定來源給 Google Play services location services
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    private void buildLocationSettingsRequest(){

        /** 當已連接 Google Play services 和 location services API，就可以開始取得當前位置資訊，
         * 可以建立 LocationSettingsRequest.Builder，並且加上一或多個需求 **/

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }


    /**
     * Requests location updates from the FusedLocationApi.
     */
    private void startLocationUpdates() {
        if (Build.VERSION.SDK_INT > 22 ) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                Log.i(TAG,"開啟位置更新： " + status.toString());
            }
        });

    }


    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                Log.i(TAG,"關閉位置更新： "+ status.toString());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Snackbar.make(mLayout, "已取得位置權限",
                        Snackbar.LENGTH_SHORT).show();
                mLastLocation =
                        LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    printLocation(mLastLocation.getLongitude(),mLastLocation.getLatitude());
                } else {
                    Toast.makeText(this, "mLastLocation is null", Toast.LENGTH_LONG).show();
                }
            } else {
                // Permission was denied or request was cancelled
                Snackbar.make(mLayout, "拒絕位置權限，無法存取裝置位置",
                        Snackbar.LENGTH_SHORT).show();
            }

        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {

        // Android 6.0 之後才有 runtime permission
        if (Build.VERSION.SDK_INT > 22) {
            checkPermission();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        mGoogleApiClient.connect();
    }

    private void checkPermission() {

        // GoogleApiClient成功連接後，判斷是否有取得位置的權限
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            /** 沒有權限則顯示視窗告知使用者 **/

            // 自訂視窗來解釋為何要有此權限，對使用者有什麼好處
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Display custom UI and wait for user interaction
                Snackbar.make(mLayout, "為了記錄您的位置，需要取得您裝置的位置權限",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("確認", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_ACCESS_FINE_LOCATION);
                            }
                        }).show();
            } else {
                // 顯示Android預設視窗 (安裝後第一次使用會跑這裡，使用者如果去關掉這個權限，那會跑上面)
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ACCESS_FINE_LOCATION);
            }

        } else {

            /** The permissions are agree. **/

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                printLocation(mLastLocation.getLongitude(),mLastLocation.getLatitude());
            } else {
                Toast.makeText(this, "mLastLocation is null", Toast.LENGTH_LONG).show();
            }

            startLocationUpdates();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        printLocation(location.getLongitude(),location.getLatitude());
    }

    private void printLocation(double longitude, double latitude){
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        Log.i(TAG,"時間： " + timeStamp + "  座標： " + longitude + ", " + latitude + "\n");
    }
}
