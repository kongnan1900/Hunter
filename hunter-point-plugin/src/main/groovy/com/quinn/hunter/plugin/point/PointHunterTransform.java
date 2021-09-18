package com.quinn.hunter.plugin.point;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.quinn.hunter.plugin.point.bytecode.PointWeaver;
import com.quinn.hunter.transform.HunterTransform;
import com.quinn.hunter.transform.RunVariant;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by quinn on 07/09/2018
 */
public final class PointHunterTransform extends HunterTransform {

    private Project project;
    private PointHunterExtension pointHunterExtension;

    public PointHunterTransform(Project project) {
        super(project);
        this.project = project;
        project.getExtensions().create("pointHunterExt", PointHunterExtension.class);
        this.bytecodeWeaver = new PointWeaver(project);
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        pointHunterExtension = (PointHunterExtension) project.getExtensions().getByName("pointHunterExt");
        bytecodeWeaver.setExtension(pointHunterExtension);
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }

    protected RunVariant getRunVariant() {
        return pointHunterExtension.runVariant;
    }

    @Override
    protected boolean inDuplicatedClassSafeMode() {
        return pointHunterExtension.duplicatedClassSafeMode;
    }
}
