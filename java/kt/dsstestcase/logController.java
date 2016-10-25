package kt.dsstestcase;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kt.dsstestcase.service.authService;
import kt.dsstestcase.service.logService;
import kt.dsstestcase.vo.authVO;
import kt.dsstestcase.vo.logVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;

@Controller
public class logController extends MultiActionController {

    @Resource(name="logService")
    private logService _logService;

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


    // *****************************************************************************
    // log
    // *****************************************************************************
    // log리스트반환
    @RequestMapping(value="/logListByUuid.do")
    public void logListByUuid(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                    @RequestParam (value="uuid", required=true) String uuid)throws IOException, URISyntaxException {

        List<logVO> _logLt = this._logService.selectByUuid(uuid);

        Gson gson = new Gson();
        String result = gson.toJson(_logLt);
        response.setContentType( "application/json; charset=utf-8" );
        response.getWriter().println(result);
    }

    /**
     * log상세정보 보여주기
     * @param request
     * @param response
     * @param session
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @RequestMapping(value="/showLogXML.do")
    public ModelAndView showLogXML(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                @RequestParam (value="uuid", required=true) String uuid,
                                @RequestParam (value="sn", required=true) String sn)throws IOException, URISyntaxException {

        logVO _vo = this._logService.selectOneByUuidSn(uuid, sn);
        ModelAndView _mod = this.setPage("pop_showXML", session);
        _mod.addObject("logVO", _vo);
        return _mod;
    }
}
