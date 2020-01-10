package com.seagetech.web.commons.login.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seagetech.common.util.SeageJson;
import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.login.LoginProperties;
import com.seagetech.web.commons.login.exception.LoginException;
import com.seagetech.web.commons.login.exception.UseLoginUserNameNotFindException;
import com.seagetech.web.commons.view.DefaultViewName;
import com.seagetech.web.commons.view.exception.UserEntityNotFindPasswordFieldException;
import com.seagetech.web.commons.view.load.*;
import com.seagetech.web.commons.view.service.PageViewService;

import java.util.*;

/**
 * 默认的登录认证
 * 使用可以作为用户名（用户名、手机号、邮箱等唯一约束的字段）登录
 * @author wangzb
 * @date 2020/1/9 13:42
 * @company 矽甲（上海）信息科技有限公司
 */
public class UseUserNameLoginPermission implements IPermission {
    /**
     * {@link PageViewService}
     */
    private PageViewService pageViewService;

    public UseUserNameLoginPermission(PageViewService pageViewService) {
        this.pageViewService = pageViewService;
    }

    /**
     * 获取权限
     *
     * @param userName 用户名，通常是具体登录使用的用户名（手机号或者邮箱），具有唯一性
     * @return
     */
    @Override
    public Set<String> getPermissions(String userName) {
        return null;
    }

    /**
     * 获取用户密码
     *
     * @param userName 用户名，通常是具体登录使用的用户名（手机号或者邮箱），具有唯一性
     * @param password 用户提交的密码（加密后）
     * @return
     */
    @Override
    public LoginInfo getPasswordByUserId(String userName, String password) {
        //获取用户有关PageViewInfo
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(DefaultViewName.USER);
        Class pageViewClass = pageViewInfo.getPageViewClass();
        //主键
        PrimaryKeyInfo primaryKeyInfo = pageViewInfo.getPrimaryKey();
        //密码
        List<IFunctionInfo> passwords = pageViewInfo.get(FunctionType.PASSWORD);
        if (SeageUtils.isEmpty(passwords)){
            throw new UserEntityNotFindPasswordFieldException();
        }
        PasswordInfo passwordInfo = pageViewInfo.getPassword();
        //可以作为登录的用户名字段
        List<IFunctionInfo> useLoginUserNames = pageViewInfo.get(FunctionType.USE_LOGIN_USER_NAME);
        if (SeageUtils.isEmpty(useLoginUserNames)){
            throw new UseLoginUserNameNotFindException();
        }
        for (IFunctionInfo useLoginUserName : useLoginUserNames){
            UseLoginUserNameInfo useLoginUserNameInfo = (UseLoginUserNameInfo) useLoginUserName;
            Map<String,Object> params = new HashMap<>(1);
            params.put(useLoginUserNameInfo.getName(),userName);
            List<Map<String, Object>> users = pageViewService.getListByPage(DefaultViewName.USER, params);
            if (!SeageUtils.isEmpty(users)){
                for (Map<String, Object> user : users){
                    //获取用户主键并且进行加密
                    String primaryKeyValue = user.get(primaryKeyInfo.getName()).toString();
                    String ciphertext = ShiroUtils.encryption(password,primaryKeyValue);
                    //数据库中密码
                    String userPassword = user.get(passwordInfo.getName()).toString();
                    if (Objects.equals(userPassword,ciphertext)){
                        //获取状态值
                        List<IFunctionInfo> userStatuss = pageViewInfo.get(FunctionType.USER_STATUS);
                        if (SeageUtils.isEmpty(userStatuss)){
                            Object o = map2Object(user, LoginProperties.getInstance().getLoginUserClass());
                            return LoginInfo.builder().build()
                                    .setPrincipal(o)
                                    .setPassword(userPassword)
                                    .setCredentialsSalt(primaryKeyValue)
                                    .setLogin(true);
                        }
                        for (IFunctionInfo userStatus : userStatuss){
                            UserStatusInfo userStatusInfo = (UserStatusInfo) userStatus;
                            if (Objects.equals(user.get(userStatusInfo.getName()).toString(),userStatusInfo.getValue())){
                                if (userStatusInfo.isValid()){
                                    Object o = map2Object(user, LoginProperties.getInstance().getLoginUserClass());
                                    return LoginInfo.builder().build()
                                            .setPrincipal(o)
                                            .setPassword(userPassword)
                                            .setCredentialsSalt(primaryKeyValue)
                                            .setLogin(true);
                                }
                                String message = userStatusInfo.getMessage();
                                if (SeageUtils.isEmpty(message)){
                                    message = LoginException.DEFAULT_MESSAGE;
                                }
                                throw new LoginException(message,userName);
                            }
                        }
                    }
                }
            }
        }
        return LoginInfo.builder().build().setLogin(false);
    }

    public static <T> T map2Object(Map map, Class<T> tClass) {
        String jsonStr = JSON.toJSON(map).toString();
        return parseObject(jsonStr,tClass);
    }

    /**
     * 将json字符串转Object对象
     * 当然也支持json数组字符串转List，但注意返回的List的泛型为JSONObject对象
     * @param jsonStr 目标字符串
     * @param tClass 目标对象Class
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String jsonStr,Class<T> tClass)  {
        return JSON.parseObject(jsonStr,tClass);
    }


}
