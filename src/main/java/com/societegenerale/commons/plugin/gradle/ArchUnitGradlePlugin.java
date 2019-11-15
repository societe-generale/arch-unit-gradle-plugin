package com.societegenerale.commons.plugin.gradle;

import org.gradle.api.*;


public class ArchUnitGradlePlugin implements Plugin<Project> {

    public void apply(Project project) {

        ArchUnitGradleConfig archUnitGradleConfig = project.getExtensions().create("archUnit", ArchUnitGradleConfig.class, project);

        Task archUnitTask=project.getTasks().create("checkRules", ArchUnitRulesTask.class, archUnitGradleConfig);

        archUnitTask.setGroup("verification");

        System.out.println("project build path : "+project.getBuildDir());

        final Task checkTask = project.getTasks().findByName("check");

        final Task testTask = project.getTasks().findByName("test");

        if (checkTask==null){
            throw new GradleException("can't find the 'check' task on which archUnitGradle task will depend - please check Gradle java plugin is applied");
        }

        if (testTask==null){
            throw new GradleException("can't find the 'test' task on which archUnitGradle task will depend - please check Gradle java plugin is applied");
        }


        checkTask.dependsOn(archUnitTask);
        archUnitTask.mustRunAfter(testTask);

    }
}
