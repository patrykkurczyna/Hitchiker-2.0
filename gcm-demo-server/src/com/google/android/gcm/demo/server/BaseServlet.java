/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gcm.demo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Skeleton class for all servlets in this package.
 */
@SuppressWarnings("serial")
abstract class BaseServlet extends HttpServlet {

	static final String GET_ALL_DEVICES_URL = "http://87.206.242.147:1111/webservice/drivers/search/findAllDevices";
	// change to true to allow GET calls
	static final boolean DEBUG = true;

	protected final Logger logger = Logger.getLogger(getClass().getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if (DEBUG) {
			doPost(req, resp);
		} else {
			super.doGet(req, resp);
		}
	}

	protected String getParameter(HttpServletRequest req, String parameter)
			throws ServletException {
		String value = req.getParameter(parameter);
		if (isEmptyOrNull(value)) {
			if (DEBUG) {
				StringBuilder parameters = new StringBuilder();
				@SuppressWarnings("unchecked")
				Enumeration<String> names = req.getParameterNames();
				while (names.hasMoreElements()) {
					String name = names.nextElement();
					String param = req.getParameter(name);
					parameters.append(name).append("=").append(param)
							.append("\n");
				}
				logger.fine("parameters: " + parameters);
			}
			throw new ServletException("Parameter " + parameter + " not found");
		}
		return value.trim();
	}

	protected String getParameter(HttpServletRequest req, String parameter,
			String defaultValue) {
		String value = req.getParameter(parameter);
		if (isEmptyOrNull(value)) {
			value = defaultValue;
		}
		return value.trim();
	}

	protected void setSuccess(HttpServletResponse resp) {
		setSuccess(resp, 0);
	}

	protected void setSuccess(HttpServletResponse resp, int size) {
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("text/plain");
		resp.setContentLength(size);
	}

	protected boolean isEmptyOrNull(String value) {
		return value == null || value.trim().length() == 0;
	}

	protected List<String> getAllDevices() {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(GET_ALL_DEVICES_URL);
		// Depends on your web service
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("Accept", "application/json");

		InputStream inputStream = null;
		String result = null;
		ArrayList<String> listdata = new ArrayList<String>();
		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			inputStream = entity.getContent();
			// json is UTF-8 by default
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			result = sb.toString();

			JSONObject json = new JSONObject(result);

			JSONArray jArray = json.getJSONArray("content");

			if (jArray != null) {
				for (int i = 0; i < jArray.length(); i++) {
					listdata.add(jArray.get(i).toString());
				}
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Cannot make http request");
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception squish) {
			}
		}
		return listdata;
	}

}
