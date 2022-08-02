package com.osokin.practice.admin;

import com.osokin.practice.exceptions.WrongCommandException;

import java.io.IOException;

public interface AdminPageMBean {
    public void startRecording();

    public void stopRecording() throws IOException;

    public void sendRequest() throws WrongCommandException, IOException;
}
