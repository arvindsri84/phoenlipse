package com.anucana.phoenix.rt.classes;


public class CallSeqFormatterFactory {

    public static ICallSeqFormatter getInstance() {
        return new ConcatCallSequence();
    }
}
