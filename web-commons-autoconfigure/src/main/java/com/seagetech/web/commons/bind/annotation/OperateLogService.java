package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.view.entity.LogOperateVo;
import org.springframework.stereotype.Component;

/** 日志操作超类
 * @author gdl
 * @date 2020/1/6 14:07
 * @company 矽甲（上海）信息科技有限公司
 */
@Component
public interface OperateLogService {
    void logOperate(LogOperateVo logOperateVo);
}
