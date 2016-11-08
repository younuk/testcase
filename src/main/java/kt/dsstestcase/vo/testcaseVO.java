package kt.dsstestcase.vo;

import java.lang.reflect.Field;

import kt.dsstestcase.util.Utils;

import org.apache.ibatis.type.Alias;

@Alias("testcaseVO")
public class testcaseVO {

    public String toInfoString() {
        StringBuffer buf = new StringBuffer();
        String[] fields = getPropertyNames();
        for(int i=0; i<fields.length; i++) {
        try {
            buf.append(fields[i] + "=" + getValue(fields[i]));
            buf.append("\n");
        }catch(Exception e) {   }
        }
        return buf.toString();
    }

    public String getTrx() {
        StringBuffer buf = new StringBuffer();
        buf.append("<tr>");
        String[] fields = getPropertyNames();
        for(int i=0; i<fields.length; i++) {
        try {
            buf.append("<td>"+fields[i]+"</td>");
        }catch(Exception e) {   }
        }
        buf.append("</tr>");
        return buf.toString();
    }

    public String getTdx() {
        StringBuffer buf = new StringBuffer();
        buf.append("<tr>");
        String[] fields = getPropertyNames();
        for(int i=0; i<fields.length; i++) {
        try {
            buf.append("<td>"+getValue(fields[i])+"</td>");
        }catch(Exception e) {   }
        }
        buf.append("</tr>");
        return buf.toString();
    }

    public String[] getPropertyNames() {
        Field[] fs = getClass().getDeclaredFields();
        String[] names = new String[fs.length];
        for(int i=0; i<fs.length; i++) {
            names[i] = fs[i].getName();
        }
        return names;
    }

    public Object getValue(String prop_name) throws Exception {
        Field fd = getClass().getDeclaredField(prop_name);
        try {
            return fd.get(this);
        }catch(Exception e) {
            throw new Exception(e.toString() + "\nprop_name=" + prop_name);
        }
    }



	private int idx = 0;
    private int sn = 0;
    private int gid = 0;
    private int cat_id = 0;
    private int service_domain = 0;
    private String in_domain = "";
    private String act_type_str = "";
    private String descr = "";
    private String exam = "";
    private String service_id_tmpl = "";
    private String service_id_ptrn = "";
    private String service_id_str = "";
    private String chk_method = "equal";
    private String ans_sysAct = "";
    private String ans_actType = "";
    private String ans_srchword = "";
    private String g_domain = "";
    private String ans_srchopt = "";
    private String ans_srchqry = "";
    private String ans_sword = "";
    private String service_id_example = "";
    private String service_rmk = "";
    private String p_status = "";
    private String p_event = "";
    private String p_uinfo = "";
    private String  p_context  = "";

    private String test_id  = "";
    private int is_check = 0;
    private int action_len = 0;
    private String xml = "";

    private String cat_nm = "";
    private String service_domain_name = "";


    private String testcase_sn = "";



	public String getTestcase_sn() {
        return testcase_sn;
    }

    public void setTestcase_sn(String testcase_sn) {
        this.testcase_sn = testcase_sn;
    }

    public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		idx = Utils.isNumber(idx, 0);
		this.idx = idx;
	}

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		sn = Utils.isNumber(sn, 0);
		this.sn = sn;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		gid = Utils.isNumber(gid, 0);
		this.gid = gid;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		cat_id = Utils.isNumber(cat_id, 0);
		this.cat_id = cat_id;
	}

	public int getService_domain() {
		return service_domain;
	}

	public void setService_domain(int service_domain) {
		service_domain = Utils.isNumber(service_domain, 0);
		this.service_domain = service_domain;
	}

	public String getIn_domain() {
		return in_domain;
	}

	public void setIn_domain(String in_domain) {
		this.in_domain = in_domain;
	}

	public String getAct_type_str() {
		return act_type_str;
	}

	public void setAct_type_str(String act_type_str) {
		this.act_type_str = act_type_str;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getExam() {
		return exam;
	}

	public void setExam(String exam) {
		this.exam = exam;
	}

	public String getService_id_tmpl() {
		return service_id_tmpl;
	}

	public void setService_id_tmpl(String service_id_tmpl) {
		this.service_id_tmpl = service_id_tmpl;
	}

	public String getService_id_ptrn() {
		return service_id_ptrn;
	}

	public void setService_id_ptrn(String service_id_ptrn) {
		this.service_id_ptrn = service_id_ptrn;
	}

	public String getService_id_str() {
		return service_id_str;
	}

	public void setService_id_str(String service_id_str) {
		this.service_id_str = service_id_str;
	}

	public String getChk_method() {
		return chk_method;
	}

	public void setChk_method(String chk_method) {
		this.chk_method = chk_method;
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

	public String getAns_srchword() {
		return ans_srchword;
	}

	public void setAns_srchword(String ans_srchword) {
		this.ans_srchword = ans_srchword;
	}

	public String getG_domain() {
		return g_domain;
	}

	public void setG_domain(String g_domain) {
		this.g_domain = g_domain;
	}

	public String getAns_srchopt() {
		return ans_srchopt;
	}

	public void setAns_srchopt(String ans_srchopt) {
		this.ans_srchopt = ans_srchopt;
	}

	public String getAns_srchqry() {
		return ans_srchqry;
	}

	public void setAns_srchqry(String ans_srchqry) {
		this.ans_srchqry = ans_srchqry;
	}

	public String getAns_sword() {
		return ans_sword;
	}

	public void setAns_sword(String ans_sword) {
		this.ans_sword = ans_sword;
	}

	public String getService_id_example() {
		return service_id_example;
	}

	public void setService_id_example(String service_id_example) {
		this.service_id_example = service_id_example;
	}

	public String getService_rmk() {
		return service_rmk;
	}

	public void setService_rmk(String service_rmk) {
		this.service_rmk = service_rmk;
	}

	public String getP_status() {
		return p_status;
	}

	public void setP_status(String p_status) {
		this.p_status = p_status;
	}

	public String getP_event() {
		return p_event;
	}

	public void setP_event(String p_event) {
		this.p_event = p_event;
	}

	public String getP_uinfo() {
		return p_uinfo;
	}

	public void setP_uinfo(String p_uinfo) {
		this.p_uinfo = p_uinfo;
	}

	public String getP_context() {
		return p_context;
	}

	public void setP_context(String p_context) {
		this.p_context = p_context;
	}

	public String getTest_id() {
		return test_id;
	}

	public void setTest_id(String test_id) {
		this.test_id = test_id;
	}

	public int getIs_check() {
		return is_check;
	}

	public void setIs_check(int is_check) {
		is_check = Utils.isNumber(is_check, 0);
		this.is_check = is_check;
	}

	public int getAction_len() {
		return action_len;
	}

	public void setAction_len(int action_len) {
		action_len = Utils.isNumber(action_len, 0);
		this.action_len = action_len;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getCat_nm() {
		return cat_nm;
	}

	public void setCat_nm(String cat_nm) {
		this.cat_nm = cat_nm;
	}

	public String getService_domain_name() {
		return service_domain_name;
	}

	public void setService_domain_name(String service_domain_name) {
		this.service_domain_name = service_domain_name;
	}

}
