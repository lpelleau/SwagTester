package net.pelleau.swagger.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		minExecTime = Math.min(minExecTime, result.getResponse().getResponseTime());
		maxExecTime = Math.max(maxExecTime, result.getResponse().getResponseTime());
		sumExecTime += result.getResponse().getResponseTime();

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

	public List<SwagTest> getResults() {
		return results;
	}

	public Map<TestType, Stat> getStats() {
		return stats;
	}
}
