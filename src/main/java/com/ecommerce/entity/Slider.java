package com.ecommerce.entity;

import java.sql.Timestamp;
import lombok.*;


/**
 * This is a sample class to demonstrate author annotation.
 * @author TeKu.Tran
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Slider {
    
    private Integer id;

    private String sliderImage;

    private String sliderUrl;

    private String sliderTitle;

    private String sliderDescription;

    private Boolean isDeleted;

    private Timestamp createdAt;

    private String createdBy;

    private Timestamp updatedAt;

    private String updatedBy;
}