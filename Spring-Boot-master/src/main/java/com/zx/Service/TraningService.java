package com.zx.Service;

import com.github.pagehelper.PageHelper;
import com.zx.Dao.TraningMapper;
import com.zx.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TraningService {
	@Autowired
	private TraningMapper traningMapper;

	public Integer getCountofTraningByCond(OnlineTraning onlineTraning){
		return traningMapper.getCountOfTraning(onlineTraning);
	}

	public List<OnlineTraning> getListofTraning(Pageinfo pageinfo){
		return traningMapper.getListOfTraning(pageinfo);
	}

	public Integer saveOnlineTraning(OnlineTraning onlineTraning){
	    return traningMapper.addTraningInfo(onlineTraning);
    }


	public OnlineTraning getOnlineTraningByid(Integer id){
		return traningMapper.getTraningInfoByid(id);
	}

	public Integer updateTraningInfo(OnlineTraning onlineTraning){
		return traningMapper.updateTraningInfo(onlineTraning);
	}
	/**
	 * 获取在线培训信息
	 * @param id
	 * @return
	 */
	public OnlineTraning getTraningByid(Integer id){
		OnlineTraning onlineTraning=traningMapper.getTraningInfoByid(id);
		if(onlineTraning!=null){
			List<TraningQuestion> list = traningMapper.getQuestioninfoByArticalId(id);
			for(TraningQuestion traningQuestion:list){
				List<TraningAnswer> answerList = traningMapper.getAnswerbyQuestionId(traningQuestion);
				traningQuestion.setAnswers(answerList);
			}
			Collections.sort(list, new Comparator<TraningQuestion>() {
				@Override
				public int compare(TraningQuestion o1, TraningQuestion o2) {
					return o1.getSeqNo()-o2.getSeqNo();
				}
			});
			onlineTraning.setQuestionList(list);
			//阅读数加一
			traningMapper.addReadNumbyTraningId(id);
		}
		return onlineTraning;
	}

	public void addTraningQuestion(TraningQuestion traningQuestion){
		traningMapper.addTraningQuestion(traningQuestion);
	}

	public void batchaddTraningAnswer(ArrayList<TraningAnswer> list){
		traningMapper.BatchAddQuestionAnswer(list);
	}

	public void saveExamination(Examination examination){
		traningMapper.saveExamation(examination);
	}

	public void batchSaveAnswer(List<Answer> list){
		traningMapper.BatchAddAnswer(list);
	}

	public void saveQuestion(Question question){
		traningMapper.saveQuestion(question);
	}


	public HashMap<String,String> getQuestionAndAnswer(String examId){
		List<Question> list = traningMapper.getAnswerInfoByExamId(examId);
		HashMap<String,String> map=new HashMap<String, String>();
		for (Question question : list){
            if(!map.containsKey(question.getId())){
                map.put(question.getId(),question.getQuestionAnswer());
            }
		}
		return map;
	}


	public HashMap<String,String> getClassQuestionAndAnswer(Integer traningId){
        List<TraningQuestion> list = traningMapper.getQuestioninfoByArticalId(traningId);
        HashMap<String,String> map=new HashMap<String, String>();
        for (TraningQuestion traningQuestion : list){
            if(!map.containsKey(traningQuestion.getId())){
                map.put(traningQuestion.getId(),traningQuestion.getAnswer());
            }
        }
		return map;
	}
	public void saveUserExam(UserExam userExam){
	    traningMapper.addUserExamInfo(userExam);
    }

    public Integer getCountExam(){
	    return traningMapper.getCountOfExam();
    }

    public List<Examination> getListofExam(Pageinfo pageinfo){
	    return traningMapper.getListofExamInfo(pageinfo);
    }

    public List<Question> getlistofQuestion(String examId){
	    List<Question> list = traningMapper.getQuestionByExamId(examId);
        for (Question question : list){
            question.setAnswers(traningMapper.getAnswerbyQid(question.getId()));
        }
	    return list;
    }

	/**
	 * 删除试卷
	 * @param examId
	 */
	@Transactional(propagation = Propagation.REQUIRED)
    public void deleteExamination(String examId){
		//答案列表
		LinkedList<String> linkedList= new LinkedList<String>();
		List<Question> list = traningMapper.getQuestionByExamId(examId);
		for(Question question : list){
			List<String> list1= traningMapper.getAnswerIdByQid(question.getId());
			if(list1.size()>0){
				linkedList.addAll(list1);
			}
		}
		traningMapper.delExamationByid(examId);
		//删除问题
		traningMapper.delQuestionById(examId);
		if(linkedList.size()>0){
            traningMapper.BatchdelAnswerById(linkedList);
        }
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteQuestion(String qid){
		//答案列表
		LinkedList<String> linkedList= new LinkedList<String>();
		List<String> list1= traningMapper.getAnswerIdByQid(qid);
		if(list1.size()>0){
			linkedList.addAll(list1);
		}
		traningMapper.delQuetionByQid(qid);
		if(linkedList.size()>0){
            traningMapper.BatchdelAnswerById(linkedList);
        }
	}

    /**
     * 更具ID查询对应的 问题信息
     * @param id
     * @return
     */
	public List<TraningQuestion> getlistofTraningWithAnswer(Integer id){
	    List<TraningQuestion> list = traningMapper.getQuestioninfoByArticalId(id);
	    for (TraningQuestion traningQuestion : list){
            List<TraningAnswer> answerList = traningMapper.getAnswerbyQuestionId(traningQuestion);
            traningQuestion.setAnswers(answerList);
        }
	    return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delTraningQuestions(String id){
	    traningMapper.delTraningQuestionByid(id);
	    traningMapper.delTraningAnswerByid(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteOnlineTraningByid(Integer id){
		List<TraningQuestion> list = traningMapper.getQuestioninfoByArticalId(id);
		for (TraningQuestion traningQuestion : list){
			traningMapper.delTraningAnswerByid(traningQuestion.getId());
		}
		traningMapper.delTraningQuestionBytraningId(id);
		traningMapper.deleteOnlineTraningByid(id);
	}

	public void addMessageList(LinkedList<UserMessage> linkedList){
		traningMapper.addMessage(linkedList);
	}

	public void addOneMessage(UserMessage userMessage){
		traningMapper.addOneMessage(userMessage);
	}

	public List<User> getUserList(){
		return traningMapper.getUserList();
	}

	public void updateUnFavorite(Integer id){
		traningMapper.updateUnFavorite(id);
	}

	public void updateFavorite(Integer id){
		traningMapper.updateFavorite(id);
	}

	public Integer getCountFt(){
		return traningMapper.getFavoriteTraningCount();
	}

	public List<OnlineTraning> getListFt(Pageinfo pageinfo){
		return traningMapper.getFavoriteTraningList(pageinfo);
	}

	public Integer addPushMessage(UserMessage userMessage){
		//将所有 推送消息置为已读状态
		traningMapper.updatePushMessage();
		//添加消息
		return traningMapper.addPushMessage(userMessage);
	}

	public UserMessage getPushMessage(){
		return traningMapper.getPushMessage();
	}

	public Integer getCountofPushMessage(){
		return traningMapper.getCountPushMessage();
	}

	public List<UserMessage> getListofPushMessage(Pageinfo pageinfo){
		PageHelper.startPage(pageinfo.getPagenum(),pageinfo.getShownum());
		return traningMapper.getListofPushMessage();
	}
}
