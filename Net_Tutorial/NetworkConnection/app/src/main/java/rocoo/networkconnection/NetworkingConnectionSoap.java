package rocoo.networkconnection;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by geosat-rd01 on 2016/8/3.
 */
public class NetworkingConnectionSoap {

    static String url = "http://www.webservicex.net/globalweather.asmx";

    static String namespace = "http://www.webserviceX.NET";

    static String methodName = "GetWeather";
    static String soapAction = "http://www.webserviceX.NET/" + methodName;


    public static String fetchDataFromSoap(String country, String city) {
        try {
            SoapObject request = new SoapObject(namespace, methodName);
            request.addProperty("CityName", city);
            request.addProperty("CountryName", country);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;
            envelope.encodingStyle = "UTF-8";
            envelope.setOutputSoapObject(request);
            HttpTransportSE ht = new HttpTransportSE(url);
            ht.call(soapAction, envelope);
            final SoapPrimitive result = (SoapPrimitive) envelope
                    .getResponse();

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
