# 静态资源加载服务

## 打包和运行

要求JDK1.8+

在项目下执行

```
mvn clean package
```

即可打包，解压后，执行  `bin/` 目录下的 `startup.bat` 即可运行程序

## 配置

```
config {

sever.port=9090
sever.host="10.0.0.134"

#  本程序加载的静态资源的路径,必须与本程序所在磁盘分区相同
#  不能跨磁盘分区,例如程序在c盘,静态资源在 F盘
#  这样文件可以上传成功,但无法加载
static.resource.path="E:\\static"

# 上传完文件时，表单中对应的 key
upload.part="file"

# 上传图片时的存储路径，必须是 static.resource.path 的子目录
images.store.path="E:\\static\\images"

}
```

例如以上配置，上传一张名为 `test.jpg` 的图片，图片将被存到 `E:\\static\\images\test.jpg`(替换同名文件),
要访问这种图片资源，访问 `http://10.0.0.134:9090/images/test.jpg`。
