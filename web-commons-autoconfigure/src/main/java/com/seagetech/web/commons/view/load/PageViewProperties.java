package com.seagetech.web.commons.view.load;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 自动配置类
 * @author wangzb
 * @date 2020/1/8 17:06
 * @company 矽甲（上海）信息科技有限公司
 */
@ConfigurationProperties(prefix = "page.view")
@Data
public class PageViewProperties implements ApplicationContextAware {

    private static PageViewProperties pageViewProperties;

    /**
     * 默认密码
     */
    private String defaultPassword = "0000";

    public static PageViewProperties getInstance(){
        return pageViewProperties;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        pageViewProperties = applicationContext.getBean(PageViewProperties.class);
    }
}
