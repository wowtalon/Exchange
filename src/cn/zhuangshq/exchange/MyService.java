package cn.zhuangshq.exchange;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	
	private static final String TAG="MyService"; 

	private final String ACTION_UPDATE_ALL = "android.appwidget.action.APPWIDGET_UPDATE";
    // �����Ը��� widget ������
    private static final int UPDATE_TIME = 10000;
    // �����Ը��� widget ���߳�
    private UpdateThread mUpdateThread;
    private Context mContext;
    // �������ڵļ���
    private int count=0;      

    @Override
    public void onCreate() {
        
        // �����������߳�UpdateThread
        mUpdateThread = new UpdateThread();
        mUpdateThread.start();
        
        mContext = this.getApplicationContext();

        super.onCreate();
    }
    
    @Override
    public void onDestroy(){
        // �ж��̣߳��������̡߳�
        if (mUpdateThread != null) {
            mUpdateThread.interrupt();
        }
        
        super.onDestroy();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * ����ʼʱ��������startService()ʱ��onStartCommand()��ִ�С�
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");        
        super.onStartCommand(intent, flags, startId);
        
        return START_STICKY;
    }
    
    private class UpdateThread extends Thread {

        @Override
        public void run() {
            super.run();

            try {
                count = 0;
                while (true) {
                    Log.d(TAG, "run ... count:"+count);
                    count++;

                    Intent updateIntent=new Intent(ACTION_UPDATE_ALL);
                    mContext.sendBroadcast(updateIntent);
                    
                    Thread.sleep(UPDATE_TIME);
                } 
            } catch (InterruptedException e) {
                // �� InterruptedException ������whileѭ��֮�⣬��ζ���׳� InterruptedException �쳣ʱ����ֹ�̡߳�
                e.printStackTrace();
            }
        }
    }
}