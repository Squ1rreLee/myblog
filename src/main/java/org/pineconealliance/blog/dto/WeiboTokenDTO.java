package org.pineconealliance.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微博token
 *
 * @Author Squ1rrel
 * @Date 2022/06/14
 * @since 1.0.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeiboTokenDTO {
    /**
     * 微博uid
     */
    private String uid;
    /**
     * 访问令牌
     */
    private String access_token;
}
