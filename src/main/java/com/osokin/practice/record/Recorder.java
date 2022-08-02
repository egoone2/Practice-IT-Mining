package com.osokin.practice.record;

import com.osokin.practice.client.Client;
import com.osokin.practice.command.VoiceCommand;
import com.osokin.practice.exceptions.WrongCommandException;


import java.io.IOException;

import static com.osokin.practice.util.Utils.*;

public class Recorder extends Thread {

    private final Record record;
    private final Client client;

    public Recorder(Record record, Client client) {
        this.record = record;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            RELAY.doCommand(new VoiceCommand(1), record);
            waitForThirtySec();
            RELAY.doCommand(new VoiceCommand(0), record);
            client.sendRecord();
        } catch (WrongCommandException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void waitForThirtySec() {
        try {
            wait(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void notifyRecorder() {
        notify();
    }
}
