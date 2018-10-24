package vn.softlink.core.util.data;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.StringReader;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public class JsonHelper {

    private static final JsonHelper sInstance = new JsonHelper();

    public static JsonHelper getInstance() {
        return sInstance;
    }

    private Gson mGson;

    private JsonHelper() {
        mGson = new Gson();
    }

    public static <T> T parse(String string, Class<T> cls) {

        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return getInstance().mGson.fromJson(string, cls);
        } catch (IllegalStateException e) {
            return null;
        } catch (JsonSyntaxException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static <T> T[] parseArray(String string, Class<T[]> cls) {

        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return getInstance().mGson.fromJson(new StringReader(string), cls);
        } catch (IllegalStateException e) {
            return null;
        } catch (JsonSyntaxException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}

