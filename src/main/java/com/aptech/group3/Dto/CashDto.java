package com.aptech.group3.Dto;

import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CashDto {

    private Long userId;
    private List<Long> subjectId ;
    
    

}