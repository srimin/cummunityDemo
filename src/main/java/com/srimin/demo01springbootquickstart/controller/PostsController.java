package com.srimin.demo01springbootquickstart.controller;

import com.srimin.demo01springbootquickstart.dto.PostsDTO;
import com.srimin.demo01springbootquickstart.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PostsController {
    @Autowired
    private PostsService postsService;

    @GetMapping("/posts/{Id}")
    public String posts(@PathVariable(name="Id")Integer Id,
                          Model model){
        PostsDTO postsDTO = postsService.getById(Id);
        model.addAttribute("posts",postsDTO);
        return "posts";
    }
}
