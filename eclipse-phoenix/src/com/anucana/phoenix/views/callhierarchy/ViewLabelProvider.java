package com.anucana.phoenix.views.callhierarchy;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;

import com.anucana.phoenix.core.ImageCache;

public class ViewLabelProvider extends StyledCellLabelProvider {

    @Override
    public void update(ViewerCell cell) {
        TreeNode treeNode = (TreeNode) cell.getElement();
        StyledString text = new StyledString();

        cell.setImage(ImageCache.getMethodModifierImage(treeNode.getModel().getModifier()));
        text.append(treeNode.getModel().getMethod());
        text.append(" (" + treeNode.getModel().getArgumentType() + ") ");
        text.append(" : " + treeNode.getModel().getReturnType(), StyledString.COUNTER_STYLER);
        text.append(" - " + treeNode.getModel().getClassName(), StyledString.DECORATIONS_STYLER);

        cell.setText(text.toString());
        cell.setStyleRanges(text.getStyleRanges());

        super.update(cell);
    }
}
