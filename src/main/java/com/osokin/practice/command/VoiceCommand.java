package com.osokin.practice.command;

import com.google.gson.annotations.SerializedName;
import com.osokin.practice.exceptions.WrongCommandException;

public class VoiceCommand {
    @SerializedName("Voice")
    private final int command;

    public VoiceCommand(int code) throws WrongCommandException {
        if (code != 0 && code != 1)
            throw new WrongCommandException("Wrong command code");
        this.command = code;
    }

    public int getCommand() {
        return command;
    }
}
