package com.anucana.phoenix.rt.classes;

import org.aspectj.lang.JoinPoint;


public class ConcatCallSequence implements ICallSeqFormatter {

    public static final String LOG_MAIN_SEPERATOR = "|";
    public static final String LOG_METH_ARG_SEPERATOR = ",";

    private String callId = null;
    private long seqNumber;
    private Object output = null;
    private JoinPoint thisJoinPoint = null;

    public StringBuilder formatCallSeq() {

        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append(callId).append(LOG_MAIN_SEPERATOR);
        strBuilder.append(seqNumber).append(LOG_MAIN_SEPERATOR);

        representClass(strBuilder);
        strBuilder.append(LOG_MAIN_SEPERATOR);

        representAccessModifier(strBuilder);
        strBuilder.append(LOG_MAIN_SEPERATOR);

        representMethdName(strBuilder);
        strBuilder.append(LOG_MAIN_SEPERATOR);

        representReturnType(strBuilder);
        strBuilder.append(LOG_MAIN_SEPERATOR);

        representArgurments(strBuilder, LOG_METH_ARG_SEPERATOR);
        strBuilder.append(LOG_MAIN_SEPERATOR);

        representLineNumber(strBuilder);
        return strBuilder;

    }

    private void representLineNumber(final StringBuilder strBuilder) {
        strBuilder.append(thisJoinPoint.getStaticPart().getSourceLocation().getLine());
    }

    private void representClass(final StringBuilder strBuilder) {
        strBuilder.append(thisJoinPoint.getStaticPart().getSourceLocation().getWithinType().getName());
    }

    private void representAccessModifier(final StringBuilder strBuilder) {
        strBuilder.append(thisJoinPoint.getStaticPart().getSignature().getModifiers());
    }

    private void representMethdName(final StringBuilder strBuilder) {
        strBuilder.append(thisJoinPoint.getStaticPart().getSignature().getName());
    }

    private void representReturnType(final StringBuilder strBuilder) {
        if (this.callId.contains(ICallSeqFormatter.FORWARD_CALL_ID_PREFIX)) {
            // for forward calls we are interested only in argument type
            strBuilder.append(deriveReturnType());
        } else {
            // for backward calls we are interested in actual arguments
            // TODO : to use apache's ToStringBuilder for it to be more useful
            if (output != null) {
                strBuilder.append(output);
            } else {
                strBuilder.append("void");
            }
        }

    }

    private String deriveReturnType() {
        String modifier = java.lang.reflect.Modifier.toString(thisJoinPoint.getStaticPart().getSignature().getModifiers());
        String[] modifiers = modifier.split(" ");

        int returnTypeIndex = 0;
        if (modifier.trim().length() != 0) {
            returnTypeIndex = modifiers.length;
        }

        return thisJoinPoint.getSignature().toLongString().split(" ")[returnTypeIndex];
    }

    private void representArgurments(final StringBuilder strBuilder, final String seperator) {
        if (this.callId.contains(ICallSeqFormatter.FORWARD_CALL_ID_PREFIX)) {
            // for forward calls we are interested only in argument type
            strBuilder.append(deriveArgumentType());
        } else {
            // for backward calls we are interested in actual arguments
            Object[] args = thisJoinPoint.getArgs();
            if (args != null && args.length != 0) {
                for (int index = 0; index < args.length; index++) {
                    if (index != 0) {
                        strBuilder.append(seperator);
                    }
                    // TODO : to use apache's ToStringBuilder for it to be more useful
                    strBuilder.append(args[index]);
                }
            } else {
                strBuilder.append("void");
            }

        }

    }

    private String deriveArgumentType() {
        String methodSignature = thisJoinPoint.getSignature().toLongString();
        String[] firstSplit = methodSignature.split("\\(");
        String[] secondSplit = firstSplit[1].split("\\)");

        // return the arguments if they exist
        if (secondSplit != null && secondSplit.length != 0) {
            return secondSplit[0];
        }
        return "void";
    }

    public void setCallSeqNum(long seqNum) {
        this.seqNumber = seqNum;
    }

    public void setJoinPoint(JoinPoint thisJoinPoint) {
        this.thisJoinPoint = thisJoinPoint;
    }

    public void setOutput(Object output) {
        this.output = output;

    }

    public void setCallId(String callId) {
        this.callId = callId;
    }
}
