package com.mybbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybbs.constants.SystemConstants;
import com.mybbs.domain.ResponseResult;
import com.mybbs.domain.entity.Article;
import com.mybbs.domain.entity.Category;
import com.mybbs.domain.vo.CategoryVo;
import com.mybbs.mapper.CategoryMapper;
import com.mybbs.service.ArticleService;
import com.mybbs.service.CategoryService;
import com.mybbs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表（Category）表服务实现类
 */
@Service("CategoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult categoryList() {
        //查询文章表
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleService.list(articleWrapper);
        //获取文章分类id并且去重
        Set<Long> categoryIds = articles.stream()
//                .map(new Function<Article, Long>() {
//                    @Override
//                    public Long apply(Article article) {
//                        return article.getCategoryId();
//                    }
//                })
//                .map((article) -> article.getCategoryId())
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        // 查询分类表
        List<Category> categories = listByIds(categoryIds);
//        LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
//        categoryWrapper.eq(Category::getStatus, SystemConstants.CATEGORY_STATUS_NORMAL);
//        List<Category> categories = categoryWrapper.
        categories.stream()
                .filter((category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus())))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        // LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        return ResponseResult.okResult(categoryVos);
    }
}
