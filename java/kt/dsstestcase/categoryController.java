package kt.dsstestcase;


import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kt.dsstestcase.service.authService;
import kt.dsstestcase.service.testcaseCategoryService;
import kt.dsstestcase.vo.authVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


@Controller
public class categoryController extends MultiActionController {

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

    // *****************************************************************************
    // subCat
    // *****************************************************************************
    @RequestMapping(value="/subCatListJson.do")
    public void testcaseListByGid(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                    @RequestParam (value="gid", required=false) String gid)throws IOException, URISyntaxException {

        /*
        buf.append("\t $('#"+domId+"').append($('<option></option>').attr('value','"+cd+"').text('"+nm+"')); \n");
         */
        String result = "";
        response.setContentType( "application/x-javascript; charset=utf-8" );
        response.getWriter().println(result);
    }
}
