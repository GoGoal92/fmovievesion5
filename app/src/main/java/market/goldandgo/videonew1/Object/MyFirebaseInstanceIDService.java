package market.goldandgo.videonew1.Object;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "onTokenRefresh: " + refreshedToken);

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);
        sendRegistrationToServers(refreshedToken);
        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent("registrationComplete");
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        Log.e(TAG, "sendRegistrationToServer: " + token);

        // MyApplication.getInstance().getPrefManager().putRegId(token);
    }

    private void sendRegistrationToServers(final String token) {
//        User user = MyApplication.getInstance().getPrefManager().getUser();
//        if (user == null) {
//            // TODO
//            // user not found, redirecting him to login screen
//            return;
//        }
//
//        String endPoint = EndPoints.FCM.replace("_ID_", user.getApiKey());
//
//        Log.e(TAG, "endpoint: " + endPoint);
//
//        StringRequest strReq = new StringRequest(Request.Method.PUT,
//                endPoint, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.e(TAG, "response: " + response);
//
//                try {
//                    JSONObject obj = new JSONObject(response);
//
//                    // check for error
//                    if (obj.getBoolean("error") == false) {
//                        // broadcasting token sent to server
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Unable to send fcm registration id to our sever. " + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (JSONException e) {
//                    Log.e(TAG, "json parsing error: " + e.getMessage());
//                    Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                NetworkResponse networkResponse = error.networkResponse;
//                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
//                Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("fcm_registration_id", token);
//
//                Log.e(TAG, "params: " + params.toString());
//                return params;
//            }
//        };
//
//        //Adding request to request queue
//        MyApplication.getInstance().addToRequestQueue(strReq);
    }
}

