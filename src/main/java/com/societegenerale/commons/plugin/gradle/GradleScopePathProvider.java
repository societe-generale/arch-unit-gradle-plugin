package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.model.RootClassFolder;
import com.societegenerale.commons.plugin.service.ScopePathProvider;

public class GradleScopePathProvider implements ScopePathProvider {

    private ArchUnitGradleConfig archUnitGradleConfig;

    public GradleScopePathProvider(ArchUnitGradleConfig archUnitGradleConfig) {
        this.archUnitGradleConfig=archUnitGradleConfig;
    }

    @Override
    public RootClassFolder getMainClassesPath() {
        return new RootClassFolder(
                archUnitGradleConfig.getBuildPath() +"/classes/java/main");
    }

    @Override
    public RootClassFolder getTestClassesPath() {
        return new RootClassFolder(
                archUnitGradleConfig.getBuildPath() +"/classes/java/test");
    }
}
