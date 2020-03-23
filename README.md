# ArchUnit Gradle plugin

[![Build Status](https://travis-ci.org/societe-generale/arch-unit-gradle-plugin.svg?branch=master)](https://travis-ci.org/societe-generale/arch-unit-gradle-plugin)



<p align="center">
  <img src="./gefa_logo.png" title="Gefa Bank Logo">
</p>


### Context

In our dev team at [Gefa Bank GmbH](https://www.gefa-bank.de/), we really liked how [ArchUnit Maven plugin](https://github.com/societe-generale/arch-unit-maven-plugin) was enabling teams to distribute rules across projects. Only "problem" was that we're using Gradle, not Maven. So we decided to write an equivalent plugin for Gradle. 

### What is it, and how does it work ?

ArchUnit-Gradle-plugin is a wrapper around [Arch-Unit-Build-Plugin-Core](https://github.com/societe-generale/arch-unit-build-plugin-core), which itself is a wrapper around [ArchUnit](https://www.archunit.org/), that enables you to easily make sure all your projects follow the same architecture rules.
 
Using a plugin brings a way to manage the rules through build configuration and to easily share and enforce architecture rules across projects. 

To use the plugin, your `build.gradle` require these changes:

1. Declare the dependency to the plugin :

```Gradle
   buildscript {
       dependencies {
           classpath "com.societegenerale.commons:arch-unit-gradle-plugin:1.0.3"
       }
       repositories {
           mavenCentral()
       }
   }
```

2. Apply the `java` plugin and the `ArchUnitGradlePlugin`, then configure it:

```Gradle
    allprojects {
    
        apply plugin: 'java'
        apply plugin: 'com.societegenerale.commons.plugin.gradle.ArchUnitGradlePlugin'
    
        archUnit{
        
                 excludedPaths=["generated-sources"]

                  preConfiguredRules=["com.societegenerale.commons.plugin.rules.NoInjectedFieldTest",
                            "com.societegenerale.commons.plugin.rules.NoAutowiredFieldTest",
                            "com.societegenerale.commons.plugin.rules.NoTestIgnoreWithoutCommentRuleTest",
                            "com.societegenerale.commons.plugin.rules.NoPrefixForInterfacesRuleTest",
                            "com.societegenerale.commons.plugin.rules.NoPowerMockRuleTest",
                            "com.societegenerale.commons.plugin.rules.NoJodaTimeRuleTest",
                            "com.societegenerale.commons.plugin.rules.NoJunitAssertRuleTest",
                            "com.societegenerale.commons.plugin.rules.HexagonalArchitectureTest",
                            "com.societegenerale.commons.plugin.rules.DontReturnNullCollectionTest"
                                    ]

                  configurableRules=[configurableRule("com.tngtech.archunit.library.GeneralCodingRules", applyOn("com.my.project","main") )]
  
        }
    }
    
```
  
4. Build your project with `gradlew clean build` : if some of your code is not compliant with the rules defined, the build will fail, pointing you to the rule(s) and the class(es) that are violating it.

### Adding new rules

All rules referenced in the configuration have to be available in the classpath. Therefore, you have 2 solutions : 
- package your rule into a custom jar, add a dependency to this jar (probably with `test` scope) and declare the rule in the config
- Propose your rule through a PullRequest to [Arch-Unit-Build-Plugin-Core](https://github.com/societe-generale/arch-unit-build-plugin-core) : if it's accepted, it will be part of the next release and usable by everyone. 

### Releasing a new version of the plugin

(to publish in local repo during tests, use `gradlew -Dmaven.repo.local=.m2/repository publishToMavenLocal`)

- make sure everything is committed, then run `gradlew release -Prelease.useAutomaticVersion=true` : 

  - the -SNAPSHOT will be removed
  - the version will be tagged and committed
  - the version will be incremented with -SNAPSHOT
  - the plugin will be uploaded to https://plugins.gradle.org/plugin/com.societegenerale.common.arch-unit-gradle-plugin/   
  
