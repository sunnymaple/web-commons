package com.seagetech.web.commons.view.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 配置类
 * @author wangzb
 * @date 2019/12/26 10:07
 * @company 矽甲（上海）信息科技有限公司
 */
@Configuration
@MapperScan("com.seagetech.web.commons.view.mapper")
public class Config {

    @Autowired
    private MybatisPlusProperties mybatisPlusProperties;

    /**
     * 初始化操作
     * 1、设置驼峰命名
     * 2、null时不显示变量
     * 3、驼峰创建规则
     * @return
     */
    @PostConstruct
    public void init(){
        MybatisConfiguration configuration = mybatisPlusProperties.getConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);
        configuration.setObjectWrapperFactory(new MapWrapperFactory());
    }
}
