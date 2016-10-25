package kt.dsstestcase.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kt.dsstestcase.dao.authDao;
import kt.dsstestcase.vo.authVO;

import org.springframework.stereotype.Service;


@Service
public class authService {

	@Resource(name="authDao")
	private authDao _authDao;
	
	
	/**
	 * select
	 * @param str
	 */
	public List<authVO> select(){
		return this._authDao.select("");
	}
	
	/**
	 * selectOneByLoginId
	 * @param str
	 */
	public authVO selectOneByLoginId(String login_id, String login_pass){
		return this._authDao.selectOneByLoginId(login_id, login_pass);
	}	
	
	/**
	 * selectOneByLoginId
	 * @param str
	 */
	public authVO selectOneByLoginIdForAdmin(String login_id){
		return this._authDao.selectOneByLoginIdForAdmin(login_id);
	}		
	
	/**
	 * selectOneByLoginSession
	 * @param str
	 */
	public authVO selectOneByLoginSession(String login_session){
		return this._authDao.selectOneByLoginSession(login_session);
	}		
	
	
	/**
	 * insert
	 * @param _vo
	 * @return
	 */
	public int insert(authVO _vo){
		int result = 0;
		if(_vo != null){
			result = this._authDao.insert(_vo);
		}
		return result;
	}
	
	
	/**
	 * updateField
	 * @param _map
	 * @return
	 */
	public int updateField(String login_id, String field, String val){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("login_id",login_id);
		_map.put("field", field);
		_map.put("val", val);
		return this._authDao.updateField(_map);
	}
	
	
	/**
	 * updateLoginSession
	 * @param login_id
	 * @param login_session
	 * @return
	 */
	public int updateLoginSession(String login_id, String login_session){
		return this._authDao.updateLoginSession(login_id, login_session);
	}

	
}
