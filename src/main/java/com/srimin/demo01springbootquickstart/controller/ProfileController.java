package com.srimin.demo01springbootquickstart.controller;

import com.srimin.demo01springbootquickstart.dto.PageInfoDTO;
import com.srimin.demo01springbootquickstart.mapper.UserMapper;
import com.srimin.demo01springbootquickstart.model.User;
import com.srimin.demo01springbootquickstart.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostsService postsService;

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable(name="userId")Integer userId,
                          Model model){
        User user = userMapper.findById(userId);
        model.addAttribute("user",user);
        return "profile";
    }

    @GetMapping("/profile/{userId}/{type}")
    public String profileType(@PathVariable(name = "userId") Integer userId,
                              @PathVariable(name = "type") String type,
                              //HttpServletRequest request,
                              Model model,
                              @RequestParam(name = "page", defaultValue = "1") Integer page,
                              @RequestParam(name = "range", defaultValue = "5") Integer range) {
        User user = userMapper.findById(userId);
        //User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        if("posts".equals(type)){
            model.addAttribute("section","myPosts");
            model.addAttribute("sectionName","我的贴子");
        }
        PageInfoDTO pageInfoDTO = postsService.list(userId,page, range);
        model.addAttribute("pageInfoDTO",pageInfoDTO);
        return "profileother";
    }
}
