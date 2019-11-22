package com.yjy.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlCreate {
	public static <T>  String getSql(String[] split,List<String> paramList,T t) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> c = t.getClass();
		StringBuffer sbForSql=new StringBuffer();
		for(int i=0;i<split.length;i++) {
			sbForSql.append(split[i]);
			if(i!=split.length-1) {
				String fieldName = paramList.get(i);
				Field declaredField = c.getDeclaredField(fieldName);
				declaredField.setAccessible(true);
				Object object = declaredField.get(t);
				if(object==null) {
					sbForSql.append("null");
				}else {
					sbForSql.append("'");
					sbForSql.append(object);
					sbForSql.append("'");
				}
			}
			
		}
		sbForSql.append(";\n");
		
		return sbForSql.toString();
	}
	public static <T> String getSqlForList(String sql,List<T> t) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		//解析sql
				String re=("#\\{(\\w*)\\}");
				String[] split = sql.split(re);
				int paramCount=split.length-1;
				System.out.println("里面有参数："+paramCount);
				
				StringBuffer sb=new StringBuffer();
				sb.append("^.*");
				for(int i=1;i<split.length;i++) {
					if(i== split.length-1) {
						sb.append(re);
						sb.append(".*$");
						continue;
					}
					sb.append(re);
					//sb.append(",");
					sb.append(".*");
				}
				Pattern compile = Pattern.compile(sb.toString());
				Matcher matcher = compile.matcher(sql);
				List<String> paramList=new ArrayList<String>();
				if(matcher.find()) {
					int groupCount = matcher.groupCount();
					for(int i=1;i<=groupCount;i++) {
						System.out.println("第"+i+"个为："+matcher.group(i));
						paramList.add(matcher.group(i));
					}
				}
		
		StringBuffer sbResult=new StringBuffer();
		for(T o:t) {
			sbResult.append(getSql(split,paramList,o));
		}
		return sbResult.toString();
	}
}
