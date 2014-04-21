package com.scorelive.module;

public class Match {

	public String matchLeague;// 所属赛事
	public int matchId;// 当日比赛Id
	public String matchBet;// 所属彩票类型,包括竞彩、北单、足彩
	public int leagueId;//联赛Id
	public int matchState;// 比赛开始、结束、延期及中场休息的状态
	public String matchStartTime;// 比赛开始时间
	public String matchScore;// 比赛比分
	public String matchTime;// 比赛进行了多久
	public String hostTeamName;// 主队名称
	public String hostTeamIndex;// 主队排名
	public String hostTeamScore;// 主队积分
	public String hostTeamRed;// 主队红牌数
	public String hostTeamYellow;// 主队黄牌数
	public String visitTeamName;// 客队名称
	public String visitTeamIndex;// 客队排名
	public String visitTeamScore;// 客队积分
	public String visitTeamRed;// 客队红牌数
	public String visitTeamYellow;// 客队黄牌数
	public String bjNum;//北单num
	public String SMGNum;//竞赛num
	public String zcNum;//足彩num

}
