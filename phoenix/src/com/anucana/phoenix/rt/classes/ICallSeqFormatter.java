package com.anucana.phoenix.rt.classes;

import org.aspectj.lang.JoinPoint;

public interface ICallSeqFormatter {

    String FORWARD_CALL_ID_PREFIX = "F";

    String BACKWARD_CALL_ID_PREFIX = "B";

    void setCallId(String callId);

    void setCallSeqNum(long seqNum);

    void setJoinPoint(JoinPoint thisJoinPoint);

    void setOutput(Object output);

    StringBuilder formatCallSeq();

}
