package kt.dsstestcase.dao;

import java.util.List;
import java.util.Map;

import kt.dsstestcase.vo.testcaseGroupVO;
import kt.dsstestcase.vo.testcaseVO;

import org.springframework.stereotype.Repository;

@Repository(value="testcaseGroupDao")
public interface testcaseGroupDao {

	public List<testcaseGroupVO> select(String param);

	public testcaseGroupVO selectOneByGid(String param);

    public  List<Map<String, Object>> selectTestcasebyGroup(String param);

	public int insert(testcaseGroupVO vo) throws Exception;

	public int update(testcaseGroupVO vo) throws Exception;

	public int delete(String param);

    public int deleteGroupItem(int param) throws Exception;

    public int insertGroupItem(List<testcaseVO> param) throws Exception;
}