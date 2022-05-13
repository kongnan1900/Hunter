package com.quinn.hunter.plugin.timing.bytecode;

import com.quinn.hunter.plugin.timing.TimingHunterExtension;
import com.quinn.hunter.transform.asm.BaseWeaver;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.gradle.api.logging.Logger;
import org.gradle.api.Project;

/**
 * Created by Quinn on 09/07/2017.
 */
public final class TimingWeaver extends BaseWeaver {

    private static final String PLUGIN_LIBRARY = "com.hunter.library";

    private TimingHunterExtension timingHunterExtension;

    private final Logger logger;

    public TimingWeaver(Project project) {
        super();
        this.logger = project.getLogger();
    }

    @Override
    public void setExtension(Object extension) {
        if(extension == null) return;
        this.timingHunterExtension = (TimingHunterExtension) extension;
    }

    @Override
    public boolean isWeavableClass(String fullQualifiedClassName) {
        boolean superResult = super.isWeavableClass(fullQualifiedClassName);
        boolean isByteCodePlugin = fullQualifiedClassName.startsWith(PLUGIN_LIBRARY);
        if(timingHunterExtension != null) {
            //whitelist is prior to to blacklist
            if(!timingHunterExtension.whitelist.isEmpty()) {
                boolean inWhiteList = false;
                for(String item : timingHunterExtension.whitelist) {
                    if(fullQualifiedClassName.startsWith(item)) {
                        inWhiteList = true;
                    }
                }
                log(fullQualifiedClassName, superResult && !isByteCodePlugin && inWhiteList,1);
                return superResult && !isByteCodePlugin && inWhiteList;
            }
            if(!timingHunterExtension.blacklist.isEmpty()) {
                boolean inBlackList = false;
                for(String item : timingHunterExtension.blacklist) {
                    if(fullQualifiedClassName.startsWith(item)) {
                        inBlackList = true;
                    }
                }
                log(fullQualifiedClassName, superResult && !isByteCodePlugin && !inBlackList,2);
                return superResult && !isByteCodePlugin && !inBlackList;
            }
        }
        log(fullQualifiedClassName, superResult && !isByteCodePlugin,3);
        return superResult && !isByteCodePlugin;
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new TimingClassAdapter(classWriter);
    }

    private void log(String fullQualifiedClassName, boolean ret, int from) {
        logger.warn("timing-weave-class: " + fullQualifiedClassName + "," + ret + "," + from);
    }

}
