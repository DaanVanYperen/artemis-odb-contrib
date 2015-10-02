package net.mostlyoriginal.api.event.dispatcher;

import com.artemis.Manager;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.annotations.Wire;
import net.mostlyoriginal.api.MyBenchmark;
import net.mostlyoriginal.api.event.common.*;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Benchmark the dispatching of events, dispatch including event listeners.
 *
 * Intended for class based dispatchers, like the PollingPooledDispatcher.
 *
 * @author DaanVanYperen
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
public abstract class ClassBasedDispatcherBenchmark extends MyBenchmark {

	public static final int DISPATCH_BATCH_SIZE = 1000;
	protected EventSystem em;
	private ActiveEventHandlers activeEventHandlers;

	/**
	 * Events with deep hierarchies apply to more event listeners,
	 * and generally have more overhead.
	 */
	public static class MassiveListenerEvent implements Event {}
	public static class EventNest0 implements Event {}
	public static class EventNest0v2 implements Event {}
	public static class EventNest1 extends EventNest0 {}
	public static class EventNest2 extends EventNest1 {}
	public static class EventNest3 extends EventNest2 {}
	public static class EventNest4 extends EventNest3 {}
	public static class EventNest5 extends EventNest4 {}
	public static class EventNest6 extends EventNest5 {}
	public static class EventNest7 extends EventNest6 {}
	public static class EventNest8 extends EventNest7 {}

	public static class IgnoredEvent01 implements Event {}
	public static class IgnoredEvent02 implements Event {}
	public static class IgnoredEvent03 implements Event {}
	public static class IgnoredEvent04 implements Event {}
	public static class IgnoredEvent05 implements Event {}
	public static class IgnoredEvent06 implements Event {}
	public static class IgnoredEvent07 implements Event {}
	public static class IgnoredEvent08 implements Event {}
	public static class IgnoredEvent09 implements Event {}
	public static class IgnoredEvent10 implements Event {}
	public static class IgnoredEvent11 implements Event {}
	public static class IgnoredEvent12 implements Event {}
	public static class IgnoredEvent13 implements Event {}
	public static class IgnoredEvent14 implements Event {}
	public static class IgnoredEvent15 implements Event {}
	public static class IgnoredEvent16 implements Event {}
	public static class IgnoredEvent17 implements Event {}
	public static class IgnoredEvent18 implements Event {}
	public static class IgnoredEvent19 implements Event {}
	public static class IgnoredEvent20 implements Event {}
	public static class IgnoredEvent21 implements Event {}
	public static class IgnoredEvent22 implements Event {}
	public static class IgnoredEvent23 implements Event {}
	public static class IgnoredEvent24 implements Event {}
	public static class IgnoredEvent25 implements Event {}
	public static class IgnoredEvent26 implements Event {}
	public static class IgnoredEvent27 implements Event {}
	public static class IgnoredEvent28 implements Event {}
	public static class IgnoredEvent29 implements Event {}
	public static class IgnoredEvent30 implements Event {}
	public static class IgnoredEvent31 implements Event {}
	public static class IgnoredEvent32 implements Event {}
	public static class IgnoredEvent33 implements Event {}
	public static class IgnoredEvent34 implements Event {}
	public static class IgnoredEvent35 implements Event {}
	public static class IgnoredEvent36 implements Event {}
	public static class IgnoredEvent37 implements Event {}
	public static class IgnoredEvent38 implements Event {}
	public static class IgnoredEvent39 implements Event {}
	/**
	 * These will cause the EventNest0 handler to be called.
	 * Typical use case would be a superclass event handler
	 * that accepts a lot of different event subclasses.
 	 */
	public static class EventNest1NoImmediateHandler extends EventNest0 {}
	public static class EventNest2NoImmediateHandler extends EventNest1NoImmediateHandler {}

	/** Give the dispatcher some padding to deal with. Dispatchers that
	 * do not prep event lists will perform badly.
	 */
	@Wire
	public static class PaddingHandlers extends Manager {
		@Subscribe public void notUsedHandler01( IgnoredEvent01 event ) {}
		@Subscribe public void notUsedHandler02( IgnoredEvent02 event ) {}
		@Subscribe public void notUsedHandler03( IgnoredEvent03 event ) {}
		@Subscribe public void notUsedHandler04( IgnoredEvent04 event ) {}
		@Subscribe public void notUsedHandler05( IgnoredEvent05 event ) {}
		@Subscribe public void notUsedHandler06( IgnoredEvent06 event ) {}
		@Subscribe public void notUsedHandler07( IgnoredEvent07 event ) {}
		@Subscribe public void notUsedHandler08( IgnoredEvent08 event ) {}
		@Subscribe public void notUsedHandler09( IgnoredEvent09 event ) {}
		@Subscribe public void notUsedHandler10( IgnoredEvent10 event ) {}
		@Subscribe public void notUsedHandler11( IgnoredEvent11 event ) {}
		@Subscribe public void notUsedHandler12( IgnoredEvent12 event ) {}
		@Subscribe public void notUsedHandler13( IgnoredEvent13 event ) {}
		@Subscribe public void notUsedHandler14( IgnoredEvent14 event ) {}
		@Subscribe public void notUsedHandler15( IgnoredEvent15 event ) {}
		@Subscribe public void notUsedHandler16( IgnoredEvent16 event ) {}
		@Subscribe public void notUsedHandler17( IgnoredEvent17 event ) {}
		@Subscribe public void notUsedHandler18( IgnoredEvent18 event ) {}
		@Subscribe public void notUsedHandler19( IgnoredEvent19 event ) {}
		@Subscribe public void notUsedHandler20( IgnoredEvent20 event ) {}
		@Subscribe public void notUsedHandler21( IgnoredEvent21 event ) {}
		@Subscribe public void notUsedHandler22( IgnoredEvent22 event ) {}
		@Subscribe public void notUsedHandler23( IgnoredEvent23 event ) {}
		@Subscribe public void notUsedHandler24( IgnoredEvent24 event ) {}
		@Subscribe public void notUsedHandler25( IgnoredEvent25 event ) {}
		@Subscribe public void notUsedHandler26( IgnoredEvent26 event ) {}
		@Subscribe public void notUsedHandler27( IgnoredEvent27 event ) {}
		@Subscribe public void notUsedHandler28( IgnoredEvent28 event ) {}
		@Subscribe public void notUsedHandler29( IgnoredEvent29 event ) {}
		@Subscribe public void notUsedHandler30( IgnoredEvent30 event ) {}
		@Subscribe public void notUsedHandler31( IgnoredEvent31 event ) {}
		@Subscribe public void notUsedHandler32( IgnoredEvent32 event ) {}
		@Subscribe public void notUsedHandler33( IgnoredEvent33 event ) {}
		@Subscribe public void notUsedHandler34( IgnoredEvent34 event ) {}
		@Subscribe public void notUsedHandler35( IgnoredEvent35 event ) {}
		@Subscribe public void notUsedHandler36( IgnoredEvent36 event ) {}
		@Subscribe public void notUsedHandler37( IgnoredEvent37 event ) {}
		@Subscribe public void notUsedHandler38( IgnoredEvent38 event ) {}
		@Subscribe public void notUsedHandler39( IgnoredEvent39 event ) {}
	}

