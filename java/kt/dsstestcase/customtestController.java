package kt.dsstestcase;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kt.dsstestcase.service.authService;
import kt.dsstestcase.service.testcaseCategoryService;
import kt.dsstestcase.service.testcaseGroupItemService;
import kt.dsstestcase.service.testcaseGroupService;
import kt.dsstestcase.service.testcaseService;
import kt.dsstestcase.util.Utils;
import kt.dsstestcase.vo.actTypeVO;
import kt.dsstestcase.vo.authVO;
import kt.dsstestcase.vo.customCaseVO;
import kt.dsstestcase.vo.testcaseCategoryVO;
import kt.dsstestcase.vo.testcaseGroupVO;
import kt.dsstestcase.vo.testcaseVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;


@Controller
public class customtestController extends MultiActionController {

	@Resource(name="testcaseService")
	private testcaseService _testcaseService;

	@Resource(name="testcaseGroupService")
	private testcaseGroupService _testcaseGroupService;

	@Resource(name="testcaseGroupItemService")
	private testcaseGroupItemService _testcaseGroupItemService;

	@Resource(name="testcaseCategoryService")
	private testcaseCategoryService _testcaseCategoryService;

	@Resource(name="authService")
	private authService _authService;

    @Autowired
	private authVO _authVO;
    private String _LOGIN_ID = "";


    /**
     * For Auth
     * @param urls
     * @param session
     * @return
     */
    public ModelAndView setPage(String urls, HttpSession session){
       	this._authVO = (authVO)session.getAttribute("authVO");
       	if(this._authVO != null){
       		this._LOGIN_ID  = this._authVO.getLogin_id();
            if (this._LOGIN_ID.equals("") || this._LOGIN_ID == null){
                return new ModelAndView("/index").addObject("errMsg", "인증오류입니다");
            }else{
           		return new ModelAndView(urls).addObject("authVO", this._authVO);
            }
        }else{
            return new ModelAndView("/index").addObject("errMsg", "인증오류입니다");
        }
    }
    // -----------------------------------------------------------------------------
    public boolean setSess(HttpSession session){
    	boolean isSess = false;
       	this._authVO = (authVO)session.getAttribute("authVO");
       	if(this._authVO != null){
       		isSess = true;
       	}else{
       		isSess = false;
       	}
       	return isSess;
    }


	/**
	 * View
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
    // *****************************************************************************
    // casetest
    // *****************************************************************************
    @RequestMapping(value="/customtest.do")
    public ModelAndView customtest(HttpServletRequest request, HttpServletResponse response, HttpSession session)
                                    throws IOException, URISyntaxException {
    	ModelAndView _mod = this.setPage("customtest", session);
    	return _mod;
    }


    // *****************************************************************************
    // casetestManage
    // *****************************************************************************
    @RequestMapping(value="/customtestManage.do")
    public ModelAndView customtestManage(HttpServletRequest request, HttpServletResponse response, HttpSession session,
    										@RequestParam (value="gid", required=false) String gid)
    										throws IOException, URISyntaxException {

    	System.out.println("gid = "+gid);
    	testcaseGroupVO _vo = new testcaseGroupVO();
    	if(Utils.isNumber(gid, 0)>0){
    		_vo = _testcaseGroupService.selectOneByGid(gid);
    	}
    	
        List<testcaseCategoryVO> _catList = _testcaseCategoryService.select(new testcaseCategoryVO());

    	System.out.println("_catList.size="+Integer.toString(_catList.size()) );

    	ModelAndView _mod = this.setPage("customtest_manage", session);
    	_mod.addObject("catVO", _vo);
    	_mod.addObject("catList",_catList);
        _mod.addObject("upperCatList", _testcaseCategoryService.selectUpperCategory(null));
    	return _mod;
    }
    // -----------------------------------------------------------------------------
    @RequestMapping(value="/testcaseGroupListJson.do")
    public void testcaseGroupListJson(HttpServletRequest request, HttpServletResponse response, HttpSession session)
                                        throws IOException, URISyntaxException {

    	String srchWord = Utils.isString(request.getParameter("srchWord"), "");
    	System.out.println(srchWord);

        List<testcaseVO> _testcaseList   = this._testcaseService.select(new testcaseVO());
    	List<testcaseGroupVO> _groupList = this._testcaseGroupService.select(srchWord);
    	List<actTypeVO>  _actTypeGroupList = this._testcaseService.selectActTypeGroupList();
    	List<testcaseCategoryVO> _testcaseCategoryList = this._testcaseCategoryService.select(new testcaseCategoryVO());

    	customCaseVO _rv = new customCaseVO();
    	_rv.setTestcaseList(_testcaseList);
    	_rv.setGroupList(_groupList);
    	_rv.setActTypeList(_actTypeGroupList);
    	_rv.setCategoryList(_testcaseCategoryList);

    	Gson gson = new Gson();
    	String result = gson.toJson(_rv);

    	response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
    }
}
