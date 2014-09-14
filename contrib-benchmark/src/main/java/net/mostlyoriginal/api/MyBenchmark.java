package net.mostlyoriginal.api;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Default benchmark settings.
 */
@State(Scope.Thread)
@Threads(1)
@Fork(1)
public class MyBenchmark {
}
