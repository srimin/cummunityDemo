package com.srimin.demo01springbootquickstart.controller;

import com.srimin.demo01springbootquickstart.dto.CommentResultDTO;
import com.srimin.demo01springbootquickstart.dto.PostsDTO;
import com.srimin.demo01springbootquickstart.service.CommentService;
import com.srimin.demo01springbootquickstart.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PostsController {
    @Autowired
    private PostsService postsService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{id}")
    public String posts(@PathVariable(name="id")Integer id,
                          Model model){
        PostsDTO postsDTO = postsService.getById(id);
        List<CommentResultDTO> comments = commentService.listByPostsId(id);
        model.addAttribute("posts",postsDTO);
        model.addAttribute("comments",comments);
        return "posts";
    }
}
