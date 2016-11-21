package net.pelleau.utils;

import org.junit.Assert;

import net.pelleau.swagger.container.SwagResponse;
import net.pelleau.swagger.container.SwagTest;

public final class SwagAssert extends Assert {

	private SwagAssert() {
	}

	public static void assertValid(SwagTest test) {
		if (test == null || !test.isValid()) {
			fail();
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
