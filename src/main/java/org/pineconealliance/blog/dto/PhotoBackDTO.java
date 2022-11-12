package org.pineconealliance.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台照片
 *
 * @Author Squ1rrel
 * @Date 2022/08/05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoBackDTO {
    /**
     * 照片id
     */
    private Integer id;
    /**
     * 照片名
     */
    private String photoName;
    /**
     * 照片描述
     */
    private String photoDesc;
    /**
     * 照片地址
     */
    private String photoSrc;
}
