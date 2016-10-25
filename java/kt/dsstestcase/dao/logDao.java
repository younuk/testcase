package kt.dsstestcase.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kt.dsstestcase.vo.logVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class logDao {
	
	@Autowired
	private SqlSession sql;
	
	
	/**
	 * selectByUuidSn
	 * @param vo
	 * @return
	 */
	public logVO selectOneByUuidSn(String uuid, String sn){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("uuid", uuid);		
		_map.put("sn", sn);					
		return sql.selectOne("log.selectOneByUuidSn", _map);
	}		
	
	/**
	 * selectByUuid
	 * @param vo
	 * @return
	 */
	public List<logVO> selectByUuid(String uuid){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("uuid", uuid);			
		return sql.selectList("log.selectByUuid", _map);
	}		
	
			
	/**
	 * insert
	 * @param vo
	 * @return
	 */
	public int insert(logVO vo){
		return sql.insert("log.insert", vo);
	}
	
}