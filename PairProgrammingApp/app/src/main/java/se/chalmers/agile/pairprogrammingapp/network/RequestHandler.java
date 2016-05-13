package se.chalmers.agile.pairprogrammingapp.network;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import se.chalmers.agile.pairprogrammingapp.PairProgrammingApplication;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.User;
import se.chalmers.agile.pairprogrammingapp.utils.SecretKeys;

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
        };
        // Add request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq, tag);
    }

    public static void loadStringDelete(String url, final OnStringLoadedListener listener,
                                        final Request.Priority priority, String tag) {
        com.android.volley.toolbox.StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (listener != null) {
                            listener.onStringLoadedSuccessfully(response);
                        }
                    }
                },
                new Response.ErrorListener() {
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
                            listener.onStringLoadingFailure(errorId);
                        }
                    }
                }) {
            @Override
            public Request.Priority getPriority() {
                return priority;
            }
        };

        // Add request to request queue
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, tag);
    }

    public interface OnJsonDataLoadedListener {
        void onJsonDataLoadedSuccessfully(JSONObject data);

        void onJsonDataLoadingFailure(int errorId);
    }

    public interface OnJsonArrayLoadedListener {
        void onJsonDataLoadedSuccessfully(JSONArray data);

        void onJsonDataLoadingFailure(int errorId);
    }

    public interface OnStringLoadedListener {
        void onStringLoadedSuccessfully(String data);

        void onStringLoadingFailure(int errorId);
    }
}
