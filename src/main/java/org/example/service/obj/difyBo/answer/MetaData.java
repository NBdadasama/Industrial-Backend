package org.example.service.obj.difyBo.answer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {


    private List<RetrieverResource> retrieverResources;
    private Usage usage;


}
