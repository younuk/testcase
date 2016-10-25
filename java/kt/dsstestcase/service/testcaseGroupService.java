package kt.dsstestcase.service;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;

import kt.dsstestcase.dao.testcaseGroupDao;
import kt.dsstestcase.vo.testcaseGroupVO;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class testcaseGroupService {

	@Resource(name="testcaseGroupDao")
	private testcaseGroupDao _testcaseGroupDao;

	/**
	 * selectOneBySn
	 * @param mid
	 */
	public List<testcaseGroupVO> select(String param){
		return this._testcaseGroupDao.select(param);
	}

	/**
	 * selectOneBySn
	 * @param mid
	 */
	public testcaseGroupVO selectOneByGid(String gid){
		return this._testcaseGroupDao.selectOneByGid(gid);
	}

	/**
	 * insert
	 * @param vo
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String insert(testcaseGroupVO vo) throws NoSuchAlgorithmException, IOException{
		JSONObject retObj = new JSONObject();
		int errCount = 0;
		
		// 오류가 없는 경우에 정상적으로 DB에 입력
		if(errCount==0){
			int r = this._testcaseGroupDao.insert(vo);
			if(r==1){
				retObj.put("result", "1");
				retObj.put("msg","");
			}else{
				retObj.put("result", "0");
				retObj.put("msg","");					
			}
		}
		return retObj.toJSONString();
	}

	/**
	 * update
	 * @param vo
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String update(testcaseGroupVO vo) throws NoSuchAlgorithmException, IOException{
		JSONObject retObj = new JSONObject();
		int errCount = 0;
		
		// 오류가 없는 경우에 정상적으로 DB에 입력
		if(errCount==0){
			int r = this._testcaseGroupDao.update(vo);
			if(r==1){
				retObj.put("result", "1");
				retObj.put("msg","");
			}else{
				retObj.put("result", "0");
				retObj.put("msg","");					
			}
		}
		return retObj.toJSONString();
	}

	/**
	 * delete
	 * @param vo
	 * @return
	 */
	public int delete(String sn){
		return this._testcaseGroupDao.delete(sn);
	}
}
