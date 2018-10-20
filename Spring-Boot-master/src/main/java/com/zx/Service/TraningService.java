package com.zx.Service;

import com.zx.Dao.TraningMapper;
import com.zx.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public List<OnlineTraning> getlistOfTraningByType(Pageinfo pageinfo){
		return traningMapper.getListofinfoBytype(pageinfo);
	}

	/**
	 * 获取在线培训信息
	 * @param id
	 * @return
	 */
	public OnlineTraning getTraningByid(Integer id){
		OnlineTraning onlineTraning=traningMapper.getTraningInfoByid(id);
		List<TraningQuestion> list = traningMapper.getQuestioninfoByArticalId(id);
		for(TraningQuestion traningQuestion:list){
			List<TraningAnswer> answerList = traningMapper.getAnswerbyQuestionId(traningQuestion);
			traningQuestion.setAnswers(answerList);
		}
		onlineTraning.setQuestionList(list);
		//阅读数加一
		traningMapper.addReadNumbyTraningId(id);
		return onlineTraning;
	}

	public void addTraningQuestion(TraningQuestion traningQuestion){
		traningMapper.addTraningQuestion(traningQuestion);
	}

	public void batchaddTraningAnswer(ArrayList<TraningAnswer> list){
		traningMapper.BatchAddQuestionAnswer(list);
	}

	public List<TraningQuestion> getlistofThingByTraning(Integer id){
		return traningMapper.getQuestioninfoByArticalId(id);
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
}
