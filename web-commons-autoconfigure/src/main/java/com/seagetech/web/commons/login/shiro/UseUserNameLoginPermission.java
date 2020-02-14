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
import com.seagetech.web.commons.view.entity.BasedPermission;
import com.seagetech.web.commons.view.exception.UserEntityNotFindPasswordFieldException;
import com.seagetech.web.commons.view.load.*;
import com.seagetech.web.commons.view.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IBasedUserRoleService basedUserRoleService;

    @Autowired
    private IBasedRolePermissionService basedRolePermissionService;

    @Autowired
    private IBasedPermissionService basedPermissionService;

    public UseUserNameLoginPermission() {
    }

    public UseUserNameLoginPermission(PageViewService pageViewService) {
        this.pageViewService = pageViewService;
    }

    private static final String VIEW_START = "/view";
    private static final String VIEW_PERMISSION = "view";

    /**
     * 获取权限
     *
     * @param userName 用户名，通常是具体登录使用的用户名（手机号或者邮箱），具有唯一性
     * @return
     */
    @Override
    public Set<String> getPermissions(String userName) {
        //获取用户角色
        List<Integer> roles = basedUserRoleService.getUserRoles(Integer.parseInt(userName));
        //通过角色再获取权限
        List<Integer> permissionsIds = basedRolePermissionService.getPermissionsByRoles(roles);
        //获取权限
        Collection<BasedPermission> basedPermissions = basedPermissionService.listByIds(permissionsIds);
        String servletPath = request.getServletPath();
        if (servletPath.startsWith(VIEW_START)){
            for (BasedPermission basedPermission:basedPermissions){
                if (Objects.equals(basedPermission.getPermissionAlias(),servletPath)){
                    return Arrays.stream(new String[]{VIEW_PERMISSION})
                            .collect(Collectors.toSet());
                }
            }
        }
        return basedPermissions
                .stream()
                .map(BasedPermission::getPermissionAlias)
                .collect(Collectors.toSet());
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
            String useLoginUserNameValue = useLoginUserNameInfo.getName();
            params.put(useLoginUserNameValue,userName);
            List<Map<String, Object>> users = pageViewService.getList(DefaultViewName.USER, params);
            if (!SeageUtils.isEmpty(users)){
                for (Map<String, Object> user : users){
                    if (!Objects.equals(user.get(useLoginUserNameValue),userName)){
                        continue;
                    }
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
