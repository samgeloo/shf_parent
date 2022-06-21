import com.atguigu.util.QiniuUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;



/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : PACKAGE_NAME
 * @Description :
 * @date : 2022-06-10 11:32
 **/
public class QiniuTest {

        /*
        测试七牛云文件上传
        Zone.zone0():华东
        Zone.zone1():华北
        Zone.zone2():华南
         */

    @Test
    public void testUpload() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传.去注册中心将秘钥AK和Sk拷贝过来
        String accessKey = "QPmn_6MMdh7k-j4HWmqfhRO3WrP7Eb-IOE_ulFeZ";
        String secretKey = "_AZrb2TcUW-ZgJkjUtAcZYxRiA0pz4OzhPrsbhcq";
        String bucket = "samge";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "D:/image/samge/samge001.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "班长最爱";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    @Test
    public void testDelete() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
//...其他参数参考类注释

        String accessKey = "QPmn_6MMdh7k-j4HWmqfhRO3WrP7Eb-IOE_ulFeZ";
        String secretKey = "_AZrb2TcUW-ZgJkjUtAcZYxRiA0pz4OzhPrsbhcq";
        String bucket = "samge";
        //删除文件的名字
        String key = "FixqAAhv1zNsWZpwM3AvSg3uoaBA";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }


    @Test
    public void testUpload2() {
        byte[] bytes = new byte[5];


            String fileName = "30156415";
            QiniuUtil.upload2Qiniu(bytes, fileName);

    }

    @Test
    public void testUpload3()  {
        String filePath = "D:/image/samge/samge001.jpg";
        String fileName = "154154";
     QiniuUtil.upload2Qiniu(filePath, fileName);

    }
}
