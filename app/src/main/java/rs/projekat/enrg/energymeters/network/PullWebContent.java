package rs.projekat.enrg.energymeters.network;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;


public class PullWebContent<T> {

    public WebRequestCallbackInterface<T> webRequestCallbackInterface;
    //private Context context;
    private String url;
    private Class<T> t;
    private VolleySingleton mVolleySingleton;


    public PullWebContent(Class<T> mClass, String url, VolleySingleton mVolleySingleton) {

        //this.context = context;
        webRequestCallbackInterface = null;
        this.url = url;
        this.t = mClass;
        this.mVolleySingleton = mVolleySingleton;

    }

    public void setCallbackListener(WebRequestCallbackInterface<T> listener) {
        this.webRequestCallbackInterface = listener;

    }

    public void pullList() {
        // Tag used to cancel the request
        String tag_string_req = "req_" + url;


        final GsonRequest<T> gsonRequest = new GsonRequest<T>(url, t, null, new Response.Listener<T>() {

            @Override
            public void onResponse(T model) {

                if (model != null) {
                    webRequestCallbackInterface.webRequestSuccess(true, model);
                    Log.i("tag_string_req", model.toString());
                } else {
                    Log.i("tag_string_req", "NULL RESPONSE");
                    webRequestCallbackInterface.webRequestSuccess(false, model);
                }

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                webRequestCallbackInterface.webRequestError(error.getMessage());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Post params
                Map<String, String> params = new HashMap<>();
                params.put("action", "pull " + t.getName());
                params.put("id", url);
                return params;
            }
        };

        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(Config.DEFAULT_TIMEOUT_MS, Config.DEFAULT_MAX_RETRIES, Config.DEFAULT_BACKOFF_MULT));
        // Adding request to  queue
        mVolleySingleton.addToRequestQueue(gsonRequest);
    }
}
