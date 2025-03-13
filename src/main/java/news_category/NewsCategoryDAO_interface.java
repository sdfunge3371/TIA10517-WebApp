package news_category;

import java.util.List;

import news_status.NewsStatusVO;

public interface NewsCategoryDAO_interface {

	public void insert(NewsCategoryVO newsCategoryVO);
    public void update(NewsCategoryVO newsCategoryVO);
    public void delete(String newsCategoryId);
    public NewsCategoryVO findByPrimaryKey(String newsCategoryId);
    public List<NewsCategoryVO> getAll();
    
}
