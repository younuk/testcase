package kt.dsstestcase.service;


import java.util.List;
import javax.annotation.Resource;
import kt.dsstestcase.dao.sysActDao;
import kt.dsstestcase.vo.sysActVO;

import org.springframework.stereotype.Service;


@Service
public class sysActService {

	@Resource(name="sysActDao")
	private sysActDao _sysActDao;
	
	
	/**
	 * select
	 * @param str
	 */
	public List<sysActVO> select(){
		return this._sysActDao.select("");
	}

}
