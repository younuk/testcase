package kt.dsstestcase.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.Resource;
import kt.dsstestcase.dao.testcaseCategoryDao;
import kt.dsstestcase.vo.testcaseCategoryVO;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class testcaseCategoryService {

	@Resource(name="testcaseCategoryDao")
	private testcaseCategoryDao _testcaseCategoryDao;
	
	
	/**
	 * select
	 * @param mid
	 */
	public List<testcaseCategoryVO> select(testcaseCategoryVO vo){
		return this._testcaseCategoryDao.select(vo);
	}

    
    /**
     * select
     * @param mid
     */
    public List<testcaseCategoryVO> selectUpperCategory(testcaseCategoryVO vo){
        return this._testcaseCategoryDao.selectUpperCategory(vo);
    }
	/**
	 * selectByGid
	 * @param mid
	 */
	public List<testcaseCategoryVO> selectByGid(String gid){
		return this._testcaseCategoryDao.selectByGid(gid);
	}	
	
	
	/**
	 * selectOneBySn
	 * @param mid
	 */
	public testcaseCategoryVO selectOneBySn(String sn){
		return this._testcaseCategoryDao.selectOneBySn(sn);
	}

	
	/**
	 * insert
	 * @param vo
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String insert(testcaseCategoryVO vo) throws NoSuchAlgorithmException, IOException{
		JSONObject retObj = new JSONObject();
		int errCount = 0;
		
		// 오류가 없는 경우에 정상적으로 DB에 입력
		if(errCount==0){
			int r = this._testcaseCategoryDao.insert(vo);
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
	public String update(testcaseCategoryVO vo) throws NoSuchAlgorithmException, IOException{
		JSONObject retObj = new JSONObject();
		int errCount = 0;
		String msg = "";

		// value check
		
		
		if(errCount == 0){
			int r = this._testcaseCategoryDao.update(vo);
			if(r==1){
				retObj.put("result", "1");
				retObj.put("msg","정보가 수정되었습니다.");							
			}else{
					retObj.put("result", "0");
					retObj.put("msg","DB오류입니다");						
			}

		}else{  // _m == null
			retObj.put("result", "0");
			retObj.put("msg",msg);			
		}
		
		System.out.println(retObj.toJSONString());		
		return retObj.toJSONString();		
	}
	
	
	
	
	/**
	 * delete
	 * @param vo
	 * @return
	 */
	public int delete(int sn){
		return this._testcaseCategoryDao.delete(sn);
	}


}
