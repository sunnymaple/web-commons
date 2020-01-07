package com.seagetech.web.commons.view.mapper.provider;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.Condition;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.entity.DeleteConstants;
import com.seagetech.web.commons.view.load.DeleteInfo;
import com.seagetech.web.commons.view.load.IFunctionInfo;
import com.seagetech.web.commons.view.load.PageViewContainer;
import com.seagetech.web.commons.view.load.PageViewInfo;
import com.seagetech.web.commons.view.load.exception.PageViewException;
import lombok.var;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/** 动态实现删除sql
 * @author gdl
 * @date 2020/1/6 18:08
 * @company 矽甲（上海）信息科技有限公司
 */
public class DynamicDeleteProvider {
    //删除sql
    private static final String DELETE_SQL_PRE = "DELETE FROM ";
    private static final String UPDATE_SQL_PRE = "UPDATE ";

    public String deleteById(String viewName,Integer deleteId){
        StringBuilder sql = new StringBuilder();
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        //不需要视图，直接使用表名称
        String tableName = pageViewInfo.getTable();
        if(SeageUtils.isEmpty(deleteId)){
            throw new PageViewException("删除记录主键不能为空");
        }
        List<IFunctionInfo> functionInfos = pageViewInfo.get(FunctionType.DELETE);
        //暂时仅支持根据主键删除，即删除的字段只能是主键，有且仅有一个
        if (CollectionUtils.isEmpty(functionInfos)){
            throw new PageViewException("实体类未标注删除主键");
        }
        var iFunctionInfo = functionInfos.get(0);
        DeleteInfo deleteInfo = (DeleteInfo) iFunctionInfo;
        //拼接sql
        //删除
        if(deleteInfo.getDeleteType().equals(DeleteConstants.DELETE_TYPE_DELETE)){
            if(SeageUtils.isEmpty(deleteInfo.getColumnName())){
                throw new PageViewException("主键名称不能为空");
            }
            sql.append(DELETE_SQL_PRE)
                    .append(tableName)
                    .append(" WHERE ")
                    .append(deleteInfo.getColumnName())
                    .append(Condition.EQ.getCondition())
                    .append(deleteId);
        }else if(deleteInfo.getDeleteType().equals(DeleteConstants.DELETE_TYPE_UPDATE)){
            //修改
            if(SeageUtils.isEmpty(deleteInfo.getColumnName())){
                throw new PageViewException("主键名称不能为空");
            }
            if(SeageUtils.isEmpty(deleteInfo.getStatusName())){
                throw new PageViewException("修改状态名称不能为空");
            }
            sql.append(UPDATE_SQL_PRE)
                    .append(tableName)
                    .append(" SET ")
                    .append(deleteInfo.getStatusName())
                    .append(Condition.EQ.getCondition())
                    .append(DeleteConstants.DELETE_STATUS_DELETE)
                    .append(" WHERE ")
                    .append(deleteInfo.getColumnName())
                    .append(Condition.EQ.getCondition())
                    .append(deleteId);
        }else {
            throw new PageViewException("删除类型输入错误");
        }
        return sql.toString();
    }
}
