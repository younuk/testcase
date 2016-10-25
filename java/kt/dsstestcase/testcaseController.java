package kt.dsstestcase;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kt.dsstestcase.etc.Settings;
import kt.dsstestcase.service.authService;
import kt.dsstestcase.service.logService;
import kt.dsstestcase.service.testcaseCategoryService;
import kt.dsstestcase.service.testcaseService;
import kt.dsstestcase.util.Utils;
import kt.dsstestcase.vo.actTypeVO;
import kt.dsstestcase.vo.authVO;
import kt.dsstestcase.vo.baseDataVO;
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
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Resource(name="testcaseCategoryService")
    private testcaseCategoryService _testcaseCategoryService;

    @Resource(name="authService")
    private authService _authService;

    @Resource(name="logService")
    private logService _logService;

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


    // *****************************************************************************
    // testcaseBaseData
    // *****************************************************************************
    @RequestMapping(value="/testcaseBaseData.do")
    public void testcaseBaseData(HttpServletRequest request, HttpServletResponse response, HttpSession session)
                                    throws IOException, URISyntaxException {

        List<testcaseVO> _testcaseList = this._testcaseService.select(new testcaseVO());
        List<actTypeVO>  _actTypeGroupList = this._testcaseService.selectActTypeGroupList();
        List<testcaseCategoryVO> _testcaseCategoryList = this._testcaseCategoryService.select(new testcaseCategoryVO());

        baseDataVO _lt = new baseDataVO();
        _lt.setTestcaseList(_testcaseList);
        _lt.setActTypeList(_actTypeGroupList);
        _lt.setTestcaseCategoryList(_testcaseCategoryList);

        Gson gson = new Gson();
        String result = gson.toJson(_lt);

        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
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

        String reqmsg = URLEncoder.encode(_vo.getReqmsg(),"EUC-KR");
        StringBuffer sb = new StringBuffer();
        
        StringBuffer mkUrl = new StringBuffer();
        mkUrl.append(Settings._TEST_URL);
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
        response.setContentType("application/xml;charset=utf-8");
        response.getWriter().println(sb.toString());
    }

    /**
     * TestBroker
     * @param request
     * @param response
     * @param session
     * @throws IOException
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     */
    public void getActionGetBrokerUTFLocal(@ModelAttribute testVO _vo)
                                            throws IOException, URISyntaxException, NoSuchAlgorithmException {

        String reqmsg = URLEncoder.encode(_vo.getReqmsg(),"EUC-KR");
        StringBuffer sb = new StringBuffer();
        
        StringBuffer mkUrl = new StringBuffer();
        mkUrl.append(Settings._TEST_URL);
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
                // HttpGet생성
                HttpGet httpget   = new HttpGet(mkUrl.toString());
                HttpResponse res  = httpclient.execute(httpget);
                HttpEntity entity = res.getEntity();

                // 응답 결과
                //System.out.println(res.getStatusLine());
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

        /*
        byte[] euckrStringBuffer = sb.toString().getBytes(Charset.forName("euc-kr"));
        String decodedFromEucKr = new String(euckrStringBuffer, "euc-kr");
        byte[] utf8StringBuffer = decodedFromEucKr.getBytes("utf-8");
        String decodedFromUtf8 = new String(utf8StringBuffer, "utf-8");
         */

        // DB에 입력한다.
        String sn      = _vo.getI();
        String uuid    = _vo.getUuid();
        String loginId = Utils.isString(this._authVO.getLogin_id(),"unknown");
        String xml     = sb.toString();
        String req     = _vo.toInfoString();

        // insert Log
        logVO _lv = new logVO();
        _lv.setLogin_id(loginId);
        _lv.setReq(req);
        _lv.setUuid(uuid);
        _lv.setSn(sn);
        _lv.setXml(xml);
        this._logService.insert(_lv);
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
        
        String snList = _vo.getSnList();  // -로 구분된 sn 리스트

        String uuid = Utils.isString(_vo.getUuid(),"");
        if(uuid.equals("")){
            uuid = UUID.randomUUID().toString();
        }
        _vo.setUuid(uuid);

        System.out.println(snList);

        if(!Utils.isString(snList, "").equals("")){

            String[] snl = snList.split("-");
            if(snl != null){
                int cnt = snl.length;
                for(int i=0; i<cnt; i++){
                    int sn = Utils.isNumber(snl[i], -1);
                    if(sn > -1){
                        testcaseVO v = this._testcaseService.selectOneBySn(sn);
                        if(v != null){    // 발화에 개별적으로 세팅된 값이 있으면 이 값이 다른 값보다 우선한다.
                            String status  = Utils.isString(v.getP_status(), "");
                            String event   = Utils.isString(v.getP_event(), "");
                            String uinfo   = Utils.isString(v.getP_uinfo(), "");
                            String context = Utils.isString(v.getP_context(), "");
                            String exam    = Utils.isString(v.getExam(), "");

                            if(!status.equals("")){
                                _vo.setStatus(status);
                            }

                            if(!event.equals("")){
                                _vo.setEvent(event);
                            }

                            if(!uinfo.equals("")){
                                _vo.setUinfo(uinfo);
                            }

                            if(!context.equals("")){
                                _vo.setContext(context);
                            }

                            _vo.setI(Integer.toString(sn));
                            _vo.setReqmsg(exam);
                            getActionGetBrokerUTFLocal(_vo);
                        }
                    }
                }
            }
        }
        response.setContentType( "text/html; charset=UTF-8" );
        response.getWriter().println(uuid);
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
        ModelAndView _mod = this.setPage("casetest", session);
        return _mod;
    }


    // *****************************************************************************
    // casetestManage
    // *****************************************************************************
    @RequestMapping(value="/casetestManage.do")
    public ModelAndView testcaseManage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
                                            throws IOException, URISyntaxException {
        ModelAndView _mod = this.setPage("casetest_manage", session);
        return _mod;
    }
    
    // -----------------------------------------------------------------------------
    @RequestMapping(value="/testcaseListJson.do")
    public void testcaseListJson(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                     @ModelAttribute testcaseVO _vo)
                                     throws IOException, URISyntaxException {

        List<testcaseVO> _testcaseList = this._testcaseService.selectView(_vo);
        List<actTypeVO>  _actTypeGroupList = this._testcaseService.selectActTypeGroupList();
        List<testcaseCategoryVO> _testcaseCategoryList = this._testcaseCategoryService.select(new testcaseCategoryVO());

        int cnt = _testcaseList.size();
        for(int i=0; i<cnt; i++){
            testcaseVO dt = (testcaseVO)_testcaseList.get(i);
            int k = i+1;
            dt.setIdx(k);
        }

        baseDataVO _lt = new baseDataVO();
        _lt.setTestcaseList(_testcaseList);
        _lt.setActTypeList(_actTypeGroupList);
        _lt.setTestcaseCategoryList(_testcaseCategoryList);

        Gson gson = new Gson();
        String result = gson.toJson(_lt);

        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
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

    // *****************************************************************************
    // testcaseEdit
    // *****************************************************************************
    @RequestMapping(value="/casetestEdit.do")
    public ModelAndView testcaseEdit(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                        @RequestParam (value="sn", required=false) String sn)
                                        throws IOException, URISyntaxException {

        // 카테고리전체
        List<testcaseCategoryVO> _testcaseCategoryList = this._testcaseCategoryService.select(new testcaseCategoryVO());
        Gson gson = new Gson();
        String catStr = gson.toJson(_testcaseCategoryList);

        StringBuffer sbTop = new StringBuffer();
        StringBuffer sbMain = new StringBuffer();

        int voGid   = 0;
        int voCatId = 0;

        testcaseVO _v = new testcaseVO();
        if(!Utils.isString(sn, "").equals("")){
             _v = this._testcaseService.selectOneBySn(Integer.parseInt(sn));
            if(_v != null){
                //System.out.println(_v.toInfoString());
                voGid   = _v.getGid();
                voCatId = _v.getCat_id();
            }
        }else{
            sbTop.append("<option value='' selected>--선택--</option>");
            sbMain.append("<option value='' selected>--선택--</option>");
        }


        //JSTL로 카테고리 드롭을 만들면 귀찮으니 그냥 여기서 만들어준다
        int len = _testcaseCategoryList.size();
        int ggid = 0;
        for(int i=0; i<len; i++){
            testcaseCategoryVO _d = (testcaseCategoryVO)_testcaseCategoryList.get(i);
            if(_d.getOrd()>0){
                if(voCatId == _d.getCat_id()){
                    ggid = _d.getGid();
                    sbMain.append("<option value='"+Integer.toString(_d.getCat_id())+"' selected>"+_d.getNm()+"</option>");
                }else{
                    sbMain.append("<option value='"+Integer.toString(_d.getCat_id())+"' >"+_d.getNm()+"</option>");
                }
            }
        }

        for(int j=0; j<len; j++){
            testcaseCategoryVO _d = (testcaseCategoryVO)_testcaseCategoryList.get(j);
            if(_d.getOrd()==0){
                if(ggid == _d.getGid()){
                    sbTop.append("<option value='"+Integer.toString(_d.getGid())+"' selected>"+_d.getNm()+"</option>");
                }else{
                    sbTop.append("<option value='"+Integer.toString(_d.getGid())+"' >"+_d.getNm()+"</option>");
                }
            }
        }


        ModelAndView _mod = new ModelAndView("pop_casetestEdit");
        _mod.addObject("testcaseVO",_v);
        _mod.addObject("categoryList", catStr);
        _mod.addObject("catTop",  sbTop.toString());
        _mod.addObject("catMain", sbMain.toString());
        return _mod;
    }

    // *****************************************************************************
    // singletest
    // *****************************************************************************
    @RequestMapping(value="/singletest.do")
    public ModelAndView singletest(HttpServletRequest request, HttpServletResponse response, HttpSession session)
                                    throws IOException, URISyntaxException {
        ModelAndView _mod = this.setPage("singletest", session);
        return _mod;
    }





    // *****************************************************************************
    // testcaseList
    // *****************************************************************************
    @RequestMapping(value="/testcaseList.do")
    public void testcaseList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                 @ModelAttribute testcaseVO _vo)
                                 throws IOException, URISyntaxException {

        List<testcaseVO> _lt = this._testcaseService.select(_vo);
        Gson gson = new Gson();
        String result = gson.toJson(_lt);

        response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);
    }



    // *****************************************************************************
    // testcaseBySn
    // *****************************************************************************
    @RequestMapping(value="/testcaseBySn.do")
    public void testcaseBySn(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                 @RequestParam (value="sn", required=true) int sn)
                                 throws IOException, URISyntaxException {

        testcaseVO _dt = this._testcaseService.selectOneBySn(sn);
        Gson gson = new Gson();
        String result = gson.toJson(_dt);

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
}