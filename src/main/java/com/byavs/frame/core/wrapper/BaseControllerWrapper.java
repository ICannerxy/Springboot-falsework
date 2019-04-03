package com.byavs.frame.core.wrapper;

import com.byavs.frame.core.entity.PageResult;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author XuYang
 * @description: 控制器查询结果的包装类基类
 * @date 2019/4/115:02
 */
public abstract class BaseControllerWrapper {

    private Page<Map<String, Object>> page = null;

    private PageResult<Map<String, Object>> pageResult = null;

    private Map<String, Object> single = null;

    private List<Map<String, Object>> multi = null;

    public BaseControllerWrapper(Map<String, Object> single) {
        this.single = single;
    }

    public BaseControllerWrapper(List<Map<String, Object>> multi) {
        this.multi = multi;
    }

    @SuppressWarnings("unchecked")
    public <T> T wrap() {

        /**
         * 包装结果
         */
        if (single != null) {
            wrapTheMap(single);
        }
        if (multi != null) {
            for (Map<String, Object> map : multi) {
                wrapTheMap(map);
            }
        }

        /**
         * 根据请求的参数响应
         */
        if (page != null) {
            return (T) page;
        }
        if (pageResult != null) {
            return (T) pageResult;
        }
        if (single != null) {
            return (T) single;
        }
        if (multi != null) {
            return (T) multi;
        }

        return null;
    }

    protected abstract void wrapTheMap(Map<String, Object> map);
}
