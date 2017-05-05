package cn.leancloud.demo.todo;
import java.util.ArrayList;

public class WebContent {
	private String webSiteName;//网站名
	private String thumbnail;//配图/缩略图
	private String title;//标题
	private String link;//点击之后要进去的网页URL
	private ArrayList<String> img;//原图链接
	private String detail;//详细文字内容


	public boolean haveThumbnail(){
		if (thumbnail!=null){
			return true;
		}
		return false;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public ArrayList<String> getImg() {
		return img;
	}

	public void setImg(ArrayList<String> img) {
		this.img = img;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWebSiteName() {
		return webSiteName;
	}

	public void setWebSiteName(String webSiteName) {
		this.webSiteName = webSiteName;
	}
}