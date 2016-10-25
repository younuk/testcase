package kt.dsstestcase.vo;

import java.lang.reflect.Field;

public class testVO {
	
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
    
    
	
	private String i = "";
	private String id = "";
	private String reqmsg = "";
	private String status = "";
	private String event = "";
	private String uinfo = "";
	private String context = "";
	private String STB_VER = "";
	private String stbVersion = "";
	private String product_id = "";
	private String appID = "";
	private String tm = "";
	
	private String uuid = "";
	private String snList = "";
	
	
	public String getI() {
		return i;
	}
	public void setI(String i) {
		this.i = i;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReqmsg() {
		return reqmsg;
	}
	public void setReqmsg(String reqmsg) {
		this.reqmsg = reqmsg;
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
	public String getTm() {
		return tm;
	}
	public void setTm(String tm) {
		this.tm = tm;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSnList() {
		return snList;
	}

	public void setSnList(String snList) {
		this.snList = snList;
	}
	
	
	
}
