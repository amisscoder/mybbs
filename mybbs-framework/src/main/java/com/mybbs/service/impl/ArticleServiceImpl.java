package com.mybbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybbs.constants.SystemConstants;
import com.mybbs.domain.ResponseResult;
import com.mybbs.domain.entity.Category;
import com.mybbs.domain.vo.ArticleVo;
import com.mybbs.domain.vo.HotArticleVo;
import com.mybbs.domain.vo.ArticlePageVo;
import com.mybbs.domain.vo.PageVo;
import com.mybbs.service.CategoryService;
import com.mybbs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mybbs.mapper.ArticleMapper;
import com.mybbs.domain.entity.Article;
import com.mybbs.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service("ArticleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult<List> hotArticleList() {
        //查询热门文章，封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
            //queryWrapper.eq( "status", 0); // 不安全
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量倒叙排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多查询10条信息
        Page<Article> page = new Page(1, 10);

        // 结果会封装到page中
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();

        //bean拷贝
        //BeanUtils.copyProperties();
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(articleVos);
    };

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果又 categoryId 查询时就要和传入的先相同
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0, Article::getCategoryId, categoryId);
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                    .orderByDesc(Article::getIsTop);
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Article> articlePage = page.getRecords().stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
//        //查询categoryName
//        for(Article article: articlePage) {
//
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        // 封装结果vo
        List<ArticlePageVo> articleListVos = BeanCopyUtils.copyBeanList(articlePage, ArticlePageVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //根据id查询文章
//        Article article = queryWrapper.eq(Article::getId, id);
        Article article = getById(id);
        //转换成vo
        BeanCopyUtils.copyBean(article, ArticleVo.class);
        //根据分类id查询分类名称

        Category category = categoryService.getById(article.getCategoryId());
        //判断避免空指针异常
        if (category != null ) {
            article.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(article);
    }

}
