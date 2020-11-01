package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {

    @Resource
    public LoginService loginService;

    //登录
    @GetMapping("")
    public String Login( HttpSession session){
        Account account = (Account) session.getAttribute("account");
        if(account!=null){
            if(account.getType().equals("1")){
                return "redirect:index";
            }else{
                return "redirect:home";
            }
        }
        return "login";
    }

    @GetMapping("/login")
    public String Login(){
        return "login";
    }

    @PostMapping("/login")
    public String Login(Model model, String accountId,String password,HttpSession session){

        //输入为空
        if(accountId.equals("")||password.equals("")){
            model.addAttribute("LoginCode",1);
            return "login";
        }

        // 登录成功
        String type = loginService.isLogin(accountId,password);
        Account account=new Account();
        account.setAccountid(accountId);
        account.setPassword(password);

        if(type.equals("0")||type.equals("2")){
            account.setType(type);
            session.setAttribute("account",account);
            return "redirect:home";
        }
        if(type.equals("1")){
            account.setType(type);
            session.setAttribute("account",account);
            return "redirect:index";
        }
        //账号或者密码错误
        model.addAttribute("LoginCode",2);
        return "login";
    }


}