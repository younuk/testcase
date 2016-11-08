package kt.dsstestcase;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kt.dsstestcase.service.testcaseGroupService;
import kt.dsstestcase.service.testcaseService;
import kt.dsstestcase.util.Utils;
import kt.dsstestcase.vo.authVO;
import kt.dsstestcase.vo.testcaseGroupVO;
import kt.dsstestcase.vo.testcaseVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

   //@Autowired
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
    public boolean setSess(HttpSession session){
    	this._authVO = (authVO)session.getAttribute("authVO");

       	return (this._authVO != null)? true: false;
    }


    /**
     * List - Call Page
     * @param request/response/session
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @RequestMapping(value="/customtest.do")
    public ModelAndView customtest(HttpServletRequest request, HttpServletResponse response, HttpSession session)
                                    throws IOException, URISyntaxException {
        return this.setPage("customtest", session);
    }


    /**
     * List - Load Init Data
     * @param request/response/session
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @RequestMapping(value="/testcaseGroupListJson.do")
    public void testcaseGroupListJson(HttpServletRequest request, HttpServletResponse response, HttpSession session)
                                        throws IOException, URISyntaxException {

        String srchWord = Utils.isString(request.getParameter("srchWord"), "");

        Map<String, Object> _rv = new HashMap<String, Object>();
        _rv.put("groupList", this._testcaseGroupService.select(srchWord));
        _rv.put("actTypeList", this._testcaseService.selectActTypeGroupList());
        _rv.put("categoryList", this._testcaseService.selectCtgr(""));


        Gson gson = new Gson();
        String result = gson.toJson(_rv);

        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
    }


    /**
     * Detail - Call Detail page
     * @param request/response/session
     * @param gid
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @RequestMapping(value="/customtestManage.do")
    public ModelAndView customtestManage(HttpServletRequest request, HttpServletResponse response, HttpSession session,
    										@RequestParam (value="gid", required=false) String gid)
    										throws IOException, URISyntaxException {

    	ModelAndView _mod = this.setPage("customtest_manage", session);
        _mod.addObject("gid", gid);
    	return _mod;
    }

    /**
     * Detail - Load Init Data
     * @param request/response/session
     * @param gid
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @RequestMapping(value="/viewTestcaseGroupDetail.do")
    public void viewTestcaseGroupDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                            @RequestParam (value="gid", required=false) String gid)
                                            throws IOException, URISyntaxException {

        Map<String, Object> _rv = new HashMap<String, Object>();
        //get category list(all)
        _rv.put("catList", _testcaseService.selectCtgr(""));
        //get testcase list(all)
        _rv.put("testcaseList", _testcaseService.selectCtgrMapping(""));

        if(!Utils.isString(gid, "").equals("")){
            //get group info(one)
            _rv.put("testGroup", _testcaseGroupService.selectOneByGid(gid));

            //get testcase list of this group
            _rv.put("testcaseGroupList", _testcaseGroupService.selectTestcasebyGroup(gid));
        }

        Gson gson = new Gson();
        String result = gson.toJson(_rv);

        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
    }

    /**
     * Detail - Save Testcase Group Detail
     * @param request/response/session
     * @param testcaseGroupVO
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @RequestMapping(value="/setTestcaseGroupDetail.do")
    public void saveTestcaseGroupDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session
                                    , @ModelAttribute testcaseGroupVO _vo)
                                    throws Exception {

        String snList = Utils.isString(request.getParameter("snList"), "");
        String result = "";

        List<testcaseVO> tcList = new ArrayList<testcaseVO>();
        if(!snList.equals("")){
            String[] snl = snList.split("-");

            testcaseVO vo;
            for(String sn :  snl){
                vo = new testcaseVO();

                vo.setTestcase_sn(sn);
                tcList.add(vo);
            }
        }
        result = _testcaseGroupService.update(tcList, _vo);

        Gson gson = new Gson();
        result = gson.toJson(result);

        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
    }
}
