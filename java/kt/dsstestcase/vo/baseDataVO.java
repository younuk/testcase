package kt.dsstestcase.vo;

import java.util.ArrayList;
import java.util.List;

public class baseDataVO {

	
	private List<actTypeVO> actTypeList   = new ArrayList<actTypeVO>();
	private List<testcaseVO> testcaseList = new ArrayList<testcaseVO>();
	private List<testcaseCategoryVO> testcaseCategoryList = new ArrayList<testcaseCategoryVO>();
	
	
	public List<actTypeVO> getActTypeList() {
		return actTypeList;
	}
	public void setActTypeList(List<actTypeVO> actTypeList) {
		this.actTypeList = actTypeList;
	}
	public List<testcaseVO> getTestcaseList() {
		return testcaseList;
	}
	public void setTestcaseList(List<testcaseVO> testcaseList) {
		this.testcaseList = testcaseList;
	}
	public List<testcaseCategoryVO> getTestcaseCategoryList() {
		return testcaseCategoryList;
	}
	public void setTestcaseCategoryList(
			List<testcaseCategoryVO> testcaseCategoryList) {
		this.testcaseCategoryList = testcaseCategoryList;
	}
	
	

}
