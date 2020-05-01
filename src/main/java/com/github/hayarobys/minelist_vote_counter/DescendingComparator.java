package com.github.hayarobys.minelist_vote_counter;

import java.util.Comparator;

/**
 * "30 = hayarobys" 형식으로 들어오는 데이터에서 숫자부분만 떼어 비교정렬하기 위한 컴퍼레이터 입니다.
 * @author hayarobys
 *
 */
public class DescendingComparator implements Comparator<String>{
	@Override
	public int compare(String a, String b){
		// "30 = hayarobys"
		Integer a2 = Integer.parseInt(a.split("회:")[0].trim());
		Integer b2 = Integer.parseInt(b.split("회:")[0].trim());
		return b2.compareTo(a2);
	}
}