	@Wire
	public static class ActiveEventHandlers extends Manager {

		int count=0;

		@Subscribe
		/* This will handle EventNest0 and all higher events, so will be called the most! */
		public void handle0 ( EventNest0 event ) { count++; }

		@Subscribe
		public void handlev2 ( EventNest0v2 event ) { count++; }

		@Subscribe
		/* This will handle EventNest1 and all higher events, so will be called less. */
		public void handle1 ( EventNest1 event ) { count++; }
		@Subscribe
		/* .. */
		public void handle2 ( EventNest2 event ) { count++; }
		@Subscribe

		public void handle3 ( EventNest3 event ) { count++; }
		@Subscribe
		public void handle4 ( EventNest4 event ) { count++; }
		@Subscribe
		public void handle5 ( EventNest5 event ) { count++; }
		@Subscribe
		public void handle6 ( EventNest6 event ) { count++; }
		@Subscribe
		public void handle7 ( EventNest7 event ) { count++; }
		@Subscribe
		public void handle8 ( EventNest8 event ) { count++; }


		@Subscribe public void h01( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h02( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h03( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h04( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h05( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h07( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h08( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h09( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h10( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h11( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h12( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h13( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h14( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h15( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h17( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h18( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h19( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h20( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h21( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h22( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h23( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h24( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h25( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h27( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h28( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h29( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h30( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h31( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h32( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h33( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h34( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h35( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h37( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h38( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h39( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h40( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h41( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h42( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h43( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h44( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h45( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h47( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h48( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h49( MassiveListenerEvent event ) { count++; }
		@Subscribe public void h50( MassiveListenerEvent event ) { count++; }
	}


	@Setup
	public void setup()
	{
		final WorldConfiguration config = new WorldConfiguration();

		em = new EventSystem(instanceDispatcher(), new SubscribeAnnotationFinder());
		config.setSystem(em);
		activeEventHandlers = new ActiveEventHandlers();
		config.setSystem(activeEventHandlers);
		config.setSystem(new PaddingHandlers());

		World w = new World(config);
	}

	protected abstract EventDispatchStrategy  instanceDispatcher();

	@Benchmark
	@OperationsPerInvocation(DISPATCH_BATCH_SIZE)
	public void eventWithNoHierarchyAndOneHandler()
	{
		// eventNest0 events apply only to handle0 listener.
		for (int i = 0; i < DISPATCH_BATCH_SIZE; i++) {
			em.dispatch(EventNest0.class);
		}
		em.process();
	}

	protected void dispatch(Event event) {
		em.dispatch(event);
	}

	@Benchmark
	@OperationsPerInvocation(DISPATCH_BATCH_SIZE)
	public void eventWithHierarchyAndOneHandler()
	{
		//
		for (int i = 0; i < DISPATCH_BATCH_SIZE; i++) {
			em.dispatch(EventNest2NoImmediateHandler.class);
		}
		em.process();
	}

	@Benchmark
	@OperationsPerInvocation(DISPATCH_BATCH_SIZE)
	public void eventWithManySubclassListeners()
	{
		// eventNest0 events applies to handle0-8 listeners.
		// Also has a deep hierarchy.
		for (int i = 0; i < DISPATCH_BATCH_SIZE; i++){
			em.dispatch(EventNest8.class);
		}
		em.process();
	}


	@Benchmark
	@OperationsPerInvocation(DISPATCH_BATCH_SIZE*2)
	public void eventWithMixedCalls()
	{
		// mix up calls, just to see how it works.
		for (int i = 0; i < DISPATCH_BATCH_SIZE; i++) {
			em.dispatch(EventNest0.class);
			em.dispatch(EventNest0v2.class);
		}
		em.process();
	}

	@Benchmark
	@OperationsPerInvocation(DISPATCH_BATCH_SIZE)
	public void eventWithFiftyListeners()
	{
		// has many listeners.
		for (int i = 0; i < DISPATCH_BATCH_SIZE; i++) {
			em.dispatch(MassiveListenerEvent.class);
		}
		em.process();
	}
}
