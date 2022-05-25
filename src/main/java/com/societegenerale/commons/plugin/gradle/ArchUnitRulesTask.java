package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.Log;
import com.societegenerale.commons.plugin.model.Rules;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.TaskAction;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;

public abstract class ArchUnitRulesTask extends DefaultTask {

    @InputFiles
    @Classpath
    public abstract ConfigurableFileCollection getClasspath();

    @Input
    public abstract Property<ArchUnitGradleConfig> getArchUnitGradleConfig();

    @Inject
    protected abstract WorkerExecutor getWorkerExecutor();


    private final Log logger = new GradleLogAdapter(LoggerFactory.getLogger(ArchUnitRulesTask.class));

    @TaskAction
    public void checkRules() {

        ArchUnitGradleConfig archUnitGradleConfig = getArchUnitGradleConfig().get();

        if (archUnitGradleConfig.isSkip()) {
            logger.warn("Rule checking has been skipped!");
            return;
        }

        Rules rules = archUnitGradleConfig.getRules();

        if (!rules.isValid()) {
            throw new GradleException("Arch unit Gradle Plugin should have at least one preconfigured/configurable rule!");
        }

        WorkQueue queue = getWorkerExecutor().classLoaderIsolation(spec -> spec.getClasspath().from(getClasspath()));
        queue.submit(WorkerAction.class, params -> {
            params.getMainClassesPath().set(archUnitGradleConfig.getBuildPath() + "/classes/java/main");
            params.getTestClassesPath().set(archUnitGradleConfig.getBuildPath() + "/classes/java/test");
            params.getProjectBuildDirPath().set(archUnitGradleConfig.getBuildPath());
            params.getPreConfiguredRules().addAll(archUnitGradleConfig.getPreConfiguredRules());
            params.getConfigurableRules().addAll(archUnitGradleConfig.getConfigurableRules());
        });

    }

}