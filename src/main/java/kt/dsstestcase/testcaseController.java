package kt.dsstestcase;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kt.dsstestcase.service.logService;
import kt.dsstestcase.service.testcaseGroupService;
import kt.dsstestcase.service.testcaseService;
import kt.dsstestcase.util.Utils;
import kt.dsstestcase.vo.authVO;
import kt.dsstestcase.vo.logVO;
import kt.dsstestcase.vo.testVO;
import kt.dsstestcase.vo.testcaseCategoryVO;
import kt.dsstestcase.vo.testcaseVO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;


@Controller
public class testcaseController extends MultiActionController {

    @Resource(name="testcaseService")
    private testcaseService _testcaseService;

    @Resource(name="testcaseGroupService")
    private testcaseGroupService _testcaseGroupService;

    @Resource(name="logService")
    private logService _logService;

   // @Autowired
    private authVO _authVO;
    private String _LOGIN_ID = "";

    @Value("${test_url}")
    private String _TEST_URL;


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
        this._authVO = (authVO)session.getAttribute("authVO");

        return (this._authVO != null)? true: false;
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
    @RequestMapping(value="/casetest.do")
    public ModelAndView casetest(HttpServletRequest request, HttpServletResponse response, HttpSession session)
                                    throws IOException, URISyntaxException {
        return this.setPage("casetest", session);
    }

    // *****************************************************************************
    // singletest
    // *****************************************************************************
    @RequestMapping(value="/singletest.do")
    public ModelAndView singletest(HttpServletRequest request, HttpServletResponse response, HttpSession session)
                                    throws IOException, URISyntaxException {
        return this.setPage("singletest", session);
    }

    // *****************************************************************************
    // casetestManage
    // *****************************************************************************
    @RequestMapping(value="/casetestManage.do")
    public ModelAndView testcaseManage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
                                            throws IOException, URISyntaxException {
        return this.setPage("casetest_manage", session);
    }

    // *****************************************************************************
    // testcaseBaseData
    // *****************************************************************************
    @RequestMapping(value="/testcaseBaseData.do")
    public void testcaseBaseData(HttpServletRequest request, HttpServletResponse response, HttpSession session)
                                    throws IOException, URISyntaxException {

        Map<String, Object> _rv = new HashMap<String, Object>();
        _rv.put("testcaseList", this._testcaseService.select(null));
        _rv.put("actTypeList", this._testcaseService.selectActTypeGroupList());
        _rv.put("testcaseCategoryList", this._testcaseService.selectCtgr(""));

        Gson gson = new Gson();
        String result = gson.toJson(_rv);

        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
    }

