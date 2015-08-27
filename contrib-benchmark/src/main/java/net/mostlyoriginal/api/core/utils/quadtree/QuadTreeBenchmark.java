package net.mostlyoriginal.api.core.utils.quadtree;

import com.artemis.utils.Bag;
import com.artemis.utils.IntBag;
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
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS, batchSize = 100)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS, batchSize = 100)
public class QuadTreeBenchmark extends MyBenchmark {
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
	@Param({"1000", "10000"})
	int entities;
	@Param({"16", "64", "256"})
	int treeSize;
	@Param({"0.1", "0.25", "0.5"})
	float searchScale;

	protected QuadTree quadTree;
	protected Bag<TestData> testData;
	protected Bag<TestData> searchData;
	protected IntBag fill;
	private static class TestData {
		public int id;
		public float x;
		public float y;
		public float width;
		public float height;

		public boolean contains (float x, float y) {
			return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y;
		}

		public boolean overlaps (float x, float y, float width, float height) {
			return this.x < x + width && this.x + this.width > x && this.y < y + height && this.y + this.height > y;
		}
	}

	@Setup(Level.Iteration)
	public void setup() {
		quadTree = new QuadTree(0, 0, treeSize, treeSize);
		testData = new Bag<>(entities);
		fill = new IntBag();
		searchData = new Bag<>(1000);

		Random random = new Random(0);
		for (int id = 0; id < entities; id++) {
			TestData data = new TestData();
			data.id = id;
			data.width = 0.025f + random.nextFloat() * 0.075f;
			data.height = 0.025f + random.nextFloat() * 0.075f;
			data.x = random.nextFloat() * (treeSize - data.width);
			data.y = random.nextFloat() * (treeSize - data.width);
			testData.add(data);
			quadTree.insert(data.id, data.x, data.y, data.width, data.height);
		}
		for (int id = 0; id < 1000; id++) {
			TestData search = new TestData();
			search.width = 0.1f + random.nextFloat() * (treeSize * searchScale);
			search.height = 0.1f + random.nextFloat() * (treeSize * searchScale);
			search.x = random.nextFloat() * (treeSize - search.width);
			search.y = random.nextFloat() * (treeSize - search.height);
			searchData.add(search);
		}
	}

	@TearDown(Level.Iteration)
	public void tearDown () {
		quadTree.reset();
		fill.clear();
	}

//	@Benchmark
//	public QuadTree quad_tree_insert_benchmark(Blackhole bh) {
//		for (int i = 0; i < testData.size(); i++) {
//			bh.consume(insert(quadTree, testData.get(i)));
//		}
//		return quadTree;
//	}
//
//	static QuadTree insert(QuadTree tree, TestData data) {
//		tree.insert(data.id, data.x, data.y, data.width, data.height);
//		return tree;
//	}

	@Benchmark
	public QuadTree quad_tree_get_point_benchmark(Blackhole bh) {
		for (int i = 0; i < testData.size(); i++) {
			bh.consume(getPoint(quadTree, testData.get(i), fill));
		}
		return quadTree;
	}

	static QuadTree getPoint(QuadTree tree, TestData data, IntBag fill) {
		tree.get(fill, data.x, data.y);
		fill.clear();
		return tree;
	}

	@Benchmark
	public QuadTree quad_tree_get_exact_point_benchmark(Blackhole bh) {
		for (int i = 0; i < testData.size(); i++) {
			bh.consume(getPointExact(quadTree, testData.get(i), fill));
		}
		return quadTree;
	}

	static QuadTree getPointExact(QuadTree tree, TestData data, IntBag fill) {
		tree.getExact(fill, data.x, data.y);
		fill.clear();
		return tree;
	}


	@Benchmark
	public QuadTree quad_tree_get_benchmark(Blackhole bh) {
		for (int i = 0; i < testData.size(); i++) {
			bh.consume(get(quadTree, testData.get(i), fill));
		}
		return quadTree;
	}

	static QuadTree get(QuadTree tree, TestData data, IntBag fill) {
		tree.get(fill, data.x, data.y, data.width, data.height);
		fill.clear();
		return tree;
	}

	@Benchmark
	public QuadTree quad_tree_get_exact_benchmark(Blackhole bh) {
		for (int i = 0; i < testData.size(); i++) {
			bh.consume(getExact(quadTree, testData.get(i), fill));
		}
		return quadTree;
	}

	static QuadTree getExact(QuadTree tree, TestData data, IntBag fill) {
		tree.getExact(fill, data.x, data.y, data.width, data.height);
		fill.clear();
		return tree;
	}


	@Benchmark
	public void linear_point_benchmark(Blackhole bh) {
		for (int i = 0; i < searchData.size(); i++) {
			bh.consume(findPoint(searchData.get(i), fill, testData));
		}
	}

	static IntBag findPoint (TestData data, IntBag fill, Bag<TestData> testData) {
		for (int i = 0; i < testData.size(); i++) {
			TestData test = testData.get(i);
			if (test.contains(data.x, data.y)) {
				fill.add(test.id);
			}
		}
		fill.clear();
		return fill;
	}

	@Benchmark
	public void linear_benchmark(Blackhole bh) {
		for (int i = 0; i < searchData.size(); i++) {
			bh.consume(find(searchData.get(i), fill, testData));
		}
	}

	static IntBag find (TestData data, IntBag fill, Bag<TestData> testData) {
		for (int i = 0; i < testData.size(); i++) {
			TestData test = testData.get(i);
			if (test.overlaps(data.x, data.y, data.width, data.height)) {
				fill.add(test.id);
			}
		}
		fill.clear();
		return fill;
	}
}
