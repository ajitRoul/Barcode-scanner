package cordova.plugin.barcode;

import android.util.Log;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * This class echoes a string called from JavaScript.
 */
public class Barcode extends CordovaPlugin {
    private CallbackContext callbackContext;
    private String displayValue;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("scan")) {
            String message = args.getString(0);
            startScan(callbackContext);

            return true;
        }
        return false;
    }

    private void scan(String message, CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
        if (message != null && message.length() > 0) {

        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void startScan(CallbackContext callbackContext) {
       /**
        * Build a new MaterialBarcodeScanner
        */
        final CallbackContext callbackContexTemp = callbackContext;
       final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
               .withActivity(cordova.getActivity())
               .withEnableAutoFocus(true)
               .withBleepEnabled(true)
               .withBackfacingCamera()
               .withText("Scanning...")
               .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                   @Override
                   public void onResult(com.google.android.gms.vision.barcode.Barcode barcode) {
                       Log.d("test",barcode.displayValue);
                       callbackContexTemp.success(barcode.displayValue);
                   }
               }).build();
       materialBarcodeScanner.startScan();
   }
}
