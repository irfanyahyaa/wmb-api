package com.enigma.wmb_api.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {
    private String url;
    private String name;

    // optional klo error responsenya pke ini
    /*@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("id")
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("path")
    private String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("size")
    private Long size;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("contentType")
    private String contentType;*/

}