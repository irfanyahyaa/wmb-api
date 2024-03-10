package com.enigma.wmb_api.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse<T> {
    private Integer statuscode;
    private String message;
    private T data;
    private PagingResponse paging;
}