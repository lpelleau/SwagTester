package net.pelleau.swagger.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.pelleau.swagger.methods.TestType;

public class SwagMetrics {

	public static class Stat {
		public int nbValids;
		public int nbTotals;
	}

	private List<SwagTest> results;

	private long minExecTime = Integer.MAX_VALUE;
	private long maxExecTime = Integer.MIN_VALUE;
	private long sumExecTime;

	private Map<TestType, Stat> stats;

	public SwagMetrics() {
		results = new ArrayList<>();
		stats = new HashMap<>();

		for (TestType type : TestType.values()) {
			stats.put(type, new Stat());
		}
	}

	synchronized public void addResult(SwagTest result) {
		results.add(result);

		long currentResponseTime = result.getResponse().getResponseTime();

		minExecTime = Math.min(minExecTime, currentResponseTime);
		maxExecTime = Math.max(maxExecTime, currentResponseTime);
		sumExecTime += currentResponseTime;

		Stat s = stats.get(result.getRequest().getTestType());
		s.nbTotals++;
		if (result.isValid()) {
			s.nbValids++;
		}
	}

	public long getMinExecTime() {
		return minExecTime;
	}

	public long getMaxExecTime() {
		return maxExecTime;
	}

	public long getAvgExecTime() {
		return sumExecTime / results.size();
	}

	/**
	 * @return A list containing every tests (ie. successfull tests and failed
	 *         tests).
	 */
	public List<SwagTest> getResults() {
		return results;
	}

	/**
	 * @return A list containing only the successful tests.
	 */
	public List<SwagTest> getSuccessfulTests() {
		return results.stream().filter(t -> t.isValid()).collect(Collectors.toList());
	}

	/**
	 * @return A list containing only the failed tests.
	 */
	public List<SwagTest> getFailedTests() {
		return results.stream().filter(t -> !t.isValid()).collect(Collectors.toList());
	}

	/**
	 * @return The number of successful tests for every testType.
	 */
	public Map<TestType, Stat> getStats() {
		return stats;
	}
}
