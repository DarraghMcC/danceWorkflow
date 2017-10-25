package com.common.timer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class ProcessTimer {

	private static final String OVERALL = "OVERALL";
	private static final String SQL = "SQL";
	private static final String JAVA = "JAVA";

	private final Map<String, StopWatch> timers;

	public ProcessTimer() {
		timers = new ConcurrentHashMap<>();
	}

	public synchronized ProcessTimer startOverall() {
		return this.startJava().startTimer(ProcessTimer.OVERALL);
	}

	public synchronized ProcessTimer stopOverall() {
		return this.stopJava().stopSQL().stopTimer(ProcessTimer.OVERALL);
	}

	public synchronized ProcessTimer startJava() {
		return this.stopSQL().startTimer(ProcessTimer.JAVA);
	}

	public synchronized ProcessTimer stopJava() {
		return this.stopTimer(ProcessTimer.JAVA);
	}

	public synchronized ProcessTimer startSQL() {
		return this.stopJava().startTimer(ProcessTimer.SQL);
	}

	public synchronized ProcessTimer stopSQL() {
		return this.stopTimer(ProcessTimer.SQL);
	}

	public Long overallTime() {
		return this.time(ProcessTimer.OVERALL);
	}

	public Long javaTime() {
		return this.time(ProcessTimer.JAVA);
	}

	public Long sqlTime() {
		return this.time(ProcessTimer.SQL);
	}

	public String report() {
		return this.report(StringUtils.EMPTY, null);
	}

	public String report(final String message) {
		return this.report(message, null);
	}

	public String report(final Long fullTime) {
		return this.report(StringUtils.EMPTY, fullTime);
	}

	public String report(final String message, final Long fullTime) {
		final StringBuilder report = new StringBuilder(message);
		final DecimalFormat decimalFormat = new DecimalFormat("#.##");

		if (StringUtils.isNotBlank(message)) {
			report.append(StringUtils.SPACE);
		}

		if (this.javaTime() > 0L && !Objects.equals(this.javaTime(), this.overallTime())) {
			report.append(ProcessTimer.JAVA).append("=").append(DurationFormatUtils.formatDurationHMS(this.javaTime()));
			report.append("(").append(decimalFormat.format(this.doPercentage(this.javaTime(), this.overallTime()))).append("%)");
			report.append(", ");
		}

		if (this.sqlTime() > 0L) {
			report.append(ProcessTimer.SQL).append("=").append(DurationFormatUtils.formatDurationHMS(this.sqlTime()));
			report.append("(").append(decimalFormat.format(this.doPercentage(this.sqlTime(), this.overallTime()))).append("%)");
			report.append(", ");
		}

		for (final Map.Entry<String, StopWatch> entry : timers.entrySet()) {
			if (!StringUtils.equals(ProcessTimer.OVERALL, entry.getKey())
					&& !StringUtils.equals(ProcessTimer.JAVA, entry.getKey())
					&& !StringUtils.equals(ProcessTimer.SQL, entry.getKey())
					&& this.time(entry.getKey()) > 0L) {
				report.append(entry.getKey()).append("=").append(DurationFormatUtils.formatDurationHMS(this.time(entry.getKey())));
				report.append("(").append(decimalFormat.format(this.doPercentage(this.time(entry.getKey()), this.overallTime()))).append("%)");
				report.append(", ");
			}
		}

		report.append(ProcessTimer.OVERALL).append("=").append(DurationFormatUtils.formatDurationHMS(this.overallTime()));

		if (fullTime != null) {
			report.append("(").append(decimalFormat.format(this.doPercentage(this.overallTime(), fullTime))).append("%)");
		}

		return report.toString();
	}

	public synchronized String reportJava(final String message) {
		final StringBuilder report = new StringBuilder();

		if (this.javaTime() > 0L && !Objects.equals(this.javaTime(), this.overallTime())) {
			report.append(message).append(":").append(DurationFormatUtils.formatDurationHMS(this.javaTime()));
		}

		return report.toString();
	}

	public synchronized ProcessTimer startTimer(final String timerName) {
		if (this.getTimer(timerName).isSuspended()) {
			this.getTimer(timerName).resume();
		} else if (!this.getTimer(timerName).isStarted()) {
			this.getTimer(timerName).start();
		}
		return this;
	}

	public synchronized ProcessTimer stopTimer(final String timerName) {
		if (this.getTimer(timerName).isStarted() && !this.getTimer(timerName).isSuspended()) {
			this.getTimer(timerName).suspend();
		}
		return this;
	}

	public Long time(final String timerName) {
		if (this.getTimer(timerName).isStarted()) {
			this.getTimer(timerName).stop();
		}
		return this.getTimer(timerName).getTime();
	}

	private StopWatch getTimer(final String timerName) {
		if (StringUtils.isBlank(timerName)) {
			throw new NullPointerException("Timer name can't be blank.");
		}

		if (!this.timers.containsKey(timerName)) {
			this.timers.put(timerName, new StopWatch());
		}

		return this.timers.get(timerName);
	}

	private Double doPercentage(final Long processTime, final Long fullTime) {
		return (((double) processTime) / ((double) fullTime)) * 100;
	}

	public synchronized void restartJava() {
		this.getTimer(ProcessTimer.JAVA).reset();
		this.getTimer(ProcessTimer.JAVA).start();
	}
	
	public Map<String, StopWatch> getTimers() {
		return this.timers;
	}

}
