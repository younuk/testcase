package kt.dsstestcase.service;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kt.dsstestcase.dao.logDao;
import kt.dsstestcase.vo.logVO;

import org.springframework.stereotype.Service;


@Service
public class logService {

	@Resource(name="logDao")
	private logDao _logDao;




	/**
	 * selectByUuid
	 * @param mid
	 */
	public List<logVO> selectOne(Map<String, String> map){
		return this._logDao.selectOne(map);
	}

	/**
	 * insert
	 * @param vo
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public int insert(logVO vo) throws NoSuchAlgorithmException, IOException{
			return this._logDao.insert(vo);
	}
    public int insertList(List<logVO> voL) throws NoSuchAlgorithmException, IOException{
        return this._logDao.insertList(voL);
    }
}
