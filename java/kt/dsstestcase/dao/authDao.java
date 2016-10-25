package kt.dsstestcase.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kt.dsstestcase.vo.authVO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class authDao {
	
	@Autowired
	private SqlSession sql;
	

	/**
	 * select
	 * @param not used
	 * @return
	 */
	public List<authVO> select(String param){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("param", param);
		return sql.selectList("auth.select", _map);
	}

	/**
	 * selectOneByLoginId
	 * @param not used
	 * @return
	 */
	public authVO selectOneByLoginId(String login_id, String login_pass){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("login_id", login_id);
		_map.put("login_pass", login_pass);
		return (authVO)sql.selectOne("auth.selectOneByLoginId", _map);
	}	
	

	/**
	 * selectOneByLoginIdForAdmin
	 * @param not used
	 * @return
	 */
	public authVO selectOneByLoginIdForAdmin(String login_id){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("login_id", login_id);
		return (authVO)sql.selectOne("auth.selectOneByLoginIdForAdmin", _map);
	}	
	
	
	/**
	 * selectOneByLoginSession
	 * @param not used
	 * @return
	 */
	public authVO selectOneByLoginSession(String login_session){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("login_session", login_session);
		return (authVO)sql.selectOne("auth.selectOneByLoginSession", _map);
	}			
	
	
	/**
	* insert
	* @param vo
	* @return
	*/
	public int insert(authVO vo){
		return sql.insert("auth.insert", vo);
	}

	
	/**
	 * updateField
	 * @param vo
	 * @return
	 */
	public int updateField(Map<String,String> _map){
		return sql.update("auth.updateField", _map);
	}
		
		
	/**
	 * updateField
	 * @param vo
	 * @return
	 */
	public int update(authVO _authVO){
		return sql.update("auth.update", _authVO);
	}
		
	
	/**
	 * update
	 * @param vo
	 * @return
	 */
	public int updateLoginSession(String login_id, String login_session){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("login_id", login_id);
		_map.put("login_session", login_session);		
		return sql.update("auth.updateLoginSession", _map);
	}
}