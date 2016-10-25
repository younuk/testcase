package kt.dsstestcase.service;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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
	public logVO selectOneByUuidSn(String uuid, String sn){
		return (logVO)this._logDao.selectOneByUuidSn(uuid, sn);
	}	
	
	
	/**
	 * selectByUuid
	 * @param mid
	 */
	public List<logVO> selectByUuid(String uuid){
		return this._logDao.selectByUuid(uuid);
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
}
