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
  static int positionWebsite =0;
  static int positionCategory =0;
  private static boolean hasInit=false;

  static ItemRule rulePOOCG=new ItemRule();
  final static Website POOCG=new Website("Poocg","https://www.poocg.com/works/index/type/new",rulePOOCG);
  static ItemRule ruleDEVIANTART=new ItemRule();
  final static Website DEVIANTART=new Website("Deviantart","http://www.deviantart.com/browse/all/?order=67108864",ruleDEVIANTART);
  static ItemRule ruleLEIFENG=new ItemRule();
  final static Website LEIFENG=new Website("雷锋网","http://www.leiphone.com/category/sponsor",ruleLEIFENG);
  static ItemRule ruleHAOQIXIN=new ItemRule();
  final static Website HAOQIXIN=new Website("好奇心日报","http://www.qdaily.com/tags/1068.html",ruleHAOQIXIN);

  final static Website[] websites=new Website[]{POOCG,DEVIANTART,LEIFENG,HAOQIXIN};//先暂时这样写WebsiteList 以后再动态生成

  @EngineFunction("init")
  public static void init(){
    POOCG.setItemSelector("li:has(div.imgbox)");
    rulePOOCG.setLinkRule(new Rule("div.imgbox > a[href]","attr","href"));
    POOCG.setCategory(new String[]{"最新","https://www.poocg.com/works/index/type/new","新赞","https://www.poocg.com/works/index/type/love","热门","https://www.poocg.com/works/index/type/hot"
            ,"精华","https://www.poocg.com/works/index/type/best","推荐","https://www.poocg.com/works/index/type/rem"});

    DEVIANTART.setItemSelector("span[class*=thumb]:has(img[data-sigil=torpedo-img])");
    ruleDEVIANTART.setLinkRule(new Rule("a.torpedo-thumb-link","attr","href"));
     DEVIANTART.setCategory(new String[]{"Newest","http://www.deviantart.com/newest/","What's Hot","http://www.deviantart.com/whats-hot/"
            ,"Undiscovered","http://www.deviantart.com/undiscovered/","Popular 24 hours","http://www.deviantart.com/popular-24-hours/","Popular All Time","http://www.deviantart.com/popular-all-time/"});

    LEIFENG.setItemSelector("li > div.box:has(div.img)");
    ruleLEIFENG.setLinkRule(new Rule("div.img > a[target]","attr","href"));
    LEIFENG.setCategory(new String[]{"人工智能","http://www.leiphone.com/category/ai","智能驾驶","http://www.leiphone.com/category/transportation","网络安全","http://www.leiphone.com/category/letshome"
            ,"AR/VR","http://www.leiphone.com/category/arvr","机器人","http://www.leiphone.com/category/robot","Fintect","http://www.leiphone.com/category/fintech","物联网","http://www.leiphone.com/category/iot"
            ,"未来医疗","http://www.leiphone.com/category/aihealth","只能硬件","http://www.leiphone.com/category/weiwu","AI+","http://www.leiphone.com/category/aijuejinzhi"});

    HAOQIXIN.setItemSelector("div[class*=packery-item] > a[href]");
    ruleHAOQIXIN.setLinkRule(new Rule("a[href]","attr","href","(.*)","http://www.qdaily.com$1"));
    HAOQIXIN.setCategory(new String[]{"长文章","http://www.qdaily.com/tags/1068.html","10个图","http://www.qdaily.com/tags/1615.html","TOP15","http://www.qdaily.com/tags/29.html"
            ,"商业","http://www.qdaily.com/categories/18.html","智能","http://www.qdaily.com/categories/4.html","设计","http://www.qdaily.com/categories/17.html","时尚","http://www.qdaily.com/categories/19.html"
            ,"娱乐","http://www.qdaily.com/categories/3.html","城市","http://www.qdaily.com/categories/5.html","游戏","http://www.qdaily.com/categories/54.html"});

    hasInit=true;
  }
  @EngineFunction("Update")
  public static void Update() {
    logger.info("begin Update");
    if (!hasInit){
      init();
    }

    websiteNow=websites[positionWebsite];
    if (websiteNow.getCategory()!=null) {
      if (positionCategory <websiteNow.getCategory().length){
        websiteNow.setIndexUrl(websiteNow.getCategory()[positionCategory +1]);
        positionCategory +=2;
        logger.info("index:"+websiteNow.getIndexUrl());
        //Browser.sendRequest(websiteNow);
      }else {
        positionCategory =0;
        positionWebsite++;
        if (positionWebsite<websites.length){
          //Browser.sendRequest(websiteNow);
        }else {
          positionWebsite =0;
          positionCategory =0;
          logger.info("finish===============================================");
          return;
        }

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
