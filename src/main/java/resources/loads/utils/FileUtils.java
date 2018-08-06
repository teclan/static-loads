package resources.loads.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.slf4j.Logger;

public class FileUtils {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FileUtils.class);


	public static boolean saveFileFromInputStream(InputStream stream, String path) {
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(path);
			byte[] buffer = new byte[1024 * 1024];
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
				fs.flush();
			}

		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		} finally {
			try {
				if (fs != null) {
					fs.close();
				}
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
				// NO LOG
			}
		}
		return true;
	}

	public static boolean deleteFile(String path) {
		try {
			File f = new File(path);
			if (f.exists()) {
				f.delete();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	 /**
     * @author Teclan 向文件追加内容，如果文件不存在，创建文件
     * @param fileName
     *            文件路径
     * @param content
     *            文件内容
     * 
     */
    public static void randomWrite2File(String fileName, String content) {
        RandomAccessFile randomFile = null;
        try {
            creatIfNeed(fileName);
            randomFile = new RandomAccessFile(fileName, "rw");
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (randomFile != null) {
                    randomFile.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
    
    public static void creatIfNeed(String fileName) {
        try {
            File parentFile = new File(fileName).getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            new File(fileName).createNewFile();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
