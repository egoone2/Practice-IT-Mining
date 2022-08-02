package com.osokin.practice.server;

import com.osokin.practice.exceptions.WrongCommandException;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import com.osokin.practice.record.Record;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


import static com.osokin.practice.util.Utils.*;

@Log
public class Server implements Runnable {
    private DatagramSocket serverSocket;
    private final byte[] receivingDataBuffer = new byte[20000];
    private final AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private int suffix = 0;


    @Override
    public void run() {
        try {
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            serverSocket = new DatagramSocket(SERVICE_PORT);
            while (true) {
                log.info("Waiting for a client to connect...");

                while (true) {
                    try {
//                        Ресивер принимает команду на запись и завершает цикл
                        Receiver.receiveAndPass(serverSocket, inputPacket);
                        break;
                    } catch (IOException | WrongCommandException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.info("Correct command");
//                Путь к папке для отправленных аудио
                File file = getNewFile("C:\\Users\\whale\\IdeaProjects\\PracticeIT-Mining\\samples\\");
                int counter = 0;
                while (true) {

                    try {
                        serverSocket.receive(inputPacket);
                        log.info("Packet received!");
                        byte[] bytes = inputPacket.getData();
//                        Запись байтов в файл
                        FileUtils.writeByteArrayToFile(file, bytes, true);
                        log.info("Part " + counter + " is written!");
                        counter++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        } catch (IOException e) {
            serverSocket.close();
            e.printStackTrace();
        }


    }

    public File getNewFile(String path) {
        File file = null;
        try {
            do {
                // новое название файла
                String filename = "samples_";
                String soundFileName = path + filename + (suffix++) + "." + fileType.getExtension();
                file = new File(soundFileName);
            } while (!file.createNewFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }

}
