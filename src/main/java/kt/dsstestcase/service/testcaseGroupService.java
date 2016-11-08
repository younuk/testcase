package kt.dsstestcase.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kt.dsstestcase.dao.testcaseGroupDao;
import kt.dsstestcase.vo.testcaseGroupVO;
import kt.dsstestcase.vo.testcaseVO;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class testcaseGroupService {

	@Resource(name="testcaseGroupDao")
	private testcaseGroupDao _testcaseGroupDao;

	/**
	 * select
	 * @param group_nm
	 * @return list
	 */
	public List<testcaseGroupVO> select(String param){
		return this._testcaseGroupDao.select(param);
	}

	/**
	 * selectOneByGid
	 * @param gid
	 * @return testcaseGroupVO
	 */
	public testcaseGroupVO selectOneByGid(String gid){
		return this._testcaseGroupDao.selectOneByGid(gid);
	}

    /**
     * selectTestcasebyGroup
     * @param gid
     * @return testcaseGroupVO
     */
    public List<Map<String, Object>> selectTestcasebyGroup(String gid){
        return this._testcaseGroupDao.selectTestcasebyGroup(gid);
    }


    /**
     * update
     * @param list
     * @param testcaseGroupVO
     * @return null
     * @throws Exception
     */
    @Transactional
    public String update(List<testcaseVO> list, testcaseGroupVO _vo){
        JSONObject retObj = new JSONObject();

        try {
            int gid = _vo.getGid();
            //1. test group 정보 수정
            if(gid < 1){
                this._testcaseGroupDao.insert(_vo);
                gid = _vo.getGid();
            }else{
                this._testcaseGroupDao.update(_vo);
            }

            //2. mapping 정보 수정
            this._testcaseGroupDao.deleteGroupItem(gid);


            if(list != null && list.size()>0){
                for(testcaseVO tcVo: list){
                    tcVo.setGid(gid);
                }
                this._testcaseGroupDao.insertGroupItem(list);
            }
            //의미없음
            //this._testcaseGroupDao.update1(strGid);
            //this._testcaseGroupDao.update2(strGid);

            retObj.put("result", "1");
            retObj.put("msg","정보가 수정되었습니다.");
        } catch (Exception e) {
            retObj.put("result", "0");
            retObj.put("msg","DB오류입니다");
        }

        return retObj.toJSONString();
    }

    /**
     * delete
     * @param gid
     * @return
     */
    public int delete(String sn) throws Exception{
        return this._testcaseGroupDao.delete(sn);
    }

	/**
	 * insert
	 * @param vo
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException

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
	} */

	/**
	 * update
	 * @param vo
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException

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
 */
}
