package kt.dsstestcase.service;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kt.dsstestcase.dao.testcaseDao;
import kt.dsstestcase.vo.actTypeVO;
import kt.dsstestcase.vo.testcaseCategoryVO;
import kt.dsstestcase.vo.testcaseVO;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class testcaseService {

    @Resource(name="testcaseDao")
    private testcaseDao _testcaseDao;

    /**
     * select
     * @param mid
     */
    public List<testcaseVO> select(String[] array){
        //return this._testcaseDao.select(vo);
        return this._testcaseDao.select(array);
    }


    /**
     * select
     * @param mid
     */
    public testcaseVO selectOne(String param){
        //return this._testcaseDao.select(vo);
        return this._testcaseDao.selectOne(param);
    }

    /**
     * selectView
     * @param mid
     */
    public List<testcaseVO> selectView(testcaseVO vo){
        return this._testcaseDao.selectView(vo);
    }

    /**
     * selectActTypeGroupList
     * @return
     */
    public List<actTypeVO> selectActTypeGroupList(){
        return this._testcaseDao.selectActTypeGroupList();
    }

    /**
     * insert
     * @param vo
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public String insert(testcaseVO vo) throws NoSuchAlgorithmException, IOException{
        JSONObject retObj = new JSONObject();
        int errCount = 0;

        // 오류가 없는 경우에 정상적으로 DB에 입력
        if(errCount==0){
            int r = this._testcaseDao.insert(vo);
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
    public String update(testcaseVO vo) throws NoSuchAlgorithmException, IOException{
        JSONObject retObj = new JSONObject();
        int errCount = 0;
        String msg = "";

        // value check
        if(errCount == 0){
            int r = this._testcaseDao.update(vo);
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
        return retObj.toJSONString();
    }

    /**
     * updateField
     * @param vo
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String updateField(String sn, String field, String val) throws NoSuchAlgorithmException, IOException{
        JSONObject retObj = new JSONObject();

        Map<String, String> _map = new HashMap<String,String>();
        _map.put("sn", sn);
        _map.put("field", field);
        _map.put("value", val);

        int r = this._testcaseDao.updateField(_map);
        if(r==1){
            retObj.put("result", "1");
            retObj.put("msg","정보가 수정되었습니다.");
        }else{
            retObj.put("result", "0");
            retObj.put("msg","DB오류입니다");
        }
        return retObj.toJSONString();
    }

    /**
     * delete
     * @param vo
     * @return
     */
    public int delete(int sn){
        return this._testcaseDao.delete(sn);
    }


    /**
     * select
     * @param mid
     */
    public List<testcaseCategoryVO> selectCtgr(String param){
        return this._testcaseDao.selectCtgr(param);
    }

    /**
     * selectMapping
     * @param sid
     */
    public List<HashMap<String, Object>> selectCtgrMapping(String param){
        return this._testcaseDao.selectCtgrMapping(param);
    }
}
