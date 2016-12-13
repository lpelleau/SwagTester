package test.swagger;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.pelleau.swagger.container.SwagMetrics;
import net.pelleau.swagger.container.SwagRequest;
import net.pelleau.swagger.container.SwagResponse;
import net.pelleau.swagger.container.SwagTest;
import net.pelleau.swagger.methods.TestType;

public class TestSwagMetrics {

	private SwagTest getValidTest() {
		SwagResponse resp = new SwagResponse(300);

		SwagRequest req = new SwagRequest();
		req.setTestType(TestType.VALID);

		SwagTest s = new SwagTest(req, resp);

		return s;
	}

	private SwagTest getInvalidTest() {
		SwagResponse resp = new SwagResponse(100);

		SwagRequest req = new SwagRequest();
		req.setTestType(TestType.INVALID);

		SwagTest s = new SwagTest(req, resp);

		SwagResponse expect = new SwagResponse();
		expect.setBody("<tag>message</tag>");

		s.getExpectedValues().add(expect);

		return s;
	}

	@Test
	public void testSuccessfulAndFailedNumber1() {

		SwagMetrics metrics = new SwagMetrics();

		for (int i = 0; i < 10; i++) {
			metrics.addResult(getValidTest());
			metrics.addResult(getInvalidTest());
		}

		assertEquals(10, metrics.getSuccessfulTests().size());
		assertEquals(10, metrics.getFailedTests().size());
		assertEquals(20, metrics.getResults().size());
	}

	@Test
	public void testSuccessfulAndFailedNumber2() {

		SwagMetrics metrics = new SwagMetrics();

		for (int i = 0; i < 20; i++) {
			metrics.addResult(getValidTest());
		}

		assertEquals(20, metrics.getSuccessfulTests().size());
		assertEquals(0, metrics.getFailedTests().size());
		assertEquals(20, metrics.getResults().size());
	}

	@Test
	public void testSuccessfulAndFailedNumber3() {

		SwagMetrics metrics = new SwagMetrics();

		for (int i = 0; i < 20; i++) {
			metrics.addResult(getInvalidTest());
		}

		assertEquals(0, metrics.getSuccessfulTests().size());
		assertEquals(20, metrics.getFailedTests().size());
		assertEquals(20, metrics.getResults().size());
	}

	@Test
	public void testExecTime() {

		SwagMetrics metrics = new SwagMetrics();

		for (int i = 0; i < 10; i++) {
			metrics.addResult(getValidTest());
			metrics.addResult(getInvalidTest());
		}

		assertEquals(100, metrics.getMinExecTime());
		assertEquals(300, metrics.getMaxExecTime());
		assertEquals(200, metrics.getAvgExecTime());
	}

}
