package com.mybbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mybbs.domain.ResponseResult;
import com.mybbs.domain.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    ResponseResult<List> categoryList();
}
