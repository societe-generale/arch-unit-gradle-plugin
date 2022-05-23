package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.model.RootClassFolder;
import com.societegenerale.commons.plugin.service.ScopePathProvider;

public class GradleScopePathProvider implements ScopePathProvider {

    private final RootClassFolder mainClassesPath;
    private final RootClassFolder testClassesPath;

    public GradleScopePathProvider(String mainPath, String testPath) {
        mainClassesPath = new RootClassFolder(mainPath);
        testClassesPath = new RootClassFolder(testPath);
    }

    @Override
    public RootClassFolder getMainClassesPath() {
        return mainClassesPath;
    }

    @Override
    public RootClassFolder getTestClassesPath() {
        return testClassesPath;
    }
}
