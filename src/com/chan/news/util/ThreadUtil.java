package com.chan.news.util;

import android.os.Bundle;
import android.util.Log;

public class ThreadUtil {

	public static long getThreadId() {
		Thread t = Thread.currentThread();
		return t.getId();
	}
	
	public static String getThreadSignature() {
		Thread t = Thread.currentThread();
		long id = t.getId();
		String name = t.getName();
		long priority = t.getPriority();
		String gname = t.getThreadGroup().getName();
		return (name + "{" + "id:" + id + " priority:" + priority + " group:" + gname + "}");
	}
	
	public static void logThreadSignature() {
		Log.d("ThreadUtil", getThreadSignature());
	}
	
	public static void sleepForInSecs(int secs) {
		try {
			Thread.sleep(secs * 1000);
		} catch(InterruptedException e) {
			throw new RuntimeException("interrupted",e);
		}
	}
	
	// The following two methods are used by worker threads (not main thread)
	public static Bundle getStringAsABundle(String message) {
		Bundle b = new Bundle();
		b.putString("mesage", message);
		return b;
	}
	
	public static String getStringFromABundle(Bundle b) {
		return b.getString("message");
	}
	
	
}
