package com.osokin.practice.client;

import com.osokin.practice.command.VoiceCommand;
import com.osokin.practice.exceptions.WrongCommandException;
import com.osokin.practice.record.Record;
import com.osokin.practice.record.Recorder;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.*;

import static com.osokin.practice.util.Utils.JSON;
import static com.osokin.practice.util.Utils.SERVICE_PORT;


@Log
public class Client {
    private DatagramSocket clientSocket = new DatagramSocket();
    private final InetAddress IPAddress;
    private Record record = new Record();
    private Recorder recorder;


    public Client(InetAddress ipAddress) throws SocketException {
        IPAddress = ipAddress;
    }

    public Client() throws UnknownHostException, SocketException {
        IPAddress = InetAddress.getByName("localhost");
    }

    public void startRecording() throws IOException {
        if (record.isRecording()) {
            log.info("Error! Record has already been started.");
            return;
        }
        record = new Record();
        recorder = new Recorder(record, this);
        recorder.start();


    }

    public void stopRecording()  {
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        if (!record.isRecording()) {
            log.info("Error! Record has not been started.");
            return;
        }
        recorder.notifyRecorder();
    }

    public void sendRequest() throws WrongCommandException, IOException {
        clientSocket = new DatagramSocket();
        byte[] dataBuffer;
        String jsonRequest = JSON.toJson(new VoiceCommand(1));
        dataBuffer = jsonRequest.getBytes();
        DatagramPacket sendingPacket = new DatagramPacket(dataBuffer,
                dataBuffer.length, IPAddress, SERVICE_PORT);
        clientSocket.send(sendingPacket);
        clientSocket.close();
    }

    public void sendRecord() throws IOException {
        File audio = record.getFile();
        int offSet = 0;
        byte[] bytes = FileUtils.readFileToByteArray(audio);
        int bytesLength = bytes.length;
        log.info(String.valueOf(bytesLength));
        while (offSet < bytesLength) {

            int length = 20000;
            if (offSet+length > bytesLength)
                offSet -= (offSet+length) - bytesLength;
            DatagramPacket sendingPacket = new DatagramPacket(bytes, offSet,
                    length, IPAddress, SERVICE_PORT);
            offSet += length;
            System.out.println(offSet);
            clientSocket.send(sendingPacket);
        }
    }

}
