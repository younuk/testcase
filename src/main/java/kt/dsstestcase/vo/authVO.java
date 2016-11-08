package kt.dsstestcase.vo;

import org.apache.ibatis.type.Alias;

@Alias("authVO")
public class authVO {

	private String login_nm = "";
	private String login_id = "";
	private String login_pass = "";
	private String login_session = "";
	private String gid = "0";
	private String is_super = "0";
	private String is_avab = "0";


	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getLogin_pass() {
		return login_pass;
	}
	public void setLogin_pass(String login_pass) {
		this.login_pass = login_pass;
	}
	public String getLogin_session() {
		return login_session;
	}
	public void setLogin_session(String login_session) {
		this.login_session = login_session;
	}
	public String getLogin_nm() {
		return login_nm;
	}
	public void setLogin_nm(String login_nm) {
		this.login_nm = login_nm;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getIs_super() {
		return is_super;
	}
	public void setIs_super(String is_super) {
		this.is_super = is_super;
	}
	public String getIs_avab() {
		return is_avab;
	}
	public void setIs_avab(String is_avab) {
		this.is_avab = is_avab;
	}


}
