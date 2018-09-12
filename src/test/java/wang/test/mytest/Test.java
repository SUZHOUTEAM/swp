package wang.test.mytest;

import cn.jiguang.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.util.Base64Util;
import com.mlsc.yifeiwang.sms.common.CheckSumBuilder;
import com.mlsc.yifeiwang.im.common.YunXinConstants;
import com.mlsc.yifeiwang.im.common.YunXinStatusCode;
import com.taobao.api.internal.util.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("deprecation")
public class Test {

	static String AppKey = "1a88926e4245ddf4440a0c8923e2ede5";
	static String AppSecret = "64948e4bfdbe";

	public static final String IM_APP_ENV = "IM_APP_ENV";
	private static Map<String, String> keyValueMap = new HashMap<String, String>(24);
	private static Map<String, String> valueKeyMap = new HashMap<String, String>(24);
	private static JSONObject imEnvJson;

	static {
		try (InputStream in = Test.class.getClassLoader().getResourceAsStream("appSettings.properties")) {
			Properties prop = new Properties();
			prop.load(in);
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				if (!key.startsWith("druid")) {
					String value = prop.getProperty(key);
					if (StringUtils.isNotEmpty(value)) {
						value = new String(value.getBytes("ISO8859_1"), "utf-8");
						keyValueMap.put(key, value);
						valueKeyMap.put(value, key);
					}
				}
			}
			if (keyValueMap.containsKey(IM_APP_ENV)) {
				if (keyValueMap.get(IM_APP_ENV).toLowerCase().startsWith("test")) {
					imEnvJson = JSONObject.parseObject(keyValueMap.get("IM_APP_ENV_TEST"));
				}
				if (keyValueMap.get(IM_APP_ENV).toLowerCase().startsWith("online")) {
					imEnvJson = JSONObject.parseObject(keyValueMap.get("IM_APP_ENV_ONLINE"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		return keyValueMap.get(key);
	}

	public static void updateData(String key, String value) {
		keyValueMap.put(key, value);
		valueKeyMap.put(value, key);
	}

	public static String getKey(String value) {
		return valueKeyMap.get(value);
	}

	public static JSONObject getImEnv() {
		return imEnvJson;
	}
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		String s = "mlsc";
		System.out.println("原字符串：" + s);
		String encryptString = Base64Util.encryptBASE64(s);
		System.out.println("加密后：" + encryptString);
		System.out.println("解密后：" + Base64Util.decryptBASE64(encryptString));
	}


//		JSONObject json = getImEnv();
//
//		AppKey = json.getString(Constant.IM_APP_ENV_APPKEY);
//		AppSecret = json.getString(Constant.IM_APP_ENV_SECRET);
//
//		String token = genterateIMToken("12100000001","测试1");
//		String token = refreshToken("1805111682766");
//		System.out.println(token);//6cbf7509cb9b704c8b6dd5285570b17a



	static void testYinxin() {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try(CloseableHttpClient httpClient = httpClientBuilder.build()) {
			String url = "https://api.netease.im/nimserver/user/create.action";
			HttpPost httpPost = new HttpPost(url);

			String nonce = "12345";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(AppSecret, nonce, curTime);// 参考
			// 计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", AppKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			String token = "";
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accid", "18051116827"));// {"desc":"already
			// register","code":414}
			nvps.add(new BasicNameValuePair("name", "zhanglei"));
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
				HttpResponse response = httpClient.execute(httpPost);
				JSONObject jObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
				System.out.println(jObject.toJSONString());
				jObject = JSONObject.parseObject(jObject.getString("info"));
				token = jObject.getString("token");
				// 打印执行结果
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static JSONObject executeAction(String actionUrl, List<NameValuePair> nvps) {
		JSONObject jObject = null;


		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try(CloseableHttpClient httpClient = httpClientBuilder.build()) {
			HttpPost httpPost = new HttpPost(actionUrl);
			String nonce = "12345";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(AppSecret, nonce, curTime);// 参考
			// 设置请求的header
			httpPost.addHeader("AppKey", AppKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
				HttpResponse response = httpClient.execute(httpPost);
				jObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
			} catch (Exception e) {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jObject;
	}

	public static String refreshToken(String phoneNo) throws Exception {
		String token = "";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>(1);
		nvps.add(new BasicNameValuePair("accid", phoneNo));
		try {
			JSONObject jsonObject = executeAction(YunXinConstants.IM_REFRESH_TOKEN_ACTION,	nvps);
			System.out.println(jsonObject.toJSONString());
			YunXinStatusCode statusCode = YunXinStatusCode.getStatusCode(jsonObject);
			if (isNotNullAndSuccess(statusCode)) {
				token = jsonObject.getJSONObject("info").getString("token");
			}
		} catch (Exception e) {
		}
		return token;
	}

	public static String genterateIMToken(String phoneNo, String enterName) throws Exception  {
		String token  = "";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("accid", phoneNo));
		nvps.add(new BasicNameValuePair("name", enterName));
		try {
			JSONObject jsonObject = executeAction(YunXinConstants.IM_USER_CREATE_ACTION, nvps);/*{ "code":200,  "info":{"token":"xx","accid":"xx","name":"xx"} }*/
			YunXinStatusCode statusCode = YunXinStatusCode.getStatusCode(jsonObject);
			if(null != statusCode){
				if (statusCode.equals(YunXinStatusCode.SUCCESS)) {
					token = jsonObject.getJSONObject("info").getString("token");
				}else if(statusCode.equals(YunXinStatusCode.ALREADY_REGISTER_ERROR)){
					token = refreshToken(phoneNo);
				}else if(statusCode.equals(YunXinStatusCode.CURTIME_EXPIRED_ERROR)){
					token = refreshToken(phoneNo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	private static boolean isNotNullAndSuccess(YunXinStatusCode statusCode) {
		return null != statusCode && statusCode.equals(YunXinStatusCode.SUCCESS);
	}




}
