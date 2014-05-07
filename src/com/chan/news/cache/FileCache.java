package com.chan.news.cache;

import java.io.File;
import java.net.URLEncoder;

import android.content.Context;
import android.os.Environment;

public class FileCache {
	
	private File cacheDir;
    
    public FileCache(Context context){
        // �ҵ����滺��ͼƬ��·��
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(Environment.getExternalStorageDirectory(), "NewsLazyImages");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
    
    public File getFile(String url){
    	// ͨ��hashcode��֤ͼƬ������һ�������Ľ������������demo������
        String filename=String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;
        
    }
    
    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files == null)
            return;
        for(File f : files)
            f.delete();
    }
    
}
