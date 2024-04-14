package org.example.service.obj.difyBo.user.history;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHistoryBo {


    private int limit;

    private boolean isEnabled;
    private List<TalkData> data;



}
