package news_status;

import java.sql.Timestamp;

public class NewsStatusVO implements java.io.Serializable{

	//news_status_id, category_name, created_time
	private Integer newsStatusId;
	private String categoryName;
	private Timestamp createdTime;
	
	
	public Integer getNewsStatusId() {
		return newsStatusId;
	}
	public void setNewsStatusId(Integer newsStatusId) {
		this.newsStatusId = newsStatusId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	
}
