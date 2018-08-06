package resources.loads.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import resources.loads.utils.FileUtils;

import static spark.Spark.post;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.MultipartConfigElement;

@Singleton
public class FileController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
	private static String IMAGES_STORE_PATH;
	private static String PART;

	static {
		File file = new File("config/application.conf");
		Config root = ConfigFactory.parseFile(file);
		Config config = root.getConfig("config");
		IMAGES_STORE_PATH = config.getString("images.store.path");
		PART = config.getString("upload.part");
	}

	public void init() {

		post("/upload/images", (req, res) -> {

			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

			String path = req.raw().getPart(PART).getSubmittedFileName();

			String filePath = IMAGES_STORE_PATH + File.separator + path;

			JSONObject result = new JSONObject();
			result.put("filePath", filePath);

			try {
				try (InputStream input = req.raw().getPart(PART).getInputStream()) { // getPart needs to use same
					FileUtils.creatIfNeed(filePath);
					Files.copy(input, new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
				result.put("code", "200");
				result.put("massage", "上传成功");
				LOGGER.info("文件存储成功：{}", filePath);
			} catch (IOException e) {
				LOGGER.info("文件存储失败：{}", filePath);
				LOGGER.error(e.getMessage(), e);
				result.put("code", "500");
				result.put("massage", "上传失败");
				result.put("exception", e.getMessage());
			}

			return result;
		});
	}

}
