package kt.dsstestcase.vo;

import org.apache.ibatis.type.Alias;

@Alias("actTypeVO")
public class actTypeVO {

	private String act_type_str = "";
	private String actType = "";


	public String getAct_type_str() {
		return act_type_str;
	}
	public void setAct_type_str(String act_type_str) {
		this.act_type_str = act_type_str;
	}
	public String getActType() {
		return actType;
	}
	public void setActType(String actType) {
		this.actType = actType;
	}
}
