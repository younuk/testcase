package kt.dsstestcase.vo;

import java.util.ArrayList;
import java.util.List;

public class customCaseVO {
	

	List<testcaseVO> testcaseList   = new ArrayList<testcaseVO>();  	
	List<testcaseGroupVO> groupList = new ArrayList<testcaseGroupVO>();
	List<actTypeVO> actTypeList     = new ArrayList<actTypeVO>();
	List<testcaseCategoryVO> categoryList = new ArrayList<testcaseCategoryVO>();
	
	
	public List<testcaseVO> getTestcaseList() {
		return testcaseList;
	}
	public void setTestcaseList(List<testcaseVO> testcaseList) {
		this.testcaseList = testcaseList;
	}
	public List<testcaseGroupVO> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<testcaseGroupVO> groupList) {
		this.groupList = groupList;
	}
	public List<actTypeVO> getActTypeList() {
		return actTypeList;
	}
	public void setActTypeList(List<actTypeVO> actTypeList) {
		this.actTypeList = actTypeList;
	}
	public List<testcaseCategoryVO> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<testcaseCategoryVO> categoryList) {
		this.categoryList = categoryList;
	}
	
}
