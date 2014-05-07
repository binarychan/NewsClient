package com.chan.news.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.chan.news.model.News;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;


/**
 * [
   {
     "id": 912345678901,
     "text": "How do I read a JSON stream in Java?",
     "geo": null,
     "user": {
       "name": "json_newb",
       "followers_count": 41
      }
   },
   {
     "id": 912345678902,
     "text": "@json_newb just use JsonReader!",
     "geo": [50.454722, -104.606667],
     "user": {
       "name": "jesse",
       "followers_count": 2
     }
   }
 ]
 *
 */

public class TestJson {

	public List<Message> readJsonStream(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		try {
			return readMessagesArray(reader);
		} finally {
			reader.close();
		}
	}

    public List<Message> readMessagesArray(JsonReader reader) throws IOException {
    	List<Message> messages = new ArrayList<Message>();

    	reader.beginArray();
		while (reader.hasNext()) {
			messages.add(readMessage(reader));
		}
		reader.endArray();
		return messages;
    }

    public Message readMessage(JsonReader reader) throws IOException {
    	long id = -1;
		String text = null;
		User user = null;
		List<Double> geo = null;

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
		    if (name.equals("id")) {
		    	id = reader.nextLong();
		    } else if (name.equals("text")) {
		        text = reader.nextString();
		    } else if (name.equals("geo") && reader.peek() != JsonToken.NULL) {
		        geo = readDoublesArray(reader);
		    } else if (name.equals("user")) {
		        user = readUser(reader);
		    } else {
		        reader.skipValue();
		    }
		}
		reader.endObject();
		return new Message(id, text, user, geo);
    }

    public List<Double> readDoublesArray(JsonReader reader) throws IOException {
    	List<Double> doubles = new ArrayList<Double>();

		reader.beginArray();
		while (reader.hasNext()) {
			doubles.add(reader.nextDouble());
		}
		reader.endArray();
		return doubles;
    }

    public User readUser(JsonReader reader) throws IOException {
    	String username = null;
		int followersCount = -1;

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
		    if (name.equals("name")) {
		    	username = reader.nextString();
		    } else if (name.equals("followers_count")) {
		    	followersCount = reader.nextInt();
		    } else {
		    	reader.skipValue();
		    }
		}
		reader.endObject();
		return new User(username, followersCount);
	}
		 
    class User {
    	String name;
    	int followersCount;
    
    	public User() {}
    	
    	public User(String name, int followersCount) {
    		this.name = name;
    		this.followersCount = followersCount;
    	}
    	
    	
    	
    }
    
    class Message {
    	long id;
    	String text;
    	User user;
    	List<Double> geo;
    	
    	public Message(long id, String text, User user, List<Double> geo) {
    		this.id = id;
    		this.text = text;
    		this.user = user;
    		this.geo = geo;
    	}
    }
    
    public static void main(String[] args) {
    	String json = "{\"newses\":[{\"content\":\"#��Ѱ��370#��������������Ҫ���� �ݲ���������������ȫ�������ܳ��������Ϳ��������ڹ�����б�ʾ�������������飬�Ѿ��ų��˿����ɣ������ڻ�����Ա�ĵ������ڽ����С��Ϳ�����˵������Ŀǰ�Ѿ�����ǿ��������������ֻ�����������ݻ��޷�������\",\"followNum\":0,\"id\":4,\"newsCategory\":\"ͷ��\",\"picUrl\":\"/UploadImages\\132b8f46-76e5-4a34-993d-2014bbf0a828.jpg\",\"publishTime\":\"2014-04-02T23:52:46\",\"summary\":\"����:����MH370�¼���Ҫ���� �ݲ�����\",\"title\":\"��ʧ��ʱ��\"}]}";
    	List<News> newses = fromJsonToList(json);
    	System.out.println(newses.get(0).getContent());
    }
    
    public static List<News> fromJsonToList(String newsesJsonString) {
		List<News> resultList = new ArrayList<News>();
		JsonReader reader = null;
		News news = null;
		try {
			reader = new JsonReader(new StringReader(newsesJsonString));
			//��ʼ��������:{"newses":[]}
			reader.beginObject();
			if(!"newses".equals(reader.nextName())) {
				return null;
			}
			while(reader.hasNext()) {
				// ��ʼ��������[{},{},{}]
				reader.beginArray();
//				while(reader.hasNext()) {
					// ��ʼ��������{"content":"","":"",...}
					reader.beginObject();
					news = new News();
					while(reader.hasNext()) {
						
						String tagName = reader.nextName();
						if("content".equals(tagName)) {
							news.setContent(reader.nextString());
						} else if("followNum".equals(tagName)) {
							news.setFollowNum(reader.nextInt());
						} else if("id".equals(tagName)) {
							news.setId(reader.nextInt());
						} else if("title".equals(tagName)) {
							news.setTitle(reader.nextString());
						} else if("summary".equals(tagName)) {
							news.setSummary(reader.nextString());
						} else if("pulishTime".equals(tagName)) {
							news.setPublishTime(reader.nextString());
						} else if("picUrl".equals(tagName)) {
							news.setPicUrl(reader.nextString());
						} else if("newsCategory".equals(tagName)) {
							news.setNewsCategory(reader.nextString());
						}
					}
					reader.endObject();
					resultList.add(news);
				}
				reader.endArray();
			
//			}
			
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

}



 