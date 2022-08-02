package com.osokin.practice.record;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

public class Record {
    @Getter
    private File file;
    @Getter
    private OutputStream outputStream = new ByteArrayOutputStream();
    @Setter
    @Getter
    private byte[] buffer = new byte[1024];
    // полное имя файла
    private String soundFileName;
    // основное имя файла
    private String filename = "samples_";
    // номер файла
    private int suffix = 0;
    // аудио формат
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private int MONO = 1;
    // определение формата аудио данных
    private final AudioFormat format = new AudioFormat(16000, 8, MONO, true, false);
    // микрофонный вход
    @Getter
    private TargetDataLine mike;

    private AudioInputStream inputStream;
    @Getter
    private boolean isRecording = false;

    // создать новый файл
    public File getNewFile() {
        try {
            do {
                // новое название файла
                soundFileName = filename + (suffix++) + "." + fileType.getExtension();
                file = new File(soundFileName);
            } while (!file.createNewFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }


    // запуск записи
    public void startRecording() {
        isRecording = true;
        new Thread() {
            public void run() {

                // линию соединения
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                // проверить, поддерживается ли линия
                if (!AudioSystem.isLineSupported(info)) {
                    JOptionPane.showMessageDialog(null, "Line not supported" +
                                    info, "Line not supported",
                            JOptionPane.ERROR_MESSAGE);
                }
                try {
                    // получить подходящую линию
                    mike = (TargetDataLine) AudioSystem.getLine(info);
                    // открываем линию соединения с указанным
                    // форматом и размером буфера
                    mike.open(format, mike.getBufferSize());
                    // поток микрофона
                    AudioInputStream sound = new AudioInputStream(mike);
                    // запустить линию соединения
                    mike.start();

                    // записать содержимое потока в файл
                    AudioSystem.write(sound, fileType, file);


                } catch (LineUnavailableException ex) {
                    JOptionPane.showMessageDialog(null, "Line not available" +
                                    ex, "Line not available",
                            JOptionPane.ERROR_MESSAGE);
                    isRecording = false;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "I/O Error " + ex,
                            "I/O Error", JOptionPane.ERROR_MESSAGE);
                    isRecording = false;
                }
            }
        }.start();
    }

    // остановка записи
    public void stopRecording() {
        isRecording = false;
        mike.stop();
        mike.close();
    }

}
