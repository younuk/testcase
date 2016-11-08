package kt.dsstestcase.dao;

import java.util.List;
import java.util.Map;

import kt.dsstestcase.vo.authVO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository(value="authDao")
public interface authDao {

	/**
	 * select
	 * @param not used
	 * @return
	 */
	public List<authVO> select();

	/**
	 * selectOneByLoginId
	 * @param not used
	 * @return
	 */
	public authVO selectOneByLoginId(Map<String, String> map);


	/**
	 * selectOneByLoginIdForAdmin
	 * @param not used
	 * @return
	 */
	public authVO selectOneByLoginIdForAdmin(@Param("login_id") String login_id);


	/**
	 * selectOneByLoginSession
	 * @param not used
	 * @return
	 */
	public authVO selectOneByLoginSession(@Param("login_session") String login_id);

	/**
	* insert
	* @param vo
	* @return
	*/
	public int insert(authVO vo);

	/**
	 * updateField
	 * @param vo
	 * @return
	 */
	public int updateField(Map<String,String> _map);

	/**
	 * updateField
	 * @param vo
	 * @return
	 */
	public int update(authVO _authVO);

	/**
	 * update
	 * @param vo
	 * @return
	 */
	public int updateLoginSession(Map<String,String> map);
}