package news_status;

import java.util.List;

import news.NewsVO;

public interface NewsStatusDAO_interface {

	 public void insert(NewsStatusVO newsStatusVO);
     public void update(NewsStatusVO newsStatusVO);
     public void delete(Integer newsStatusId);
     public NewsStatusVO findByPrimaryKey(Integer newsStatusId);
     public List<NewsStatusVO> getAll();
     
}
