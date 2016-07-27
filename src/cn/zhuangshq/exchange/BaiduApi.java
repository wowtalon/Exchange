package cn.zhuangshq.exchange;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;

public class BaiduApi {
	private Parameters parameters = new Parameters();
	private String string;
	
	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public BaiduApi() {
		// TODO Auto-generated constructor stub
		parameters.put("fromCurrency", "JPY");
		parameters.put("toCurrency", "CNY");
		parameters.put("amount", "100");
		parameters.put("apikey", "8623646921e71ac210e064932f7679eb");
		// TODO Auto-generated method stub
		String url = "http://apis.baidu.com/apistore/currencyservice/currency";
		ApiStoreSDK.execute(url, ApiStoreSDK.GET, parameters, new ApiCallBack() {
			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				super.onComplete();
			}
			
			@Override
			public void onError(int arg0, String arg1, Exception arg2) {
				// TODO Auto-generated method stub
				super.onError(arg0, arg1, arg2);
			}
			
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				string = arg1.trim();
				int len = string.length();
				for(int i = len - 1; i > 0; i--) {
					if(string.charAt(i) == '.') {
						System.out.println(i);
						string = string.substring(i - 1, i + 4);
						break;
					}
				}
				//string = string.substring(string.length() - 16, string.length() - 9);
			}
		});
	}
}
