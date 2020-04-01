package com.srimin.demo01springbootquickstart.mapper;

import com.srimin.demo01springbootquickstart.model.Posts;

public interface PostsExMapper {
    int incCommentCount(Posts record);
}
