package cn.zhuangshq.exchange;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;

import android.R.string;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

public class MyTime extends TimerTask {

	String string;
	
	RemoteViews remoteViews;

	AppWidgetManager appWidgetManager;

	ComponentName thisWidget;

	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
	
	Parameters parameters = new Parameters();
	
	public MyTime(Context context,AppWidgetManager appWidgetManager) {

	this.appWidgetManager = appWidgetManager;

	remoteViews = new RemoteViews(context.getPackageName(),R.layout.my_layout);

	thisWidget = new ComponentName(context, ExampleAppWidgetProvider.class);
	}

	@Override
	public void run() {
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
				
			}
		});
		
		remoteViews.setTextViewText(R.id.txtRate,string);

		appWidgetManager.updateAppWidget(thisWidget, remoteViews);
	}

}
