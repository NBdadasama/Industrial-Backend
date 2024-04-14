package org.example.service.obj.difyBo.user.history;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalkData {

    private String id;
    private String name;
    private Map<String,String> inputs;
    private String status;
    private String createdAt;


}
