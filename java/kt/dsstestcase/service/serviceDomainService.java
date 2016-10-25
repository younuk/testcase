package kt.dsstestcase.service;


import java.util.List;
import javax.annotation.Resource;
import kt.dsstestcase.dao.serviceDomainDao;
import kt.dsstestcase.vo.serviceDomainVO;
import org.springframework.stereotype.Service;


@Service
public class serviceDomainService {

	@Resource(name="serviceDomainDao")
	private serviceDomainDao _serviceDomainDao;
	
	
	/**
	 * select
	 * @param str
	 */
	public List<serviceDomainVO> select(){
		return this._serviceDomainDao.select("");
	}

}
