package cn.zhuangshq.exchange;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

public class BigWidget extends AppWidgetProvider {

	private final Intent BIG_WIDGET_SERVICE_INTENT = 
            new Intent("android.appwidget.action.BIG_APP_WIDGET_SERVICE");
	
	private String string;
	private Parameters parameters = new Parameters();
	Context context;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);

        // Get the layout for the App Widget and attach an on-click listener
        // to the button
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.big_widget);
        remoteViews.setOnClickPendingIntent(R.id.imgRate, pendingIntent);
        ComponentName thisWidget = new ComponentName(context, BigWidget.class);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
	}
	
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		System.out.println("onEnalbled");
		context.startService(BIG_WIDGET_SERVICE_INTENT);
		super.onEnabled(context);

	}
	
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		context.stopService(BIG_WIDGET_SERVICE_INTENT);
	}
	
	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		System.out.println("------>onReceiveBIGNBIGNBIGNBIGN~");
		this.context = context;
		new Thread(networkTask).start();
		
	}
	
	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			System.out.println("=============================This is handler");
			if(msg != null) {
				Bundle data = msg.getData();
				
				Intent intent1 = new Intent(context, MainActivity.class);
		        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);

		        // Get the layout for the App Widget and attach an on-click listener
		        // to the button
		        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.big_widget);
		        remoteViews.setOnClickPendingIntent(R.id.imgRate, pendingIntent);
		        ComponentName thisWidget = new ComponentName(context, BigWidget.class);
				
		        double CNprice = Double.parseDouble(data.getString("CNprice")) * 100;
		        double HXprice = Double.parseDouble(data.getString("HXprice")) * 100;
		        //double nowPrice = Double.parseDouble(new BaiduApi().getString());
		        
		        NumberFormat numberFormat = NumberFormat.getInstance();
		        numberFormat.setMaximumFractionDigits(3);
		        		
				remoteViews.setTextViewText(R.id.txtRateCN, String.valueOf(numberFormat.format(CNprice)));
		        remoteViews.setTextViewText(R.id.txtRateHX, String.valueOf(numberFormat.format(HXprice)));
		        //remoteViews.setTextViewText(R.id.txtNow, String.valueOf(numberFormat.format(nowPrice)));

		        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		        
				appWidgetManager.updateAppWidget(thisWidget, remoteViews);
			}
		};

	};
	
	Runnable networkTask = new Runnable() {
		public void run() {
			try {
				Kuaiyi kuaiyi = new Kuaiyi();
				kuaiyi.setUrl(new URL("http://www.kuaiyilicai.com/uprate/jpy.html"));
				Map<String, String> map = kuaiyi.getList();
				Bundle data = new Bundle();
				String tmp;
				data.putString("yinLian", kuaiyi.getYinlian());
				data.putString("CNprice", map.get("CNprice"));
				data.putString("HXprice", map.get("HXprice"));
				tmp = map.get("CNtime");
				tmp = tmp.substring(0, 5) + " " + tmp.substring(5, 10);
				data.putString("CNtime", tmp);
				tmp = map.get("HXtime");
				tmp = tmp.substring(0, 5) + " " + tmp.substring(5, 10);
				data.putString("HXtime", tmp);
				Message message = new Message();
				message.setData(data);
				System.out.println("OK!!");
				handler.sendMessage(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
}
