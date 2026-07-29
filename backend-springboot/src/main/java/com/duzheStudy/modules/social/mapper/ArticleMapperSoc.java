package com.duzheStudy.modules.social.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duzheStudy.modules.social.controller.dto.ArticleDto;
import com.duzheStudy.modules.social.controller.dto.ArticleQuery;
import com.duzheStudy.modules.social.controller.dto.CommentDto;
import com.duzheStudy.modules.social.domain.ArticleSoc;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapperSoc extends BaseMapper<ArticleSoc> {



    //删除说说
    @Delete("UPDATE soc_article\n" +
            "SET is_deleted=1\n" +
            "WHERE id=#{id}")
    int deleteArticle(String id);
    @Select("SELECT a.id as id,u.user_id as userId,u.avatar as avatar,u.nick_name as `name`,a.publish_time as publishTime,a.content as content,a.img_list as imgList,a.view_num as viewNum,a.authority as authority \n" +
            "FROM soc_article a,sys_user u \n" +
            "WHERE a.user_id=u.user_id AND a.is_deleted=0 AND u.user_id=#{personId}\n" +
            "${condition}"+
            "ORDER BY id DESC LIMIT 10")
    List<ArticleDto> selectPersonAllArticles(ArticleQuery articleQuery);

    @Select("SELECT user_id\n" +
            "FROM soc_comment_like\n" +
            "where comment_id=#{commentId} and is_deleted=0")
    List<Long> selectCommentLikeUserId(String commentId);
    @Select("SELECT c.id as id,c.user_id as user_id,u.nick_name as nickname,u.avatar as avatar,c.content as content,c.publish_time as publishTime\n" +
            "FROM soc_comment c,sys_user u\n" +
            "WHERE c.user_id=u.user_id \n" +
            "AND c.is_deleted=0 AND c.article_id=#{articleId}\n" +
            "ORDER BY c.id ASC")
    List<CommentDto> selectComments(ArticleQuery articleQuery);

    @Select("SELECT c.id as id,c.user_id as user_id,u.nick_name as nickname,u.avatar as avatar,c.content as content,c.publish_time as publishTime " +
            "FROM soc_comment c,sys_user u " +
            "WHERE c.user_id=u.user_id AND c.id=#{commentId}")
    CommentDto selectOneComment(String commentId);
    @Update("UPDATE soc_article\n" +
            "SET view_num=view_num+1\n" +
            "WHERE id=#{articleId}")
    void addViewNum(String articleId);
    @Select("SELECT a.id as id,u.user_id as userId,u.avatar as avatar,u.nick_name as `name`,a.publish_time as publishTime,a.content as content,a.img_list as imgList,a.view_num as viewNum " +
            "FROM soc_article a,sys_user u " +
            "WHERE a.user_id=u.user_id AND a.id=#{articleId}")
    ArticleDto selectOneArticle(String articleId);
    ///全世界 查询最新的记录
    @Select("SELECT a.id as id,u.user_id as userId,u.avatar as avatar,u.nick_name as `name`,a.publish_time as publishTime,a.content as content,a.img_list as imgList,a.view_num as viewNum " +
            "FROM soc_article a,sys_user u " +
            "WHERE a.user_id=u.user_id AND a.is_deleted=0 " +
            "AND(a.user_id=#{userId} OR a.authority=0 OR(a.authority=1 AND a.user_id IN( " +
            "SELECT friend_id FROM soc_friend WHERE my_id=#{userId} AND is_deleted=0 " +
            "))) " +
            "ORDER BY a.id DESC LIMIT 10")
    List<ArticleDto> selectLast(ArticleQuery articleQuery);

    ///全世界 查询历史记录
    @Select("SELECT a.id as id,u.user_id as userId,u.avatar as avatar,u.nick_name as `name`,a.publish_time as publishTime,a.content as content,a.img_list as imgList,a.view_num as viewNum\n" +
            "FROM soc_article a,sys_user u\n" +
            "WHERE a.user_id=u.user_id AND a.is_deleted=0\n" +
            "AND(a.user_id=#{userId} OR a.authority=0 OR(a.authority=1 AND a.user_id IN(\n" +
            "\tSELECT friend_id FROM soc_friend WHERE my_id=#{userId} AND is_deleted=0\n" +
            ")))\n" +
            "AND a.id<#{indexId}\n" +
            "ORDER BY a.id DESC LIMIT 10")
    List<ArticleDto> selectHistory(ArticleQuery articleQuery);
    ///仅好友 查询最新记录
    @Select("SELECT a.id as id,u.user_id as userId,u.avatar as avatar,u.nick_name as `name`,a.publish_time as publishTime,a.content as content,a.img_list as imgList,a.view_num as viewNum\n" +
            "FROM soc_article a,sys_user u\n" +
            "WHERE a.user_id=u.user_id AND a.is_deleted=0\n" +
            "AND (a.user_id=#{userId} OR a.user_id IN(\n" +
            "\tSELECT friend_id FROM soc_friend WHERE my_id=#{userId} AND is_deleted=0\n" +
            "))\n" +
            "ORDER BY a.id DESC LIMIT 10\n")
    List<ArticleDto> selectFriendLast(ArticleQuery articleQuery);
    ///仅好友 查询最新记录
    @Select("SELECT a.id as id,u.user_id as userId,u.avatar as avatar,u.nick_name as `name`,a.publish_time as publishTime,a.content as content,a.img_list as imgList,a.view_num as viewNum\n" +
            "FROM soc_article a,sys_user u\n" +
            "WHERE a.user_id=u.user_id AND a.is_deleted=0\n" +
            "AND(a.user_id=#{userId} OR  a.user_id IN(\n" +
            "\tSELECT friend_id FROM soc_friend WHERE my_id=#{userId} AND is_deleted=0\n" +
            "))\n" +
            "AND a.id<#{indexId}\n" +
            "ORDER BY a.id DESC LIMIT 10\n")
    List<ArticleDto> selectFriendHistory(ArticleQuery articleQuery);

    @Select("SELECT user_id\n" +
            "FROM soc_comment\n" +
            "where article_id=#{articleId} and is_deleted=0")
    List<Long> selectCommentUserId(String articleId);

    @Select("SELECT user_id\n" +
            "FROM soc_article_like\n" +
            "where article_id=#{articleId} and is_deleted=0")
    List<Long> selectLikeUserId(String articleId);

    //取消点赞
    @Update("UPDATE soc_article_like\n" +
            "set is_deleted=1\n" +
            "where user_id=#{userId} and article_id=#{articleId}")
    int cancelArticleLike(ArticleQuery articleQuery);

    @Update("UPDATE soc_comment_like\n" +
            "set is_deleted=1\n" +
            "where comment_id=#{commentId}")
    int cancelCommentLike(ArticleQuery articleQuery);

}
