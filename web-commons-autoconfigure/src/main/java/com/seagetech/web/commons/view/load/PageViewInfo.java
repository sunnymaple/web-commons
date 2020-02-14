package com.seagetech.web.commons.view.load;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.login.exception.PrimaryKeyNotFindException;
import com.seagetech.web.commons.view.DefaultViewName;
import com.seagetech.web.commons.view.exception.DisabledFunctionException;
import com.seagetech.web.commons.view.exception.UserEntityNotFindPasswordFieldException;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
     * excel从第几行开始读取(从 0 开始)
     */
    private Integer row;
    /**
     * excel模板文件路径
     */
    private String excelTemplatePath;

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
     * 没有获取到时抛出异常
     * @param key
     * @throws DisabledFunctionException
     * @return
     */
    public List<IFunctionInfo> getThrow(Object key) {
        Optional<List<IFunctionInfo>> functionInfoOp = Optional.ofNullable(super.get(key));
        return functionInfoOp.orElseThrow(()->new DisabledFunctionException(key));
    }

    /**
     * 获取主键
     * @return
     */
    public PrimaryKeyInfo getPrimaryKey(){
        List<IFunctionInfo> primaryKeys = get(FunctionType.PRIMARY_KEY);
        if (SeageUtils.isEmpty(primaryKeys)){
            throw new PrimaryKeyNotFindException(DefaultViewName.USER);
        }
        PrimaryKeyInfo primaryKeyInfo = (PrimaryKeyInfo) primaryKeys.get(0);
        return primaryKeyInfo;
    }

    /**
     * 获取密码
     * @return
     */
    public PasswordInfo getPassword(){
        if (Objects.equals(viewName,DefaultViewName.USER)){
            List<IFunctionInfo> passwords = get(FunctionType.PASSWORD);
            if (SeageUtils.isEmpty(passwords)){
                throw new UserEntityNotFindPasswordFieldException();
            }
            PasswordInfo passwordInfo = (PasswordInfo) passwords.get(0);
            return passwordInfo;
        }
        return null;
    }

}
