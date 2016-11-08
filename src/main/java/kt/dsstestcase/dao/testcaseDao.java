package kt.dsstestcase.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kt.dsstestcase.vo.actTypeVO;
import kt.dsstestcase.vo.testcaseCategoryVO;
import kt.dsstestcase.vo.testcaseVO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository(value="testcaseDao")
public interface testcaseDao {
	public List<testcaseVO> select(String[] array);

	public testcaseVO selectOne(String sn);

	public List<testcaseVO> selectView(testcaseVO vo);

	public List<actTypeVO> selectActTypeGroupList();

	public int insert(testcaseVO vo);

	public int update(testcaseVO vo);


	public int updateField(Map<String,String> _map);

	public int delete(@Param("sn") int sn);

    public List<testcaseCategoryVO> selectCtgr(@Param("gid") String param);

    public List<HashMap<String, Object>> selectCtgrMapping(@Param("gid") String param);
}