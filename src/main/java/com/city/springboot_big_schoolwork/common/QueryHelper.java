package com.city.springboot_big_schoolwork.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.StringUtils;

import java.util.Map;

public class QueryHelper {

    @SuppressWarnings("unchecked")
    public static <T> QueryWrapper<T> buildQueryWrapper(Map<String, Object> params, String... defaultFields) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();

        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (StringUtils.hasText(key) && value != null && StringUtils.hasText(value.toString())) {
                    if (key.endsWith("_like")) {
                        wrapper.like(key.replace("_like", ""), value.toString());
                    } else if (key.endsWith("_ge")) {
                        wrapper.ge(key.replace("_ge", ""), value.toString());
                    } else if (key.endsWith("_le")) {
                        wrapper.le(key.replace("_le", ""), value.toString());
                    } else if (key.endsWith("_in")) {
                        wrapper.in(key.replace("_in", ""), (Object[]) value.toString().split(","));
                    } else {
                        wrapper.eq(key, value);
                    }
                }
            }
        }

        for (String field : defaultFields) {
            wrapper.orderByDesc(field);
        }

        return wrapper;
    }
}
