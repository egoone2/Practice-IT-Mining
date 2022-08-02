package com.osokin.practice.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {
    public static final Gson JSON = new GsonBuilder().setLenient().create();
    public static final Relay RELAY = new Relay();
    public static final int SERVICE_PORT = 50001;

    public static int countLength(String receivedData) {
        int count = 0;
        byte i;
        byte[] receivedDataBytes = receivedData.getBytes();
        while ((i = receivedDataBytes[count]) != 0) {
            count++;
        }
        return count;
    }

    public static String formatData(String receivedData) {
        int count = countLength(receivedData);
        byte[] newReceivedDataBuffer = new byte[count];
        byte[] oldReceivedDataBuffer = receivedData.getBytes();
        if (count >= 0) System.arraycopy(oldReceivedDataBuffer, 0, newReceivedDataBuffer, 0, count);
        return new String(newReceivedDataBuffer);
    }
}
