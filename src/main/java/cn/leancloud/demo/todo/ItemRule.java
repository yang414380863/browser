package cn.leancloud.demo.todo;


/**
 * Created by YanGGGGG on 2017/4/6.
 */

public class ItemRule {

    private Rule thumbnailRule;
    private Rule titleRule;
    private Rule linkRule;
    private Rule imgRule;
    private Rule articleRule;


    public Rule getThumbnailRule() {
        return thumbnailRule;
    }

    public void setThumbnailRule(Rule thumbnailRule) {
        this.thumbnailRule = thumbnailRule;
    }

    public Rule getTitleRule() {
        return titleRule;
    }

    public void setTitleRule(Rule titleRule) {
        this.titleRule = titleRule;
    }

    public Rule getLinkRule() {
        return linkRule;
    }

    public void setLinkRule(Rule linkRule) {
        this.linkRule = linkRule;
    }

    public Rule getImgRule() {
        return imgRule;
    }

    public void setImgRule(Rule imgRule) {
        this.imgRule = imgRule;
    }

    public Rule getArticleRule() {
        return articleRule;
    }

    public void setArticleRule(Rule articleRule) {
        this.articleRule = articleRule;
    }


}
