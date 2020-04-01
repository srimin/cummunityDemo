package com.srimin.demo01springbootquickstart.service;

import com.srimin.demo01springbootquickstart.dto.CommentResultDTO;
import com.srimin.demo01springbootquickstart.enums.CommentTypeEnum;
import com.srimin.demo01springbootquickstart.exception.CustomizeErrorCode;
import com.srimin.demo01springbootquickstart.exception.CustomizeException;
import com.srimin.demo01springbootquickstart.mapper.CommentMapper;
import com.srimin.demo01springbootquickstart.mapper.PostsExMapper;
import com.srimin.demo01springbootquickstart.mapper.PostsMapper;
import com.srimin.demo01springbootquickstart.mapper.UserMapper;
import com.srimin.demo01springbootquickstart.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private PostsMapper postsMapper;
    @Autowired
    private PostsExMapper postsExMapper;
    @Autowired
    private UserMapper userMapper;
    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARENT_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType().equals(CommentTypeEnum.COMMENT.getType())){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复贴子
            Posts posts = postsMapper.selectByPrimaryKey(comment.getParentId());
            if (posts == null) {
                throw new CustomizeException(CustomizeErrorCode.POSTS_NOT_FOUND);
            }
            commentMapper.insert(comment);
            posts.setCommentCount(1);
            postsExMapper.incCommentCount(posts);
        }
    }

    public List<CommentResultDTO> listByPostsId(Integer id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(CommentTypeEnum.POSTS.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size() == 0){
            return new ArrayList<>();
        }

        Set<Integer> commentators = comments.stream().map(Comment::getCommentator).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));

        List<CommentResultDTO> commentResultDTOS = comments.stream().map(comment -> {
            CommentResultDTO commentResultDTO = new CommentResultDTO();
            BeanUtils.copyProperties(comment, commentResultDTO);
            commentResultDTO.setUser(userMap.get(comment.getCommentator()));
            return commentResultDTO;
        }).collect(Collectors.toList());

        return commentResultDTOS;
    }
}
