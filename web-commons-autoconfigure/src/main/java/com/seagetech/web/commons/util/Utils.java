package com.seagetech.web.commons.util;

import com.seagetech.common.util.SeageUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author wangzb
 * @date 2019/12/26 9:45
 * @company 矽甲（上海）信息科技有限公司
 */
public class Utils {

    /**
     * 获取request域参数
     * @param request
     */
    public static Map<String,Object> getParameter(HttpServletRequest request){
        Map<String,Object> params = new HashMap<>(SeageUtils.initialCapacity());
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            params.put(parameterName,request.getParameter(parameterName));
        }
        return params;
    }

    /**
     * 获取属性字段
     * @param clazz
     * @return
     */
    public static List<Field> getFieldList(Class<?> clazz){
        if(null == clazz){
            return null;
        }
        List<Field> fieldList = new LinkedList<Field>();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            //过滤静态属性
            if(Modifier.isStatic(field.getModifiers())){
                continue;
            }
            //过滤transient 关键字修饰的属性
            if(Modifier.isTransient(field.getModifiers())){
                continue;
            }
            fieldList.add(field);
        }
        //处理父类字段
        Class<?> superClass = clazz.getSuperclass();
        if(superClass.equals(Object.class)){
            return fieldList;
        }
        fieldList.addAll(getFieldList(superClass));
        return fieldList;
    }
}
