package com.iksad.simpluencer.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageTools {
    public static byte[] loadImage(String imageUrl) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            return in.readAllBytes();
        } catch (IOException e) {
            return null;
        }
    }
}
