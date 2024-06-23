package com.aptech.group3.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aptech.group3.entity.News;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.NewsService;

@Controller

public class DefaultController {
	
	@Autowired NewsService newsService ;

//	@PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping({"/","/index"})
    
    public String index(Model model )
     { 
   
    	List<News> newsList = newsService.findAll();
        model.addAttribute("newsList", newsList);
    	return "page/index";
     }
    
    @RequestMapping({"/signup"})
    public String sigin()
     { 
    	return "signup";
     }
    
    
    
    @RequestMapping({"/login"})
    public String login()
     { 
 
    	return "login";
     }
    
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('ADMMIN')")
    @RequestMapping({"/admin/testAdmin"})
    public String testAdmin()
    { 

   	return "testAdmin";
    }
    

      

     

}
