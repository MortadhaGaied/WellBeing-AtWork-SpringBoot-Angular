package com.wellbeignatwork.backend.model;

public class Result {
	int id;
	int quizId;
	int studentId;
	int totalMarks;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuizId() {
		return quizId;
	}
	public void setQuizID(int quizId) {
		this.quizId = quizId;
	}
	public Result(int quizId, int studentId, int totalMarks) {
		super();
		this.quizId = quizId;
		this.studentId = studentId;
		this.totalMarks = totalMarks;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}
}
