 package com.simplereddit.model;

import java.io.BufferedReader;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;

import java.net.URI;

import java.net.URISyntaxException;

import java.security.KeyManagementException;

import java.security.KeyStoreException;

import java.security.NoSuchAlgorithmException;

import java.security.cert.CertificateException;

import java.security.cert.X509Certificate;

import java.util.ArrayList;

import java.util.List;

import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpMessage;

import org.apache.http.HttpResponse;

import org.apache.http.NameValuePair;

import org.apache.http.client.CookieStore;

import org.apache.http.client.HttpClient;

import org.apache.http.client.config.RequestConfig;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.client.methods.HttpPut;

import org.apache.http.client.methods.HttpRequestBase;

import org.apache.http.client.protocol.HttpClientContext;

import org.apache.http.client.utils.URIBuilder;

import org.apache.http.config.Registry;

import org.apache.http.config.RegistryBuilder;

import org.apache.http.conn.socket.ConnectionSocketFactory;

import org.apache.http.conn.socket.PlainConnectionSocketFactory;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import org.apache.http.entity.ContentType;

import org.apache.http.entity.FileEntity;

import org.apache.http.impl.client.BasicCookieStore;

import org.apache.http.impl.client.HttpClientBuilder;

import org.apache.http.impl.client.LaxRedirectStrategy;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import org.apache.http.message.BasicNameValuePair;

import org.apache.http.protocol.BasicHttpContext;

import org.apache.http.protocol.HttpContext;

import org.apache.http.ssl.SSLContextBuilder;

import org.apache.http.ssl.TrustStrategy;

public class HTTPRequester {

	/**
	 * 
	 * CookieStore that is used to manage the cookies. Helps remember if a login
	 * 
	 * has already occurred
	 * 
	 */

	private static CookieStore cookieStore;

	/********************
	 * 
	 * Basic constructor*
	 * 
	 ********************/

	public HTTPRequester() {

		super();

		cookieStore = new BasicCookieStore();

	}

	public String getHttp(String url, String path, File params, File headers) {

		HttpClientBuilder httpBuild = HttpClientBuilder.create();

		httpBuild.setRedirectStrategy(new LaxRedirectStrategy());

		addSSLContext(httpBuild);

		HttpClient httpClient = httpBuild.build();

		URI uri = makeHttpsURI(url, path, params);

		HttpGet httpGet = constructHttpGet(uri, headers);

		HttpContext httpContext = makeHttpContext();

		System.out.println("Sending 'GET' request to URL: " + httpGet.getURI());

		HttpResponse response = executeConnection(httpClient, httpGet,

		httpContext);

		return printResult(response);

	}

	public String postHttp(String url, String path, File params,

	File entityFile, File headers) {

		HttpClientBuilder httpBuild = HttpClientBuilder.create();

		httpBuild.setRedirectStrategy(new LaxRedirectStrategy());

		addSSLContext(httpBuild);

		HttpClient httpClient = httpBuild.build();

		URI uri = makeHttpsURI(url, path, params);

		HttpPost httpPost = constructHttpPost(uri, headers, entityFile);

		HttpContext httpContext = makeHttpContext();

		System.out.println("PATH: " + httpPost.getURI().getPath());

		System.out.println("Sending 'POST' request to URL: " + httpPost.getURI());

		HttpResponse response = executeConnection(httpClient, httpPost, httpContext);

		return printResult(response);

	}

	public String putHttp(String url, String path, File entityFile, File headers) {

		HttpClientBuilder httpBuild = HttpClientBuilder.create();

		httpBuild.setRedirectStrategy(new LaxRedirectStrategy());

		addSSLContext(httpBuild);

		HttpClient httpClient = httpBuild.build();

		URI uri = makeHttpsURI(url, path, null);

		HttpPut httpPut = constructHttpPut(uri, headers, entityFile);

		HttpContext httpContext = makeHttpContext();

		System.out.println("Sending 'PUT' request to URL: " + httpPut.getURI());

		HttpResponse response = executeConnection(httpClient, httpPut, httpContext);

		return printResult(response);

	}

