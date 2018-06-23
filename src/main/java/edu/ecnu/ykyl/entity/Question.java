package edu.ecnu.ykyl.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "t_question")
public class Question extends EntityId implements Serializable {
	
	@Column(name = "KPrelativeId")
	private Long KPrelativeId;

	@Column(name = "questionType", columnDefinition = "smallint")
	@Enumerated(EnumType.ORDINAL)
	private QuestionType questionType;

	@Column(name = "questionContent", length = 200, nullable = false)
	private String content;

	@Column(name = "std_answer", length = 100, nullable = false)
	private String stdAnswer;

	@Column(name = "questionAnalyse")
	private String quAnalyse;

	@JsonIgnore
	@OneToMany(mappedBy = "anQuestionId", cascade = { CascadeType.ALL })
	private Set<Answer> myAnswer = new HashSet<Answer>();

	@JsonIgnore
	@OneToMany(mappedBy = "opQestionId")
	// cascade={CascadeType.ALL},fetch=FetchType.EAGER
	// fetch=FetchType.LAZY 懒加载将option顺便加载了
	private List<Option> option = new ArrayList<Option>();

	@ManyToOne
	@JoinColumn(name = "qu_knowledge_point_id")
	private KnowledgePoint quKnowledgePointId;

	@ManyToOne
	@JoinColumn(name = "qu_label_id")
	private Label quLabelId;

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public String getQuAnalyse() {
		return quAnalyse;
	}

	public void setQuAnalyse(String quAnalyse) {
		this.quAnalyse = quAnalyse;
	}

	public Label getQuLabelId() {
		return quLabelId;
	}

	public void setQuLabelId(Label quLabelId) {
		this.quLabelId = quLabelId;
	}

	public KnowledgePoint getQuKnowledgePointId() {
		return quKnowledgePointId;
	}

	public void setQuKnowledgePointId(KnowledgePoint quKnowledgePointId) {
		this.quKnowledgePointId = quKnowledgePointId;
	}

	public String getStdAnswer() {
		return stdAnswer;
	}

	public void setStdAnswer(String stdAnswer) {
		this.stdAnswer = stdAnswer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<Answer> getMyAnswer() {
		return myAnswer;
	}

	public void setMyAnswer(Set<Answer> myAnswer) {
		this.myAnswer = myAnswer;
	}

	public List<Option> getOption() {
		return option;
	}

	public void setOption(List<Option> option) {
		this.option = option;
	}
	
	public Long getKPrelativeId() {
		return KPrelativeId;
	}

	public void setKPrelativeId(Long kPrelativeId) {
		KPrelativeId = kPrelativeId;
	}

	@Override
	public String toString() {
		return "Question [content=" + content + ", stdAnswer=" + stdAnswer
				+ ", myAnswer=" + myAnswer + ", option=" + option + "]";
	}

}