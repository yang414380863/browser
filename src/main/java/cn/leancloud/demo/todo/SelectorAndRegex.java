package cn.leancloud.demo.todo;


import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by YanGGGGG on 2017/4/13.
 */

public class SelectorAndRegex {
    private static String string;

    public static String getItemData(Document doc,Website website,String ruleString,int position){
        //判断是哪个Rule
        Rule rule;
        switch (ruleString){
            case "Link":{
                rule=website.getItemRule().getLinkRule();
                break;
            }
            default:
                rule=website.getItemRule().getLinkRule();//肯定不触发
                break;
        }
        if (rule==null){
            return "";
        }
        //先用选择器
        if (doc.select(website.getItemSelector()).size()==0){
            //匹配不到
            return "";
        }
        if (rule.getMethod().equals("attr")){
            string=doc.select(website.getItemSelector()).get(position).select(rule.getSelector()).attr(rule.getAttribute());
        }else if (rule.getMethod().equals("text")){
            string=doc.select(website.getItemSelector()).get(position).select(rule.getSelector()).text();
        }else if (rule.getMethod().equals("html")){
            string=doc.select(website.getDetailItemSelector()).get(position).select(rule.getSelector()).html();
        }
        if (rule.getRegex()!=null){
            //用正则
            Pattern pattern=Pattern.compile(rule.getRegex());
            Matcher matcher=pattern.matcher(string);
            ArrayList<String> strings=new ArrayList<>();
            if (matcher.find()){
                for (int i=0;i<matcher.groupCount();i++){
                    strings.add("");
                    strings.set(i,matcher.group(i+1));
                }
            }
            string=rule.getReplace();
            for (int i=1;i-1<strings.size();i++){
                string=string.replace("$"+i,strings.get(i-1));
            }
        }
        return string;
    }

    public static int getItemcount(Document doc,Website website){
        int itemcount=doc.select(website.getItemSelector()).size();
        return itemcount;
    }
}
