package com.anucana.phoenix.rt.classes;


public class ConsoleWriter implements ICallSeqWriter {

    public void write(ICallSeqFormatter formatter) {
        System.out.println(formatter.formatCallSeq());
    }

}
