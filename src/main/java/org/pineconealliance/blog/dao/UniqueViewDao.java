package org.pineconealliance.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.pineconealliance.blog.dto.UniqueViewDTO;
import org.pineconealliance.blog.entity.UniqueView;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 访问量
 *
 * @Author xiaojie
 * @Date 2022/08/10
 * @since 2020-05-18
 */
@Repository
public interface UniqueViewDao extends BaseMapper<UniqueView> {
    /**
     * 获取7天用户量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 用户量
     */
    List<UniqueViewDTO> listUniqueViews(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
