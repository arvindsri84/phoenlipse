package com.anucana.phoenix.rt.aspects;

import com.anucana.phoenix.rt.classes.CallSeqFormatterFactory;
import com.anucana.phoenix.rt.classes.CallSeqWriterFactory;
import com.anucana.phoenix.rt.classes.ICallSeqFormatter;
import com.anucana.phoenix.rt.classes.ICallSeqWriter;

public aspect CallTracer {

    // Method call sequence 
    private long callSeqNum = 0;
    
    private ICallSeqWriter callSeqWriter = CallSeqWriterFactory.getInstance();
    private ICallSeqFormatter callSeqFormatter = CallSeqFormatterFactory.getInstance();
    
    pointcut classesOfNoInterest(): within(com.anucana.phoenix.rt..*);
    
    pointcut methodsOfInterest(): execution(* *(..)) && !classesOfNoInterest();

    Object around(): methodsOfInterest() {
        
        callSeqFormatter.setJoinPoint(thisJoinPoint);
        callSeqFormatter.setOutput(null);
        callSeqFormatter.setCallId(ICallSeqFormatter.FORWARD_CALL_ID_PREFIX);
        callSeqFormatter.setCallSeqNum(callSeqNum);
        
        callSeqNum = callSeqNum + 1;
        callSeqWriter.write(callSeqFormatter);
        
        // Proceed with the method invocation
        Object o = proceed();
        
        callSeqNum = callSeqNum - 1;
        callSeqFormatter.setJoinPoint(thisJoinPoint);
        callSeqFormatter.setOutput(o);
        callSeqFormatter.setCallId(ICallSeqFormatter.BACKWARD_CALL_ID_PREFIX);
        callSeqFormatter.setCallSeqNum(callSeqNum);
        callSeqWriter.write(callSeqFormatter);  
        
        return o;
    }

}
