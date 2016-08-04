package rocoo.networkconnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by geosat-rd01 on 2016/7/25.
 */
public class Global {

    public static boolean isConnected(Context context) {
        boolean isConnect = false;
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isConnect = true;
        }
        return isConnect;
    }

}
