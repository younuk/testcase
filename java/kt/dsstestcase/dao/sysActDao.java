package kt.dsstestcase.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kt.dsstestcase.vo.sysActVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class sysActDao {
	
	@Autowired
	private SqlSession sql;
	

	/**
	 * select
	 * @param not used
	 * @return
	 */
	public List<sysActVO> select(String param){
		Map<String,String> _map = new HashMap<String,String>();
		_map.put("param", param);
		return sql.selectList("sysAct.select", _map);
	}
	
	
}