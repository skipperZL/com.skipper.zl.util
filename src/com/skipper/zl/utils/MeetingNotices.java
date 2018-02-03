package com.skipper.zl.utils;

import java.util.Date;

public class MeetingNotices  {

	/** ID */
	 private String id;
	/** 标题 */
	 private String title;
	/** 内容 */
	 private String content;
	/** 会议开始时间 */
	 private Date starttime;
	/** 会议地点 */
	 private String place;
	/** 是否发送短信  0否 1是 */
	 private int msgflag;
	/** 短信内容 */
	 private String message;
	/** 备注 */
	 private String note;
	/** 创建时间 */
	 private Date draf_created;
	/** 创建人 */
	 private String draf_author;
	/** 创建人ID */
	 private String draf_authorid;
	/** appid */
	 private String appid;
}