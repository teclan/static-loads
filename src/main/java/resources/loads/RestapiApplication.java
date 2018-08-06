package resources.loads;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import resources.loads.controller.FileController;
import spark.servlet.SparkApplication;

import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.staticFiles;

public class RestapiApplication implements SparkApplication {
	private final Logger LOGGER = LoggerFactory.getLogger(RestapiApplication.class);

	static int serverPort;
	static String serverHost;
	static String staticResourcesPath;
	
	@Inject
	private FileController fileController;

	static {
		File file = new File("config/application.conf");
		Config root = ConfigFactory.parseFile(file);
		Config config = root.getConfig("config");

		serverPort = config.getInt("sever.port");
		serverHost=config.getString("sever.host");
		staticResourcesPath = config.getString("static.resource.path");
	}

	@Override
	public void init() {
		defaultConfig();
		apiInit();
		filter();

		LOGGER.info("\n\n================================\n");
		LOGGER.info("\n\n启动成功，端口：{},静态资源目录:{}\n", serverPort, staticResourcesPath);
		LOGGER.info("\n\n================================\n");
	}

	private void apiInit() {
		fileController.init();
	}

	public void filter() {

		before((request, response) -> {
		});

		after((request, response) -> {
		});
	}

	private void defaultConfig() {
		ipAddress(serverHost);
		port(serverPort);
		staticFiles.externalLocation(staticResourcesPath);
	}
}
