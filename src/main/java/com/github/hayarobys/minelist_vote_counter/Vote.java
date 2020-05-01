package com.github.hayarobys.minelist_vote_counter;

import java.util.Date;

/**
 * 추천 정보 클래스입니다.
 * @author hayarobys
 *
 */
public class Vote{

	/** 추천자 */
	protected String name;

	/** 추천 시간 */
	protected Date date;

	/** 추천 고유 번호 */
	protected String serialNo;

	/**
	 * 추천 정보
	 * 
	 * @param name
	 *            추천자
	 * @param date
	 *            추천 시간
	 * @param serialNo
	 *            추천 고유 번호
	 */
	public Vote(String name, Date date, String serialNo){
		super();
		this.name = name;
		this.date = date;
		this.serialNo = serialNo;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public Date getDate(){
		return date;
	}

	public void setDate(Date date){
		this.date = date;
	}

	public String getSerialNo(){
		return serialNo;
	}

	public void setSerialNo(String serialNo){
		this.serialNo = serialNo;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Vote [name=").append(name).append(", date=").append(date).append(", serialNo=").append(serialNo)
				.append("]");
		return builder.toString();
	}

}
