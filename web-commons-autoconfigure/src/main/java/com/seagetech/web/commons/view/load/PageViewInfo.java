package com.seagetech.web.commons.view.load;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.exception.DisabledFunctionException;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * 页面信息
 * @author wangzb
 * @date 2019/12/23 11:53
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@Builder
@Accessors(chain = true)
public class PageViewInfo extends HashMap<FunctionType,List<IFunctionInfo>> {

    /**
     * 页面视图名称
     */
    private String viewName;
    /**
     * 视图路径
     */
    private String viewPath;

    /**
     * 表
     */
    private String table;
    /**
     * 数据库视图
     */
    private String view;
    /**
     * 主键ID
     */
    private String tableId;
    /**
     * 页面功能自定义类
     * key 对应的增删改查功能
     * value 自定义功能类
     */
    private IPageViewCustom pageViewCustom;
    /**
     * 是否开启查询自定义功能
     */
    private FunctionType[] enableCustomFunctions;
    /**
     * 类
     */
    private Class pageViewClass;

    /**
     * 判断是否开启自定义功能
     * @param function 目标功能
     * @return false 目标功能自定义未开启
     *         true 目标功能自定义已开启
     */
    public boolean enableCustomFunction(FunctionType function){
        if (!SeageUtils.isEmpty(enableCustomFunctions)){
            for (FunctionType enableCustomFunction : enableCustomFunctions){
                if (enableCustomFunction == function){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     *
     * <p>A return value of {@code null} does not <i>necessarily</i>
     * indicate that the map contains no mapping for the key; it's also
     * possible that the map explicitly maps the key to {@code null}.
     * The {@link #containsKey containsKey} operation may be used to
     * distinguish these two cases.
     *
     * @param key
     */
    @Override
    public List<IFunctionInfo> get(Object key) {
        Optional<List<IFunctionInfo>> functionInfoOp = Optional.ofNullable(super.get(key));
        return functionInfoOp.orElseThrow(()->new DisabledFunctionException(key));
    }
}
