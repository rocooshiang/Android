package rocoo.networkconnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by geosat-rd01 on 2016/8/3.
 */
public class NetworkingConnectionGet {

    /**
     * MARK - Networking Connection Get
     *
     * Have data return data.
     * Have not data return empty string.
     **/

    public static String fetchDataFromGet(String myUrl) throws IOException {
        InputStream is = null;
        int len = 500;
        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();
            if (response == 200) {
                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                return contentAsString;
            } else {
                return "";
            }

        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


    public static String readIt(InputStream stream, int len) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
