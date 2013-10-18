package com.anucana.phoenix.views.callhierarchy;



public class TreeLeaf {

    private final String name;
    private TreeNode parent;

    public TreeLeaf(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return getName();
    }

}
