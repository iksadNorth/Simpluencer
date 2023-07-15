package com.iksad.simpluencer.utils;

import java.util.Random;

public class RandomUtils {
    public static String getPassWordHex() {
        String table = "0123456789abcdefghijklmnopqrstuvwzyxABCEDFGHIJKLMNOPQRSTUVWXZY";
        int length = table.length();
        String separator = "-";

        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i=0; i < 16; i++) {
            int index = random.nextInt(length);

            builder.append(table.charAt(index));
            if (i % 4 == 3 && i != 15) {
                builder.append(separator);
            }
        } return builder.toString();
    }

    public static String getState() {
        String table = "0123456789abcdefghijklmnopqrstuvwzyxABCEDFGHIJKLMNOPQRSTUVWXZY";
        int length = table.length();

        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i=0; i < 16; i++) {
            int index = random.nextInt(length);

            builder.append(table.charAt(index));
        } return builder.toString();
    }
}
