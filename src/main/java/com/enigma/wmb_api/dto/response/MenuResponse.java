package com.enigma.wmb_api.dto.response;

import com.enigma.wmb_api.entity.MImage;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse {
    private String id;
    private String menu;
    private Long price;
    private ImageResponse image;
}