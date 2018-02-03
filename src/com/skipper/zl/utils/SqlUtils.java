package com.skipper.zl.utils;

import java.util.Arrays;
import java.util.List;

public class SqlUtils {
	
	public static void main(String[] args) {
		String updateSql = "select count(1) from (select * from (select t.*,rownum rn from (select tw.priority hj,tw.*   from v_pub_workitem_attachType tw   where (tw.participant = ? )   union    select tw.priority hj,tw.*   from v_pub_workitem_attachType tw   where (exists (select t.userid from (   select de.userid,de.starttime,de.endtime,de.deputyid,null as   process_def_id from pub_deputy_info de   where de.state = 1 and de.allprocess = 1   union all   select de.userid,de.starttime,de.endtime,ru.deputyid,ru.rulecontentid as   process_def_id from pub_deputy_info de, pub_DeputyRule ru   where de.state = 1 and de.allprocess = 0 and de.DEPUTY_ID = ru.DEPUTY_ID   and ru.state = 1) t   where t.deputyid = ? and tw.exeid=userid   and ((t.starttime is null)  or t.starttime  <= to_date('2018-01-13 16:53:25','yyyy-MM-dd hh24:mi:ss'))   and ((t.endtime is null)  or t.endtime >= to_date('2018-01-13 16:53:25','yyyy-MM-dd hh24:mi:ss'))))    )   t where  to_date(t.createTime,'yyyy-MM-dd hh24:mi:ss')  <= to_date('2018-01-13 17:53:25','yyyy-MM-dd hh24:mi:ss')   and to_date(t.createTime,'yyyy-MM-dd hh24:mi:ss') >=to_date('1990-01-01 00:00:00','yyyy-MM-dd hh24:mi:ss')                                          )t1   )  ";
		String params = " [100133, 100133]";
		System.out.println(replaceSqlParams(updateSql, params));
	}
	
	/**
	 * Replace the sql parameters  that matches the given parameters
	 * @param sql 
	 * @param params 
	 * @return
	 */
	private static String  replaceSqlParams(String sql, String params) {
		if (StringUtils.isEmpty(sql) || StringUtils.isEmpty(params)) 
			return null;
		params = params.trim();
		if (params.startsWith("["))
			params = params.substring(1);
		if (params.endsWith("]"))
			params = params.substring(0, params.length() - 1);
		String[] sqls = params.split(",");
		for (String e : sqls) {
			if (!" null".equals(e)) 
				sql = sql.replaceFirst("\\?", "'" + e.trim() + "'");
			else
				sql = sql.replaceFirst("\\?", e.trim());
		}
		return sql;
	}
}
