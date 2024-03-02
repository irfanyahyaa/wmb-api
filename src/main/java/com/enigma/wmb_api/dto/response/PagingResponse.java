package com.enigma.wmb_api.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse {
    private Integer totalPages;

    private Long totalElements;

    private Integer page;

    private Integer size;

    private Boolean hasNext;

    private Boolean hasPrevious;
}