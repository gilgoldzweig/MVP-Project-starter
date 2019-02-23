# MVP - Project starter
![Jitpack versiob](https://jitpack.io/v/gilgoldzweig/MVP.svg)
![Codacy Badge](https://api.codacy.com/project/badge/Grade/e3da2babcb5046bebe7c9cf32138d8ce)
![Lincence](https://img.shields.io/github/license/gilgoldzweig/MVP.svg)
![Say Thanks!](https://img.shields.io/badge/Say%20Thanks-!-1EAEDB.svg)

A suite of classes for implementing a testable MVP design pattern with kotlin coroutines
# Implement in your project

## Using gradle

      repositories {
        maven { url "https://jitpack.io" }
      }
      dependencies {
	  	implementation "com.github.gilgoldzweig:MVP:latest_version"
      }
      
### Usage
This library contains 3 main utilities 

1. Base MVP classes as described in the article plus a Coroutines BasePresenter class
2. Custom version of Timber to supoort logging any type of object and analytic events/non fatel exception
3. A system wide SharedPreferences 

For now Look at the sample for some usage, Real documentation coming soon

###
This library is an implementation of [How to make MVP testable](https://medium.com/@gilgoldzweig/how-to-write-a-testable-mvp-in-kotlin-b099ab46a3df) with all the base classes I normaly use

### Contributing
If you want to contribute to this project check if there are any open issues or just send a pull request and I'll do my best to include it

Before submitting a pull request, make sure you follow the code style and documentation.

Pull requests without tests will be ignored.


### License

     Copyright [2019] [Gil Goldzweig Goldbaum]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
