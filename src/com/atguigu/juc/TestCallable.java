package com.atguigu.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建执行线程方式三:实现Callable接口,有返回值,可以抛出异常
 * 
 * @author xfc
 *
 */
public class TestCallable {
	public static void main(String[] args) {
		ThreadDemoC td = new ThreadDemoC();
		
		//执行Callable接口,需要FutureTask实现类支持,用于接收运算结果. FutureTask 是 Future 类的实现.
		FutureTask<Integer> result = new FutureTask<>(td);
		new Thread(result).start();
		
		//接收线程运算后的结果
			try {
				Integer sum = result.get();
				System.out.println(sum);
				System.out.println("-----------------");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
		
	}
}
class ThreadDemoC implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		int sum = 0;
		for (int i = 0; i < 1000; i++) {
			sum += i;
		}
		return sum;
	}
	
}

//class ThreadDemo implements Runnable{
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//	}
	
