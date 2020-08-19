package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.model.RootClassFolder;
import com.societegenerale.commons.plugin.service.ScopePathProvider;

public class GradleScopePathProvider implements ScopePathProvider {


    @Override
    public RootClassFolder getMainClassesPath() {
        return new RootClassFolder("/classes/java/main");
    }

    @Override
    public RootClassFolder getTestClassesPath() {
        return new RootClassFolder("/classes/java/test");
    }
}
