package org.pineconealliance.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访问量
 *
 * @Author Squ1rrel
 * @Date 2022/08/01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniqueViewDTO {
    /**
     * 日期
     */
    private String day;
    /**
     * 访问量
     */
    private Integer viewsCount;
}
