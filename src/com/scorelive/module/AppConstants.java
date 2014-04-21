package com.scorelive.module;

public class AppConstants {

	public class BetType{
		public static final int ALL = 0;
		public static final int NORMAL = 1;
		public static final int BJ = 3;
		public static final int SMG = 2;
		public static final int ZC = 4;
		public static final int CUSTOMIZE = -1;
	}
	
	public class MatchStatus{
		public static final int UNSTART = 1;//未开始
		public static final int DELAY = 2;//延期
		public static final int CANCEL = 3;//取消
		public static final int MATCHING = 4;//比赛中
		public static final int MIDDLE = 5;//中场休息
		public static final int ENDED = 6;//结束
		public static final int UPADDED=7;//上半场补时
		public static final int DOWNADDED = 8;//下半场补时
		public static final int PAUSEHURT= 9;//受伤暂停
		public static final int PAUSEFOUL = 10;//犯规暂停
	}
}
