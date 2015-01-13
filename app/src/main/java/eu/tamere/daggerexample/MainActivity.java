package eu.tamere.daggerexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioGroup;

import javax.inject.Inject;

import dagger.ObjectGraph;
import eu.tamere.daggerexample.modules.BingEndpointModule;
import eu.tamere.daggerexample.modules.YahooEndpointModule;


public class MainActivity extends ActionBarActivity {
    ObjectGraph objectGraph;

    RadioGroup searchEngineSwitch;
    WebView webView;

    // The Inject annotation tells the objectGraph that this field
    // want to be populated.
    @Inject Endpoint endpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());

        searchEngineSwitch = (RadioGroup) findViewById(R.id.search_choice);
        searchEngineSwitch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.yahoo_choice) {
                    selectYahoo();
                } else {
                    selectBing();
                }
            }
        });

        // As the OnCheckedChangeListener is triggered when the radio group
        // is instantiated, we don't need to setup the initial state.
    }

    private void selectYahoo() {
        updateModule(new YahooEndpointModule());
    }

    private void selectBing() {
        updateModule(new BingEndpointModule());
    }

    private void refreshWebview() {
        Log.d("DaggerActivity", endpoint.getUrl());
        webView.loadUrl(endpoint.getUrl());
    }

    // This will create a new ObjectGraph with the newly selected module
    // We then re-inject so the injectable properties of the activities are updated with
    // the new object graph.
    private void updateModule(Object module) {
        objectGraph = ObjectGraph.create(module);
        objectGraph.inject(this);

        refreshWebview();
    }
}
