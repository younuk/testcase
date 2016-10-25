package kt.dsstestcase;


import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kt.dsstestcase.service.authService;
import kt.dsstestcase.vo.authVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;


@Controller
public class authController extends MultiActionController {

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
     * login
     * @param request
     * @param response
     * @param session
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @RequestMapping(value="/login.do")    
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, HttpSession session)throws IOException, URISyntaxException {        
    	return new ModelAndView("index");
    } 

    
    /**
     * loginMain
     * @param request
     * @param response
     * @param session
     * @param login_id
     * @param login_pass
     * @throws IOException
     * @throws URISyntaxException
     */
    @RequestMapping(value="/loginMain.do")    
    public void loginMain(HttpServletRequest request, HttpServletResponse response, HttpSession session,
				            @RequestParam String login_id,
				            @RequestParam String login_pass)throws IOException, URISyntaxException {        
        		
        String ret = "0";     
        this._authVO = _authService.selectOneByLoginId(login_id, login_pass);
        
        if(this._authVO!=null){
        	String sid = UUID.randomUUID().toString().replace("-", "");
        	this._authVO.setLogin_session(sid);
        	this._authService.updateLoginSession(login_id, sid);
	        session.setAttribute("authVO", this._authVO);                                      
	        ret = "1";
        }
        
        System.out.println(ret);
        response.setContentType( "text/plain; charset=UTF-8" );
        response.getWriter().println(ret);                             
    } 
    
    
    
    
    // *****************************************************************************
    // adminInfoById
    // *****************************************************************************
    @RequestMapping(value="/adminInfoById.do")    
    public void adminInfoById(HttpServletRequest request, HttpServletResponse response, HttpSession session,
    						 @RequestParam (value="login_id", required=true) String login_id)throws IOException, URISyntaxException {        
        		
    	authVO _dt = new authVO();
    	if(this._authVO.getIs_super().equals("1")){
    		_dt = this._authService.selectOneByLoginIdForAdmin(login_id);
    	}
    	
    	Gson gson = new Gson();
    	String result = gson.toJson(_dt);
    	
    	response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);                      
    }     
  
    
    // *****************************************************************************
    // adminInsert
    // *****************************************************************************    
    @RequestMapping(value="/adminInsert.do")    
    public void adminInsert(HttpServletRequest request, HttpServletResponse response, HttpSession session,
    						@ModelAttribute authVO _vo) throws NoSuchAlgorithmException, IOException {        
    	int result = 0;    	
    	if(_vo != null){
			result = this._authService.insert(_vo);
    	} 	    	
    	response.setContentType( "application/json; charset=UTF-8" );
		response.getWriter().println(result);         
    }     
    
    
    // *****************************************************************************    
    // adminUpdateField
    // *****************************************************************************    
    @RequestMapping(value="/adminUpdateField.do")    
    public void adminUpdateField(HttpServletRequest request, HttpServletResponse response, HttpSession session,
    									@RequestParam (value="login_id", required=true) String loginId,
    									@RequestParam (value="field", required=true) String field,
    									@RequestParam (value="value", required=true) String val)throws IOException, URISyntaxException, NoSuchAlgorithmException {        
        	
    	int result = this._authService.updateField(loginId, field, val); 	    	
    	response.setContentType( "application/json; charset=UTF-8" );
        response.getWriter().println(result);                      
    }   
    
 
}
