package com.srimin.demo01springbootquickstart.service;

import com.srimin.demo01springbootquickstart.dto.PageInfoDTO;
import com.srimin.demo01springbootquickstart.dto.PostsDTO;
import com.srimin.demo01springbootquickstart.mapper.PostsMapper;
import com.srimin.demo01springbootquickstart.mapper.UserMapper;
import com.srimin.demo01springbootquickstart.model.Posts;
import com.srimin.demo01springbootquickstart.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostsService {
    @Autowired
    private PostsMapper postsMapper;
    @Autowired
    private UserMapper userMapper;
    public PageInfoDTO list(Integer page, Integer range) {
        PageInfoDTO pageInfoDTO = new PageInfoDTO();
        Integer integer = page;
        page = (page-1)*range;
        List<Posts> posts = postsMapper.list(page,range);
        List<PostsDTO> postsDTOList = new ArrayList<>();
        for (Posts post : posts) {
            PostsDTO postDTO = new PostsDTO();
            User user = userMapper.findById(post.getCreator());
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postsDTOList.add(postDTO);
        }
        pageInfoDTO.setPostsList(postsDTOList);
        pageInfoDTO.setPage(integer);
        Integer count = postsMapper.count();
        integer = count%range==0? count/range : count/range +1;
        pageInfoDTO.setTotalPage(integer);
        return pageInfoDTO;
    }

    public PageInfoDTO list(Integer id,Integer page, Integer range) {
        PageInfoDTO pageInfoDTO = new PageInfoDTO();
        Integer integer = page;
        page = (page-1)*range;
        List<Posts> posts = postsMapper.listByUserId(id,page,range);
        List<PostsDTO> postsDTOList = new ArrayList<>();
        for (Posts post : posts) {
            PostsDTO postDTO = new PostsDTO();
            User user = userMapper.findById(post.getCreator());
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postsDTOList.add(postDTO);
        }
        pageInfoDTO.setPostsList(postsDTOList);
        pageInfoDTO.setPage(integer);
        Integer count = postsMapper.countByUserId(id);
        integer = count%range==0? count/range : count/range +1;
        pageInfoDTO.setTotalPage(integer);
        return pageInfoDTO;
    }

    public PostsDTO getById(Integer Id) {
        PostsDTO postDTO = new PostsDTO();
        Posts posts = postsMapper.getById(Id);
        BeanUtils.copyProperties(posts,postDTO);
        postDTO.setUser(userMapper.findById(posts.getCreator()));
        return postDTO;
    }
}
