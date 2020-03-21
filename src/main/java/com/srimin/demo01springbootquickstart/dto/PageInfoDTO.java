package com.srimin.demo01springbootquickstart.dto;

import java.util.List;

public class PageInfoDTO {
    private List<PostsDTO> postsList;
    private Integer page;
    private Integer totalPage;

    public List<PostsDTO> getPostsList() {
        return postsList;
    }

    public void setPostsList(List<PostsDTO> postsList) {
        this.postsList = postsList;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
