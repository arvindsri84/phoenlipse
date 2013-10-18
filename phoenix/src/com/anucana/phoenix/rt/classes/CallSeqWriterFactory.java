package com.anucana.phoenix.rt.classes;


public class CallSeqWriterFactory {

    public static ICallSeqWriter getInstance() {
        String filePath = System.getProperty("outFilePath");

        String outIP = System.getProperty("outIP");
        String outPort = System.getProperty("outPort");

        ICallSeqWriter writer = null;

        try {
            if (filePath != null && filePath.trim().length() != 0) {
                writer = new FileWriter(filePath);
            } else if (outIP != null && outIP.trim().length() != 0 && outPort != null && outPort.trim().length() != 0) {
                writer = new SocketWriter(outIP, outPort);
            } else {
                writer = new ConsoleWriter();
            }
        } catch (Exception ex) {
            System.err.println("Error occurred while creating a call seqquence writer ");
            ex.printStackTrace();
        }

        if (writer == null) {
            writer = new DoNothingWriter();
        }
        return writer;
    }
}
