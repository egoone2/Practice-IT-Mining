package com.osokin.practice.server;

import com.osokin.practice.command.VoiceCommand;
import com.osokin.practice.exceptions.WrongCommandException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static com.osokin.practice.util.Utils.*;


public class Receiver {

    public static void receiveAndPass(DatagramSocket datagramSocket, DatagramPacket inputPacket) throws IOException, WrongCommandException {
        datagramSocket.receive(inputPacket);
        String data = formatData(new String(inputPacket.getData()));
        try {
            VoiceCommand command = JSON.fromJson(data, VoiceCommand.class);
            switch (command.getCommand()) {
                case 0:
                    throw new WrongCommandException("Wrong command");
                case 1:
            }
        } catch (Exception e) {
            throw new WrongCommandException("Wrong command");
        }
    }

}
