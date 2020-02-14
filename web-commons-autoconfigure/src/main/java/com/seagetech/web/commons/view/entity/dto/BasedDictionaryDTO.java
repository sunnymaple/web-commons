package com.seagetech.web.commons.view.entity.dto;

import com.seagetech.web.commons.bind.OptionType;
import com.seagetech.web.commons.bind.annotation.Option;
import com.seagetech.web.commons.bind.annotation.PageView;
import com.seagetech.web.commons.bind.annotation.PrimaryKey;
import com.seagetech.web.commons.bind.annotation.Query;
import com.seagetech.web.commons.view.DefaultViewName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wangzb
 * @date 2020/1/19 14:54
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@PageView(value = DefaultViewName.DICTIONARY,table = "tb_based_dictionary",tableId = "dictionary_id",viewPath = "system/dictionary")
public class BasedDictionaryDTO {
    /**
     * 字典表注解ID
     */
    @PrimaryKey
    private Integer dictionaryId;

    /**
     * 字典key
     */
    @Query
    private String dicKey;

    /**
     * 字典ID
     */
    @Option(OptionType.KEY)
    private String dicId;

    /**
     * 字典名称
     */
    @Option(OptionType.VALUE)
    private String dicName;

    /**
     * 字典排序，对相同字典key字段的排序
     */
    private Integer dicOrder;

    /**
     * 描述
     */
    private String detail;
}
