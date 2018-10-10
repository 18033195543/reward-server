package com.kdl.common.util.netUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


public class HttpClientUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static HttpClientBuilder httpClientBuilder;
	private static HttpClientBuilder httpsClientBuilder;

	private static final int MAX_TIMEOUT = 7000;

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		requestConfig = configBuilder.build();
		httpClientBuilder = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig);
		httpsClientBuilder = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig);
	}

	public static CloseableHttpClient getClient(boolean isHttps) {
		if (isHttps) {
			return httpsClientBuilder.build();
		}
		return httpClientBuilder.build();
	}

	/**
	 * 发送 GET 请求（HTTP），不带输入数据
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, new HashMap<String, Object>());
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doGet(String url, Map<String, Object> params) {
		String apiUrl = url + doGetParam(params);
		String result = null;
		HttpResponse response = null;
		try {
			CloseableHttpClient httpclient = getClient(apiUrl.startsWith("https"));
			HttpGet httpPost = new HttpGet(apiUrl);
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			logger.info("url:{},param:{},responseCode:{},responseStr:{}", url, JSON.toJSONString(params),
					response.getStatusLine().getStatusCode(), result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String doGet(String url, Map<String, Object> params, Map<String, String> headers)
			throws ParseException, IOException {

		String apiUrl = params == null ? url : url + doGetParam(params);
		String result = null;
		HttpResponse response = null;
		try {
			CloseableHttpClient httpclient = getClient(apiUrl.startsWith("https"));
			HttpGet httpGet = new HttpGet(apiUrl);
			setHeaders(httpGet, headers);
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			logger.info("url:{},param:{},responseCode:{},responseStr:{}", url, JSON.toJSONString(params),
					response.getStatusLine().getStatusCode(), result);
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private static void setHeaders(HttpRequestBase request, Map<String, String> headers) {
		if (headers == null) {
			return;
		}
		if (request == null) {
			throw new RuntimeException("request must not be null");
		}
		Iterator<String> iterator = headers.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			request.setHeader(key, headers.get(key));
		}
	}

	/**
	 * 发送 POST 请求（HTTP），不带输入数据
	 * 
	 * @param apiUrl
	 * @return
	 */
	public static String doPost(String apiUrl) {
		return doPost(apiUrl, new HashMap<String, Object>());
	}

	/**
	 * 发送 POST 请求（HTTP），K-V形式
	 * 
	 * @param url
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPost(String url, Map<String, Object> params) {
		String httpStr = null;
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpClient = getClient(false);
			List<NameValuePair> pairList = doPostParam(params);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				httpStr = EntityUtils.toString(entity, "UTF-8");
			}
			logger.info("url:{},param:{},responseCode:{},responseStr:{}", url, JSON.toJSONString(params),
					response.getStatusLine().getStatusCode(), httpStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），K-V形式
	 * 
	 * @param url
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPostMulipart(String url, Map<String, String> params, Map<String, String> headers,
			File tmep) {
		String httpStr = null;
		CloseableHttpResponse response = null;
		try {
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("liveness_file", tmep);
			CloseableHttpClient httpClient = getClient(false);
			for (Entry<String, String> entry : params.entrySet()) {
				builder.addTextBody(entry.getKey(), entry.getValue(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(builder.build());
			setHeaders(httpPost, headers);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				httpStr = EntityUtils.toString(entity, "UTF-8");
			}
			logger.info("url:{},param:{},responseCode:{},responseStr:{}", url, JSON.toJSONString(params),
					response.getStatusLine().getStatusCode(), httpStr);
		} catch (IOException e) {
			logger.warn("连接商汤失败，url：{}，原因：{}", url, e.getMessage());
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 * 
	 * @param url
	 * @param json
	 *            json对象
	 * @return
	 */
	public static String doPost(String url, String json) {
		String httpStr = null;
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpClient = getClient(false);
			StringEntity stringEntity = new StringEntity(json, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				httpStr = EntityUtils.toString(entity, "UTF-8");
			}
			logger.info("url:{},param:{},responseCode:{},responseStr:{}", url, json.toString(),
					response.getStatusLine().getStatusCode(), httpStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），K-V形式
	 * 
	 * @param url
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPostSSL(String url, Map<String, Object> params) {
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			CloseableHttpClient httpClient = (CloseableHttpClient) getClient(true);
			List<NameValuePair> pairList = doPostParam(params);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				httpStr = EntityUtils.toString(entity, "utf-8");
			}
			logger.info("url:{},param:{},responseCode:{},responseStr:{}", url, JSON.toJSONString(params),
					response.getStatusLine().getStatusCode(), httpStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 * 
	 * @param url
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @return
	 */
	public static String doPostSSL(String url, Object json) {
		// CloseableHttpClient httpClient =
		// HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
		// .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();

		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			CloseableHttpClient httpClient = (CloseableHttpClient) getClient(true);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				httpStr = EntityUtils.toString(entity, "utf-8");
			}
			logger.info("url:{},param:{},responseCode:{},responseStr:{}", url, json.toString(),
					response.getStatusLine().getStatusCode(), httpStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 创建SSL安全连接
	 * 
	 * @return
	 */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return sslsf;
	}

	private static String doGetParam(Map<String, Object> params) {
		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : params.keySet()) {
			if (i == 0)
				param.append("?");
			else
				param.append("&");
			param.append(key).append("=").append(params.get(key));
			i++;
		}
		return param.toString();
	}

	private static List<NameValuePair> doPostParam(Map<String, Object> params) {
		List<NameValuePair> pairList = new ArrayList<>(params.size());
		for (Entry<String, Object> entry : params.entrySet()) {
			NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
			pairList.add(pair);
		}
		return pairList;
	}
	// public static CloseableHttpClient createSSLClientDefault(){
	// try {
	// SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new
	// TrustStrategy() {
	// //信任所有
	// public boolean isTrusted(X509Certificate[] chain,
	// String authType) throws CertificateException {
	// return true;
	// }
	// }).build();
	// SSLConnectionSocketFactory sslsf = new
	// SSLConnectionSocketFactory(sslContext);
	// return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	// } catch (KeyManagementException e) {
	// e.printStackTrace();
	// } catch (NoSuchAlgorithmException e) {
	// e.printStackTrace();
	// } catch (KeyStoreException e) {
	// e.printStackTrace();
	// }
	// return HttpClients.createDefault();
	// }
}