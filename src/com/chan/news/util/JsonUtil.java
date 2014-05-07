package com.chan.news.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.chan.news.model.News;
import com.google.gson.stream.JsonReader;

public class JsonUtil {

	public static List<News> fromJsonToList(String newsesJsonString) {
		List<News> resultList = new ArrayList<News>();
		JsonReader reader = null;
		News news = null;
		
		try {
			reader = new JsonReader(new StringReader(newsesJsonString));
			//开始解析对象:{"newses":[]}
			reader.beginObject();
			reader.nextName(); // consume property "newses"
			// 开始解析数组[{},{},{}]
			reader.beginArray();
			while(reader.hasNext()) {
				// 开始解析对象{"content":"","":"",...}
				reader.beginObject();
				news = new News();
				String tagName = null;
				while(reader.hasNext()) {
					tagName = reader.nextName();
					if("content".equals(tagName)) {
						String content = reader.nextString();
						news.setContent(content);
					} else if("followNum".equals(tagName)) {
						int num = reader.nextInt();
						news.setFollowNum(num);
					} else if("id".equals(tagName)) {
						int id = reader.nextInt();
						System.out.println("id:" + id);
						news.setId(id);
					} else if("title".equals(tagName)) {
						String title = reader.nextString();
						news.setTitle(title);
					} else if("summary".equals(tagName)) {
						String summary = reader.nextString();
						news.setSummary(summary);
					} else if("publishTime".equals(tagName)) {
						String publishTime = reader.nextString();
						news.setPublishTime(publishTime);
					} else if("picUrl".equals(tagName)) {
						String picUrl = reader.nextString();
						System.out.println("picUrl:" + picUrl);
						news.setPicUrl(picUrl);
					} else if("newsCategory".equals(tagName)) {
						String newsCategory = reader.nextString();
						news.setNewsCategory(newsCategory);
					}
			
				}
				reader.endObject();
				resultList.add(news);

			}
			reader.endArray();
			reader.endObject();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}

	public static String parseCityNameFromJson(String result) {

		JsonReader reader = null;
		List<AddressComponent> acs = null;
		List<Result> results = null;
		try {
			reader = new JsonReader(new StringReader(result));
			
			//开始解析对象:{"results":[]}
			reader.beginObject();
			while(reader.hasNext()) {
				if("results".equals(reader.nextName())) {
					results = new ArrayList<Result>();
					reader.beginArray(); // 开始读results数组
					while(reader.hasNext()) {
						reader.beginObject(); // 开始读每一个result
						while(reader.hasNext()) {
							String name = reader.nextName();
							if("address_components".equals(name)) {
								acs = readAddressComponents(reader);
								
							} else {
								reader.skipValue();
							}
						}
						reader.endObject();
						results.add(new Result(acs));
					}
					reader.endArray();
				} else { // 忽略status
					reader.skipValue();
				}
			}
			reader.endObject();
			
		
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
//		for(int i = 0; i < results.size(); i++) {
//			List<AddressComponent> acs1 = results.get(i).acs;
//			
//			Log.i("TestMethods", i + "-----------------------");
//			
//			for(int j = 0; j < acs1.size(); j++) {
//				Log.i("TestMethods", acs1.get(j).longName + "----" + j);
//		
//			}
//			
//		}
		Log.i("TestMethods",results.get(0).acs.get(2).longName);
//		cityName = acs.get(3).longName;
		return results.get(0).acs.get(2).longName;
	}
	
	private static List<AddressComponent> readAddressComponents(JsonReader reader) throws IOException {
		List<AddressComponent> acs = new ArrayList<AddressComponent>();
		
		reader.beginArray();
		while(reader.hasNext()) {
			acs.add(readAddressComponent(reader));
		}
		reader.endArray();
		
		return acs;
	}
	
	private static AddressComponent readAddressComponent(JsonReader reader) throws IOException {
		String long_name = null;
		String short_name = null;
		List<String> types = null;
		
		reader.beginObject();
		
		while(reader.hasNext()) {
			String tagName = reader.nextName();
			if("long_name".equals(tagName)) {
				long_name = reader.nextString();
			} else if("short_name".equals(tagName)) {
				short_name = reader.nextString();
			} else if("types".equals(tagName)) {
				types = readTypes(reader);
			}
		}
		
		reader.endObject();
		
		return new AddressComponent(long_name,short_name,types);
	}
	
	private static List<String> readTypes(JsonReader reader) throws IOException {
		List<String> types = new ArrayList<String>();
		
		reader.beginArray();
		while(reader.hasNext()) {
			types.add(reader.nextString());
		}
		reader.endArray();
		return types;
	}
	
	static class Result {
		List<AddressComponent> acs;
		public Result(List<AddressComponent> acs) {
			this.acs = acs;
		}
	}
	
	static class AddressComponent {
		String longName;
		String shortName;
		List<String> types;
		
		public AddressComponent(String longName, String shortName, List<String> types) {
			this.longName = longName;
			this.shortName = shortName;
			this.types = types;
		}
	}

}
