package vng.trueidsdk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Base64;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import vng.com.vn.trueid.models.VerificationResult;
import vng.com.vn.trueid.models.CheckingRecord;
import vng.com.vn.trueid.models.ConfigInfo;
import vng.com.vn.trueid.models.DetectionType;
import vng.com.vn.trueid.models.Person;
import vng.com.vn.trueid.TrueID;
import vng.com.vn.trueid.TrueIDListener;

public class TrueIdModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public TrueIdModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "TrueId";
    }

    @ReactMethod
    public void setLanguage(ReadableMap data) {
        String language = data.getString("language");
        if (null != language)
            TrueID.setLanguage(getCurrentActivity(), language);
    }

    @ReactMethod
    public void configure(ReadableMap data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ConfigInfo configInfo = new ConfigInfo(data.getString("domain"),
                    data.getString("domainPath"),
                    data.getString("authDomain"),
                    data.getString("authDomainPath"),
                    data.getString("appId"),
                    data.getString("appSecret"),
                    data.getString("zoomLicenseKey"),
                    data.getString("zoomPublicKey"),
                    data.getString("zoomServerBaseURL"),
                    data.getString("zoomAuthURL"));

            if (isValidData(data, "configHeader")) {
                TrueID.configure(getCurrentActivity(), configInfo, data.getString("configUI"), data.getString("accessToken"), "", data.getString("configHeader"));
            } else {
                TrueID.configure(getCurrentActivity(), configInfo, data.getString("configUI"), data.getString("accessToken"));
            }
            String language = data.getString("language");
            if (null != language)
                TrueID.setLanguage(getCurrentActivity(), language);
        } else {
            this.showAlert("trueID doesn't support Android less than 6");
        }
    }

    private static boolean isValidData(ReadableMap data, String key) {
        return data.hasKey(key) && !data.getString(key).isEmpty();
    }

    @ReactMethod
    public void start(final Callback callback) {
        TrueID.start(getCurrentActivity(), cardInfo -> {
            if (callback != null) {
                WritableMap returnMap = kUtils.convertMapToWritableMap(cardInfo.toMap());
                callback.invoke(returnMap);
            }
        });
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentActivity());
        builder.setTitle("trueID");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
