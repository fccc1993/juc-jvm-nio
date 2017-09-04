package com.atguigu.juc;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

import org.junit.Test;

public class TestForkJoinPool {
	public static void main(String[] args) {
		Instant start = Instant.now();
		
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Long> task = new ForkJoinDemo(0L, 50000000000L);
		
		long sum = pool.invoke(task);
		System.out.println(sum);
		
		Instant end = Instant.now();
		System.out.println("耗费时间为:" + Duration.between(start, end).toMillis());//耗费时间为:38368
	}
	
	@Test
	public void test1(){
		long sum = 0L;
		Instant start = Instant.now();
		for (long i = 0l; i <= 50000000000L; i++) {
			sum += i;
		}
		System.out.println(sum);
		Instant end = Instant.now();
		System.out.println("耗费时间为:" + Duration.between(start, end).toMillis());//
	}
	
	@Test
	public void test2(){
		Instant start = Instant.now();
		
		long sum = LongStream.rangeClosed(0L, 50000000000L)
				.parallel().reduce(0L, Long::sum);
		System.out.println(sum);
		Instant end = Instant.now();
		
		System.out.println("耗费时间为:" + Duration.between(start, end).toMillis());//耗费时间为:22546
	}
}
class ForkJoinDemo extends RecursiveTask<Long>{
	
	
	private static final long serialVersionUID = 1L;
	private long start;
	private long end;
	private static final long THURSHOLD = 10000L;
	
	public ForkJoinDemo(long start, long end){
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Long compute() {
		long length = end - start;
		if(length <= THURSHOLD){
			long sum = 0L;
			for (long i = start; i <= end; i++) {
				sum += i;
			}
			return sum;
		}else{
			long middle = (start + end) / 2;
			ForkJoinDemo left = new ForkJoinDemo(start, middle);
			left.fork();
			
			ForkJoinDemo right = new ForkJoinDemo(middle+1, end);
			right.fork();
			
			return left.join()+right.join();
		}
	}
	
	
	
	
}