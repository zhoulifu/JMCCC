package com.github.to2mbn.jmccc.util;

import java.io.BufferedInputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class Utils {

    /**
     * Gets the current 'java' path.
     * <p>
     * On *nix systems it's <code>$java.home/bin/java</code>.<br>
     * On Windows it's <code>$java.home\bin\java.exe</code>.
     * 
     * @return the current 'java' path
     */
    public static File getJavaPath() {
        return new File(System.getProperty("java.home"), "bin/java" + (OsTypes.CURRENT == OsTypes.WINDOWS ? ".exe" : ""));
    }

    /**
     * Generate a random minecraft token
     * 
     * @return a random minecraft token
     */
    public static String generateRandomToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Uncompresses the given zip to the given output dir
     * 
     * @param zip file to uncompress
     * @param outputDir the output dir
     * @param excludes the excludes list
     * @throws IOException if an I/O error occurs
     */
    public static void uncompressZipWithExcludes(File zip, File outputDir, Set<String> excludes) throws IOException {
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        byte[] buffer = new byte[8192];
        int read;

        try (ZipInputStream in = new ZipInputStream(new FileInputStream(zip))) {
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                boolean excluded = false;
                if (excludes != null) {
                    for (String exclude : excludes) {
                        if (entry.getName().startsWith(exclude)) {
                            excluded = true;
                            break;
                        }
                    }
                }

                if (!excluded) {
                    try (OutputStream out = new FileOutputStream(new File(outputDir, entry.getName()))) {
                        while ((read = in.read(buffer)) != -1) {
                            out.write(buffer, 0, read);
                        }
                    }
                }

                in.closeEntry();
            }
        }
    }

    /**
     * Returns true if the current java is supported CGC.
     * <p>
     * Java 1.8 and higher versions don't support CGC.
     * 
     * @return true if the current java is supported CGC
     */
    public static boolean isCGCSupported() {
        String javaVersion = System.getProperty("java.version");
        return !(javaVersion.contains("1.8.") || javaVersion.contains("1.9."));
    }

    /**
     * Reads json from given file with charset UTF-8.
     * 
     * @param file file to read
     * @return json in the file
     * @throws IOException an I/O error occurs
     * @throws JSONException a json parsing error occurs
     */
    public static JSONObject readJson(File file) throws IOException, JSONException {
        try (Reader reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(file)), "UTF-8")) {
            return new JSONObject(new JSONTokener(reader));
        }
    }

    /**
     * Gets the stack trace of the given exception.
     * 
     * @param e the exception
     * @return the stack trace of the given exception
     */
    public static String getStackTrace(Throwable e) {
        CharArrayWriter cw = new CharArrayWriter();
        PrintWriter pw = new PrintWriter(cw);
        e.printStackTrace(pw);
        pw.close();
        return cw.toString();
    }

    private Utils() {
    }
}