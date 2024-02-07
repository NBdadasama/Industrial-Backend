package org.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.obj1.AnswerQuestion;
import org.example.entity.obj1.BehavioralRecord;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Repository
public interface AnswerQuestionMapper extends BaseMapper<AnswerQuestion> {

    @Select("SELECT max(key_id) from answer_question where user_id= #{userId}")
    Integer findUserKey(String userId);

    @Select("SELECT * from answer_question where talk_id=#{talkId} ORDER BY number ASC")
    List<AnswerQuestion> selectAllByTalkId(String talkId);//查询所有

    @Select("SELECT * from answer_question where user_Id=#{userId} ORDER BY number ASC")
    List<AnswerQuestion> selectAllByUserId(String userId);//通过userId查询

    @Select("UPDATE answer_question SET show_or_not = 0 WHERE talk_id =#{talkId}")
    AnswerQuestion deleteByTalkId(String talkId);
}
