package net.pelleau.utils;

import net.pelleau.swagger.SwagResponse;

public class SwagResponseAssert {

	/**
	 * Test if the statusCode and the body are equals.
	 * 
	 * @param expected
	 *            the expected result.
	 * @param actual
	 *            the actual value.
	 * @return a boolean indicates if both result have the same statusCode and
	 *         body.
	 */
	public static boolean assertEquals(SwagResponse expected, SwagResponse actual) {
		return expected.getStatusCode() == actual.getStatusCode() && expected.getBody().equals(actual.getBody());
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
	public static boolean assertStatusCodeEquals(SwagResponse expected, SwagResponse actual) {
		return expected.getStatusCode() == actual.getStatusCode();
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
	public static boolean assertStatusCodeEquals(int expected, SwagResponse actual) {
		return expected == actual.getStatusCode();
	}

}
