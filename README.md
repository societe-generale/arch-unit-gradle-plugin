# ArchUnit Gradle plugin

[![Build Status](https://travis-ci.org/societe-generale/arch-unit-gradle-plugin.svg?branch=master)](https://travis-ci.org/societe-generale/arch-unit-gradle-plugin)



![Gefa logo](./gefa_logo.png)



In the dev team at [Gefa Bank GmbH](https://www.gefa-bank.de/), we really liked how [ArchUnit Maven plugin](https://github.com/societe-generale/arch-unit-maven-plugin) was enabling teams to distribute rules across projects. Only "problem" was that we're using Gradle, not Maven. So we decided to write an equivalent plugin, but for Gradle. 

A gradle wrapper around ArchUnit, to easily share and enforce architecture rules across projects. This plugin is under construction and the java tests are still missing!

The option to use configurable rules has been added but has not been tested yet!

This plugin is based on https://github.com/societe-generale/arch-unit-build-plugin-core.

To use the plugin, some steps have to be done in the `build.gradle` file in the project where you want to use this plugin :

1. Your build script has to contain following code, to set a dependency on the plugin:

```
   buildscript {
       dependencies {
           classpath "com.societegenerale.commons:arch-unit-gradle-plugin:1.0.2"
       }
       repositories {
           mavenCentral()
       }
   }
```

2. You have to apply the java plugin and the ArchUnitGradlePlugin, then configure it:

```
    allprojects {
    
        apply plugin: 'java'
        apply plugin: 'com.societegenerale.commons.plugin.gradle.ArchUnitGradlePlugin'
    
        archUnit{
        
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
  
4. Build your project with gradlew clean build : if some of your code is not compliant with the rules defined, the build will fail, pointing you to the rule and the class that is violating it.

### Releasing a new version of the plugin

(to publish in local repo during tests, use gradle -Dmaven.repo.local=.m2/repository publishToMavenLocal)

- make sure everything is committed, then run `gradlew release -Prelease.useAutomaticVersion=true` : 

  - the -SNAPSHOT will be removed
  - the version will be tagged and committed
  - the version will be incremented with -SNAPSHOT  
  
