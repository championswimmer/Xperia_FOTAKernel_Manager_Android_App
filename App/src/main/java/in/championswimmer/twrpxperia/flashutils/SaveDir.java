package in.championswimmer.twrpxperia.flashutils;

import android.os.Environment;

import java.io.File;

/**
 * Created by championswimmer on 23/1/14.
 */
public class SaveDir {

    public static String STORAGE_DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/TWRPXperia/";

    public SaveDir () {
        File valid = new File(STORAGE_DIRECTORY+"valid.txt");
        if (!valid.exists()) {
            valid.mkdirs();
        }
    }

    public Boolean existsCwmImage () {
        File cwm = new File(STORAGE_DIRECTORY+"cwm.img");
        return cwm.exists();
    }
    public Boolean existsTwrpImage () {
        File twrp = new File(STORAGE_DIRECTORY+"twrp.img");
        return twrp.exists();
    }
    public Boolean existsFotaBackup () {
        File backup = new File(STORAGE_DIRECTORY+"fotabackup.img");
        return backup.exists();
    }


}
