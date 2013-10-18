package com.anucana.phoenix.views.callhierarchy;

import java.util.ArrayList;

import com.anucana.phoenix.core.CallSeqModel;

public class TreeNode extends TreeLeaf {

    private final ArrayList<TreeLeaf> children;
    private final CallSeqModel model;

    public TreeNode(String name, CallSeqModel model) {
        super(name);
        if (model == null) {
            throw new IllegalArgumentException("CallSequence Model can not be null");
        }
        this.model = model;
        children = new ArrayList<TreeLeaf>();
    }

    public void addChild(TreeLeaf child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(TreeLeaf child) {
        children.remove(child);
        child.setParent(null);
    }

    public TreeLeaf[] getChildren() {
        return children.toArray(new TreeLeaf[children.size()]);
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    public CallSeqModel getModel() {
        return model;
    }


}
