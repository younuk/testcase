package kt.dsstestcase.dao;


import java.util.HashMap;
import java.util.Map;
import kt.dsstestcase.vo.testcaseGroupVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class testcaseGroupItemDao {
	
	@Autowired
	private SqlSession sql;
	

	/**
	 * selectOneBySn
	 * @param sn
	 * @return
	 */
	public testcaseGroupVO selectByGid(String gid){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("gid", gid);
		return (testcaseGroupVO)sql.selectOne("testcaseGroupItem.selectByGid", _map);
	}
	

	
	/**
	 * insert
	 * @param vo
	 * @return
	 */
	public int insert(testcaseGroupVO vo){
		return sql.insert("testcaseGroupItem.insert", vo);
	}
	

	/**
	 * delete
	 * @param sn
	 * @return
	 */
	public int delete(String sn){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("sn", sn);		
		return sql.delete("testcaseGroupItem.delete", _map);
	}
	
}