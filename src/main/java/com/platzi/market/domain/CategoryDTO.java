package com.platzi.market.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDTO {
    private int categoryId;
    private String category;
    private boolean active;

}
