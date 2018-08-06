package resources.loads;


import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
	public static void main(String[] args) {
		Injector injector = Guice.createInjector();
		RestapiApplication application = injector.getInstance(RestapiApplication.class);
		application.init();
	}
}
