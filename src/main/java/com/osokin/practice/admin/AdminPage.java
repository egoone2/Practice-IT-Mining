package com.osokin.practice.admin;


import com.osokin.practice.client.Client;
import com.osokin.practice.exceptions.WrongCommandException;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class AdminPage implements AdminPageMBean {
    private final Client client = new Client();

    public AdminPage() throws IOException {
    }


    public static void init() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, IOException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name1 = new ObjectName("main.java.Utils:type=AdminPage");
        StandardMBean mBean = new StandardMBean(new AdminPage(), AdminPageMBean.class);
        mbs.registerMBean(mBean, name1);
    }

    @Override
    public void startRecording() {
        try {
            client.startRecording();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stopRecording() throws IOException {
        client.stopRecording();
    }

    @Override
    public void sendRequest() throws WrongCommandException, IOException {
        client.sendRequest();
    }
}
