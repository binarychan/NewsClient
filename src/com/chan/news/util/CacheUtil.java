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
	 *  ���ListView��ʾ�������⣺����ListView�е�������Ҫ�������ϻ�ȡ��
	 *  ��ʱ��UI�߳��Ͻ����Ȼ�ǲ����ʵģ����ܵ���ANR��
	 *  ����Ӧ�ÿ���һ�������߳̽������ݻ�ȡ����������ΪUI�߳��Ǿ�����ȫ���̰߳�ȫ�ģ�
	 *  ��͵����ڹ����߳��е�������ImageView�����ܵ���UI���»��ҳ��֣�
	 *  ���У����listview����Ҫ���غܶ������ݺ�ͼƬ��Դ���û��п��ܲ�����������
	 *  �⽫�������Ĵ���������Դ�͵�ص�������õĽ�������ǣ�
	 *  ��ͼƬ����Դ����һ���߳̽��������أ�ͬʱ��UI�߳������ImageView����ʾ������
	 *  Ҳ���ǽ�ͼƬ��Դ�����ݻ�����UI�߳��п��Է��ʵ��ĵط�����UI�߳�֪Ϥ��Щ����ͼƬ
	 *  ��Դ�Ѿ���������ʾ�ˣ�ͬʱΪ�˼���������Դ��ʹ�ã�ͨ��ÿһ������һ��ͼƬ�ķ�ʽ
	 *  �������ڱ��ػ����С�
	 *  To start, we��ll be adding a class to manage 
	 *  both our local image cache and a download queue for the images.
	 *  Let��s call this class ImageManager, and get it started:
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
            
            //1. ��ϵͳʱ�䲻׼ȷ�������غܾ���ǰ
            //2. �����粻����ʱ��ֻ�ܶ�ȡ����
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
            //�����������ݵ����̣����Ǵ����ļ�
            FileUtil.writeTextFile(file, data);
        } catch (IOException e) {
            Log.d(TAG, "write " + file.getAbsolutePath() + " data failed!");
            e.printStackTrace();
        }
    }
 
    public static String getCacheDecodeString(String url) {
        //1. ���������ַ�
        //2. ȥ����׺���������ļ����������ͼ����
    	//   (�ر���ͼƬ����Ҫ������ƴ��������е��ֻ���ͼ�⣬ȫ�����ǵĻ���ͼƬ)
        if (url != null) {
            return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
        }
        return null;
    }
	
//    void getConfig(){
//        //���ȳ��Զ�ȡ����
//        String cacheConfigString = CacheUtil.getUrlCache(CONFIG_URL);
//        
//        //���ݽ���ж��Ƕ�ȡ���棬�������¶�ȡ
//        if (cacheConfigString != null) {
//            showConfig(cacheConfigString);
//        } else {
//            //����������ǿգ�˵����Ҫ���¼���
//            //����Ϊ�յ�ԭ�������1.�޻���;2. �������;3.��ȡ�������
//            AsyncHttpClient client = new AsyncHttpClient();
//            
//            client.get(CONFIG_URL, new AsyncHttpResponseHandler(){
// 
//                @Override
//                public void onSuccess(String result){
//                    //�ɹ����أ��򱣴浽������Ϊ���滺���ļ�
//                    CacheUtil.setUrlCache(result,  CONFIG_URL);
//                    //���������UI���£������ο�
//                    showConfig(result);
//                }
// 
//                @Override
//                public void onFailure(Throwable arg0) {
//                    //����ʧ��ԭ�򣬿�������ʾ����ʧ�ܣ������ٶ�ȡ����
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
