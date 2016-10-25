package kt.dsstestcase.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kt.dsstestcase.vo.serviceDomainVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class serviceDomainDao {
	
	@Autowired
	private SqlSession sql;
	

	/**
	 * select
	 * @param not used
	 * @return
	 */
	public List<serviceDomainVO> select(String param){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("param", param);
		return sql.selectList("serviceDomain.select", _map);
	}
	
	
}