package com.osokin.practice;


import com.osokin.practice.admin.AdminPage;
import com.osokin.practice.client.Client;
import com.osokin.practice.exceptions.WrongCommandException;
import com.osokin.practice.server.Server;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws WrongCommandException, InterruptedException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, IOException, MBeanRegistrationException {
//        AdminPage.init();
        new Thread(new Server()).start();

        Client c = new Client();
        c.sendRequest();
        c.startRecording();
        Thread.sleep(10000);
        c.stopRecording();
    }
}
