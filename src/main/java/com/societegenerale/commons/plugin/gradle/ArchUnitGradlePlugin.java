package com.societegenerale.commons.plugin.gradle;

import org.gradle.api.*;
import org.gradle.api.artifacts.Configuration;


public class ArchUnitGradlePlugin implements Plugin<Project> {

    public void apply(Project project) {

        Configuration conf = project.getConfigurations().create("archUnitExtraLib");
        ArchUnitGradleConfig archUnitGradleConfig = project.getExtensions()
            .create("archUnit", ArchUnitGradleConfig.class, project);

        ArchUnitRulesTask archUnitTask = project.getTasks().create("checkRules", ArchUnitRulesTask.class);
        archUnitTask.setGroup("verification");
        archUnitTask.getClasspath().from(conf);
        archUnitTask.getArchUnitGradleConfig().convention(archUnitGradleConfig);

        final Task checkTask = findExistingTaskOrFailOtherwise("check", project);
        final Task testTask = findExistingTaskOrFailOtherwise("test", project);

        checkTask.dependsOn(archUnitTask);
        archUnitTask.mustRunAfter(testTask);

    }

    private Task findExistingTaskOrFailOtherwise(String taskName, Project project){

        final Task taskToFind = project.getTasks().findByName(taskName);

        if (taskToFind==null){
            throw new GradleException("can't find the '"+taskName+"' task on which archUnitGradle task will depend - please check Gradle java plugin is applied");
        }

        return taskToFind;
    }
}
