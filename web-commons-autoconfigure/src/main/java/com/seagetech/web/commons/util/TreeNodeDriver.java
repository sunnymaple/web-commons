package com.seagetech.web.commons.util;


import com.seagetech.common.exception.SeageException;
import com.seagetech.common.util.SeageUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 属性结构组装
 * @author wangzb
 * @date 2018-11-28 12:47
 * @company 矽甲（上海）信息科技有限公司
 */
public class TreeNodeDriver<T> {

    private Class<T> tClass;
    /**
     * 主键名称
     */
    private String primaryKey;
    /**
     * 父ID的字段名称
     */
    private String parentKey;
    /**
     * 孩子节点名称
     */
    private String childrenFieldName;

    public TreeNodeDriver(Class<T> tClass, String primaryKey, String parentKey) {
        this.primaryKey = primaryKey;
        this.parentKey = parentKey;
        this.tClass = tClass;
    }

    /**
     * 带根节点
     * @param root 根节点
     * @param ts 所有节点List集合
     * @throws Exception
     */
    public void tree(T root, List<T> ts) throws Exception {
        List<T> results = tree(ts);
        if (!SeageUtils.isEmpty(root)){
            setMethod(root,childrenFieldName,results);
        }
    }

    /**
     * 组装成树
     * @param ts 目标对象的集合
     */
    public List<T> tree(List<T> ts) throws Exception {
        List<T> results = new ArrayList<>();
        String childrenFieldName = getChildrenFieldName();
        if (SeageUtils.isEmpty(childrenFieldName)){
            throw new SeageException("无孩子节点！");
        }
        for (T t : ts){
            Object pId = getGetMethod(t,parentKey);
            if (SeageUtils.isEmpty(pId) || Objects.equals(pId.toString(),"0")){
                results.add(t);
            }
            Object id = getGetMethod(t,primaryKey);
            for (T ct : ts){
                pId = getGetMethod(ct,parentKey);
                if (Objects.equals(id.toString(),pId.toString())){
                    List<T> children = (List<T>) getGetMethod(t,childrenFieldName);
                    if (SeageUtils.isEmpty(children)){
                        children = new ArrayList<>();
                        setMethod(t,childrenFieldName,children);
                    }
                    children.add(ct);
                }
            }
        }

        return results;
    }

    /**
     * 根据属性，获取get方法
     * @param ob 对象
     * @param name 属性名
     * @return
     * @throws Exception
     */
    private <T> Object getGetMethod(T ob, String name) throws Exception {
        Method[] m = ob.getClass().getMethods();
        for(int i = 0;i < m.length;i++){
            if(("get"+name).toLowerCase().equals(m[i].getName().toLowerCase())){
                return m[i].invoke(ob);
            }
        }
        return null;
    }

    /**
     * 调用set方法
     * @param ob
     * @param name
     * @param children
     * @throws Exception
     */
    private void setMethod(T ob, String name, List<T> children) throws Exception {
        Method[] m = ob.getClass().getMethods();
        for(int i = 0;i < m.length;i++){
            if(("set"+name).toLowerCase().equals(m[i].getName().toLowerCase())){
                m[i].invoke(ob,children);
            }
        }
    }

    /**
     * 获取孩子节点的字典名称
     * @return
     */
    private String getChildrenFieldName() {
        String childrenFieldName = "";
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields){
            //获取访问权限
            boolean accessFlag = field.isAccessible();
            field.setAccessible(true);
            String fieldName = field.getName();
            Class fieldTypeClass = field.getType();
            if(java.util.ArrayList.class == fieldTypeClass || java.util.List.class == fieldTypeClass){
                //属性为list集合，获取list泛型
                Type genericType = field.getGenericType();
                if(genericType == null){
                    continue;
                }
                // 如果是泛型参数的类型
                if(genericType instanceof ParameterizedType){
                    ParameterizedType pt = (ParameterizedType) genericType;
                    //得到泛型里的class类型对象
                    Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                    if (genericClazz == tClass){
                        field.setAccessible(accessFlag);
                        childrenFieldName = fieldName;
                        break;
                    }
                }
            }
            field.setAccessible(accessFlag);
        }
        this.childrenFieldName = childrenFieldName;
        return childrenFieldName;
    }
}
