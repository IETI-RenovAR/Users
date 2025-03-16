package org.adaschool.project.model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Setter
@Getter
public class Design {
    @Id
    private String id;
    private String userId;
    private String name;
    private String url;
    @JsonProperty("isPublic")
    private boolean isPublic;
    private String state;
    private boolean searchingCarpenter;
    private List<Quotation> quotations;
}
