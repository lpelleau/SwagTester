# SwagTester
## Synopsis
SwagTester is a **fuzzing** library allowing you to test a Rest API based on **[Swagger](http://swagger.io/)** specification. SwagTester allow you to send random requests either valid (specification of the API respected) or invalid (wrong types, number of elements...). You can also write a Json file with your tests and get metrics of the results.

## Motivation
This project took place in a Software Testing course. The goal of this project is to create a fuzzing library which can test a Rest API specified with a Swagger file.

## Installation
1. Clone the repository on your computer:
`git clone https://github.com/lpelleau/SwagTester.git && cd SwagTester`
2. Compile the project in Eclipse to create the *.jar* file
3. Import the *.jar* file to your project
4. Add following dependencies in your project (gradle below):
```Gradle
dependencies {
	// swagger
	compile group: 'io.swagger', name: 'swagger-parser', version: '1.0.+'
	
	// unirest
	compile group : 'com.mashape.unirest', name: 'unirest-java', version: '1.4.9'	
	compile group : 'org.apache.httpcomponents', name: 'httpclient', version: '4.5.2'
	compile group : 'org.apache.httpcomponents', name: 'httpasyncclient', version: '4.1.2'	
	compile group : 'org.apache.httpcomponents', name: 'httpmime', version: '4.5.2'	
	compile group : 'org.json', name: 'json', version: '1.5-20090211'
	
	// apache commons-lang (random string)
	compile group: 'commons-lang', name: 'commons-lang', version: '2.6'
	
	// log
	compile group: 'org.slf4j', name: 'slf4j-simple', version: '1.7.21'
	compile group: 'org.slf4j', name: 'slf4j-api', version: '1.7.21'
	
	// junit
	testCompile group: 'junit', name: 'junit', version: '4.+'
}
```

## Usage exemples
**Simple case scenario for the library**
```Java
		File pathFile = new File(pathToFile);

		SwagTester swagger = new SwagTester(pathFile.getAbsolutePath());

		if (swagger.isServerUp()) {
			System.out.println("SERVER ONLINE");

			swagger.entryPoints().forEach((name, ep) -> {
				System.out.println(name);
				System.out.println("\t" + ep);
				System.out.println();
			});

			EntryPoint pet = swagger.entryPoint("/pet");

			SwagTest vTest = pet.putMethod().validTest();

			System.out.println("VALID TEST");
			System.out.println(vTest.getRequest());
			System.out.println(vTest.getExpectedValues());
			System.out.println(vTest.getResponse());
			System.out.println(vTest.isValid());

			System.out.println();

			SwagTest iTest = pet.putMethod().invalidTest();

			System.out.println("INVALID TEST");
			System.out.println(iTest.getRequest());
			System.out.println(iTest.getExpectedValues());
			System.out.println(iTest.getResponse());
			System.out.println(iTest.isValid());

		} else {
			System.out.println("SERVER OFFLINE");
		}
```

**The exemple above send 10 requests to each entry points**
```Java
		File pathFile = new File(pathToFile);

		SwagTester swagger = new SwagTester(pathFile.getAbsolutePath());

		if (swagger.isServerUp()) {
			System.out.println("SERVER ONLINE");

			SwagMetrics metrics = swagger.runTests(10);

			System.out.print("TOTAL : " + metrics.getSuccessfulTests().size() + " / " + metrics.getResults().size());
			System.out
					.println(" (" + ((metrics.getSuccessfulTests().size() * 100) / metrics.getResults().size()) + "%)");

			metrics.getStats().forEach((type, stat) -> {
				System.out.println(type + " : " + stat);
			});

			System.out.println();

			System.out.println("MINIMUM : " + metrics.getMinExecTime());
			System.out.println("MAXIMUM : " + metrics.getMaxExecTime());
			System.out.println("AVERAGE : " + metrics.getAvgExecTime());

		} else {
			System.out.println("SERVER OFFLINE");
		}
```

**The exemple above send requests to the requests present in the `pathToFileExpected` file and compare with the expected result. You can find a sample [here](https://github.com/lpelleau/SwagTester/blob/master/src/test/resources/results.json)**
```Java
		File pathFile = new File(pathToFile);
		File pathFileExpected = new File(pathToFileExpected);

		SwagTester swagger = new SwagTester(pathFile.getAbsolutePath(), pathFileExpected.getAbsolutePath());

		if (swagger.isServerUp()) {
			System.out.println("SERVER ONLINE");

			swagger.sendRequests().forEach(test -> {
				System.out.print(test.getRequest().getUrl() + " : ");
				System.out.println(test.isValid() ? "AS EXPECTED" : "NOT AS EXPECTED");
			});

		} else {
			System.out.println("SERVER OFFLINE");
		}
```

## Documentation

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

##### 2.4. Scalling test
This type of test should be use with a batch test with threads.
Its aim is to focus on the response time and not on the generated values.
A scalling test will generate data following the specifications (like a valid test) but try to be as quick as possible. 
There is almost no random. For exemple, if you want an integer between 10 and 20, it will always return 10.


## Todo & Contribute
* Handle XML payloads for HTTP (only Json is accepted for now)
* Improve the strenght with types
* Improve the expected file parsing (to cover more types)
* Handle YAML Parser for Swagger Specs
* Authentification to the API

This software is under MIT license.
Also, feel free to **contribute** or discuss with us about possible improvements!

## Authors
* Adrien Reuzeau
* Loïc Pelleau
