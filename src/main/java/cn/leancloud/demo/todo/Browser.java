package cn.leancloud.demo.todo;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.*;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.List;

class Browser {
	static final Logger logger = LogManager.getLogger(Browser.class);
	public static Website websiteNow;
	static void sendRequest(final Website website){
		websiteNow =website;
		String url=websiteNow.getIndexUrl();
		//logger.info("index:"+url);
		OkHttpClient client = new OkHttpClient();
		final Request request = new Request.Builder()
				.url(url)
				.build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				logger.info("onFailure");
			}
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Document doc= Jsoup.parse(response.body().string());
				analysis(doc);
			}
		});
	}

	static void analysis(Document doc){
		Elements list = doc.select(websiteNow.getItemSelector());
		if (list.size()==0){
			logger.info("Can't find,Size=0");
		}else {
			final String link=SelectorAndRegex.getItemData(doc,websiteNow,"Link",0);
			final String index=websiteNow.getIndexUrl();
			//logger.info("link:"+link);

			AVQuery<AVObject> isLinkExist = new AVQuery<>("Website");
			isLinkExist.whereStartsWith("link", link);
			isLinkExist.findInBackground(new FindCallback<AVObject>() {
				@Override
				public void done(List<AVObject> list2, AVException e) {
					if(list2==null){}
					else if (list2.size()==0){
						//没找到一样link的=>是第一次添加 或者 有更新内容
						AVQuery<AVObject> getOldItem=new AVQuery<>("Website");
						getOldItem.whereStartsWith("index",index);
						getOldItem.findInBackground(new FindCallback<AVObject>() {
							@Override
							public void done(List<AVObject> list, AVException e) {
								//logger.info("list"+list);
								if (list==null){}
								else if (list.isEmpty()){
									//是第一次添加
									//logger.info("first add");
									AVObject add = new AVObject("Website");
									add.put("link",link);
									add.put("index",index);
									add.saveInBackground();
								}else{
									//有更新内容
									logger.info("update");
									for (AVObject item : list) {
										String id = item.getObjectId();
										item.put("link",link);
										item.put("index",index);
										item.setObjectId(id);
										item.saveInBackground();
										Date date=new Date();
										Push(index,date);
									}
								}
							}
						});
					}else {
						//logger.info("has exist");
					}
					Cloud.Update();
				}
			});
		}
	}

	public static void Push(String index, Date date) {
		AVPush push = new AVPush();
		String message="{\"data\": {\"action\": \"com.example.yang.myapplication.UPDATE_STATUS\",\"index\": \""+index+"\",\"date\": \""+date+"\"}}";
		JSONObject jsonObject = JSONObject.parseObject(message);
		push.setPushToAndroid(true);
		push.setData(jsonObject);
		push.sendInBackground();
	}
}
