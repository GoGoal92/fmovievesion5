package market.goldandgo.videonew1.Utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by Go Goal on 12/5/2016.
 */

public class Encode_Decoder {

    public static String encoding_string(String edtext) {

        String base64String = "";
        try {
            byte[] data = edtext.getBytes("UTF-8");
            base64String = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return base64String;
    }


    public static String decoding_string(String str) {

        String base64String = "";
        try {
            byte[] data = Base64.decode(str, Base64.DEFAULT);
            base64String = new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64String;
    }

}
