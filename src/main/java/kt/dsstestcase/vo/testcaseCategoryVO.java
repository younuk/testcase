package kt.dsstestcase.vo;

import org.apache.ibatis.type.Alias;

@Alias("testcaseCategoryVO")
public class testcaseCategoryVO {

	private int sn = 0;
	private int cat_id = 0;
	private int gid = 0;
	private int ord = 0;
	private int dept = 0;
	private String nm = "";
	private String pnm = "";

	private int tc_cnt = 0;

	public int getTc_cnt() {
        return tc_cnt;
    }

    public void setTc_cnt(int tc_cnt) {
        this.tc_cnt = tc_cnt;
    }

    public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public int getDept() {
		return dept;
	}

	public void setDept(int dept) {
		this.dept = dept;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getPnm() {
		return pnm;
	}

	public void setPnm(String pnm) {
		this.pnm = pnm;
	}



}
