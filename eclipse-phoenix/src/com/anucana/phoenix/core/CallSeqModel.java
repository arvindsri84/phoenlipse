package com.anucana.phoenix.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;


public class CallSeqModel implements Comparable<CallSeqModel> {

    private final int callSeqId;

    private final String className;
    private final String method;

    private final String argumentType;
    private final String returnType;

    private String argumentVal = null;
    private String returnVal = null;

    private final int modifier;

    private IFile file = null;
    private final int lineNumber;

    public CallSeqModel(int callSeqId, String className, int modifier, String method, String returnType, String argumentType, int lineNumber) {
        this.callSeqId = callSeqId;
        this.className = className;
        this.method = method;
        this.argumentType = argumentType;
        this.returnType = returnType;
        this.modifier = modifier;
        this.lineNumber = lineNumber;
    }

    public int getCallSeqId() {
        return callSeqId;
    }


    public String getClassName() {
        return className;
    }


    public String getMethod() {
        return method;
    }


    public String getArgumentType() {
        return argumentType;
    }


    public int getModifier() {
        return modifier;
    }

    public String getReturnType() {
        return returnType;
    }


    public String getArgumentVal() {
        return argumentVal;
    }


    public String getReturnVal() {
        return returnVal;
    }


    public void setArgumentVal(String argumentVal) {
        this.argumentVal = argumentVal;
    }


    public void setReturnVal(String returnVal) {
        this.returnVal = returnVal;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setFile(IFile file) {
        this.file = file;
    }

    public IMarker getMarker() throws CoreException {
        if (file != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(IMarker.LINE_NUMBER, lineNumber);
            // map.put(IWorkbenchPage.EDITOR_ID_ATTR, "org.eclipse.ui.DefaultTextEditor");
            IMarker marker = file.createMarker(IMarker.TEXT);
            marker.setAttributes(map);

            return marker;
        }
        return null;
    }


    public IFile getFile() {
        return file;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CallSeqModel
                && ((CallSeqModel) o).getCallSeqId() == getCallSeqId()
                && ((CallSeqModel) o).getClassName().equals(getClassName())
                && ((CallSeqModel) o).getLineNumber().equals(getLineNumber());
    }

    @Override
    public String toString() {
        return getCallSeqId() + "|" + getClassName() + "|" + getLineNumber();
    }


    public int compareTo(CallSeqModel o) {
        return Integer.valueOf(getCallSeqId()).compareTo(o.getCallSeqId());
    }

}
