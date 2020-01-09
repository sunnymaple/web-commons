package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.view.load.PageViewLoadRegistrar;
import com.seagetech.web.commons.view.load.PageViewProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启PageView功能
 * @author wangzb
 * @date 2019/12/23 11:13
 * @company 矽甲（上海）信息科技有限公司
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(PageViewLoadRegistrar.class)
@EnableConfigurationProperties(PageViewProperties.class)
public @interface EnablePageView {
    /**
     * 定义pageView扫描的包
     * 如果不指定，则扫描使用该注解类所在的包以及子包
     * @return
     */
    String[] scanBasePackages() default {};
}
