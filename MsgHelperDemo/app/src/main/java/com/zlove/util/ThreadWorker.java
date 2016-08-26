package com.zlove.util;

import java.util.concurrent.Future;

public class ThreadWorker {
	public static void execute(Runnable runnable){
		submit(runnable);
	}
	
	private static Future<?> submit(Runnable runnable){
		return ThreadManager.submit(new ThreadWorkRunnable(runnable));
	}
	
	static class ThreadWorkRunnable implements Runnable {

		Runnable runnable;
		public ThreadWorkRunnable(Runnable runnable){
			super();
			this.runnable = runnable;
		}
		
		@Override
		public void run() {
			if(runnable != null){
				runnable.run();
			}
		}
	}
}