    /**
     * 테스트헤더와 testcase sn 목록(-로 구분)을 받아서 테스트 쿼리 처리
     * testcaseTest.do ----> getActionGetBrokerUTF --> DB
     *
     * @param request
     * @param response
     * @param session
     * @param _vo
     * @throws IOException
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping(value="/testcaseTest.do")
    public void testcaseTest(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                             @ModelAttribute testVO _vo)
                             throws IOException, URISyntaxException, NoSuchAlgorithmException {

        String snList = Utils.isString(_vo.getSnList(), "");
        String gid = Utils.isString(request.getParameter("gid"), "");
        String[] array = null;
        String result = "";

        //0. casetest/customtest 구분 셋팅
        boolean custmYN = (!gid.equals("") && snList.equals(""))? true: false;

        //1. set uuid
        String uuid = Utils.isString(_vo.getUuid(), UUID.randomUUID().toString());

        //2. set snList
        if(custmYN){
            List<Map<String, Object>> snMap = _testcaseGroupService.selectTestcasebyGroup(gid);

            int cnt = snMap.size();
            array = new String[cnt];
            for(int i=0; i<cnt; i++){
                array[i] = ((Integer)(snMap.get(i)).get("sn")).toString();
            }

            //3. set return
            Map<String, Object> _rv = new HashMap<String, Object>();
            _rv.put("testcaseList", snMap);
            _rv.put("uuid", uuid);

            Gson gson = new Gson();
            result = gson.toJson(_rv);
        }else{
            array = snList.split("-");

            result = uuid;
        }

        if(array != null){
            _vo.setUuid(uuid);

            //3. set log parameter
            String rtn = "";
            String loginId = (this._authVO != null)? Utils.isString(this._authVO.getLogin_id(),"unknown"): "unknown";
            List<logVO> _lvList = new ArrayList<logVO>();
            logVO _lv = null;

            List<testcaseVO> _voList = this._testcaseService.select(array); // 발화에 개별적으로 세팅된 값이 있으면 이 값이 다른 값보다 우선한다.
            for(testcaseVO _v: _voList){
                _vo.setI(Integer.toString(_v.getSn()));
                _vo.setStatus(_v.getP_status());
                _vo.setEvent(_v.getP_event());
                _vo.setUinfo(_v.getP_uinfo());
                _vo.setContext(_v.getP_context());
                _vo.setReqmsg(_v.getExam());

                //4. call DMS
                //old : getActionGetBrokerUTFLocal(_vo);
                rtn = this.makeHTTPClient(_vo);

                //5. set log list
                _lv = new logVO();
                _lv.setLogin_id(loginId);
                _lv.setUuid(uuid);
                _lv.setSn(_vo.getI());
                _lv.setReq(_vo.toInfoString());
                _lv.setXml(rtn);

                _lvList.add(_lv);
            }

            //6. insert log
            if(_lvList != null && _lvList.size() > 0){
                this._logService.insertList(_lvList);
            }

            response.setContentType( "text/html; charset=UTF-8" );
            response.getWriter().println(result);
        }
    }

    /**
     * TestBroker
     * @param request
     * @param response
     * @param session
     * @throws IOException
     * @throws URISyntaxException
     */
    @RequestMapping(value="/getActionGetBrokerUTF.do")
    public void getActionGetBrokerUTF(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                        @ModelAttribute testVO _vo)
                                        throws IOException, URISyntaxException {

        String rtn = this.makeHTTPClient(_vo);

        response.setContentType("application/xml;charset=utf-8");
        response.getWriter().println(rtn);
    }

    // *****************************************************************************
    // testcase_mange BaseData
    // *****************************************************************************
    @RequestMapping(value="/testcaseMngBaseData.do")
    public void testcaseMngBaseData(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                     @ModelAttribute testcaseVO _vo)
                                     throws IOException, URISyntaxException {

        Map<String, Object> _rv = new HashMap<String, Object>();
        int i = 0;

        List<testcaseVO> _testcaseList = this._testcaseService.selectView(_vo);

        for(testcaseVO dt: _testcaseList){
            dt.setIdx(++i);
        }

        _rv.put("testcaseList", _testcaseList);
        _rv.put("testcaseCategoryList", this._testcaseService.selectCtgr(""));
        //_rv.put("actTypeList", this._testcaseService.selectActTypeGroupList());

        Gson gson = new Gson();
        String result = gson.toJson(_rv);

        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
    }

    // *****************************************************************************
    // testcaseEdit
    // *****************************************************************************
    @RequestMapping(value="/testcaseEdit.do")
    public ModelAndView testcaseEdit(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                        @RequestParam (value="sn", required=false) String sn)
                                        throws IOException, URISyntaxException {

        Gson gson = new Gson();

        // 카테고리전체
        List<testcaseCategoryVO> _testcaseCategoryList = this._testcaseService.selectCtgr("");

        testcaseVO _v = new testcaseVO();
        if(!Utils.isString(sn, "").equals("")){
             _v = this._testcaseService.selectOne(sn);
        }

        ModelAndView _mod = new ModelAndView("pop_casetestEdit");
        _mod.addObject("testcaseVO", gson.toJson(_v));
        _mod.addObject("categoryList", gson.toJson(_testcaseCategoryList));
        return _mod;
    }

    // *****************************************************************************
    // testcaseDelete
    // *****************************************************************************
    @RequestMapping(value="/testcaseDelete.do")
    public void testcaseDelete(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                    @RequestParam (value="sn", required=true) int sn)
                                    throws IOException, URISyntaxException, NoSuchAlgorithmException {

        int result = this._testcaseService.delete(sn);

        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
    }

