package com.example.StationMisyullaeng.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequestDto { //메뉴 등록용 DTO
    private String menuName;
    private Integer price;
    private String menuDescription;
    private String imgUrl;
    private Long storeId;
}
