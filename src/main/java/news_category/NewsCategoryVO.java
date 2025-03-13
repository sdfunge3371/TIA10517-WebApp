package news_category;

import java.sql.Timestamp;

public class NewsCategoryVO implements java.io.Serializable{

	//news_category_id, category_name, created_time
	private String newsCategoryId;
	private String newsCategoryName;
	private Timestamp createdTime;
	
	public String getNewsCategoryId() {
		return newsCategoryId;
	}
	public void setNewsCategoryId(String newsCategoryId) {
		this.newsCategoryId = newsCategoryId;
	}
	public String getNewsCategoryName() {
		return newsCategoryName;
	}
	public void setNewsCategoryName(String newsCategoryName) {
		this.newsCategoryName = newsCategoryName;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	
	
}
