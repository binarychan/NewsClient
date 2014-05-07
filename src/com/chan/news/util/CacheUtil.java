package com.chan.news.util;

import java.io.File;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class CacheUtil {
	
	/**
	 *  A good solution is to cache the images somewhere the UI thread can access them,
	 *  and let the UI thread know when they are available for display. 
	 *  This approach has the added benefit of addressing Problem #2, 
	 *  as well as our inefficient network/battery use, 
	 *  by downloading each image once and storing it in a local cache.
	 *  解决ListView显示数据问题：加入ListView中的数据需要从网络上获取，
	 *  此时在UI线程上解决显然是不合适的，可能导致ANR。
	 *  所以应该开启一个工作线程进行数据获取工作，又因为UI线程是绝对完全非线程安全的，
	 *  这就导致在工作线程中调用类似ImageView将可能导致UI更新混乱出现，
	 *  还有，如果listview中需要下载很多项数据和图片资源，用户有可能不会进行浏览，
	 *  这将导致消耗大量网络资源和电池电量，最好的解决方案是，
	 *  对图片等资源开启一条线程进行懒加载，同时在UI线程中完成ImageView的显示工作。
	 *  也就是将图片资源和数据缓存在UI线程中可以访问到的地方，让UI线程知悉这些数据图片
	 *  资源已经可用于显示了，同时为了减少网络资源的使用，通过每一次下载一个图片的方式
	 *  并保存在本地缓存中。
	 *  To start, we’ll be adding a class to manage 
	 *  both our local image cache and a download queue for the images.
	 *  Let’s call this class ImageManager, and get it started:
	 *  
	 */

	private static final String TAG = "CacheUtil";
	
    public static final int CONFIG_CACHE_MOBILE_TIMEOUT  = 3600000;  
    public static final int CONFIG_CACHE_WIFI_TIMEOUT    = 300000;   
    
    private static final String SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
 
    public static String getUrlCache(String url) {
        if (url == null) {
            return null;
        }
 
        String result = null;
        File file = new File(SDCARD_DIR + "/" + getCacheDecodeString(url));
        
        if (file.exists() && file.isFile()) {
            long expiredTime = System.currentTimeMillis() - file.lastModified();
            
            Log.d(TAG, file.getAbsolutePath() + " expiredTime:" + expiredTime/60000 + "min");
            
            //1. 当系统时间不准确，被调回很久以前
            //2. 当网络不可用时，只能读取缓存
            if (NetworkUtil.getNetworkState() != NetworkUtil.NETWORK_NONE 
            		&& expiredTime < 0) {
                return null;
            }
            
            if(NetworkUtil.getNetworkState() == NetworkUtil.NETWORK_WIFI
                   && expiredTime > CONFIG_CACHE_WIFI_TIMEOUT) {
                return null;
            } else if (NetworkUtil.getNetworkState() == NetworkUtil.NETWORK_MOBILE
                   && expiredTime > CONFIG_CACHE_MOBILE_TIMEOUT) {
                return null;
            }
            
            try {
                result = FileUtil.readTextFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
 
    public static void setUrlCache(String data, String url) {
        File file = new File(SDCARD_DIR + "/" + getCacheDecodeString(url));
        try {
            //创建缓存数据到磁盘，就是创建文件
            FileUtil.writeTextFile(file, data);
        } catch (IOException e) {
            Log.d(TAG, "write " + file.getAbsolutePath() + " data failed!");
            e.printStackTrace();
        }
    }
 
    public static String getCacheDecodeString(String url) {
        //1. 处理特殊字符
        //2. 去除后缀名带来的文件浏览器的视图凌乱
    	//   (特别是图片更需要如此类似处理，否则有的手机打开图库，全是我们的缓存图片)
        if (url != null) {
            return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
        }
        return null;
    }
	
//    void getConfig(){
//        //首先尝试读取缓存
//        String cacheConfigString = CacheUtil.getUrlCache(CONFIG_URL);
//        
//        //根据结果判定是读取缓存，还是重新读取
//        if (cacheConfigString != null) {
//            showConfig(cacheConfigString);
//        } else {
//            //如果缓存结果是空，说明需要重新加载
//            //缓存为空的原因可能是1.无缓存;2. 缓存过期;3.读取缓存出错
//            AsyncHttpClient client = new AsyncHttpClient();
//            
//            client.get(CONFIG_URL, new AsyncHttpResponseHandler(){
// 
//                @Override
//                public void onSuccess(String result){
//                    //成功下载，则保存到本地作为后面缓存文件
//                    CacheUtil.setUrlCache(result,  CONFIG_URL);
//                    //后面可以是UI更新，仅供参考
//                    showConfig(result);
//                }
// 
//                @Override
//                public void onFailure(Throwable arg0) {
//                    //根据失败原因，考虑是显示加载失败，还是再读取缓存
//                }
// 
//            });
//        }
//    }
//
//	private void showConfig(String cacheConfigString) {
//		
//	}
    
}
