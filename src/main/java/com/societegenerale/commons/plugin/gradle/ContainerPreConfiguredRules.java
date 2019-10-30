package com.societegenerale.commons.plugin.gradle;

public class ContainerPreConfiguredRules {
    private String name;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    private String rule;

    public ContainerPreConfiguredRules(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
