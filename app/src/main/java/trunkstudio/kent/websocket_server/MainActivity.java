package trunkstudio.kent.websocket_server;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import it.ennova.zerxconf.ZeRXconf;
import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

  private static  final String TAG = "WSS";

  private Context content;
  private Map<String, String> attributes = new HashMap<>(1);
  private final Map<String, String> emptyAttributes = new HashMap<>(0);
  private boolean forceEmptySet = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this.content = this.getApplicationContext();

    try {
      SSLServerExample.start(this);
    } catch (Exception e) {
      e.printStackTrace();

      Log.wtf(TAG, e.getMessage());
      Toast.makeText(content, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    //try {
    //  ChatServer chatserver = new ChatServer( 8887 );
    //  chatserver.start();
    //} catch (UnknownHostException e) {
    //  e.printStackTrace();
    //  Toast.makeText(content, e.getMessage(), Toast.LENGTH_LONG).show();
    //}

    Subscription s = ZeRXconf.advertise(this, "Secure WebSocket Server", "_http._tcp.", 8887, getAttributes())
        .subscribe(onNext, onError);

  }


  private Map<String, String> getAttributes() {
    return forceEmptySet ? emptyAttributes : attributes;
  }

  private Action1<NetworkServiceDiscoveryInfo> onNext = new Action1<NetworkServiceDiscoveryInfo>() {
    @Override
    public void call(NetworkServiceDiscoveryInfo networkServiceDiscoveryInfo) {

      Toast.makeText(content, "onNext", Toast.LENGTH_LONG).show();
    }
  };

  private Action1<Throwable> onError = new Action1<Throwable>() {
    @Override
    public void call(Throwable throwable) {
      Toast.makeText(content, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }
  };
}
