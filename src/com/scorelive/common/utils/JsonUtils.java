package com.scorelive.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.scorelive.module.Match;
import com.scorelive.module.MatchAccident;
import com.scorelive.module.PushInfo;

public class JsonUtils {

	private static final String MATCH = "match";
	private static final String BJNUM = "bdN";
	private static final String SMGNUM = "jcN";
	private static final String ZCNUM = "zcN";
	private static final String HOSTNAME = "hTN";
	private static final String HOSTINDEX = "hTR";
	private static final String VISITNAME = "vTN";
	private static final String VISITINDEX = "vTR";
	private static final String HOSTRED = "rcH";
	private static final String HOSTYELLOW = "ycH";
	private static final String VISITRED = "rcV";
	private static final String VISITYELLOW = "ycV";
	private static final String STARTTIME = "sTime";
	private static final String TYPE = "type";
	private static final String ID = "matchId";
	private static final String NAME = "ln";
	private static final String MATCHSTATUS = "mStatus";
	private static final String HOSTSCORE = "hTG";
	private static final String HOSTHALFSCORE = "hTGH";
	private static final String VISITHALFSCORE = "vTGH";
	private static final String VISITSCORE = "vTG";
	private static final String BJP = "bdP";
	private static final String SMGP = "jcP";
	private static final String ZCP = "zcP";
	private static final String LEAGUECOLOR = "lc";
	private static final String RUNTIME = "rTime";

	public static ArrayList<Match> pushJson2Match(String str) {
		ArrayList<Match> list = new ArrayList<Match>();
		try {
			JSONArray array = new JSONArray(str);
			for(int i = 0;i<array.length();i++){
				Match match = new Match();
				JSONObject content = array.getJSONObject(i);
				int eventType = content.optInt("eventType");
				JSONObject object = content.optJSONObject("matchEvent");
				String id = object.optString("matchId");
				String homeGoal = object.optString("hg");
				String visitGoal = object.optString("vg");
				String time = object.optString("rt");
				int redHome = object.optInt("hrc");
				int redVisiting = object.optInt("vrc");
				int yellowHome = object.optInt("hyc");
				int yellowVisiting = object.optInt("vyc");
				String homeName = object.optString("hn");
				String visitName = object.optString("vn");
				String sTime = object.optString("rt");
				Log.e("时间",sTime);
				match.hostTeamName = homeName;
				match.matchId = id;
//				match.time = time;
				match.hostTeamRed = String.valueOf(redHome);
				match.visitTeamRed = String.valueOf(redVisiting);
				match.hostTeamYellow = String.valueOf(yellowHome);
				match.visitTeamYellow = String.valueOf(yellowVisiting);
				match.hostTeamScore = homeGoal;
				match.visitTeamScore = visitGoal;
//				match.homeName = homeName;
				match.visitTeamName = visitName;
				match.matchState = eventType;
				match.matchStartTime = sTime;
				list.add(match);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		PushInfo match = new PushInfo();
//		try {
//			JSONObject object = new JSONObject(str);
//			int id = object.optInt("matchId");
//			String homeGoal = object.optString("hg");
//			String visitGoal = object.optString("vg");
//			String time = object.optString("rt");
//			int redHome = object.optInt("hrc");
//			int redVisiting = object.optInt("vrc");
//			int yellowHome = object.optInt("hyc");
//			int yellowVisiting = object.optInt("vyc");
//			String homeName = object.optString("hn");
//			String visitName = object.optString("vn");
//			match.homeName = homeName;
//			match.id = id;
//			match.time = time;
//			match.redHome = redHome;
//			match.redVisiting = redVisiting;
//			match.yellowHome = yellowHome;
//			match.yellowVisiting = yellowVisiting;
//			match.homeGoal = homeGoal;
//			match.visitGoal = visitGoal;
//			match.homeName = homeName;
//			match.visitName = visitName;
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return list;
	}

	public static ArrayList<Match> json2MatchList(String str) {
		JSONObject object;
		ArrayList<Match> list = new ArrayList<Match>();
		try {
			// object = new JSONObject(str);
			JSONArray array = new JSONArray(str);
			// JSONArray array = object.getJSONArray("list");
			for (int i = 0; i < array.length(); i++) {
				Match match = json2Match(array.getJSONObject(i));
				list.add(match);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	

	private static Match json2Match(JSONObject object) {
		String matchStatus = object.optString(MATCHSTATUS);
		String hostName = object.optString(HOSTNAME);
		String hostScore = object.optString(HOSTSCORE);
		String hostIndex = object.optString(HOSTINDEX);
		String visitName = object.optString(VISITNAME);
		String visitScore = object.optString(VISITSCORE);
		String visitIndex = object.optString(VISITINDEX);
		String hostRed = object.optString(HOSTRED);
		String hostYellow = object.optString(HOSTYELLOW);
		String visitRed = object.optString(VISITRED);
		String visitYellow = object.optString(VISITYELLOW);
		String type = object.optString(TYPE);
		String bjNum = object.optString(BJNUM);
		String bjP = object.optString(BJP);
		String smgNum = object.optString(SMGNUM);
		String smgP = object.optString(SMGP);
		String zcNum = object.optString(ZCNUM);
		String zcP = object.optString(ZCP);
		String startTime = object.optString(STARTTIME);
		String leagueName = object.optString(NAME);
		String leagueColor = object.optString(LEAGUECOLOR);
		String matchId = object.optString(ID);
		String homeHalfScore = object.optString(HOSTHALFSCORE);
		String visitHalfScore = object.optString(VISITHALFSCORE);
		String runTime = object.optString(RUNTIME);
		Match match = new Match();
		match.matchTime = runTime;
		match.hostTeamHalfScore = homeHalfScore;
		match.visitTeamHalfScore = visitHalfScore;
		match.hostTeamScore = hostScore;
		match.visitTeamScore = visitScore;
		match.bjP  = bjP;
		match.SMGP = smgP;
		match.zcP = zcP;
		match.bjNum = bjNum;
		match.SMGNum = smgNum;
		match.zcNum = zcNum;
		match.hostTeamIndex = hostIndex;
		if(hostName.contains("(中)")){
			hostName = hostName.substring(0, hostName.indexOf("(中)"));
		}
		match.hostTeamName = hostName;
		match.hostTeamRed = hostRed;
		match.hostTeamYellow = hostYellow;
		match.visitTeamIndex = visitIndex;
		match.visitTeamName = visitName;
		match.visitTeamRed = visitRed;
		match.visitTeamYellow = visitYellow;
		match.matchBet = type;
		match.matchStartTime = startTime;
		match.leagueName = leagueName;
		match.leagueColor = leagueColor;
		match.matchId = matchId;
		match.matchStartTime = startTime;
		match.matchState = Integer.valueOf(matchStatus);
		return match;
	}

	public static ArrayList<MatchAccident> json2MatchAccident(String str) {
		ArrayList<MatchAccident> list = new ArrayList<MatchAccident>();
		try {
			JSONObject object = new JSONObject(str);
			JSONArray array = object.optJSONArray("smalist");
			for (int i = 0; i < array.length(); i++) {
				JSONObject accident = array.getJSONObject(i);
				MatchAccident aMatch = new MatchAccident();
				aMatch.accident_content = accident
						.optString("accident_content");
				aMatch.accident_time = accident.optString("accident_time");
				aMatch.accident_type = accident.optInt("accident_type");
				aMatch.accident_team = accident.optInt("which_team");
				list.add(aMatch);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
