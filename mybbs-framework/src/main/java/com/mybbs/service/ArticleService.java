package com.mybbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mybbs.domain.ResponseResult;
import com.mybbs.domain.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult<List> hotArticleList();

    ResponseResult<List> articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult<Article> getArticleDetail(Long id);
}
