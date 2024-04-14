package org.example.service.obj.difyBo.answer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetrieverResource {

    private int position;
    private String datasetId;
    private String datasetName;
    private String documentId;
    private String documentName;
    private String dataSourceType;
    private String segmentId;
    private String retrieverFrom;
    private double score;
    private String content;
}
