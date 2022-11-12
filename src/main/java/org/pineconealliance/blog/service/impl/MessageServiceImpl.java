package org.pineconealliance.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pineconealliance.blog.constant.CommonConst;
import org.pineconealliance.blog.dao.MessageDao;
import org.pineconealliance.blog.dto.MessageBackDTO;
import org.pineconealliance.blog.dto.MessageDTO;
import org.pineconealliance.blog.entity.Message;
import org.pineconealliance.blog.service.BlogInfoService;
import org.pineconealliance.blog.service.MessageService;
import org.pineconealliance.blog.util.BeanCopyUtils;
import org.pineconealliance.blog.util.HTMLUtils;
import org.pineconealliance.blog.util.IpUtils;
import org.pineconealliance.blog.util.PageUtils;
import org.pineconealliance.blog.vo.ConditionVO;
import org.pineconealliance.blog.vo.MessageVO;
import org.pineconealliance.blog.vo.PageResult;
import org.pineconealliance.blog.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 留言服务
 *
 * @Author Squ1rrel
 * @Date 2022/07/28
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements MessageService {
    @Autowired
    private MessageDao messageDao;
    @Resource
    private HttpServletRequest request;
    @Autowired
    private BlogInfoService blogInfoService;

    @Override
    public void saveMessage(MessageVO messageVO) {
        // 判断是否需要审核
        Integer isReview = blogInfoService.getWebsiteConfig().getIsMessageReview();
        // 获取用户ip
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        Message message = BeanCopyUtils.copyObject(messageVO, Message.class);
        message.setMessageContent(HTMLUtils.filter(message.getMessageContent()));
        message.setIpAddress(ipAddress);
        message.setIsReview(isReview == CommonConst.TRUE ? CommonConst.FALSE : CommonConst.TRUE);
        message.setIpSource(ipSource);
        messageDao.insert(message);
    }

    @Override
    public List<MessageDTO> listMessages() {
        // 查询留言列表
        List<Message> messageList = messageDao.selectList(new LambdaQueryWrapper<Message>()
                .select(Message::getId, Message::getNickname, Message::getAvatar, Message::getMessageContent,
                        Message::getTime)
                .eq(Message::getIsReview, CommonConst.TRUE));
        return BeanCopyUtils.copyList(messageList, MessageDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMessagesReview(ReviewVO reviewVO) {
        // 修改留言审核状态
        List<Message> messageList = reviewVO.getIdList().stream().map(item -> Message.builder()
                        .id(item)
                        .isReview(reviewVO.getIsReview())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(messageList);
    }

    @Override
    public PageResult<MessageBackDTO> listMessageBackDTO(ConditionVO condition) {
        // 分页查询留言列表
        Page<Message> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        LambdaQueryWrapper<Message> messageLambdaQueryWrapper = new LambdaQueryWrapper<Message>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Message::getNickname, condition.getKeywords())
                .eq(Objects.nonNull(condition.getIsReview()), Message::getIsReview, condition.getIsReview())
                .orderByDesc(Message::getId);
        Page<Message> messagePage = messageDao.selectPage(page, messageLambdaQueryWrapper);
        // 转换DTO
        List<MessageBackDTO> messageBackDTOList = BeanCopyUtils.copyList(messagePage.getRecords(),
                MessageBackDTO.class);
        return new PageResult<>(messageBackDTOList, (int)messagePage.getTotal());
    }
}
