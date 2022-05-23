package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.model.ApplyOn;
import com.societegenerale.commons.plugin.model.ConfigurableRule;
import com.societegenerale.commons.plugin.model.Rules;
import org.gradle.api.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArchUnitGradleConfig {

    private List<ConfigurableRule> configurableRules = new ArrayList<>();

    private List<String> preConfiguredRules = new ArrayList<>();
    
    private List<String> excludedPaths = new ArrayList<>();

    private Project project;

    private boolean skip = false;

    public ArchUnitGradleConfig(Project project) {
        this.project = project;
    }

    public void setPreConfiguredRules(List<String> preConfiguredRules) {
        this.preConfiguredRules = preConfiguredRules;
    }

    public void setConfigurableRules(List<ConfigurableRule> configurableRules) {
        this.configurableRules = configurableRules;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public ApplyOn applyOn(String packageName,String scope){
        return new ApplyOn(packageName,scope);
    }

    public List<String> getPreConfiguredRules() {
        return preConfiguredRules;
    }

    public List<ConfigurableRule> getConfigurableRules() {
        return configurableRules;
    }

    public List<String> check(String... checks){ return Arrays.asList(checks); }

    public Rules getRules() {
        return new Rules(preConfiguredRules,configurableRules);
    }

    public ConfigurableRule configurableRule(String rule, ApplyOn applyOn, List<String> checks) {
        return new ConfigurableRule(rule,applyOn,checks,false);
    }

    public ConfigurableRule configurableRule(String rule, ApplyOn applyOn) {
        return new ConfigurableRule(rule,applyOn,new ArrayList<>(),false);
    }
  
    public boolean isSkip() {
        return skip;
    }

    public String getBuildPath() {
        return project.getBuildDir().toString();
    }
    public List<String> getExcludedPaths() {
        return excludedPaths;
    }

    public void setExcludedPaths(List<String> excludedPaths) {
        this.excludedPaths = excludedPaths;
    }

}
