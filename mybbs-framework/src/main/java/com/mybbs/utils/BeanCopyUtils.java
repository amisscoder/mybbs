package com.mybbs.utils;

import com.mybbs.domain.entity.Article;
import com.mybbs.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private  BeanCopyUtils() {

    }

    public static <T> T copyBean(Object Source, Class<T> clazz) {
        // 常见目标对象
        T result = null;
        try {
            result = clazz.newInstance();
            // 实现属性copy
            BeanUtils.copyProperties(Source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }

    public static <O, T> List<T> copyBeanList(List<O> list, Class<T> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }

}
