package org.tianjyan.luban.utils;

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

        File file = new File(getSaveOutParaFolder(outPara.getClient()),
                getSaveOutParaFileName(outPara.getKey()));
        if (file.exists()) return;

        FileWriter fileWriter;
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
                sb.append(Utils.LINE_SEP);
            }

            if (sb.length() > 0) {
                fileWriter.write(sb.toString());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File getSaveOutParaFolder(String outParaFolder) {
        String folderPath = Utils.getCacheDir() + Utils.FILE_SEP + OutParaFolder + Utils.FILE_SEP + outParaFolder;
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    private static String getSaveOutParaFileName(String outParaName) {
        return Utils.getFileTime() + "_" + outParaName + ".csv";
    }
}
