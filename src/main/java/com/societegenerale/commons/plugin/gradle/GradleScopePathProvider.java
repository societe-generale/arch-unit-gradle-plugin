package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.model.RootClassFolder;
import com.societegenerale.commons.plugin.service.ScopePathProvider;

import static org.apache.commons.lang3.StringUtils.isAllBlank;

public class GradleScopePathProvider implements ScopePathProvider {

    private ArchUnitGradleConfig archUnitGradleConfig;

    public GradleScopePathProvider(ArchUnitGradleConfig archUnitGradleConfig) {
        this.archUnitGradleConfig=archUnitGradleConfig;
    }

    @Override
    public RootClassFolder getMainClassesPath() {

        String mainClassesPath = isAllBlank(archUnitGradleConfig.getScopePaths().getMain()) ?
                archUnitGradleConfig.getScopePaths().getMain() : "/classes/java/main";

        return new RootClassFolder(
                archUnitGradleConfig.getBuildPath() + mainClassesPath);
    }

    @Override
    public RootClassFolder getTestClassesPath() {

        String testClassesPath = isAllBlank(archUnitGradleConfig.getScopePaths().getTest()) ?
                archUnitGradleConfig.getScopePaths().getTest() : "/classes/java/test";

        return new RootClassFolder(
                archUnitGradleConfig.getBuildPath() + testClassesPath);
    }
}
