package com.mybbs.controller;


import com.mybbs.domain.ResponseResult;
//import com.mybbs.domain.entity.Article;
import com.mybbs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test() {
//        return articleService.list();
//    }

    @GetMapping("/hotArticleList")
    public ResponseResult<List> hotArticleList() {
        //查询热门文章，封装成ResponseResult返回
        ResponseResult<List> result = articleService.hotArticleList();
        return result;
    }
}
