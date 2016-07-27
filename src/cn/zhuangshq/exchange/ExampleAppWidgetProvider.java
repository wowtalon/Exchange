package cn.zhuangshq.exchange;


import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class ExampleAppWidgetProvider extends AppWidgetProvider {
	
	private final Intent EXAMPLE_SERVICE_INTENT = 
            new Intent("android.appwidget.action.EXAMPLE_APP_WIDGET_SERVICE");
	
	private String string;
	private Parameters parameters = new Parameters();

	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // Perform this loop procedure for each App Widget that belongs to this provider
		
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
		
            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.my_layout);
            remoteViews.setOnClickPendingIntent(R.id.imgRate, pendingIntent);
            ComponentName thisWidget = new ComponentName(context, ExampleAppWidgetProvider.class);

            //Timer timer = new Timer();

            //timer.scheduleAtFixedRate(new MyTime(context,appWidgetManager), 1, 1000);
            
            remoteViews.setTextViewText(R.id.txtRate,string);

    		appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
	
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		System.out.println("onEnalbled");
		context.startService(EXAMPLE_SERVICE_INTENT);
		super.onEnabled(context);

	}
	
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		context.stopService(EXAMPLE_SERVICE_INTENT);
	}
	
	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		System.out.println("------>onReceive~");
		
		if(parameters == null || parameters.size() == 0) {
			parameters = new Parameters();
			parameters.put("fromCurrency", "JPY");
			parameters.put("toCurrency", "CNY");
			parameters.put("amount", "100");
			parameters.put("apikey", "8623646921e71ac210e064932f7679eb");
		}
		
		
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
				System.out.println("-----error-----");
			}
			
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				string = arg1.trim();
				int len = string.length();
				for(int i = len - 1; i > 0; i--) {
					if(string.charAt(i) == '.') {
						string = string.substring(i - 1, i + 4);
						break;
					}
				}
				Intent intent1 = new Intent(context, MainActivity.class);
		        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);

		        // Get the layout for the App Widget and attach an on-click listener
		        // to the button
		        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.my_layout);
		        remoteViews.setOnClickPendingIntent(R.id.imgRate, pendingIntent);
		        ComponentName thisWidget = new ComponentName(context, ExampleAppWidgetProvider.class);

		        
		        remoteViews.setTextViewText(R.id.txtRate, string);

		        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		        
				appWidgetManager.updateAppWidget(thisWidget, remoteViews);
				//string = string.substring(string.length() - 16, string.length() - 9);
			}
		});

		//System.out.println("my string is " + string);
        // Create an Intent to launch ExampleActivity
        
		
	}
	
	public void str(String string) {
		this.string = string;
	}
	
}
