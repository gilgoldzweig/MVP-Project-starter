# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.2.2] - 2020-01-27

### Fix

  - Cleanup and improvements to BasePresenter 

## [1.2.1] - 2020-01-27

### Fix

  - Old version of GlobalSharedPreferences was added


## [1.2.0] - 2020-01-27

### Created 

  - [MutableStateLiveData], will replace [StateLiveData] for all future releases
  - [ImmutableStateLiveData], will replace [StateLiveData] for all future releases
  
### Deprecated 

  - [StateLiveData], replaced by [MutableStateLiveData] & [ImmutableStateLiveData]
  
### Changed

  - [MutableStateLiveData] will post [Created] when initiated
  - Opened private setters in [StateData]
