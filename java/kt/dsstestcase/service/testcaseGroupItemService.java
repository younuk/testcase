package kt.dsstestcase.service;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;

import kt.dsstestcase.dao.testcaseGroupItemDao;
import kt.dsstestcase.vo.testcaseGroupVO;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class testcaseGroupItemService {

	@Resource(name="testcaseGroupItemDao")
	private testcaseGroupItemDao _testcaseGroupItemDao;
	
	/**
	 * selectByGid
	 * @param mid
	 */
	public testcaseGroupVO selectByGid(String gid){
		return this._testcaseGroupItemDao.selectByGid(gid);
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
			int r = this._testcaseGroupItemDao.insert(vo);
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
		return this._testcaseGroupItemDao.delete(sn);
	}
}
