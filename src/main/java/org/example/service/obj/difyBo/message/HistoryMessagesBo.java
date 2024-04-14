package org.example.service.obj.difyBo.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryMessagesBo {



    private int limit;

    private boolean hasMore;

    private List<MessageData> data;





}
