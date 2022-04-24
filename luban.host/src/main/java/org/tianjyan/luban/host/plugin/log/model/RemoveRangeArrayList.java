package org.tianjyan.luban.host.plugin.log.model;

import java.util.ArrayList;

public class RemoveRangeArrayList<E> extends ArrayList<E> {

    private static final long serialVersionUID = -8398394812362912219L;

    public void remove(int fromIndex, int toIndex) {
        removeRange(fromIndex, toIndex);
    }
}
