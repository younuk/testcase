package kt.dsstestcase.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kt.dsstestcase.vo.testcaseCategoryVO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class testcaseCategoryDao {
	
	@Autowired
	private SqlSession sql;
	
	
	
	/**
	 * select
	 * @param vo
	 * @return
	 */
	public List<testcaseCategoryVO> select(testcaseCategoryVO vo){
		return sql.selectList("testcaseCategory.select", vo);
	}		
	

    
    /**
     * select
     * @param vo
     * @return
     */
    public List<testcaseCategoryVO> selectUpperCategory(testcaseCategoryVO vo){
        return sql.selectList("testcaseCategory.selectUpperCategory", vo);
    }       
	
	/**
	 * selectByGid
	 * @param vo
	 * @return
	 */
	public List<testcaseCategoryVO> selectByGid(String gid){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("gid", gid);			
		return sql.selectList("testcaseCategory.selectByGid", _map);
	}		
		

	/**
	 * selectOneBySn
	 * @param vo
	 * @return
	 */
	public testcaseCategoryVO selectOneBySn(String sn){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("sn", sn);		
		return (testcaseCategoryVO)sql.selectList("testcaseCategory.selectOneBySn", _map);
	}	
	
			
	/**
	 * insert
	 * @param vo
	 * @return
	 */
	public int insert(testcaseCategoryVO vo){
		return sql.insert("testcaseCategory.insert", vo);
	}
	
	
	/**
	 * update
	 * @param vo
	 * @return
	 */
	public int update(testcaseCategoryVO vo){
		return sql.update("testcaseCategory.update", vo);
	}
	
	
	/**
	 * delete
	 * @param sn
	 * @return
	 */
	public int delete(int sn){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("sn", Integer.toString(sn));		
		return sql.delete("testcaseCategory.delete", _map);
	}
	
}