	private HttpPut constructHttpPut(URI uri, File headers, File entityFile) {

		HttpPut httpPut = new HttpPut(uri);

		if (null != headers) {

			try {

				addHeadersToRequest(httpPut, headers);

			} catch (FileNotFoundException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

		// Creates an entity from our file

		FileEntity entity = new FileEntity(entityFile, ContentType.create(

		"text/plain", "UTF-8"));

		// attaches the entity onto our post. The post now has it's data

		httpPut.setEntity(entity);

		// sets the cookie handler to be standard

		RequestConfig config = RequestConfig.custom().setCookieSpec("STANDARD")

		.build();

		httpPut.setConfig(config);

		return httpPut;

	}

	private HttpPost constructHttpPost(URI uri, File headers, File entityFile) {

		HttpPost httpPost = new HttpPost(uri);

		if (null != headers) {

			try {

				addHeadersToRequest(httpPost, headers);

			} catch (FileNotFoundException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

		// Creates an entity from our file

		FileEntity entity = new FileEntity(entityFile, ContentType.create(

		"text/plain", "UTF-8"));

		// attaches the entity onto our post. The post now has it's data

		httpPost.setEntity(entity);

		// sets the cookie handler to be standard

		RequestConfig config = RequestConfig.custom().setCookieSpec("STANDARD")

		.build();

		httpPost.setConfig(config);

		return httpPost;

	}

	private HttpGet constructHttpGet(URI uri, File headers) {

		HttpGet httpGet = new HttpGet(uri);

		try {

			addHeadersToRequest(httpGet, headers);

		} catch (FileNotFoundException | NullPointerException e) {

		}

		// sets the httpGet to have the standard cookie handler

		RequestConfig config = RequestConfig.custom().setCookieSpec("STANDARD")

		.build();

		httpGet.setConfig(config);

		return httpGet;

	}

	private String printResult(HttpResponse response) {

		System.out.println("Response Code : "

		+ response.getStatusLine().getStatusCode());

		BufferedReader rd = null;

		try {

			rd = new BufferedReader(new InputStreamReader(response.getEntity()

			.getContent()));

		} catch (UnsupportedOperationException | IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		StringBuffer result = new StringBuffer();

		String line = "";

		try {

			while ((line = rd.readLine()) != null) {

				result.append(line);

			}

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		System.out.println(result.toString());

		return result.toString();

	}

	private HttpResponse executeConnection(HttpClient httpClient,

	HttpRequestBase message, HttpContext context) {

		HttpResponse response = null;

		try {

			response = httpClient.execute(message, context);

		} catch (IOException e) {

			System.out.println("HTTPS connection does not work"

			+ "\n Attempting HTTP connection");

			try {

				changeGetToHttp(message);

				response = httpClient.execute(message, context);

			} catch (IOException e1) {

				// e1.printStackTrace();

			}

		}

		return response;

	}

	private HttpContext makeHttpContext() {

		HttpContext httpContext = new BasicHttpContext();

		// make an http context with the given cookieStore

		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

		return httpContext;

	}

	private void addSSLContext(HttpClientBuilder httpBuild) {

		// setup a Trust Strategy that allows all certificates.

		SSLContext sslContext = null;

		try {

			sslContext = new SSLContextBuilder().loadTrustMaterial(null,

			new TrustStrategy() {

				public boolean isTrusted(X509Certificate[] arg0,

				String arg1) throws CertificateException {

					return true;

				}

			}).build();

		} catch (KeyManagementException | NoSuchAlgorithmException

		| KeyStoreException e2) {

			e2.printStackTrace();

		}

		httpBuild.setSslcontext(sslContext);

		// don't check Hostnames, either.

		// -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if

		// you don't want to weaken

		@SuppressWarnings("deprecation")

		HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

		// here's the special part:

		// -- need to create an SSL Socket Factory, to use our weakened

		// "trust strategy";

		// -- and create a Registry, to register it.

		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(

		sslContext, hostnameVerifier);

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder

		.<ConnectionSocketFactory> create()

		.register("http",

		PlainConnectionSocketFactory.getSocketFactory())

		.register("https", sslSocketFactory).build();

		// now, we create connection-manager using our Registry.

		// -- allows multi-threaded use

		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(

		socketFactoryRegistry);

		httpBuild.setConnectionManager(connMgr);

	}

	private void changeGetToHttp(HttpRequestBase message) {

		// Creates the URI of our destination

		URI uri = message.getURI();

		URIBuilder preURI = new URIBuilder(uri);

		preURI.setScheme("http");

		try {

			uri = preURI.build();

		} catch (URISyntaxException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		message.setURI(uri);

	}

	private URI makeHttpsURI(String url, String path, File params) {

		// Creates the URI of our destination

		URIBuilder preURI = new URIBuilder();

		// if the params file is null, do not add any parameters

		if (null == params) {

			preURI.setScheme("https").setHost(url).setPath(path);

		} else {

			// Creates the URI of our destination

			try {

				preURI.setScheme("https").setHost(url).setPath(path)

				.setParameters(fileToNameValuePairs(params));

			} catch (FileNotFoundException | UnsupportedEncodingException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

		URI uri = null;

		try {

			uri = preURI.build();

		} catch (URISyntaxException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return uri;

	}

	private static void addHeadersToRequest(HttpMessage request, File headers)

	throws FileNotFoundException {

		Scanner input = new Scanner(headers);

		String header;

		String seperatedHeader[];

		String key;

		String value;

		while (input.hasNextLine()) {

			header = input.nextLine();

			seperatedHeader = header.split(":", 2);

			key = seperatedHeader[0];

			value = seperatedHeader[1];

			request.addHeader(key, value);

		}

		input.close();

	}

	/******************************************************************
	 * 
	 * Converts a file that has input in the format: key : value, into a list of
	 * 
	 * NameValuePair objects
	 *
	 * 
	 * 
	 * @param file
	 * 
	 *            File containing the things needed to be converted
	 * 
	 * @return A list of NameValuePairs from the file
	 * 
	 * @throws FileNotFoundException
	 * 
	 * @throws UnsupportedEncodingException
	 * 
	 ******************************************************************/

	private static List<NameValuePair> fileToNameValuePairs(File file)

	throws FileNotFoundException, UnsupportedEncodingException {

		Scanner input = new Scanner(file);

		// Line in the file that contains an unparsed property

		String property;

		// Parsed property. holds key in [0] and value in [1]

		String[] seperatedProperty;

		String key;

		String value;

		List<NameValuePair> data = new ArrayList<NameValuePair>(2);

		while (input.hasNextLine()) {

			property = input.nextLine();
						
			seperatedProperty = property.split(":", 2);

			key = seperatedProperty[0];

			value = seperatedProperty[1];

			data.add(new BasicNameValuePair(key, value));

		}

		input.close();

		return data;

	}

}