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
  static private int positionWebsite =0;
  static private int positionCategory =0;

  static ItemRule rulePOOCG=new ItemRule();
  final static Website POOCG=new Website("Poocg","https://www.poocg.com/works/index/type/new",rulePOOCG);
  static ItemRule ruleDEVIANTART=new ItemRule();
  final static Website DEVIANTART=new Website("Deviantart","http://www.deviantart.com/browse/all/?order=67108864",ruleDEVIANTART);
  static ItemRule ruleUNSPLASH=new ItemRule();
  final static Website UNSPLASH=new Website("Unsplash","https://unsplash.com/",ruleUNSPLASH);
  static ItemRule ruleLEIFENG=new ItemRule();
  final static Website LEIFENG=new Website("雷锋网","http://www.leiphone.com/category/sponsor",ruleLEIFENG);

  final static Website[] websites=new Website[]{POOCG,DEVIANTART,UNSPLASH,LEIFENG};//先暂时这样写WebsiteList 以后再动态生成

  @EngineFunction("Update")
  public static void Update() {

    POOCG.setItemSelector("li:has(div.imgbox)");
    rulePOOCG.setLinkRule(new Rule("div.imgbox > a[href]","attr","href"));
    rulePOOCG.setThumbnailRule(new Rule("div.imgbox > a > img[src]","attr","src"));
    rulePOOCG.setTitleRule(new Rule("div.infobox > p.titles","text"));
    POOCG.setDetailItemSelector("img[style*=max-width]");
    rulePOOCG.setImgRule(new Rule("*","attr","src"
            ,"(https:\\/\\/imagescdn\\.poocg\\.me\\/uploadfile\\/photo\\/[0-9]{4}\\/[0-9]{1,2}\\/[a-z|0-9|_]+\\.[a-z]+)",new String[]{""}));
    POOCG.setNextPageRule(new Rule("a#pagenav","attr","href"));
    POOCG.setCategory(new String[]{"最新","https://www.poocg.com/works/index/type/new","新赞","https://www.poocg.com/works/index/type/love","热门","https://www.poocg.com/works/index/type/hot"
            ,"精华","https://www.poocg.com/works/index/type/best","推荐","https://www.poocg.com/works/index/type/rem"});

    DEVIANTART.setItemSelector("span[class*=thumb]:has(img[data-sigil=torpedo-img])");
    ruleDEVIANTART.setLinkRule(new Rule("a.torpedo-thumb-link","attr","href"));
    ruleDEVIANTART.setThumbnailRule(new Rule("a.torpedo-thumb-link > img[data-sigil=torpedo-img]","attr","src"));
    ruleDEVIANTART.setTitleRule(new Rule("span.info > span.title-wrap > span.title","text"));
    DEVIANTART.setDetailItemSelector("div[class=dev-view-deviation]");
    ruleDEVIANTART.setImgRule(new Rule("img[class=dev-content-full]","attr","src"));
    DEVIANTART.setNextPageRule(new Rule("a.selected","attr","href","(http:\\/\\/www\\.deviantart\\.com\\/[a-z|-]+\\/)()",new String[]{"?offset=","size"}));
    DEVIANTART.setCategory(new String[]{"Newest","http://www.deviantart.com/newest/","What's Hot","http://www.deviantart.com/whats-hot/"
            ,"Undiscovered","http://www.deviantart.com/undiscovered/","Popular 24 hours","http://www.deviantart.com/popular-24-hours/","Popular All Time","http://www.deviantart.com/popular-all-time/"});

    UNSPLASH.setItemSelector("div.y5w1y");
    ruleUNSPLASH.setLinkRule(new Rule("a[title]","attr","href","()(\\/\\?photo=[a-z|A-Z|0-9|-]+)",new String[]{"https://unsplash.com",""}));
    ruleUNSPLASH.setThumbnailRule(new Rule("a[href]","attr","style","(https:\\/\\/images\\.unsplash\\.com\\/[a-z|0-9|-|-|?|=|&|,|\\/]+)",new String[]{""}));
    ruleUNSPLASH.setTitleRule(new Rule("a[class=_3XzpS _3myVE _2zITg]","text","()([a-z|A-Z|\\s]+)",new String[]{"Photo By: ",""}));
    UNSPLASH.setDetailItemSelector("div.RN0KT");
    ruleUNSPLASH.setImgRule(new Rule("*","attr","style","(https:\\/\\/images\\.unsplash\\.com\\/[a-z|0-9|&||\\/|-]+)",new String[]{""}));
    UNSPLASH.setCategory(new String[]{"home","https://unsplash.com/","New","https://unsplash.com/new","Following","https://unsplash.com/following"});
    //ruleUNSPLASH.setNextPageRule(new Rule());没写下一页RULE

    LEIFENG.setItemSelector("li > div.box:has(div.img)");
    ruleLEIFENG.setLinkRule(new Rule("div.img > a[target]","attr","href"));
    ruleLEIFENG.setThumbnailRule(new Rule("div.img > a[target] > img.lazy","attr","data-original"));
    ruleLEIFENG.setTitleRule(new Rule("div.img > a[target] > img.lazy","attr","title"));
    LEIFENG.setDetailItemSelector("div[class=lph-article-comView] > p");
    ruleLEIFENG.setImgRule(new Rule("p > img[alt]","attr","src"));
    ruleLEIFENG.setArticleRule(new Rule("p","text"));
    LEIFENG.setNextPageRule(new Rule("div.lph-page > a.next","attr","href"));
    LEIFENG.setCategory(new String[]{"人工智能","http://www.leiphone.com/category/ai","智能驾驶","http://www.leiphone.com/category/transportation","网络安全","http://www.leiphone.com/category/letshome"
            ,"AR/VR","http://www.leiphone.com/category/arvr","机器人","http://www.leiphone.com/category/robot","Fintect","http://www.leiphone.com/category/fintech","物联网","http://www.leiphone.com/category/iot"
            ,"未来医疗","http://www.leiphone.com/category/aihealth","只能硬件","http://www.leiphone.com/category/weiwu","AI+","http://www.leiphone.com/category/aijuejinzhi"});


    websiteNow=websites[positionWebsite];
    if (websiteNow.getCategory()!=null) {
      if (positionCategory <websiteNow.getCategory().length){
        websiteNow.setIndexUrl(websiteNow.getCategory()[positionCategory +1]);
        positionCategory +=2;
        //logger.info("index:"+websiteNow.getIndexUrl());
        Browser.sendRequest(websiteNow);
      }else {
        positionCategory =0;
        positionWebsite++;
        if (positionWebsite<websites.length){
          Browser.sendRequest(websiteNow);
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
