package news_status;

import java.sql.Timestamp;
import java.util.List;

public class NewsStatusService {

	private NewsStatusDAO_interface dao;

	public NewsStatusService() {
		dao = new NewsStatusDAO();
	}

	public NewsStatusVO addNewsStatus( Integer newsStatusId, String categoryName,
			Timestamp createdTime ) {

		NewsStatusVO newsStatusVO = new NewsStatusVO();

		newsStatusVO.setNewsStatusId(newsStatusId);
		newsStatusVO.setCategoryName(categoryName);
		newsStatusVO.setCreatedTime(createdTime);
		
		dao.insert(newsStatusVO);

		return newsStatusVO;
	}

	public NewsStatusVO updateNewsStatus( Integer newsStatusId, String categoryName,
			Timestamp createdTime) {

		NewsStatusVO newsStatusVO = new NewsStatusVO();

		newsStatusVO.setNewsStatusId(newsStatusId);
		newsStatusVO.setCategoryName(categoryName);
		newsStatusVO.setCreatedTime(createdTime);
		
		dao.update(newsStatusVO);

		return newsStatusVO;
	}

	public void deleteNewsStatus(Integer newsStatusId) {
		dao.delete(newsStatusId);
	}

	public NewsStatusVO getOneNewsStatus(Integer newsStatusId) {
		return dao.findByPrimaryKey(newsStatusId);
	}

	public List<NewsStatusVO> getAll() {
		return dao.getAll();
	}
}
