# Changelog - see https://keepachangelog.com for conventions

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

## [2.7.3] - 2021-03-31

### Changed
- Upgrading to arch-unit-build-plugin-core 2.7.3

## [2.7.2] - 2021-02-03

### Changed
- Upgrading to arch-unit-build-plugin-core 2.7.2

## [2.7.1] - 2021-01-27

### Changed
- Upgrading to arch-unit-build-plugin-core 2.7.1

## [2.7.0] - 2021-01-21

### Changed
 - upgraded to arch-unit-build-plugin-core:2.7.0 for Java 15 compatibility

## [2.6.1] - 2020-08-19

### Fixed
 - GradleScopePathProvider now uses project build paths

## [2.6.0] - 2020-08-19

### Changed
 - PR #15 - upgrading to arch unit core 2.6.0
 - Aligning arch-unit-gradle-plugin version with arch-unit-core 

## [1.1.1] - 2020-06-20

### Changed
 - PR #14 - upgrading to arch unit core 2.5.3

## [1.1.0] - 2020-03-04

### Changed
 - PR #12 - upgrading to arch unit core 2.5.2
 
### Fixed
 - PR #11 - fixing some error reporting

## [1.0.2] - 2019-11-16

### Fixed
 - we can now define a configurableRule with no check : all of them will be executed by default
 - classes to load now have better chances to be found (using a Gradle specific scope path provider)

## [1.0.1] - 2019-11-11

### Fixed
 - Setting a default empty list for preconfiguredRules and configurableRules to avoid NPE later on in case one is not defined through plugin config

## [1.0.0] - 2019-11-10

### First version !
