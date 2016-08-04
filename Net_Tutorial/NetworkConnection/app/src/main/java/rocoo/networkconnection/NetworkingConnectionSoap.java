package rocoo.networkconnection;

/**
 * Created by geosat-rd01 on 2016/8/3.
 */
public class NetworkingConnectionSoap {

//    public static String uploadNewInspectionPoints(String URL,String namespace ,String soap_action,String method_name,
//                                                   String json, String modifyEmpID) {
//        try {
//            SoapObject request = new SoapObject(namespace, method_name);
//            request.addProperty("json", json);
//            request.addProperty("modifyEmpID", modifyEmpID);
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//                    SoapEnvelope.VER11);
//            envelope.bodyOut = request;
//            envelope.dotNet = true;
//            envelope.encodingStyle = "UTF-8";
//            envelope.setOutputSoapObject(request);
//            HttpTransportSE ht = new HttpTransportSE(URL);
//            ht.call(soap_action + method_name, envelope);
//            final SoapPrimitive result = (SoapPrimitive) envelope
//                    .getResponse();
////            SoapObject result= (SoapObject) envelope.getResponse();
//            return result.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//
//    }
}
