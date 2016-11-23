package net.pelleau.utils;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pelleau.swagger.container.SwagResponse;
import net.pelleau.swagger.container.SwagTest;

public final class SwagAssert extends Assert {

	private static Logger log = LoggerFactory.getLogger(SwagAssert.class);

	private SwagAssert() {
	}

	public static void assertValid(SwagTest test) {
		assertValid(test, false);
	}

	public static void assertValid(SwagTest test, boolean strictWithDeprecated) {
		log.debug("\tTYPE => \t" + test.getRequest().getTestType());
		log.debug("\tREQUEST => \t" + test.getRequest().toString());
		log.debug("\tEXPECTED => \t" + test.getExpectedValues().toString());
		log.debug("\tRESPONSE => \t" + test.getResponse().toString());

		try {
			if (test == null || !test.isValid()) {
				log.debug("\tRESULT => \tFAIL");
				fail();
			} else {
				log.debug("\tRESULT => \tOK");
			}
		} catch (RuntimeException e) {
			if (strictWithDeprecated) {
				log.debug("\tRESULT => \tFAIL");
				fail();
			} else {
				log.debug("\tRESULT => \tFAIL");
				log.warn("The '" + test.getRequest().getTestType()
						+ "' test fails, but the Entrypoint is deprecated. Ignored.");
			}
		}
	}

	/**
	 * Test if the statusCode and the body of SwagResponse are equals.
	 * 
	 * @param expected
	 *            the expected result.
	 * @param actual
	 *            the actual value.
	 * @return a boolean indicates if both result have the same statusCode and
	 *         body.
	 */
	public static void assertEquals(SwagResponse expected, SwagResponse actual) {
		if (!(expected.getStatusCode() == actual.getStatusCode() && expected.getBody().equals(actual.getBody()))) {
			fail("Expected : '" + expected.getBody() + "', given : '" + actual.getBody() + "'.");
		}
	}

	/**
	 * Test if the status code of both SwagResponse are equals.
	 *
	 * @param expected
	 *            the expected result.
	 * @param actual
	 *            the actual value.
	 * @return a boolean indicates if both reuslt have the same statusCode.
	 */
	public static void assertStatusCodeEquals(SwagResponse expected, SwagResponse actual) {
		if (!(expected.getStatusCode() == actual.getStatusCode())) {
			fail("Expected : '" + expected.getStatusCode() + "', given : '" + actual.getStatusCode() + "'.");
		}
	}

	/**
	 * Test if the status code of the given SwagResponse is equals to the given
	 * int.
	 * 
	 * @param expected
	 *            the expected statusCode.
	 * @param actual
	 *            the actual value.
	 * @return a boolean indicates if the response's statusCode is equal to the
	 *         expected value.
	 */
	public static void assertStatusCodeEquals(int expected, SwagResponse actual) {
		if (!(expected == actual.getStatusCode())) {
			fail("Expected : '" + expected + "', given : '" + actual.getStatusCode() + "'.");
		}
	}

}
