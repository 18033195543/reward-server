package com.kdl.common.framework.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class DefaultRequestWrapper extends HttpServletRequestWrapper {

//	private final String requestBody;
	private final byte[] body;


	public DefaultRequestWrapper(HttpServletRequest request) {
		super(request);
//		requestBody = readBody(request);
		body = HttpHelper.getBodyString(request).getBytes(Charset.forName("UTF-8"));

	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {

//		return new CustomServletInputStream(requestBody);
		 final ByteArrayInputStream bais = new ByteArrayInputStream(body);

	        return new ServletInputStream() {
	            @Override
	            public int read() throws IOException {
	                return bais.read();
	            }
	            @Override
	    		public boolean isFinished() {
	    			return bais.available() == 0;
	    		}
	     
	    		@Override
	    		public boolean isReady() {
	    			return true;
	    		}
	     
	    		@Override
	    		public void setReadListener(ReadListener listener) {
	    			throw new RuntimeException("Not implemented");
	    		}
	        };
	}

	@Override
	public String getHeader(String name) {
		return super.getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		return super.getHeaderNames();
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		return super.getHeaders(name);
	}
	
	private static String readBody(ServletRequest request) {
		StringBuilder sb = new StringBuilder();
		String inputLine;
		BufferedReader br = null;
		try {
			br = request.getReader();
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to read body.", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		return sb.toString();
	}

	private class CustomServletInputStream extends ServletInputStream {
		private ByteArrayInputStream buffer;
 
		public CustomServletInputStream(String body) {
			body = body == null ? "" : body;
			this.buffer = new ByteArrayInputStream(body.getBytes());
		}
 
		@Override
		public int read() throws IOException {
			return buffer.read();
		}
 
		@Override
		public boolean isFinished() {
			return buffer.available() == 0;
		}
 
		@Override
		public boolean isReady() {
			return true;
		}
 
		@Override
		public void setReadListener(ReadListener listener) {
			throw new RuntimeException("Not implemented");
		}
	}
}
