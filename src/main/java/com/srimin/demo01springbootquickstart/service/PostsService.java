package com.srimin.demo01springbootquickstart.service;

import com.github.pagehelper.PageHelper;
import com.srimin.demo01springbootquickstart.dto.PageInfoDTO;
import com.srimin.demo01springbootquickstart.dto.PostsDTO;
import com.srimin.demo01springbootquickstart.exception.CustomizeErrorCode;
import com.srimin.demo01springbootquickstart.exception.CustomizeException;
import com.srimin.demo01springbootquickstart.mapper.PostsMapper;
import com.srimin.demo01springbootquickstart.mapper.UserMapper;
import com.srimin.demo01springbootquickstart.model.Posts;
import com.srimin.demo01springbootquickstart.model.PostsExample;
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
    public PageInfoDTO list(Integer pageNum, Integer pageSize) {
        PageInfoDTO pageInfoDTO = new PageInfoDTO();
        PageHelper.startPage(pageNum,pageSize);
        List<Posts> posts = postsMapper.selectByExampleWithBLOBs(new PostsExample());
        List<PostsDTO> postsDTOList = new ArrayList<>();
        for (Posts post : posts) {
            PostsDTO postDTO = new PostsDTO();
            User user = userMapper.selectByPrimaryKey(post.getCreator());
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postsDTOList.add(postDTO);
        }
        pageInfoDTO.setPostsList(postsDTOList);
        pageInfoDTO.setPage(pageNum);
        Integer count = (int) postsMapper.countByExample(new PostsExample());
        Integer tocalCount = count%pageSize==0? count/pageSize : count/pageSize +1;
        pageInfoDTO.setTotalPage(tocalCount);
        return pageInfoDTO;
    }

    public PageInfoDTO list(Integer userId,Integer pageNum, Integer pageSize) {
        PageInfoDTO pageInfoDTO = new PageInfoDTO();
        PostsExample postsExample = new PostsExample();
        postsExample.createCriteria()
                .andCreatorEqualTo(userId);
        PageHelper.startPage(pageNum,pageSize);
        List<Posts> posts = postsMapper.selectByExampleWithBLOBs(postsExample);
        List<PostsDTO> postsDTOList = new ArrayList<>();
        for (Posts post : posts) {
            PostsDTO postDTO = new PostsDTO();
            User user = userMapper.selectByPrimaryKey(post.getCreator());
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postsDTOList.add(postDTO);
        }
        pageInfoDTO.setPostsList(postsDTOList);
        pageInfoDTO.setPage(pageNum);
        PostsExample example = new PostsExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        Integer count = (int) postsMapper.countByExample(example);
        Integer totalCount= count%pageSize==0? count/pageSize : count/pageSize +1;
        pageInfoDTO.setTotalPage(totalCount);
        return pageInfoDTO;
    }

    public PostsDTO getById(Integer Id) {
        Posts posts = postsMapper.selectByPrimaryKey(Id);
        if (posts == null) {
            throw new CustomizeException(CustomizeErrorCode.POSTS_NOT_FOUND);
        }
        PostsDTO postDTO = new PostsDTO();
        BeanUtils.copyProperties(posts,postDTO);
        postDTO.setUser(userMapper.selectByPrimaryKey(posts.getCreator()));
        return postDTO;
    }
}
