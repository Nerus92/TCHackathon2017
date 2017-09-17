package com.ist.android.issomeonethere.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjeannin on 9/16/17.
 */

public class Obsolete {

    List<String> oldies;

    public List<String> getOldies() {
        return oldies;
    }

    public void setOldies(List<String> oldies) {
        this.oldies = oldies;
    }

    public Obsolete() {
        oldies = new ArrayList<String>();
    }

    public void add(String s) {
        oldies.add(s);
    }

    boolean isObsolete(String uuid) {
        // return true is the given uuid is in the obsolete list
        return false;
    }

    public void clear() {
        oldies.clear();
    }

}
