package com.scorelive.common.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.scorelive.module.Match;
import com.scorelive.module.MatchAccident;
import com.scorelive.module.PushInfo;

public class JsonUtils {

	private static final String MATCH = "match";
	private static final String Score = "liveScore";
	private static final String BJNUM = "beidanNum";
	private static final String SMGNUM = "jingcaiNum";
	private static final String ZCNUM = "zucaiNum";
	private static final String HOSTNAME = "homeTeamName";
	private static final String HOSTINDEX = "homeTeamRank";
	private static final String VISITNAME = "visitingTeamName";
	private static final String VISITINDEX = "visitingTeamRank";
	private static final String HOSTRED = "redHome";
	private static final String HOSTYELLOW = "yellowHome";
	private static final String VISITRED = "redVisiting";
	private static final String VISITYELLOW = "yellowVisiting";
	private static final String STARTTIME = "time";
	private static final String TYPE = "type";
	private static final String ID = "id";
	private static final String LEAGUE = "league";
	private static final String LEAGUEBASE = "leagueBase";
	private static final String NAME = "name";
	private static final String MATCHSTATUS = "matchStatus";

	public static PushInfo pushJson2Match(String str) {
		PushInfo match = new PushInfo();
		try {
			JSONObject object = new JSONObject(str);
			int id = object.optInt("matchId");
			String homeGoal = object.optString("hg");
			String visitGoal = object.optString("vg");
			String time = object.optString("rt");
			int redHome = object.optInt("hrc");
			int redVisiting = object.optInt("vrc");
			int yellowHome = object.optInt("hyc");
			int yellowVisiting = object.optInt("vyc");
			String homeName = object.optString("hn");
			String visitName = object.optString("vn");
			match.homeName = homeName;
			match.id = id;
			match.time = time;
			match.redHome = redHome;
			match.redVisiting = redVisiting;
			match.yellowHome = yellowHome;
			match.yellowVisiting = yellowVisiting;
			match.homeGoal = homeGoal;
			match.visitGoal = visitGoal;
			match.homeName = homeName;
			match.visitName = visitName;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return match;
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
		int matchId = matchObject.optInt(ID);
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
