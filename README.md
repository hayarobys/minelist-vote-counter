# minelist-vote-counter
1. 소개
	>마인리스트 휴월드(https://minelist.kr/servers/6500/votes) 추천 목록에서 지난 달의 추천순위를 조회하는 프로그램입니다.

2. 특징
	* 크롤링 방식
	* 지난 달 데이터 조회
	* 순위 출력

3. 의존성
	* [org.jsoup 1.8.3](https://jsoup.org/)
	* ch.qos.logback 1.1.7

4. 개발환경
	* Spring Tool Suite 4
	* Java 8
	* Windows 10

5. 사용법
	1. 실행 후 작업이 종료되기까지 대기하십시오.
	2. 실행폴더에 생성된 minelist-vote-counter.log 를 확인하세요.

6. 주의사항
	* 크롤링 중에 신규 추천이 있을 경우, 페이지 번호가 밀리며 중복 카운트 될 수 있습니다.
	* 가급적 데이터 갱신이 없는 시간에 시도하십시오.
	* 혹시 모를 무한 페이징 방지를 위해 최대 300페이지 제한을 걸어놨습니다.
	* 통계 작업에 3~5분 정도 소요됩니다.