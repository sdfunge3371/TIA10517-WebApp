package news_category;

import java.sql.Timestamp;
import java.util.List;

public class NewsCategoryService {

	private NewsCategoryDAO_interface dao;

	public NewsCategoryService() {
		dao = new NewsCategoryDAO();
	}

	public NewsCategoryVO addEmp( String newsCategoryId, String newsCategoryName,
			Timestamp createdTime ) {

		NewsCategoryVO newsCategoryVO = new NewsCategoryVO();

		newsCategoryVO.setNewsCategoryId(newsCategoryId);
		newsCategoryVO.setNewsCategoryName(newsCategoryName);
		newsCategoryVO.setCreatedTime(createdTime);
		dao.insert(newsCategoryVO);

		return newsCategoryVO;
	}

	public NewsCategoryVO updateEmp( String newsCategoryId, String newsCategoryName,
			Timestamp createdTime ) {

		NewsCategoryVO newsCategoryVO = new NewsCategoryVO();

		newsCategoryVO.setNewsCategoryId(newsCategoryId);
		newsCategoryVO.setNewsCategoryName(newsCategoryName);
		newsCategoryVO.setCreatedTime(createdTime);
		dao.update(newsCategoryVO);

		return newsCategoryVO;
	}

	public void deleteNewsCategory(String newsCategoryId) {
		dao.delete(newsCategoryId);
	}

	public NewsCategoryVO getOneNewsCategory(String newsCategoryId) {
		return dao.findByPrimaryKey(newsCategoryId);
	}

	public List<NewsCategoryVO> getAll() {
		return dao.getAll();
	}
}
