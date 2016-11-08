package kt.dsstestcase.dao;


import java.util.List;
import java.util.Map;

import kt.dsstestcase.vo.logVO;

import org.springframework.stereotype.Repository;

@Repository(value="logDao")
public interface logDao {
    /**
     * selectByUuidSn
     * @param vo
     * @return
     */
    public List<logVO> selectOne(Map<String, String> map);

    /**
     * insert
     * @param vo
     * @return
     */
    public int insert(logVO vo);

    public int insertList(List<logVO> list);
}