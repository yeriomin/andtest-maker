package com.github.yeriomin.andtest.maker;

import sun.misc.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author yeriomin <yeriomin@gmail.com>
 */
public class FileUtils {

    static final String EXT = ".json";

    public static String read(String file) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file));
        return new String(encoded, Charset.defaultCharset());
    }

    public static String read(InputStream streamIn) throws IOException {
        byte[] encoded = IOUtils.readFully(streamIn, -1, true);
        return new String(encoded, Charset.defaultCharset());
    }

    public static void write(String content, String file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.defaultCharset()))) {
            writer.write(content);
        }
    }

    public static String replaceExtension(String source) {
        int index = source.indexOf(".");
        return index == -1 ? (source + EXT) : (source.replace(source.substring(index), EXT));
    }

}
