package com.srimin.demo01springbootquickstart.controller;

import com.srimin.demo01springbootquickstart.exception.CustomizeErrorCode;
import com.srimin.demo01springbootquickstart.exception.CustomizeException;
import com.srimin.demo01springbootquickstart.mapper.PostsMapper;
import com.srimin.demo01springbootquickstart.model.Posts;
import com.srimin.demo01springbootquickstart.model.PostsExample;
import com.srimin.demo01springbootquickstart.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private PostsMapper postsMapper;

    @GetMapping(value = "/publish")
    public String publish(){
        return "publish";
    }

    //校验发布内容
    @PostMapping(value = "/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "content",required = false) String content,
                            @RequestParam(value = "tag",required = false) String tag,
                            @RequestParam(value = "id",required = false) Integer id,
                            HttpServletRequest request,
                            Model model    ){

        model.addAttribute("title",title);
        model.addAttribute("content",content);
        model.addAttribute("tag",tag);
        if (title == null || title == "") {
            model.addAttribute("error","标题不能为空");
            return "/publish";
        }
        if (content == null || content == "") {
            model.addAttribute("error","内容不能为空");
            return "/publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error","标签不能为空");
            return "/publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "/publish";
        }
        Posts posts = postsMapper.selectByPrimaryKey(id);
        if(posts == null){
            posts = new Posts();
            posts.setTitle(title);
            posts.setContent(content);
            posts.setTag(tag);
            posts.setCreator(user.getId());
            posts.setGmtCreate(System.currentTimeMillis());
            posts.setGmtModified(posts.getGmtCreate());
            postsMapper.insertSelective(posts);
        }else{
            posts.setTitle(title);
            posts.setContent(content);
            posts.setTag(tag);
            posts.setGmtModified(System.currentTimeMillis());
            PostsExample example = new PostsExample();
            example.createCriteria()
                    .andIdEqualTo(posts.getId());
            int state = postsMapper.updateByExampleSelective(posts, example);
            if (state != 1){
                throw new CustomizeException(CustomizeErrorCode.POSTS_NOT_FOUND);
            }
        }
        return "redirect:/";
    }

    @GetMapping(value = "/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id, Model model){
        Posts posts = postsMapper.selectByPrimaryKey(id);
        model.addAttribute("title",posts.getTitle());
        model.addAttribute("content",posts.getContent());
        model.addAttribute("tag",posts.getTag());
        model.addAttribute("id",posts.getId());
        return "publish";
    }
}
