package net.mostlyoriginal.api.event.dispatcher;

import net.mostlyoriginal.api.MyBenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @Author DaanVanYperen
 */
public class DispatcherBenchmark extends MyBenchmark {

	@Benchmark
	public void benchmarkBasic()
	{
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
