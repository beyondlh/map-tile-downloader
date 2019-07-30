package com.hdsx.lh.maptiles.core;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;

public class HttpTileLoadStrategy implements ITileLoadStrategy {
	@Override
	public final InputStream getTile(String url, int timeout) throws IOException {
		if (timeout <= 10)
		{
			timeout = 10;
		}
		HttpClient httpClient =  HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		System.out.println("executing request" + httpget.getRequestLine());
		CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpget);
		HttpEntity entity = response.getEntity();
//		httpClient.timeout = TimeSpan.FromSeconds(timeout);
		return entity.getContent();
	}
}