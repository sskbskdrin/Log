package cn.sskbskdrin.log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class Json {

    static String json(String json, int indent) {
        json = json.trim();
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                return "\n" + jsonObject.toString(indent);
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                return "\n" + jsonArray.toString(indent);
            }
        } catch (JSONException ignored) {
        }
        return null;
    }

}
