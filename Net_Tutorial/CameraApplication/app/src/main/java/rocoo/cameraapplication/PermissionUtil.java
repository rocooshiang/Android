package rocoo.cameraapplication;

/**
 * Created by geosat-rd01 on 2016/7/11.
 */


import android.content.pm.PackageManager;

public abstract class PermissionUtil {

    public static boolean verifyPermissions(int[] grantResults) {

        // 至少要有一個項目
        if (grantResults.length < 1) {
            return false;
        }

        // 每一個 permission 都要使用者同意
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}