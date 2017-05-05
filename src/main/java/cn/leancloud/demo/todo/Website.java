package cn.leancloud.demo.todo;

/**
 * Created by YanGGGGG on 2017/4/6.
 * 新建一个爬虫即新建一个Website对象
 */

public class Website {


    private String webSiteName;//网站名
    private String indexUrl;//网站首页
    private String nextPageUrl;//List页面下一页
    private String nextPageDetailUrl;//Detail页面下一页
    private Rule nextPageRule;//List页面下一页Rule
    private Rule nextPageDetailRule;//Detail页面下一页Rule
    private String[] category;//分类
    private int loginRequired;

    private ItemRule itemRule;
    private String itemSelector;
    private String detailItemSelector;


    public Website(String webSiteName,String indexUrl,ItemRule itemRule){
        this.webSiteName=webSiteName;
        this.indexUrl=indexUrl;
        this.itemRule = itemRule;
        detailItemSelector="*";
    }

    public String getDetailItemSelector() {
        return detailItemSelector;
    }

    public void setDetailItemSelector(String detailItemSelector) {
        this.detailItemSelector = detailItemSelector;
    }

    public String getItemSelector() {
        return itemSelector;
    }

    public void setItemSelector(String itemSelector) {
        this.itemSelector = itemSelector;
    }

    public ItemRule getItemRule() {
        return itemRule;
    }

    public void setItemRule(ItemRule itemRule) {
        this.itemRule = itemRule;
    }

    public String getWebSiteName() {
        return webSiteName;
    }

    public void setWebSiteName(String webSiteName) {
        this.webSiteName = webSiteName;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getNextPageDetailUrl() {
        return nextPageDetailUrl;
    }

    public void setNextPageDetailUrl(String nextPageDetailUrl) {
        this.nextPageDetailUrl = nextPageDetailUrl;
    }

    public Rule getNextPageRule() {
        return nextPageRule;
    }

    public void setNextPageRule(Rule nextPageRule) {
        this.nextPageRule = nextPageRule;
    }

    public Rule getNextPageDetailRule() {
        return nextPageDetailRule;
    }

    public void setNextPageDetailRule(Rule nextPageDetailRule) {
        this.nextPageDetailRule = nextPageDetailRule;
    }
}
