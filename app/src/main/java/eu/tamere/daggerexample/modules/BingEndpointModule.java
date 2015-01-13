package eu.tamere.daggerexample.modules;

import dagger.Module;
import dagger.Provides;
import eu.tamere.daggerexample.Endpoint;
import eu.tamere.daggerexample.MainActivity;

@Module(
        injects = {
                MainActivity.class
        })
public class BingEndpointModule {
    @Provides
    Endpoint provideEndpoint() {
        return new Endpoint("http://bing.com/");
    }
}
