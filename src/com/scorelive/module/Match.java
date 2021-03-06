package com.scorelive.module;

import android.os.Parcel;
import android.os.Parcelable;

public class Match implements Parcelable {

	public String leagueName;// 所属赛事
	public String leagueColor;//赛事颜色
	public String matchId;// 比赛Id
	public String matchBet;// 所属彩票类型,包括竞彩、北单、足彩
	public int leagueId;// 联赛Id
	public int matchState;// 比赛开始、结束、延期及中场休息的状态
	public String matchStartTime;// 比赛开始时间
	public String hostTeamName;// 主队名称
	public String hostTeamIndex;// 主队排名
	public String hostTeamScore;// 主队进球数
	public String hostTeamHalfScore;//主队半场进球数
	public String hostTeamRed;// 主队红牌数
	public String hostTeamYellow;// 主队黄牌数
	public String visitTeamName;// 客队名称
	public String visitTeamIndex;// 客队排名
	public String visitTeamScore;// 客队进球数
	public String visitTeamHalfScore;//客队半场进球数
	public String visitTeamRed;// 客队红牌数
	public String visitTeamYellow;// 客队黄牌数
	public String bjNum;// 北单num
	public String bjP;//北单期数
	public String SMGNum;// 竞赛num
	public String SMGP;//竞彩期数
	public String zcNum;// 足彩num
	public String zcP;//足彩期数
	public int groupId;//分组id
	public String matchTime;//比赛进行时间
	public String matchOfficalTime;//官方比赛时间

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(leagueName);
		dest.writeString(leagueColor);
		dest.writeString(matchId);
		dest.writeString(matchBet);
		dest.writeInt(leagueId);
		dest.writeInt(matchState);
		dest.writeString(matchStartTime);
		dest.writeString(hostTeamName);
		dest.writeString(hostTeamIndex);
		dest.writeString(hostTeamScore);
		dest.writeString(hostTeamRed);
		dest.writeString(hostTeamYellow);
		dest.writeString(visitTeamName);
		dest.writeString(visitTeamIndex);
		dest.writeString(visitTeamScore);
		dest.writeString(visitTeamRed);
		dest.writeString(visitTeamYellow);
		dest.writeString(bjNum);
		dest.writeString(bjP);
		dest.writeString(SMGNum);
		dest.writeString(SMGP);
		dest.writeString(zcNum);
		dest.writeString(zcP);
		dest.writeInt(groupId);
		dest.writeString(hostTeamHalfScore);
		dest.writeString(visitTeamHalfScore);
		dest.writeString(matchTime);
		dest.writeString(matchOfficalTime);
	}

	
	public static final Parcelable.Creator<Match> CREATOR=new Creator<Match>(){

		@Override
		public Match createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Match match = new Match();
			match.leagueName = source.readString();
			match.leagueColor = source.readString();
			match.matchId = source.readString();
			match.matchBet = source.readString();
			match.leagueId = source.readInt();
			match.matchState = source.readInt();
			match.matchStartTime = source.readString();
			match.hostTeamName = source.readString();
			match.hostTeamIndex = source.readString();;
			match.hostTeamScore= source.readString();
			match.hostTeamRed= source.readString();;
			match.hostTeamYellow = source.readString();;
			match.visitTeamName= source.readString();
			match.visitTeamIndex= source.readString();
			match.visitTeamScore= source.readString();
			match.visitTeamRed= source.readString();;
			match.visitTeamYellow= source.readString();
			match.bjNum= source.readString();
			match.bjP = source.readString();
			match.SMGNum= source.readString();
			match.SMGP = source.readString();
			match.zcNum= source.readString();
			match.zcP = source.readString();
			match.groupId = source.readInt();
			match.hostTeamHalfScore = source.readString();
			match.visitTeamHalfScore = source.readString();
			match.matchTime = source.readString();
			match.matchOfficalTime = source.readString();
			return match;
		}

		@Override
		public Match[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Match[size];
		}
		
	};
}
