package com.osokin.practice.util;

import com.osokin.practice.command.VoiceCommand;
import com.osokin.practice.record.Record;
import lombok.extern.java.Log;


@Log
public class Relay {
    public void doCommand(VoiceCommand command, Record record) {
        switch (command.getCommand()) {
            case 0:
                if (!record.isRecording())
                    log.info("Error! Record has not been started.");
                else {
                    log.info("Stop recording.");
                    record.stopRecording();

                }
                break;
            case 1:
                if (record.isRecording())
                    log.info("Error! Record has already been started.");
                else {
                    record.getNewFile();
                    log.info("Start recording.");
                    record.startRecording();
                }
                break;

        }
    }
}
