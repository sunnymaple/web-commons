package com.seagetech.web.commons.view.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wangzb
 * @since 2020-01-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_based_dictionary")
public class BasedDictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典表注解ID
     */
    @TableId(value = "dictionary_id", type = IdType.AUTO)
    private Integer dictionaryId;

    /**
     * 字典key
     */
    private String dicKey;

    /**
     * 字典ID
     */
    private String dicId;

    /**
     * 字典名称
     */
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
