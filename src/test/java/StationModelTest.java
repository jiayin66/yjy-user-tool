import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yjy.model.StationModel;
import com.yjy.util.SqlCreate;

public class StationModelTest {
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
			String sql=
		"insert into T_POLICE_STATION_INFO"
		+ "(ID,PARENTID,STATIONNAME,ORGCODE,STATIONNUM,LONGITUDE,LATITUDE,TEL,ADDRESS,RADIO_ID) "
		+ "values(#{id},#{parentId},#{stationName},#{orgcode},#{stationNum},#{longitude},#{latitude},#{tel},#{address},#{radioId})";

			StationModel stationModel = new StationModel();
			stationModel.setId("1");
		System.out.println(SqlCreate.getSql(sql, stationModel));
		
		
		
		/*	String sql=
				"insert into T_POLICE_STATION_INFO"
				+ "(ID,PARENTID,STATIONNAME,ORGCODE,STATIONNUM,LONGITUDE,LATITUDE,TEL,ADDRESS,RADIO_ID) "
				+ "values(#{1},#{2},#{3},#{},#{4},#{},#{},#{},#{},#{10})";
		String re=("#\\{(\\w*)\\}");
		String[] split = sql.split(re);
		System.out.println("里面有参数："+(split.length-1));
		for(String str:split) {
			System.out.println(str);
		}
		StringBuffer sb=new StringBuffer();
		sb.append("^.*");
		for(int i=1;i<split.length;i++) {
			if(i== split.length-1) {
				sb.append(re);
				sb.append(".*$");
				continue;
			}
			sb.append(re);
			sb.append(",");
			sb.append(".*");
		}
		
		Pattern compile = Pattern.compile(sb.toString());
		Matcher matcher = compile.matcher(sql);
		System.out.println(matcher.groupCount());
		if(matcher.find()) {
			int groupCount = matcher.groupCount();
			for(int i=0;i<=groupCount;i++) {
				System.out.println("第"+i+"个为："+matcher.group(i));
			}
		}*/
	}
}
