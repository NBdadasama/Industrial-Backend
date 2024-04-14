package org.example.service.obj.difyBo.next.question;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NestQuestionBo {


    private String result;
    private List<String> data;

}
