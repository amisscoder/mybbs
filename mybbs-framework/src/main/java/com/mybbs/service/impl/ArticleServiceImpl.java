package com.mybbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybbs.domain.ResponseResult;
import org.springframework.stereotype.Service;
import com.mybbs.mapper.ArticleMapper;
import com.mybbs.domain.entity.Article;
import com.mybbs.service.ArticleService;

import java.util.List;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public ResponseResult<List> hotArticleList() {
        //查询热门文章，封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
            //queryWrapper.eq( "status", 0); // 不安全
        queryWrapper.eq(Article::getStatus, 0);
        //按照浏览量倒叙排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多查询10条信息
        Page<Article> page = new Page(1, 10);

        // 结果会封装到page中
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();

        return ResponseResult.okResult(articles);
    }
}
