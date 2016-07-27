package cn.zhuangshq.exchange;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Map;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	TextView txtMainRate = null;
	TextView txtYinlian = null;
	TextView CNprice;
	TextView CNtime;
	TextView HXprice;
	TextView HXtime;
	TextView txtNow;
	TextView txtCN;
	TextView txtHX;
	TextView txtYL;
	EditText txtAmount;
	Button btnUpdate = null;
	Button btnTranslate = null;

	

	double nowRate;
	double yinLianRate;
	double cnRate;
	double hxRate;
	
	
	String url;
	Parameters parameters = new Parameters();
	Kuaiyi kuaiyi;

	private final Intent EXAMPLE_SERVICE_INTENT = new Intent("android.appwidget.action.EXAMPLE_APP_WIDGET_SERVICE");

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ApiStoreSDK.init(getApplicationContext(), "8623646921e71ac210e064932f7679eb");

		CNprice = (TextView) findViewById(R.id.CNprice);
		CNtime = (TextView) findViewById(R.id.CNtime);
		HXprice = (TextView) findViewById(R.id.HXprice);
		HXtime = (TextView) findViewById(R.id.HXtime);
		txtAmount = (EditText) findViewById(R.id.editAmount);
		txtNow = (TextView) findViewById(R.id.txtNow);
		txtCN = (TextView) findViewById(R.id.txtCN);
		txtHX = (TextView) findViewById(R.id.txtHX);
		txtYL = (TextView) findViewById(R.id.txtYL);

		parameters.put("fromCurrency", "JPY");
		parameters.put("toCurrency", "CNY");
		parameters.put("amount", "100");
		parameters.put("apikey", "8623646921e71ac210e064932f7679eb");
		// TODO Auto-generated method stub
		url = "http://apis.baidu.com/apistore/currencyservice/currency";

		startService(EXAMPLE_SERVICE_INTENT);

		txtMainRate = (TextView) findViewById(R.id.txtMainRate);
		txtYinlian = (TextView) findViewById(R.id.textYinlian);
		btnUpdate = (Button) findViewById(R.id.btnUpate);
		btnTranslate = (Button) findViewById(R.id.btnTranlate);

		getString();

		new Thread(networkTask).start();

		btnUpdate.setOnClickListener(this);
		btnTranslate.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void getString() {
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
				String string = arg1.trim();
				int len = string.length();
				for (int i = len - 1; i > 0; i--) {
					if (string.charAt(i) == '.') {
						System.out.println(i);
						string = string.substring(i - 1, i + 4);
						break;
					}
				}
				// string = string.substring(string.length() - 16,
				// string.length() - 9);
				txtMainRate.setText("实时汇率为：" + string);
				nowRate = Double.parseDouble(string);
			}
		});
	}

	public void setYinlian(TextView tv, String value) {
		tv.setText(value);
	}

	public void setList(Map<String, String> map) {
		if (map != null) {
			CNprice.setText(map.get("CNprice"));
			System.out.println("------->>>>>" + map.get("CNtime"));
			CNtime.setText(map.get("CNtime"));
			HXprice.setText(map.get("HXprice"));
			HXtime.setText(map.get("HXtime"));
			HXtime.setText("hello");
		}
	}

	// public class MyThread extends Thread {
	//
	// public void run() {
	//
	// Kuaiyi kuaiyi = new Kuaiyi();
	// try {
	// kuaiyi.setUrl(new URL("http://www.kuaiyilicai.com/uprate/jpy.html"));
	// EventHandler handler = new EventHandler(Looper.getMainLooper());
	// System.out.println("-----?>>>Hello thread");
	// Message message = handler.obtainMessage(1, 1, 1, kuaiyi);
	// handler.sendMessage(message);
	// } catch (MalformedURLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// }

	public class EventHandler extends Handler {
		public EventHandler(Looper mainLooper) {
			// TODO Auto-generated constructor stub
			super(mainLooper);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			System.out.println("--->>>===HANDLERING");
			try {
				setAllText((Kuaiyi) msg.obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setAllText(Kuaiyi arg) throws Exception {
		Map<String, String> map = arg.getList();
		System.out.println("setAllText " + map);
		if (map != null) {
			CNprice.setText(map.get("CNprice"));
			CNtime.setText(map.get("CNtime"));
			HXprice.setText(map.get("HXprice"));
			HXtime.setText(map.get("HXtime"));
		}
		txtMainRate.setText(arg.getYinlian());
	}

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			System.out.println("=============================This is handler");
			if(msg != null) {
				Bundle data = msg.getData();
				yinLianRate = Double.parseDouble(data.getString("yinLian"));
				cnRate = Double.parseDouble(data.getString("CNprice"));
				hxRate = Double.parseDouble(data.getString("HXprice"));
				txtYinlian.setText("当前银联汇率为：" + data.getString("yinLian"));
				CNprice.setText(data.getString("CNprice"));
				CNtime.setText(data.getString("CNtime"));
				HXprice.setText(data.getString("HXprice"));
				HXtime.setText(data.getString("HXtime"));
			}
			try {
				System.out.println(kuaiyi.getList());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnUpate:
			getString();
			new Thread(networkTask).start();
			break;
		case R.id.btnTranlate:
			double result;
			try {
				NumberFormat numberFormat = NumberFormat.getInstance();
				numberFormat.setMaximumFractionDigits(3);
				double amount = Double.parseDouble((String) txtAmount.getText().toString());
				if(((RadioButton)findViewById(R.id.radioCNY)).isChecked()) {
					txtNow.setText(String.valueOf(numberFormat.format(amount / nowRate * 100)));
					txtYL.setText(String.valueOf(numberFormat.format(amount / yinLianRate)));
					txtCN.setText(String.valueOf(numberFormat.format(amount / cnRate)));
					txtHX.setText(String.valueOf(numberFormat.format(amount / hxRate)));
				} else {
					txtNow.setText(String.valueOf(numberFormat.format(amount * nowRate / 100)));
					txtYL.setText(String.valueOf(numberFormat.format(amount * yinLianRate)));
					txtCN.setText(String.valueOf(numberFormat.format(amount * cnRate)));
					txtHX.setText(String.valueOf(numberFormat.format(amount * hxRate)));
				}
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), "请输入一个正确的数值", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

}