    // *****************************************************************************
    // testcaseInsertOrModify
    // *****************************************************************************
    @RequestMapping(value="/testcaseiom.do")
    public void testcaseiom(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                @ModelAttribute testcaseVO _testcaseVO)
                                throws NoSuchAlgorithmException, IOException {

        int gid = Utils.isNumber(this._authVO.getGid(),0);
        _testcaseVO.setGid(gid);
        System.out.println(_testcaseVO.toInfoString());
        String result = "";
        if(_testcaseVO != null){
            int sn = Utils.isNumber(_testcaseVO.getSn(),0);

            if(sn == 0){
                result = this._testcaseService.insert(_testcaseVO);
            }else{
                result = this._testcaseService.update(_testcaseVO);
            }
        }
        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
    }

    // *****************************************************************************
    // testcaseUpdateField
    // *****************************************************************************
    @RequestMapping(value="/testcaseUpdateField.do")
    public void testcaseUpdateField(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                        @RequestParam (value="sn", required=true) String sn,
                                        @RequestParam (value="field", required=true) String field,
                                        @RequestParam (value="value", required=true) String val)
                                        throws IOException, URISyntaxException, NoSuchAlgorithmException {

        String result = this._testcaseService.updateField(sn, field, val);
        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
    }

    private String makeHTTPClient(testVO _vo) throws UnsupportedEncodingException {
        String reqmsg = URLEncoder.encode(_vo.getReqmsg(),"EUC-KR");
        StringBuffer sb = new StringBuffer();

        StringBuffer mkUrl = new StringBuffer();
        mkUrl.append(_TEST_URL);
        mkUrl.append("?id="+Utils.isString(_vo.getId(), ""));
        mkUrl.append("&reqmsg="+reqmsg);
        mkUrl.append("&status="+Utils.isString(_vo.getStatus(), ""));
        mkUrl.append("&event="+Utils.isString(_vo.getEvent(), ""));
        mkUrl.append("&uinfo="+Utils.isString(_vo.getUinfo(), ""));
        mkUrl.append("&context="+Utils.isString(_vo.getContext(), "").replace("\r",""));
        mkUrl.append("&STB_VER="+Utils.isString(_vo.getSTB_VER(), ""));
        mkUrl.append("&stbVersion="+Utils.isString(_vo.getStbVersion(), ""));
        mkUrl.append("&product_id="+Utils.isString(_vo.getProduct_id(), ""));
        mkUrl.append("&appID="+Utils.isString(_vo.getAppID(), ""));

        if(_vo != null){
            HttpClient httpclient = new DefaultHttpClient();
        try {
            // HttpGet
            HttpGet httpget   = new HttpGet(mkUrl.toString());
            HttpResponse res  = httpclient.execute(httpget);
            HttpEntity entity = res.getEntity();

            if (entity != null) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "EUC-KR"));
                String line = "";
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
            }
            httpget.abort();
            httpclient.getConnectionManager().shutdown();

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpclient.getConnectionManager().shutdown();
            }
        }
        return sb.toString();
    }


    /**
     * TestBroker
     * @param request
     * @param response
     * @param session
     * @throws IOException
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException

    public void getActionGetBrokerUTFLocal(@ModelAttribute testVO _vo)
                                            throws IOException, URISyntaxException, NoSuchAlgorithmException {

    }

    // -----------------------------------------------------------------------------
    @RequestMapping(value="/testcaseByGidJson.do")
    public void testcaseByGidJson(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                     @RequestParam (value="gid", required=false) String gid)
                                     throws IOException, URISyntaxException {

        int g = Utils.isNumber(gid, 0);
        testcaseVO _vo = new testcaseVO();
        if(g>0){
            _vo.setGid(g);
        }
        List<testcaseVO> _testcaseList = this._testcaseService.selectView(_vo);

        Gson gson = new Gson();
        String result = gson.toJson(_testcaseList);

        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
    }

 */
}