package com.seagetech.web.commons.view.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** 日志操作类
 * @author gdl
 * @date 2020/1/6 14:17
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor()
public class LogOperateVo {
    /**
     * 操作类型
     */
    private Integer operateType;
    /**
     * 操作描述
     */
    private String operateDetail;
    /**
     * 创建人
     */
    private Integer createUserId;
    /**
     * 创建时间
     */
    private String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}
