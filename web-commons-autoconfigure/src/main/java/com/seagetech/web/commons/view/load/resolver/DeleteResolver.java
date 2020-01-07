package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.annotation.Delete;
import com.seagetech.web.commons.view.load.DeleteInfo;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 删除注解 获取参数
 * @author gdl
 * @date 2020/1/6 16:48
 * @company 矽甲（上海）信息科技有限公司
 */
public class DeleteResolver extends AbstractResolver<Delete, DeleteInfo> {
    @Override
    public List<DeleteInfo> resolver() {
        DeleteInfo deleteInfo = DeleteInfo
                .builder()
                .build()
                .setDeleteType(annotation.deleteType())
                .setColumnName(annotation.columnName())
                .setStatusName(annotation.statusName());
        return Stream.of(deleteInfo).collect(Collectors.toList());
    }
}
