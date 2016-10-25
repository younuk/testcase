package kt.dsstestcase.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kt.dsstestcase.vo.actTypeVO;
import kt.dsstestcase.vo.testcaseVO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class testcaseDao {
	
	@Autowired
	private SqlSession sql;
	

	/**
	 * selectOneBySn
	 * @param sn
	 * @return
	 */
	public testcaseVO selectOneBySn(int sn){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("sn", Integer.toString(sn));
		return (testcaseVO)sql.selectOne("testcase.selectOneBySn", _map);
	}
	

	/**
	 * select
	 * @param vo
	 * @return
	 */
	public List<testcaseVO> select(testcaseVO vo){
		return sql.selectList("testcase.select", vo);
	}		
	
	
	/**
	 * selectView
	 * @param vo
	 * @return
	 */
	public List<testcaseVO> selectView(testcaseVO vo){		
		return sql.selectList("testcase.selectView", vo);
	}			
	
	
	/**
	 * selectActTypeGroupList
	 * @return
	 */
	public List<actTypeVO> selectActTypeGroupList(){
		return sql.selectList("testcase.selectActTypeGroupList", null);
	}		
	
			
	/**
	 * insert
	 * @param vo
	 * @return
	 */
	public int insert(testcaseVO vo){
		return sql.insert("testcase.insert", vo);
	}
	
	
	/**
	 * update
	 * @param vo
	 * @return
	 */
	public int update(testcaseVO vo){
		return sql.update("testcase.update", vo);
	}
	
	
	/**
	 * updateField
	 * @param vo
	 * @return
	 */
	public int updateField(Map<String,String> _map){
		return sql.update("testcase.updateField", _map);
	}
		
	
	
	/**
	 * delete
	 * @param sn
	 * @return
	 */
	public int delete(int sn){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("sn", Integer.toString(sn));		
		return sql.delete("testcase.delete", _map);
	}
	
}