package com.anucana.phoenix.core;


public class CallSeqLineParser {

    public static final String LOG_MAIN_SEPERATOR = "\\|";

    public static final String FORWARD_CALL_ID_PREFIX = "F";
    public static final String BACKWARD_CALL_ID_PREFIX = "B";

    public CallSeqModel parse(String callSeqLine) {
        String[] callSegments = callSeqLine.split(LOG_MAIN_SEPERATOR);

        if (callSegments.length != 8) {
            throw new IllegalArgumentException("Call Sequence Line is corrupt. It must contain only * segments");
        }

        if (FORWARD_CALL_ID_PREFIX.equals(callSegments[0])) {
            return new CallSeqModel(
                    Integer.valueOf(callSegments[1]),
                    callSegments[2],
                    Integer.valueOf(callSegments[3]),
                    callSegments[4],
                    callSegments[5],
                    callSegments[6],
                    Integer.valueOf(callSegments[7]));
        } else {
            CallSeqModel callSeqModel = new CallSeqModel(
                    Integer.valueOf(callSegments[1]),
                    callSegments[2],
                    Integer.valueOf(callSegments[3]),
                    callSegments[4],
                    null,
                    null,
                    Integer.valueOf(callSegments[7]));

            callSeqModel.setReturnVal(callSegments[5]);
            callSeqModel.setArgumentVal(callSegments[6]);
            return callSeqModel;
        }
    }
}
