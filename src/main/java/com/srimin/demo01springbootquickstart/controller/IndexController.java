package com.srimin.demo01springbootquickstart.controller;

import com.srimin.demo01springbootquickstart.dto.PageInfoDTO;
import com.srimin.demo01springbootquickstart.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {


    @Autowired
    private PostsService postsService;
    @GetMapping(value = "/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "range",defaultValue = "5")Integer range){
        PageInfoDTO pageInfoDTO = postsService.list(page, range);
        model.addAttribute("pageInfoDTO",pageInfoDTO);
        return "index";
    }
}
