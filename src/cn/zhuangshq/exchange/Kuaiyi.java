package cn.zhuangshq.exchange;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kuaiyi {

	private URL url;
	private String html = "";
	
	public String getYinlian() throws Exception {
		this.html = getURLSource(url).trim();
		Pattern pattern = Pattern.compile("<span style=\"font-size:26px;\">汇率:&nbsp;&nbsp;&nbsp;<span style=\"color:Red;\">.*</span></span>");
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			System.out.println(matcher.group());
			int len = matcher.group().length();
			String ss = matcher.group().substring(len - 20, len - 14);
			// System.out.println(ss);
			return ss;
		}
		return "error";
	}
	
	public Map<String, String> getList() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		
		if("".equals(this.html)) this.html = getURLSource(url).trim();
		String string;
		Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
		Matcher matcher = pattern.matcher(html);
		string = matcher.replaceAll("");
		//System.out.println(string);
		pattern = Pattern.compile("银行/机构.*注：1");
		matcher = pattern.matcher(string);
		while (matcher.find()) {
			//System.out.println(matcher.group());
			String tmp = matcher.group();
			int cnIndex = tmp.indexOf("中国银行");
			int hxIndex = tmp.indexOf("华夏银行");
			map.put("CNprice", tmp.substring(cnIndex + 27, cnIndex + 35));
			map.put("HXprice", tmp.substring(hxIndex + 27, hxIndex + 35));
			map.put("CNtime", tmp.substring(cnIndex + 58, cnIndex + 68));
			map.put("HXtime", tmp.substring(hxIndex + 58, hxIndex + 68));
			System.out.println(map);
			return map;
		}
		
		return null;
	}
	
	public static String getURLSource(URL url) throws Exception    {
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream =  conn.getInputStream();  //通过输入流获取html二进制数据
        byte[] data = readInputStream(inStream);        //把二进制数据转化为byte字节数据
        String htmlSource = new String(data, "utf-8");
        return htmlSource;
    }

	 public static byte[] readInputStream(InputStream instream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[]  buffer = new byte[1204];
        int len = 0;
        while ((len = instream.read(buffer)) != -1){
            outStream.write(buffer,0,len);
        }
        instream.close();
        return outStream.toByteArray();         
    }

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) throws Exception {
		this.url = url;
		this.html = getURLSource(url).trim();
	}
	
}
