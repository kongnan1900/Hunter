package com.quinn.hunter.plugin.point.bytecode;

import com.quinn.hunter.plugin.point.PointHunterExtension;
import com.quinn.hunter.transform.asm.BaseWeaver;

import org.gradle.api.Project;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.gradle.api.logging.Logger;

/**
 * Created by Quinn on 09/07/2017.
 */
public final class PointWeaver extends BaseWeaver {

    private static final String PLUGIN_LIBRARY = "com.hunter.library";
    private static final String PLUGIN_HH = "cn.huohua.commonlib.point";

    private PointHunterExtension pointHunterExtension;

    private final Logger logger;

    public PointWeaver(Project project) {
        super();
        this.logger = project.getLogger();
    }

    @Override
    public void setExtension(Object extension) {
        if(extension == null) return;
        this.pointHunterExtension = (PointHunterExtension) extension;
    }

    @Override
    public boolean isWeavableClass(String fullQualifiedClassName) {
        boolean superResult = super.isWeavableClass(fullQualifiedClassName);
        boolean isByteCodePlugin = fullQualifiedClassName.startsWith(PLUGIN_LIBRARY) || fullQualifiedClassName.startsWith(PLUGIN_HH);
        if(pointHunterExtension != null) {
            boolean inBlackList = false;
            boolean inWhiteList = false;
            if(!pointHunterExtension.blacklist.isEmpty()) {
                for(String item : pointHunterExtension.blacklist) {
                    if(fullQualifiedClassName.startsWith(item)) {
                        inBlackList = true;
                    }
                }
            }
            if(!pointHunterExtension.whitelist.isEmpty()) {
                for(String item : pointHunterExtension.whitelist) {
                    if(fullQualifiedClassName.startsWith(item)) {
                        inWhiteList = true;
                    }
                }
            }
            //在白名单内并且不在黑名单内
            if (inWhiteList && !inBlackList) {
                boolean ret = superResult && !isByteCodePlugin;
//                if (ret) {
//                    logger.warn("weave-class: " + fullQualifiedClassName);
//                }
                return ret;
            }
        }
        return false;
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new PointClassAdapter(classWriter);
    }

}
