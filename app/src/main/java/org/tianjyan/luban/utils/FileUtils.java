package org.tianjyan.luban.utils;

import android.os.Environment;

import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.model.ParaHistory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtils {
    public final static String OutParaFolder = "OutPara";
    public final static long WriteBuffer = 8192;

    public static void saveOutParaHistory(OutPara outPara, List<ParaHistory> histories) {
        if (outPara == null
                || Utils.isNullOrEmpty(outPara.getClient())
                || Utils.isNullOrEmpty(outPara.getKey())
                || histories == null) {
            throw new IllegalArgumentException();
        }
        String folderName = String.format("/%s/%s/%s",
                Utils.getCacheDir(), OutParaFolder, outPara.getClient());
        String fileName = String.format("%s_%s.csv",
                Utils.getFileTime(), outPara.getKey());

        File folder = new File(folderName);
        folder.mkdirs();
        File file = new File(folder, fileName);
        if (file.exists()) return;

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            StringBuffer sb = new StringBuffer();
            for (ParaHistory history : histories) {
                if (sb.length() > WriteBuffer) {
                    fileWriter.write(sb.toString());
                    sb.setLength(0);
                }
                sb.append(history.getDisplayTime());
                sb.append(",");
                sb.append(history.getValue());
                sb.append("\r\n");
            }

            if (sb.length() > 0) {
                fileWriter.write(sb.toString());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
