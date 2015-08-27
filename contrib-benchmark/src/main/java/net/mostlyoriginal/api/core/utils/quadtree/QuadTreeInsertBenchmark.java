package net.mostlyoriginal.api.core.utils.quadtree;

import com.artemis.utils.Bag;
import net.mostlyoriginal.api.MyBenchmark;
import net.mostlyoriginal.api.utils.QuadTree;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Benchmarks for QuadTree
 *
 * NOTE run from root dir, mcn clean package, java -jar contrib-benchmark/target/microbenchmarks.jar
 *
 * @author Piotr-J
 */
@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
public class QuadTreeInsertBenchmark extends MyBenchmark {
	/*
	TODO stuff to bench tree vs linear search

	get, getExact, insert, update

	small count point
	small count small area
	small count large area

	large count point
	large count small area
	large count large area

	 */
	@Param({"1000", "10000", "100000"})
	int entities;
	@Param({"16", "64", "256"})
	int treeSize;

	protected QuadTree quadTree;
	protected Bag<TestData> testData;
//	protected Bag<TestData> searchData;
//	protected IntBag fill;
	private static class TestData {
		public int id;
		public float x;
		public float y;
		public float width;
		public float height;
	}

	@Setup(Level.Iteration)
	public void setup() {
		quadTree = new QuadTree(0, 0, treeSize, treeSize);
		testData = new Bag<>();
//		fill = new IntBag();
		Random random = new Random(0);
		for (int id = 0; id < entities; id++) {
			TestData data = new TestData();
			data.id = id;
			data.x = random.nextFloat() * (treeSize - 1);
			data.y = random.nextFloat() * (treeSize - 1);
			data.width = 0.025f + random.nextFloat() * 0.075f;
			data.height = 0.025f + random.nextFloat() * 0.075f;
			testData.add(data);
//			quadTree.insert(data.id, data.x, data.y, data.width, data.height);
		}
//		for (int id = 0; id < size; id++) {
//			TestData search = new TestData();
//			search.x = random.nextFloat() * 14;
//			search.y = random.nextFloat() * 14;
//			search.width = 0.1f + random.nextFloat() * 1.9f;
//			search.height = 0.1f + random.nextFloat() * 1.9f;
//			searchData.add(search);
//		}
	}

	@TearDown(Level.Iteration)
	public void tearDown () {
		quadTree.reset();
	}

	@Benchmark
	public QuadTree quad_tree_insert_benchmark(Blackhole bh) {
		for (int i = 0; i < testData.size(); i++) {
			bh.consume(insert(quadTree, testData.get(i)));
		}
		return quadTree;
	}

	static QuadTree insert(QuadTree tree, TestData data) {
		tree.insert(data.id, data.x, data.y, data.width, data.height);
		return tree;
	}

//	@Benchmark
//	public QuadTree quad_tree_get_point_benchmark() {
//		quadTree.get(fill,  random.nextFloat() * 16, random.nextFloat() * 16);
//		return quadTree;
//	}
//
//	@Benchmark
//	public QuadTree quad_tree_get_exact_point_benchmark() {
//		quadTree.getExact(fill, random.nextFloat() * 16, random.nextFloat() * 16);
//		return quadTree;
//	}
//
//	@Benchmark
//	public QuadTree quad_tree_get_benchmark() {
//		quadTree.get(fill,  random.nextFloat() * 14, random.nextFloat() * 15);
//		return quadTree;
//	}
//
//	@Benchmark
//	public QuadTree quad_tree_get_exact_benchmark() {
//		quadTree.getExact(fill, random.nextFloat() * 14, random.nextFloat() * 15);
//		return quadTree;
//	}

//	@Benchmark
//	public void linear_benchmark() {
//
//	}
}
