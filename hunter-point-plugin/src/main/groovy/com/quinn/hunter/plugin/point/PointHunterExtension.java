package com.quinn.hunter.plugin.point;

import com.quinn.hunter.transform.RunVariant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quinn on 27/06/2017.
 * whitelist is prior to to blacklist
 */
public class PointHunterExtension {

    public RunVariant runVariant = RunVariant.ALWAYS;
    public List<String> whitelist = new ArrayList<>();
    public List<String> blacklist = new ArrayList<>();
    public boolean duplicatedClassSafeMode = false;

    @Override
    public String toString() {
        return "PointHunterExtension{" +
                "runVariant=" + runVariant +
                ", whitelist=" + whitelist +
                ", blacklist=" + blacklist +
                ", duplicatedClassSafeMode=" + duplicatedClassSafeMode +
                '}';
    }
}
