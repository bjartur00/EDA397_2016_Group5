package se.chalmers.agile.pairprogrammingapp.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import se.chalmers.agile.pairprogrammingapp.R;

/**
 * Created by wissam on 25/04/16.
 */
public class RequestHandler {
    public static void loadJsonDataGet(String url, final OnJsonDataLoadedListener listener,
                                       final Request.Priority priority, String tag) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (listener != null) {
                            listener.onJsonDataLoadedSuccessfully(response);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    int errorId = R.string.error_connection;
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof AuthFailureError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof ServerError) {
                        errorId = R.string.error_server;
                    } else if (error instanceof NetworkError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof ParseError) {
                        errorId = R.string.error_parse;
                    }
                    listener.onJsonDataLoadingFailure(errorId);
                }
            }
        }) {
            @Override
            public Request.Priority getPriority() {
                return priority;
            }

            // TODO check if we need to include these for the app ID and the token
            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();

                if (headers == null
                        || headers.equals(Collections.emptyMap())) {
                    headers = new HashMap<String, String>();
                }

                headers.put("key", "application_key");
                headers.put("token", "optional_auth_token");


                return headers;
            }*/
        };
        // Add request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq, tag);
    }

    public static void loadJsonArrayGet(String url, final OnJsonArrayLoadedListener listener,
                                       final Request.Priority priority, String tag) {
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if (listener != null) {
                            listener.onJsonDataLoadedSuccessfully(response);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    int errorId = R.string.error_connection;
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof AuthFailureError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof ServerError) {
                        errorId = R.string.error_server;
                    } else if (error instanceof NetworkError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof ParseError) {
                        errorId = R.string.error_parse;
                    }
                    listener.onJsonDataLoadingFailure(errorId);
                }
            }
        }) {
            @Override
            public Request.Priority getPriority() {
                return priority;
            }

            // TODO check if we need to include these for the app ID and the token
            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();

                if (headers == null
                        || headers.equals(Collections.emptyMap())) {
                    headers = new HashMap<String, String>();
                }

                headers.put("key", "application_key");
                headers.put("token", "optional_auth_token");


                return headers;
            }*/
        };
        // Add request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq, tag);
    }

    public static void loadJsonDataPost(String url, final OnJsonDataLoadedListener listener,
                                        final Map<String, String> params, final Request.Priority priority,
                                        String tag) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (listener != null) {
                            listener.onJsonDataLoadedSuccessfully(response);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    int errorId = R.string.error_connection;
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof AuthFailureError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof ServerError) {
                        errorId = R.string.error_server;
                    } else if (error instanceof NetworkError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof ParseError) {
                        errorId = R.string.error_parse;
                    }
                    listener.onJsonDataLoadingFailure(errorId);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Request.Priority getPriority() {
                return priority;
            }

            // TODO check if we need to include these for the app ID and the token
            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();

                if (headers == null
                        || headers.equals(Collections.emptyMap())) {
                    headers = new HashMap<String, String>();
                }

                headers.put("key", "application_key");
                headers.put("token", "optional_auth_token");


                return headers;
            }*/
        };
        // Add request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq, tag);
    }

    public static void loadJsonDataPut(String url, final OnJsonDataLoadedListener listener,
                                        final Map<String, String> params, final Request.Priority priority,
                                        String tag) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (listener != null) {
                            listener.onJsonDataLoadedSuccessfully(response);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    int errorId = R.string.error_connection;
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof AuthFailureError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof ServerError) {
                        errorId = R.string.error_server;
                    } else if (error instanceof NetworkError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof ParseError) {
                        errorId = R.string.error_parse;
                    }
                    listener.onJsonDataLoadingFailure(errorId);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Request.Priority getPriority() {
                return priority;
            }

            // TODO check if we need to include these for the app ID and the token
            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();

                if (headers == null
                        || headers.equals(Collections.emptyMap())) {
                    headers = new HashMap<String, String>();
                }

                headers.put("key", "application_key");
                headers.put("token", "optional_auth_token");


                return headers;
            }*/
        };
        // Add request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq, tag);
    }

    public static void loadJsonDataDelete(String url, final OnJsonDataLoadedListener listener,
                                       final Request.Priority priority, String tag) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (listener != null) {
                            listener.onJsonDataLoadedSuccessfully(response);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    int errorId = R.string.error_connection;
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof AuthFailureError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof ServerError) {
                        errorId = R.string.error_server;
                    } else if (error instanceof NetworkError) {
                        errorId = R.string.error_connection;
                    } else if (error instanceof ParseError) {
                        errorId = R.string.error_parse;
                    }
                    listener.onJsonDataLoadingFailure(errorId);
                }
            }
        }) {
            @Override
            public Request.Priority getPriority() {
                return priority;
            }

            // TODO check if we need to include these for the app ID and the token
            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();

                if (headers == null
                        || headers.equals(Collections.emptyMap())) {
                    headers = new HashMap<String, String>();
                }

                headers.put("key", "application_key");
                headers.put("token", "optional_auth_token");


                return headers;
            }*/
        };
        // Add request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq, tag);
    }

    public interface OnJsonDataLoadedListener {
        void onJsonDataLoadedSuccessfully(JSONObject data);
        void onJsonDataLoadingFailure(int errorId);
    }

    public interface OnJsonArrayLoadedListener {
        void onJsonDataLoadedSuccessfully(JSONArray data);
        void onJsonDataLoadingFailure(int errorId);
    }
}
