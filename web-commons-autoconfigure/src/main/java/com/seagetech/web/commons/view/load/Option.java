package com.seagetech.web.commons.view.load;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author gdl
 * @date 2020/1/9 15:23
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Option {

    private String key;

    private String value;
}
