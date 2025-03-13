package news.model;

import java.sql.SQLException;
import java.util.List;

public interface NewsDAO_interface {

	 public void insert(NewsVO newsVO);
     public void update(NewsVO newsVO);
     public void delete(String newsId);
     public NewsVO findByPrimaryKey(String newsId);
     public List<NewsVO> getAll();
     
     public String getNextNewsId() throws SQLException;
     
}
