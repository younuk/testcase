package kt.dsstestcase.vo;

import kt.dsstestcase.util.Utils;

import org.apache.ibatis.type.Alias;

@Alias("testcaseGroupVO")
public class testcaseGroupVO {

	private int gid = 0;
	private String group_nm = "";
	private String login_id = "";
	private String login_nm = "";
	private String rgsde = "";

	private String uinfo_1 = "0";
	private String uinfo_2 = "0";
	private String uinfo_3 = "0";
	private String uinfo_4 = "0";
	private String uinfo_5 = "0";
	private String uinfo_6 = "0";
	private String fav_genre = "";
	private String fav_point = "";

    private String status = "";
    private String event = "";
    private String uinfo = "";
    private String context = "";
    private String STB_VER = "";
    private String stbVersion = "";
    private String product_id = "";
    private String appID = "";
    private String devService_id = "";

    private String testcase_sn = "";

    private String pnm = "";
    private String nm = "";
    private String exam = "";
    private String ans_sysAct = "";
    private String ans_actType = "";

    private int cnt = 0;


	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getGroup_nm() {
		return group_nm;
	}
	public void setGroup_nm(String group_nm) {
		this.group_nm = group_nm;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getLogin_nm() {
		return login_nm;
	}
	public void setLogin_nm(String login_nm) {
		this.login_nm = login_nm;
	}
	public String getRgsde() {
		return rgsde;
	}
	public void setRgsde(String rgsde) {
		this.rgsde = rgsde;
	}
	public String getUinfo_1() {
		return uinfo_1;
	}
	public void setUinfo_1(String uinfo_1) {
		this.uinfo_1 = uinfo_1;
	}
	public String getUinfo_2() {
		return uinfo_2;
	}
	public void setUinfo_2(String uinfo_2) {
		this.uinfo_2 = uinfo_2;
	}
	public String getUinfo_3() {
		return uinfo_3;
	}
	public void setUinfo_3(String uinfo_3) {
		this.uinfo_3 = uinfo_3;
	}
	public String getUinfo_4() {
		return uinfo_4;
	}
	public void setUinfo_4(String uinfo_4) {
		this.uinfo_4 = uinfo_4;
	}
	public String getUinfo_5() {
		return uinfo_5;
	}
	public void setUinfo_5(String uinfo_5) {
		this.uinfo_5 = uinfo_5;
	}
	public String getUinfo_6() {
		return uinfo_6;
	}
	public void setUinfo_6(String uinfo_6) {
		this.uinfo_6 = uinfo_6;
	}
	public String getFav_genre() {
		return fav_genre;
	}
	public void setFav_genre(String fav_genre) {
		this.fav_genre = fav_genre;
	}
	public String getFav_point() {
		return fav_point;
	}
	public void setFav_point(String fav_point) {
		this.fav_point = fav_point;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getUinfo() {
		return uinfo;
	}
	public void setUinfo(String uinfo) {
		this.uinfo = uinfo;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getSTB_VER() {
		return STB_VER;
	}
	public void setSTB_VER(String sTB_VER) {
		STB_VER = sTB_VER;
	}
	public String getStbVersion() {
		return stbVersion;
	}
	public void setStbVersion(String stbVersion) {
		this.stbVersion = stbVersion;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getAppID() {
		return appID;
	}
	public void setAppID(String appID) {
		this.appID = appID;
	}
	public String getDevService_id() {
		return devService_id;
	}
	public void setDevService_id(String devService_id) {
		this.devService_id = devService_id;
	}
	public String getTestcase_sn() {
		return testcase_sn;
	}
	public void setTestcase_sn(String testcase_sn) {
		this.testcase_sn = testcase_sn;
	}
	public String getPnm() {
		return pnm;
	}
	public void setPnm(String pnm) {
		this.pnm = pnm;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public String getExam() {
		return exam;
	}
	public void setExam(String exam) {
		this.exam = exam;
	}
	public String getAns_sysAct() {
		return ans_sysAct;
	}
	public void setAns_sysAct(String ans_sysAct) {
		this.ans_sysAct = ans_sysAct;
	}
	public String getAns_actType() {
		return ans_actType;
	}
	public void setAns_actType(String ans_actType) {
		this.ans_actType = ans_actType;
	}


	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		cnt = Utils.isNumber(cnt, 0);
		this.cnt = cnt;
	}



}
