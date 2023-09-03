package com.mybbs.controller;

import com.mybbs.domain.ResponseResult;
import com.mybbs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categoryList")
    public ResponseResult getCategoryList() {
        return categoryService.categoryList();
    }
}
