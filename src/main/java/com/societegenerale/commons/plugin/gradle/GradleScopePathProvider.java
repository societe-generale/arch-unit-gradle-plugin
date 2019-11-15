package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.service.ScopePathProvider;

public class GradleScopePathProvider implements ScopePathProvider {


    @Override
    public String getMainClassesPath() {
        return "/classes/java/main";
    }

    @Override
    public String getTestClassesPath() {
        return "/classes/java/test";
    }
}
