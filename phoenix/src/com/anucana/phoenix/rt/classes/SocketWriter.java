package com.anucana.phoenix.rt.classes;


public class SocketWriter implements ICallSeqWriter {

    private String ip = null;
    private String portNumber = null;

    public SocketWriter(String ip, String portNumber) {
        this.ip = ip;
        this.portNumber = portNumber;

    }

    public void write(ICallSeqFormatter formatter) {
    }

}
