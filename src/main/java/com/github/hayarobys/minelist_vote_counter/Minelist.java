/**
 * 
 */
package com.github.hayarobys.minelist_vote_counter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 마인리스트로부터 특정 날짜 범위의 추천 데이터를 크롤링하여 순위 내는 프로그램입니다.
 * @author hayarobys
 *
 */
public class Minelist {
	public static void main(String[] args){
		
		long startTime = new Date().getTime();
		try{
			// 닉네임별 추천 이력
			Map<String, List<Vote>> voteDetailMap = Minelist.getServerVoteMapById("6500");
			
			// 닉네임별 추천 횟수
			Map<String, Integer> voteSimpleCountMap = new HashMap<String, Integer>();
			for(String key : voteDetailMap.keySet()){
				voteSimpleCountMap.put(key, voteDetailMap.get(key).size());
			}
			
			// 추천 랭킹별 닉네임
			List<String> voteRankingList = new ArrayList<String>();
			StringBuilder builder = new StringBuilder();
			for(String key : voteSimpleCountMap.keySet()){
				builder.append(voteSimpleCountMap.get(key));
				builder.append(" = ");
				builder.append(key);
				
				voteRankingList.add(builder.toString());
				builder.setLength(0);
			}
			Collections.sort(voteRankingList, new DescendingComparator());
			
			System.out.println("\n\n<결과>");
			for(String row : voteRankingList){
				System.out.println(row);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		long finishTime = new Date().getTime();
		long diffTime = finishTime - startTime;
		System.out.printf("\n\n작업소요시간: %.2f초", diffTime/1000f);
	}
	
	public static Map<String, List<Vote>> getServerVoteMapById(String id) throws IOException {
		Map<String, List<Vote>> voteMap = new HashMap<String, List<Vote>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// 시: 0~23, 분: 0~59, 초: 0~59
		String startDateText = "2020/04/01 00:00:00";
		Date startDate = null;
		try{
			startDate = sdf.parse(startDateText);
		}catch(ParseException e1){
			System.out.printf("\n시작일 값 \"%s\"를 파싱하는데 실패했습니다. 날짜 형식(yyyy/MM/dd HH:mm:ss)이 올바른지 확인하십시오.", startDateText);
			return null;
		}
		
		String endDateText = "2020/04/30 23:59:59";
		Date endDate = null;
		try{
			endDate = sdf.parse(endDateText);
		}catch(ParseException e1){
			System.out.printf("\n종료일 값 \"%s\"를 파싱하는데 실패했습니다. 날짜 형식(yyyy/MM/dd HH:mm:ss)이 올바른지 확인하십시오.", endDateText);
			return null;
		}
		
		// 최대 300페이지까지 조회합니다.
		boolean stopFlag = false;
		List<Vote> voteList = null;
		for(int page=1; page<300; page++){
			System.out.printf("\n\npage <%d>", page);
			Document doc = Jsoup.connect("https://minelist.kr/servers/" + id + "/votes?page=" + page).userAgent("Mozilla").timeout(100000).get();
			Elements list = doc.select("table.table-bordered.table-striped > tbody tr");
			for(Element tr : list){
				Elements info1 = tr.select("img");
				String image = info1.attr("src");
				String name = info1.attr("title");
				
				Elements info2 = tr.select("td:nth-child(2)");
				String serialNo = info2.attr("title");
				String currentDateText = info2.text();
				
				try{
					Date currentDate = sdf.parse(currentDateText);
					
					// 추천일이 종료날짜보다 미래라면 스킵
					if(currentDate.compareTo(endDate) > 0){
						System.out.printf("\n종료일 이후 데이터 스킵: %s(%s) %s", name, serialNo, currentDateText);
						continue;
					}
					
					// 추천일이 지정한 시작날짜보다 과거라면 작업중단
					if(currentDate.compareTo(startDate) < 0){
						stopFlag = true;
						break;
					}
					
					voteList = voteMap.get(name);
					if(voteList == null){
						voteList = new ArrayList<Vote>();
					}
					voteList.add(new Vote(name, currentDate, serialNo));
					voteMap.put(name, voteList);
					System.out.printf("\n기록: %s(%s) %s", name, serialNo, currentDateText);
				}catch(ParseException e){
					System.out.printf("\n%s(%s)에서 다음 날짜 포맷을 파싱하는데 실패했습니다: %s", name, serialNo, currentDateText);
				}
			}
			if(stopFlag == true){
				break;
			}
			
			// 1초~2초 휴식
			long waitTime = (long)(Math.random()*1000 + 1000);
			System.out.printf("\n휴식 %.2f초...", waitTime/1000f);
			try{
				Thread.sleep(waitTime);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
		return voteMap;
	}
}
