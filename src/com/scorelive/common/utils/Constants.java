package com.scorelive.common.utils;

/**
 * 定义微博授权时所需要的参数。
 */
public interface Constants {

    public static final String SINA_APP_KEY    = "3456776882";
    
    public static final String Tencent_APP_ID  = "1101484810";

    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    public static final String SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
}
