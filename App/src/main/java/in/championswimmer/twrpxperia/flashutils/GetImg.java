package in.championswimmer.twrpxperia.flashutils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import in.championswimmer.twrpxperia.R;

/**
 * Created by championswimmer on 26/1/14.
 */
public class GetImg {
    private String LOG_TAG = "XRM";

    private static String TWRP_BASE_URL = "http://android.championswimmer.in/twrp/";
    private static String CWM_BASE_URL = "http://android.championswimmer.in/cwm/";


    private String[] codenames, props;
    private String deviceName;
    private SaveDir dir;

    private Boolean supported = false;

    private Context c;

    public GetImg(Context context) {
        c = context;
        codenames = context.getResources().getStringArray(R.array.supported_device_codename);
        props = context.getResources().getStringArray(R.array.supported_device_prop);
        dir = new SaveDir();

        for (int i = 0; i < props.length; i++) {
            if (props[i].equalsIgnoreCase(Build.DEVICE)) {
                deviceName = codenames[i];
                supported = true;
            }
        }
        Log.d(LOG_TAG, "prepare a download method for " + deviceName);
    }

    public Boolean isSupported() {
        return supported;
    }

    public void downloadTWRP() {
        String url = TWRP_BASE_URL + deviceName + "/recovery.img";
        Log.d(LOG_TAG, "downloading " + url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("TWRP recovery for " + deviceName);
        request.setTitle("twrp.img");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        File twrp = new File(dir.twrpPath());
        Log.d(LOG_TAG, "Saving at " + dir.twrpPath());
        if (twrp.exists() || twrp.isFile()) twrp.delete();
        request.setDestinationUri(Uri.parse("file://" + dir.twrpPath()));
        DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toast done = Toast.makeText(c,
                "TWRP Recovery image downloading to \n"
                + twrp.getPath(),
                Toast.LENGTH_LONG);
        done.show();
    }

    public void downloadCWM() {
        String url = CWM_BASE_URL + deviceName + "/recovery.img";
        Log.d(LOG_TAG, "downloading " + url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("CWM recovery for " + deviceName);
        request.setTitle("cwm.img");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        File cwm = new File(dir.twrpPath());
        Log.d(LOG_TAG, "Saving at " + dir.cwmPath());
        if (cwm.exists() || cwm.isFile()) cwm.delete();
        request.setDestinationUri(Uri.parse("file://" + dir.cwmPath()));
        DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toast done = Toast.makeText(c,
                "CWM Recovery image downloading to \n"
                        + cwm.getPath(),
                Toast.LENGTH_LONG);
        done.show();
    }
}
