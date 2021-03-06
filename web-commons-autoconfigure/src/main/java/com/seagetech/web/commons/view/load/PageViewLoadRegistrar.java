package com.seagetech.web.commons.view.load;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.bind.annotation.EnablePageView;
import com.seagetech.web.commons.bind.annotation.PageView;
import com.seagetech.web.commons.bind.annotation.Resolver;
import com.seagetech.web.commons.util.Utils;
import com.seagetech.web.commons.view.exception.NotImplementsPageViewCustomException;
import com.seagetech.web.commons.view.load.exception.PageViewException;
import com.seagetech.web.commons.view.load.resolver.IResolver;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * PageView功能组件注册
 * @author wangzb
 * @date 2019/12/23 11:23
 * @company 矽甲（上海）信息科技有限公司
 */
public class PageViewLoadRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * 默认包路径
     */
    private final String DEFAULT_PACKAGE_NAME = "com.seagetech.web.commons.view";

    /**
     * Register bean definitions as necessary based on the given annotation metadata of
     * the importing {@code @Configuration} class.
     * <p>Note that {@link BeanDefinitionRegistryPostProcessor} types may <em>not</em> be
     * registered here, due to lifecycle constraints related to {@code @Configuration}
     * class processing.
     *
     * @param importingClassMetadata annotation metadata of the importing class
     * @param registry               current bean definition registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        //添加@Component注解的类到容器中
        findCandidateComponents(registry);

        //扫描的包
        AnnotationAttributes attributes = getAttributes(importingClassMetadata);
        String[] scanBasePackages = (String[]) attributes.get("scanBasePackages");
        List<String> scanBasePackageList = new ArrayList<>();
        scanBasePackageList.add(DEFAULT_PACKAGE_NAME + ".entity");
        scanBasePackageList.addAll(Arrays.asList(scanBasePackages));

        //添加视图数据
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(PageView.class));
        scanBasePackageList.forEach(scanBasePackage->addPageView(pageViewContainer, registry,provider,scanBasePackage));
    }

    /**
     * Return the appropriate {@link AnnotationAttributes} from the
     * {@link AnnotationMetadata}. By default this method will return attributes for
     * {@link #getAnnotationClass()}.
     * @param metadata the annotation metadata
     * @return annotation attributes
     */
    protected AnnotationAttributes getAttributes(AnnotationMetadata metadata) {
        String name = getAnnotationClass().getName();
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(name, true));
        Assert.notNull(attributes, () -> "No auto-configuration attributes found. Is " + metadata.getClassName()
                + " annotated with " + ClassUtils.getShortName(name) + "?");
        return attributes;
    }

    /**
     * Return the source annotation class used by the selector.
     * @return the annotation class
     */
    protected Class<?> getAnnotationClass() {
        return EnablePageView.class;
    }

    /**
     *
     * @param registry
     */
    private void findCandidateComponents(BeanDefinitionRegistry registry){
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        Optional<Set<BeanDefinition>> beanDefinitionSetOp = Optional.ofNullable(provider.findCandidateComponents(DEFAULT_PACKAGE_NAME));
        beanDefinitionSetOp.ifPresent(beanDefinitions -> {
            beanDefinitions.forEach(beanDefinition -> registry.registerBeanDefinition(beanDefinition.getBeanClassName(),beanDefinition));
        });
    }

    /**
     * 添加默认的视图页
     * @param pageViewContainer 视图信息容器
     */
    private void addPageView(PageViewContainer pageViewContainer,BeanDefinitionRegistry registry,ClassPathScanningCandidateComponentProvider provider,String basePackage){
        Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents(basePackage);
        beanDefinitionSet.forEach(beanDefinition -> {
            try {
                Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
                PageView pageView = beanClass.getAnnotation(PageView.class);
                //基本信息
                PageViewInfo pageViewInfo = PageViewInfo
                        .builder()
                        .build()
                        .setTable(pageView.table())
                        .setView(pageView.view())
                        .setViewName(pageView.value())
                        .setTableId(pageView.tableId())
                        .setPageViewClass(beanClass)
                        .setViewPath(pageView.viewPath())
                        .setRow(pageView.row())
                        .setExcelTemplatePath(pageView.excelTemplatePath());
                pageViewContainer.put(pageView.value(),pageViewInfo);
                //自定义
                FunctionType[] functionTypes = pageView.enableCustomFunctions();
                if (!SeageUtils.isEmpty(functionTypes)){
                    String customClassName = pageView.customClass();
                    Class customClass = Class.forName(customClassName);
                    boolean isImplementsIPageViewCustom = IPageViewCustom.class.isAssignableFrom(customClass);
                    if (!isImplementsIPageViewCustom){
                        throw new NotImplementsPageViewCustomException(customClassName);
                    }
                    Object bean = ((DefaultListableBeanFactory) registry).getBean(customClass);
                    pageViewInfo.setPageViewCustom((IPageViewCustom) bean);
                    pageViewInfo.setEnableCustomFunctions(functionTypes);
                }
                //加载功能
                addInfo(beanClass,pageViewInfo);
            } catch (Exception e) {
                throw new PageViewException(e);
            }
        });
    }

    /**
     * 添加info信息
     * @param beanClass  实体bean classs
     * @param pageViewInfo 页面视图信息
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void addInfo(Class<?> beanClass,PageViewInfo pageViewInfo) throws IllegalAccessException, InstantiationException {
        List<Field> fields = Utils.getFieldList(beanClass);
        for (Field field : fields) {
            //获取访问权限
            boolean accessFlag = field.isAccessible();
            //修改访问权限
            field.setAccessible(true);
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations){
                Resolver resolver = annotation.annotationType().getAnnotation(Resolver.class);
                if (resolver == null){
                    continue;
                }
                FunctionType functionType = resolver.functionType();
                Class<? extends IResolver> iResolver = resolver.resolverBy();
                //初始化
                IResolver resolverInstance = iResolver.newInstance();
                resolverInstance.initialize(annotation,field,pageViewInfo.getViewName());
                //解析
                List<IFunctionInfo> iFunctionInfo = resolverInstance.resolver();
                if (pageViewInfo.containsKey(functionType)){
                    pageViewInfo.get(functionType).addAll(iFunctionInfo);
                }else {
                    List<IFunctionInfo> iInfos = new ArrayList<>(SeageUtils.initialCapacity());
                    iInfos.addAll(iFunctionInfo);
                    pageViewInfo.put(functionType,iInfos);
                }
            }
            //恢复访问权限
            field.setAccessible(accessFlag);
        }
    }

}
