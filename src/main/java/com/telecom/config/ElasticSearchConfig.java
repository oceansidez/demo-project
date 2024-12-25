//package com.telecom.config;
//
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.security.KeyStore;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Objects;
//
//import javax.net.ssl.SSLContext;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.http.Header;
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
//import org.apache.http.impl.nio.reactor.IOReactorConfig;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.ssl.SSLContextBuilder;
//import org.apache.http.ssl.SSLContexts;
//import org.elasticsearch.client.Node;
//import org.elasticsearch.client.NodeSelector;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.stereotype.Component;
//
///**
// * ES全文检索配置文件
// *
// */
//
//@Component
//@RefreshScope
//@Configuration
//@PropertySource(value = "classpath:/application-elasticsearch.properties")
//@ConfigurationProperties(prefix = "es-config")
//public class ElasticSearchConfig {
//
//	// 是否开启ES
//	private Boolean isEnabled;
//
//	// 节点地址
//	private String[] host;
//	// 节点split键值对数量
//	private Integer hostPair;
//	// 请求模式
//	private String httpScheme;
//
//	// 是否开启自定义相关配置
//	private Boolean isSetHeader;
//	private Boolean isSetFailureListener;
//	private Boolean isSetThread;
//	private Boolean isSetTimeOut;
//	private Boolean isSetCredential;
//	private Boolean isSetSSL;
//	private Boolean isSetNodeSelector;
//	private Boolean isSetMyNodeSelector;
//
//	// 请求头
//	private String[] headers;
//	// 异步请求线程数
//	private Integer threads;
//	// 连接超时时间
//	private Integer connectTimeout;
//	// 套接字超时时间
//	private Integer socketTimeout;
//	// 安全认证用户名
//	private String credentialUsername;
//	// 安全认证密码
//	private String credentialPassword;
//	// keystore目录
//	private String keyStoreFilePath;
//	// keystore文件名
//	private String keyStoreFileName;
//	// keystore密码
//	private String keyStorePassword;
//
//	public RestClientBuilder restClientBuilder() {
//		if(isEnabled){
//			// 创建host节点集
//			HttpHost[] hosts = Arrays.stream(host).map(this::makeHttpHost)
//					.filter(Objects::nonNull).toArray(HttpHost[]::new);
//
//			RestClientBuilder clientBuilder = RestClient.builder(hosts);
//
//			// 自定义配置
//			clientBuilder = isSetHeader ? setHeader(clientBuilder) : clientBuilder;
//			clientBuilder = isSetFailureListener ? setFailureListener(clientBuilder) : clientBuilder;
//			clientBuilder = isSetThread ? setThread(clientBuilder) : clientBuilder;
//			clientBuilder = isSetTimeOut ? setTimeout(clientBuilder) : clientBuilder;
//			clientBuilder = isSetCredential ? setCredential(clientBuilder) : clientBuilder;
//			clientBuilder = isSetSSL ? setSSL(clientBuilder) : clientBuilder;
//			clientBuilder = isSetNodeSelector ? setNodeSelector(clientBuilder, isSetMyNodeSelector) : clientBuilder;
//
//			return clientBuilder;
//		}
//		else {
//			return null;
//		}
//	}
//
//	// 配置请求头
//	private RestClientBuilder setHeader(RestClientBuilder clientBuilder) {
//		// 设置请求头，每个请求都会带上这个请求头
//		List<BasicHeader> list = new ArrayList<BasicHeader>();
//		for (String header : headers) {
//			String[] pair = header.split("|#|");
//			list.add(new BasicHeader(pair[0], pair[1]));
//		}
//		Header[] defaultHeaders = new Header[list.size()];
//		list.toArray(defaultHeaders);
//		clientBuilder.setDefaultHeaders(defaultHeaders);
//		return clientBuilder;
//	}
//
//	// 配置失败监听
//	private RestClientBuilder setFailureListener(RestClientBuilder clientBuilder) {
//		// 设置监听器，每次节点失败都可以监听到，可以作额外处理
//		clientBuilder.setFailureListener(new RestClient.FailureListener() {
//			@Override
//			public void onFailure(Node node) {
//				super.onFailure(node);
//				System.out.println(node.getName() + "节点失败");
//			}
//		});
//		return clientBuilder;
//	}
//
//	// 配置HTTP异步请求ES的线程数
//	private RestClientBuilder setThread(RestClientBuilder clientBuilder) {
//
//		// 配置异步请求的线程数量，Apache Http Async Client默认启动一个调度程序线程，以及由连接管理器使用的许多工作线程
//		// （与本地检测到的处理器数量一样多，取决于Runtime.getRuntime().availableProcessors()返回的数量）。
//		clientBuilder
//				.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//					@Override
//					public HttpAsyncClientBuilder customizeHttpClient(
//							HttpAsyncClientBuilder httpAsyncClientBuilder) {
//						return httpAsyncClientBuilder
//								.setDefaultIOReactorConfig(IOReactorConfig
//										.custom().setIoThreadCount(threads)
//										.build());
//					}
//				});
//		return clientBuilder;
//	}
//
//	// 配置连接超时和套接字超时
//	private RestClientBuilder setTimeout(RestClientBuilder clientBuilder) {
//
//		// 配置请求超时，将连接超时（默认为1秒）和套接字超时（默认为30秒）增加，
//		// 这里配置完应该相应地调整最大重试超时（默认为30秒）
//		clientBuilder
//				.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
//					@Override
//					public RequestConfig.Builder customizeRequestConfig(
//							RequestConfig.Builder requestConfigBuilder) {
//						// 连接5秒超时，套接字连接60s超时
//						return requestConfigBuilder.setConnectTimeout(
//								connectTimeout).setSocketTimeout(socketTimeout);
//					}
//				});
//		return clientBuilder;
//	}
//
//	// 配置安全认证
//	private RestClientBuilder setCredential(RestClientBuilder clientBuilder) {
//
//		// 如果ES设置了密码，那这里也提供了一个基本的认证机制，下面设置了ES需要基本身份验证的默认凭据提供程序
//		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//		credentialsProvider.setCredentials(AuthScope.ANY,
//				new UsernamePasswordCredentials(credentialUsername,
//						credentialPassword));
//		clientBuilder
//				.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//					@Override
//					public HttpAsyncClientBuilder customizeHttpClient(
//							HttpAsyncClientBuilder httpClientBuilder) {
//						return httpClientBuilder
//								.setDefaultCredentialsProvider(credentialsProvider);
//					}
//				});
//
//		// 上面采用异步机制实现抢先认证，这个功能也可以禁用，这意味着每个请求都将在没有授权标头的情况下发送，然后查看它是否被接受，
//		// 并且在收到HTTP 401响应后，它再使用基本认证头重新发送完全相同的请求，这个可能是基于安全、性能的考虑
//		clientBuilder
//				.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//					@Override
//					public HttpAsyncClientBuilder customizeHttpClient(
//							HttpAsyncClientBuilder httpClientBuilder) {
//						// 禁用抢先认证的方式
//						httpClientBuilder.disableAuthCaching();
//						return httpClientBuilder
//								.setDefaultCredentialsProvider(credentialsProvider);
//					}
//				});
//		return clientBuilder;
//	}
//
//	// 配置通信加密
//	private RestClientBuilder setSSL(RestClientBuilder clientBuilder) {
//		try {
//			// 配置通信加密，有多种方式：setSSLContext、setSSLSessionStrategy和setConnectionManager(它们的重要性逐渐递增)
//			KeyStore truststore = KeyStore.getInstance("jks");
//			Path path = Paths.get(keyStoreFilePath, keyStoreFileName);
//			try (InputStream is = Files.newInputStream(path)) {
//				truststore.load(is, keyStorePassword.toCharArray());
//			}
//			SSLContextBuilder sslBuilder = SSLContexts.custom()
//					.loadTrustMaterial(truststore, null);
//			final SSLContext sslContext = sslBuilder.build();
//			clientBuilder
//					.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//						@Override
//						public HttpAsyncClientBuilder customizeHttpClient(
//								HttpAsyncClientBuilder httpClientBuilder) {
//							return httpClientBuilder.setSSLContext(sslContext);
//						}
//					});
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return clientBuilder;
//	}
//
//	// 配置自定义节点选择器
//	private RestClientBuilder setNodeSelector(RestClientBuilder clientBuilder,
//			Boolean flag) {
//		if (flag) {
//			clientBuilder.setNodeSelector(new NodeSelector() {
//				// 设置分配感知节点选择器，允许选择本地机架中的节点（如果有）
//				// 否则转到任何机架中的任何其他节点。
//				@Override
//				public void select(Iterable<Node> nodes) {
//					boolean foundOne = false;
//					for (Node node : nodes) {
//						String rackId = node.getAttributes().get("rack_id")
//								.get(0);
//						if ("rack_one".equals(rackId)) {
//							foundOne = true;
//							break;
//						}
//					}
//					if (foundOne) {
//						Iterator<Node> nodesIt = nodes.iterator();
//						while (nodesIt.hasNext()) {
//							Node node = nodesIt.next();
//							String rackId = node.getAttributes().get("rack_id")
//									.get(0);
//							if ("rack_one".equals(rackId) == false) {
//								nodesIt.remove();
//							}
//						}
//					}
//				}
//			});
//		} else {
//			// 配置节点选择器，客户端以循环方式将每个请求发送到每一个配置的节点上，
//			// 发送请求的节点，用于过滤客户端，将请求发送到这些客户端节点，默认向每个配置节点发送，
//			// 这个配置通常是用户在启用嗅探时向专用主节点发送请求（即只有专用的主节点应该被HTTP请求命中）
//			clientBuilder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
//		}
//		return clientBuilder;
//	}
//
//	// 组装host
//	private HttpHost makeHttpHost(String s) {
//		assert StringUtils.isNotEmpty(s);
//		String[] address = s.split(":");
//		if (address.length == hostPair) {
//			String ip = address[0];
//			int port = Integer.parseInt(address[1]);
//			return new HttpHost(ip, port, httpScheme);
//		} else {
//			return null;
//		}
//	}
//
//	public Integer getHostPair() {
//		return hostPair;
//	}
//
//	public void setHostPair(Integer hostPair) {
//		this.hostPair = hostPair;
//	}
//
//	public String getHttpScheme() {
//		return httpScheme;
//	}
//
//	public void setHttpScheme(String httpScheme) {
//		this.httpScheme = httpScheme;
//	}
//
//	public String[] getHost() {
//		return host;
//	}
//
//	public void setHost(String[] host) {
//		this.host = host;
//	}
//
//	public Boolean getIsSetHeader() {
//		return isSetHeader;
//	}
//
//	public void setIsSetHeader(Boolean isSetHeader) {
//		this.isSetHeader = isSetHeader;
//	}
//
//	public Boolean getIsSetFailureListener() {
//		return isSetFailureListener;
//	}
//
//	public void setIsSetFailureListener(Boolean isSetFailureListener) {
//		this.isSetFailureListener = isSetFailureListener;
//	}
//
//	public Boolean getIsSetThread() {
//		return isSetThread;
//	}
//
//	public void setIsSetThread(Boolean isSetThread) {
//		this.isSetThread = isSetThread;
//	}
//
//	public Boolean getIsSetTimeOut() {
//		return isSetTimeOut;
//	}
//
//	public void setIsSetTimeOut(Boolean isSetTimeOut) {
//		this.isSetTimeOut = isSetTimeOut;
//	}
//
//	public Boolean getIsSetCredential() {
//		return isSetCredential;
//	}
//
//	public void setIsSetCredential(Boolean isSetCredential) {
//		this.isSetCredential = isSetCredential;
//	}
//
//	public Boolean getIsSetSSL() {
//		return isSetSSL;
//	}
//
//	public void setIsSetSSL(Boolean isSetSSL) {
//		this.isSetSSL = isSetSSL;
//	}
//
//	public Boolean getIsSetNodeSelector() {
//		return isSetNodeSelector;
//	}
//
//	public void setIsSetNodeSelector(Boolean isSetNodeSelector) {
//		this.isSetNodeSelector = isSetNodeSelector;
//	}
//
//	public Boolean getIsSetMyNodeSelector() {
//		return isSetMyNodeSelector;
//	}
//
//	public void setIsSetMyNodeSelector(Boolean isSetMyNodeSelector) {
//		this.isSetMyNodeSelector = isSetMyNodeSelector;
//	}
//
//	public String[] getHeaders() {
//		return headers;
//	}
//
//	public void setHeaders(String[] headers) {
//		this.headers = headers;
//	}
//
//	public Integer getThreads() {
//		return threads;
//	}
//
//	public void setThreads(Integer threads) {
//		this.threads = threads;
//	}
//
//	public Integer getConnectTimeout() {
//		return connectTimeout;
//	}
//
//	public void setConnectTimeout(Integer connectTimeout) {
//		this.connectTimeout = connectTimeout;
//	}
//
//	public Integer getSocketTimeout() {
//		return socketTimeout;
//	}
//
//	public void setSocketTimeout(Integer socketTimeout) {
//		this.socketTimeout = socketTimeout;
//	}
//
//	public String getCredentialUsername() {
//		return credentialUsername;
//	}
//
//	public void setCredentialUsername(String credentialUsername) {
//		this.credentialUsername = credentialUsername;
//	}
//
//	public String getCredentialPassword() {
//		return credentialPassword;
//	}
//
//	public void setCredentialPassword(String credentialPassword) {
//		this.credentialPassword = credentialPassword;
//	}
//
//	public String getKeyStoreFilePath() {
//		return keyStoreFilePath;
//	}
//
//	public void setKeyStoreFilePath(String keyStoreFilePath) {
//		this.keyStoreFilePath = keyStoreFilePath;
//	}
//
//	public String getKeyStoreFileName() {
//		return keyStoreFileName;
//	}
//
//	public void setKeyStoreFileName(String keyStoreFileName) {
//		this.keyStoreFileName = keyStoreFileName;
//	}
//
//	public String getKeyStorePassword() {
//		return keyStorePassword;
//	}
//
//	public void setKeyStorePassword(String keyStorePassword) {
//		this.keyStorePassword = keyStorePassword;
//	}
//
//	public Boolean getIsEnabled() {
//		return isEnabled;
//	}
//
//	public void setIsEnabled(Boolean isEnabled) {
//		this.isEnabled = isEnabled;
//	}
//
//}
