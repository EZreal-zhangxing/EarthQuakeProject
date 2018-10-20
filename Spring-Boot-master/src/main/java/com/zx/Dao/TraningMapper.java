package com.zx.Dao;

import com.zx.Pojo.*;

import java.util.ArrayList;
import java.util.List;

public interface TraningMapper {

    Integer getCountOfTraning(OnlineTraning onlineTraning);

    List<OnlineTraning> getListOfTraning(Pageinfo pageinfo);

    Integer addTraningInfo(OnlineTraning onlineTraning);

    List<OnlineTraning> getListofinfoBytype(Pageinfo pageinfo);

    OnlineTraning getTraningInfoByid(Integer id);

    void addTraningQuestion(TraningQuestion traningQuestion);

    void BatchAddQuestionAnswer(ArrayList<TraningAnswer> list);

    List<TraningQuestion> getQuestioninfoByArticalId(Integer id);

    List<TraningAnswer> getAnswerbyQuestionId(TraningQuestion traningQuestion);

    void saveExamation(Examination examination);

    void BatchAddAnswer(List<Answer> list);

    void saveQuestion(Question question);

    List<Question> getAnswerInfoByExamId(String examId);

    void addUserExamInfo(UserExam userExam);

    Integer getCountOfExam();

    List<Examination> getListofExamInfo(Pageinfo pageinfo);

    List<Question> getQuestionByExamId(String examId);

    List<Answer> getAnswerbyQid(String questionId);

    void addReadNumbyTraningId(Integer id);
}
