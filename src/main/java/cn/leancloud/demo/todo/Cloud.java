package cn.leancloud.demo.todo;

import cn.leancloud.EngineFunction;
import cn.leancloud.EngineFunctionParam;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static cn.leancloud.demo.todo.Browser.websiteNow;

public class Cloud {
  static final Logger logger = LogManager.getLogger(Cloud.class);
  static private int position=0;
  @EngineFunction("Update")
  public static void Update() {
    RuleAll rulePOOCG=new RuleAll();
    rulePOOCG.setLinkRule(new Rule("div.imgbox > a[href]","attr","href"));
    final Website POOCG=new Website("Poocg","https://www.poocg.com/works/index/type/new",rulePOOCG);
    POOCG.setCategory(new String[]{"最新","https://www.poocg.com/works/index/type/new","新赞","https://www.poocg.com/works/index/type/love","热门","https://www.poocg.com/works/index/type/hot"
            ,"精华","https://www.poocg.com/works/index/type/best","推荐","https://www.poocg.com/works/index/type/rem"});

    websiteNow=POOCG;

    if (websiteNow.getCategory()!=null) {
      if (position<websiteNow.getCategory().length){
        websiteNow.setIndexUrl(websiteNow.getCategory()[position+1]);
        position+=2;
        //logger.info("index:"+websiteNow.getIndexUrl());
        Browser.sendRequest(websiteNow);
      }else {
        position=0;
      }
    }
  }
  @EngineFunction("list")
  public static List<Todo> getNotes(@EngineFunctionParam("offset") int offset) throws AVException {
    Update();
    AVQuery<Todo> query = AVObject.getQuery(Todo.class);
    query.orderByDescending("createdAt");
    query.include("createdAt");
    query.skip(offset);
    try {
      return query.find();
    } catch (AVException e) {
      if (e.getCode() == 101) {
        // 该错误的信息为：{ code: 101, message: 'Class or object doesn\'t exists.' }，说明 Todo 数据表还未创建，所以返回空的
        // Todo 列表。
        // 具体的错误代码详见：https://leancloud.cn/docs/error_code.html
        return new ArrayList<>();
      }
      throw e;
    }
  }
}
