package com.zx.Dao;

import com.zx.Pojo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public interface TraningMapper {

    Integer getCountOfTraning(OnlineTraning onlineTraning);

    List<OnlineTraning> getListOfTraning(Pageinfo pageinfo);

    Integer CheckIsExistInCollection(HashMap hashMap);

    Integer addTraningInfo(OnlineTraning onlineTraning);

    Integer updateTraningInfo(OnlineTraning onlineTraning);

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

    List<String> getAnswerIdByQid(String questionId);

    void addReadNumbyTraningId(Integer id);

    void addCollectionNumbyTraningId(Integer id);

    void reduceCollectionNumbyTraningId(Integer id);

    void delExamationByid(String examId);

    void delQuestionById(String examId);

    void delQuetionByQid(String examId);

    void BatchdelAnswerById(LinkedList linkedList);

    void delTraningQuestionByid(String id);

    void delTraningAnswerByid(String id);

    void deleteOnlineTraningByid(Integer id);

    void delTraningQuestionBytraningId(Integer id);

    void updateFavorite(Integer id);

    void updateUnFavorite(Integer id);

    Integer getFavoriteTraningCount();

    List<OnlineTraning> getFavoriteTraningList(Pageinfo pageinfo);

    void addMessage(LinkedList<UserMessage> linkedList);

    void addOneMessage(UserMessage userMessage);

    List<User> getUserList();

    Integer addPushMessage(UserMessage userMessage);

    void updatePushMessage();

    UserMessage getPushMessage();

    Integer getCountPushMessage();

    List<UserMessage> getListofPushMessage();

}
