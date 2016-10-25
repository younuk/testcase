package kt.dsstestcase.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kt.dsstestcase.vo.testcaseGroupVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class testcaseGroupDao {
	
	@Autowired
	private SqlSession sql;
	
	

	/**
	 * select
	 * @param sn
	 * @return
	 */
	public List<testcaseGroupVO> select(String param){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("srchWord", param);
		return sql.selectList("testcaseGroup.select", _map);
	}
		
	

	/**
	 * selectOneBySn
	 * @param sn
	 * @return
	 */
	public testcaseGroupVO selectOneByGid(String param){
		return (testcaseGroupVO)sql.selectOne("testcaseGroup.selectOneByGid", param);
	}
	

	/**
	 * select
	 * @param vo
	 * @return
	 */
	public List<testcaseGroupVO> select(testcaseGroupVO vo){
		return sql.selectList("testcaseGroup.select", vo);
	}		
	
	
	/**
	 * selectMaxGid
	 * @return
	 */
	public int selectMaxGid(){
		Map<String,String> _map = new HashMap<String,String>();
		return (Integer)sql.selectOne("testcaseGroup.selectMaxGid", _map);
	}
			
	
	/**
	 * insert
	 * @param vo
	 * @return
	 */
	public int insert(testcaseGroupVO vo){
		return sql.insert("testcaseGroup.insert", vo);
	}
	
	
	/**
	 * update
	 * @param vo
	 * @return
	 */
	public int update(testcaseGroupVO vo){
		return sql.update("testcaseGroup.update", vo);
	}
	
	
	/**
	 * delete
	 * @param sn
	 * @return
	 */
	public int delete(String sn){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("sn", sn);		
		return sql.delete("testcaseGroup.delete", _map);
	}
	
}