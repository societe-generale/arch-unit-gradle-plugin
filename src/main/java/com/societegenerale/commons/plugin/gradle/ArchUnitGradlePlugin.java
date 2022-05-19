package com.societegenerale.commons.plugin.gradle;

import org.gradle.api.*;
import org.gradle.api.artifacts.Configuration;

public class ArchUnitGradlePlugin implements Plugin<Project> {

    public void apply(Project project) {

        Configuration conf = project.getConfigurations().create("archUnitClasspath");
        ArchUnitGradleConfig archUnitGradleConfig = project.getExtensions()
                .create("archUnit", ArchUnitGradleConfig.class, project);

        final Task checkTask = findExistingTaskOrFailOtherwise("check",project);

        final Task testTask = findExistingTaskOrFailOtherwise("test",project);

        project.getTasks().register("checkRules", ArchUnitRulesTask.class,
                t -> {
                    t.setGroup("verification");
                    checkTask.dependsOn(t);
                    t.mustRunAfter(testTask);

                    t.getClasspath().from(conf);
                    t.getPreConfiguredRules().convention(archUnitGradleConfig.getRules().getPreConfiguredRules());
                });
    }

    private Task findExistingTaskOrFailOtherwise(String taskName, Project project){

        final Task taskToFind = project.getTasks().findByName(taskName);

        if (taskToFind==null){
            throw new GradleException("can't find the '"+taskName+"' task on which archUnitGradle task will depend - please check Gradle java plugin is applied");
        }

        return taskToFind;
    }
}
