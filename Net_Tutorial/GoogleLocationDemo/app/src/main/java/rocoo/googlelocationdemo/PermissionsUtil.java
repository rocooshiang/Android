package rocoo.googlelocationdemo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by geosat-rd01 on 2016/7/6.
 */
public class PermissionsUtil {
    public static void requestPermission(Activity activity, String[] permissions, int requestFrom) {

        ActivityCompat.requestPermissions(activity, permissions, requestFrom);
    }
}
