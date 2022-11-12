package org.pineconealliance.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台标签
 *
 * @Author Squ1rrel
 * @Date 2022/08/01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagBackDTO {
    /**
     * 标签id
     */
    private Integer id;
    /**
     * 标签名
     */
    private String tagName;
    /**
     * 文章量
     */
    private Integer articleCount;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
