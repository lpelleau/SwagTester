# SwagTester
## Synopsis
SwagTester is a **fuzzing** library allowing you to test an API Rest based on **[Swagger](http://swagger.io/)** specification. SwagTester allow you to send random requests either valid (specification of the API respected) or invalid (wrong types, number of elements...). You can also write a Json file with tour tests and get metricsof the results.

## Motivation
This project took place in a Software Testing course. The goal of this project is to create a fuzzing library which can test an API Rest specified with a Swagger file.

## Installation
1. Clone the repository on your computer:
`git clone https://github.com/lpelleau/SwagTester.git && cd SwagTester`
2. compile the project to create the *.jar* file with `./gradlew <INCOMPÉTENCE DE REUZEAU>`
3. !!! Voir si les dépendences sont dans le .jar !!!
4. Import the *.jar* file in your project

## Usage exemples

## Documentation
### Summary
1. [HTTP Methods allowed](#http-methods-allowed)
2. [Test types](#test-types)
    2.1. [Valid test](#valid-test)
    2.2. [Invalid test](#invalid-test)
    2.3. [Extrem values test](#extrem-test)
    2.4. [Timeout test](#timeout-test)
    2.5. [Scalling test](#scalling-test)

### Content
#### 1. HTTP Methods allowed
The HTTP methods allowed by this library are **POST**, **GET**, **PUT**, **DELETE**, **OPTION**, **PATCH**, **HEAD**.
If you try to send a request to an entry point and that the method you use is not defined, the appropriate Java method (i.e. *putMethod()*, *postMethod()*, ...) will return null.

#### 2. Test types
##### 2.1. Valid test
A valid test will follow the specification of the swagger API.
The values generated will be possible values: they will respect the types and the ranges.

##### 2.2. Invalid test
An invalid test won't follow the specification of the swagger API.
The values generated will be impossible values: they will of a different type or out of range.

##### 2.3. Extrem values test
Fill the values with data at the extremums of the specifications.
For exemple, if the type of the data is integer, we will try *0*, *2^32-1* (negative) and *2^31-1* (positive).

##### 2.4. Timeout test
*NOT IMPLEMENTED*

##### 2.5. Scalling test
*NOT IMPLEMENTED*

## Todo & Contribute
* Handle XML (only Json is accepted for now)
* Improve the strenght with types
* Improve the expected file parsing (to cover more types)
* Timeout test
* Scalling test

This software is under MIT license.
Also, feel free to **contribute** or discuss with us about possible improvements!

## Authors
* Adrien Reuzeau
* Loïc Pelleau
