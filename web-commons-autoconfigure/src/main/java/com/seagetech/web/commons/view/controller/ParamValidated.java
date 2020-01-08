package com.seagetech.web.commons.view.controller;

import java.lang.annotation.*;

/**
 * 参数验证
 * @author wangzb
 * @date 2020/1/8 12:33
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface ParamValidated {
}
