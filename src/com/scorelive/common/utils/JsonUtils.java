package com.scorelive.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.scorelive.module.Match;

public class JsonUtils {
	
	private static final String MATCH="match";
	private static final String Score="liveScore";
	private static final String BJNUM="beidanNum";
	private static final String SMGNUM="jingcaiNum";
	private static final String ZCNUM="zucaiNum";
	private static final String HOSTNAME="homeTeamName";
	private static final String HOSTINDEX="homeTeamRank";
	private static final String VISITNAME="visitingTeamName";
	private static final String VISITINDEX="visitingTeamRank";
	private static final String HOSTRED="redHome";
	private static final String HOSTYELLOW="yellowHome";
	private static final String VISITRED="redVisiting";
	private static final String VISITYELLOW="yellowVisiting";
	private static final String STARTTIME="time";
	private static final String TYPE="type";
	private static final String ID="id";
	private static final String LEAGUE="league";
	private static final String LEAGUEBASE="leagueBase";
	private static final String NAME="name";
	private static final String MATCHSTATUS = "matchStatus";
	
	
	

	public static ArrayList<Match> json2MatchList(String str) {
		JSONObject object;
		ArrayList<Match> list = new ArrayList<Match>();
		try {
//			object = new JSONObject(str);
			JSONArray array = new JSONArray(str);
//			JSONArray array = object.getJSONArray("list");
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
		String score = object.optString(Score);
		JSONObject matchObject = object.optJSONObject(MATCH);
		int matchStatus = object.optInt(MATCHSTATUS);
		String hostName = matchObject.optString(HOSTNAME);
		String hostIndex = matchObject.optString(HOSTINDEX);
		String visitName = matchObject.optString(VISITNAME);
		String visitIndex = matchObject.optString(VISITINDEX);
		String hostRed = object.optString(HOSTRED);
		String hostYellow = object.optString(HOSTYELLOW);
		String visitRed = object.optString(VISITRED);
		String visitYellow = object.optString(VISITYELLOW);
		String type = matchObject.optString(TYPE);
		String bjNum = matchObject.optString(BJNUM);
		String smgNum = matchObject.optString(SMGNUM);
		String zcNum = matchObject.optString(ZCNUM);
		String startTime = matchObject.optString(STARTTIME);
		JSONObject leagueObject = matchObject.optJSONObject(LEAGUE);
		JSONObject leagueBaseObject = leagueObject.optJSONObject(LEAGUEBASE);
		String leagueName = leagueBaseObject.optString(NAME);
		int leagueId = leagueBaseObject.optInt(ID);
		int matchId  = matchObject.optInt(ID);
		Match match = new Match();
		match.bjNum = bjNum;
		match.SMGNum = smgNum;
		match.zcNum = zcNum;
		match.hostTeamIndex = hostIndex;
		match.hostTeamName = hostName;
		match.hostTeamRed = hostRed;
		match.hostTeamYellow = hostYellow;
		match.visitTeamIndex = visitIndex;
		match.visitTeamName = visitName;
		match.visitTeamRed = visitRed;
		match.visitTeamYellow = visitYellow;
		match.matchBet = type;
		match.matchScore = score;
		match.matchStartTime = startTime;
		match.leagueId = leagueId;
		match.matchLeague = leagueName;
		match.matchId = matchId;
		match.matchScore = score;
		match.matchStartTime = startTime;
		match.matchState = matchStatus;
		return match;
	}
}
