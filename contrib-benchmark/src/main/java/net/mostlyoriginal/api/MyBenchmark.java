package net.mostlyoriginal.api;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Default benchmark settings.
 *
 * @Author Junkdog
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 10, timeUnit = TimeUnit.SECONDS)
public class MyBenchmark {
}
