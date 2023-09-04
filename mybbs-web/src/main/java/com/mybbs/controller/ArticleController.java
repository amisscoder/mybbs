package com.mybbs.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mybbs.domain.ResponseResult;
//import com.mybbs.domain.entity.Article;
import com.mybbs.service.ArticleService;
import com.mybbs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    @GetMapping("/articlePage")
    public ResponseResult ArticlePage(Integer pageNum, Integer pageSize, Long categoryId) {
       ResponseResult<List> articlePage = articleService.articleList(pageNum, pageSize, categoryId);
       return articlePage;
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }
}
