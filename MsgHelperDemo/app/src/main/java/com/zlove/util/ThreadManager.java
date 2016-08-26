package com.zlove.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadManager {
	static ExecutorService executorService;

	public static void execute(Runnable runnable) {
		if (executorService == null) {
			executorService = Executors
					.newFixedThreadPool(7);
		}
		executorService.submit(runnable);
	}

	public static Future<?> submit(Runnable runnable) {
		if (executorService == null) {
			executorService = Executors
					.newFixedThreadPool(7);
		}
		try {
			Future<?> f = executorService.submit(runnable);
			return f;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
