package org.example.service.obj.difyBo.answer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usage {


    private int prompt_tokens;
    private BigDecimal prompt_unit_price; // 使用BigDecimal来处理货币值
    private BigDecimal prompt_price_unit; // 通常单位价格只有一个，这里可能是个错误，需要确认
    private BigDecimal prompt_price;
    private int completion_tokens;
    private BigDecimal completion_unit_price;
    private BigDecimal completion_price_unit; // 同上，可能是个错误
    private BigDecimal completion_price;
    private int total_tokens;
    private BigDecimal total_price;
    private String currency;
    private double latency; // 延迟通常使用double或float表示，但具体取决于你的需求精度



}
