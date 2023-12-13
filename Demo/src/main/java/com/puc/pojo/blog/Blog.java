package com.puc.pojo.blog;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Blog implements Serializable {

    private Integer id;
    private Integer userId;
    private String title;

}
