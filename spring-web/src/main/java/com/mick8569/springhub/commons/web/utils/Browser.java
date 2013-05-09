package com.mick8569.springhub.commons.web.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Browser {

	/** User agent of client */
	private String userAgent;

	/** Company of client browser */
	private Company company;

	/** Full version of client browser */
	private String version;

	/** Major version of client browser */
	private String mainVersion;

	/** Minor version of client browser */
	private String minorVersion;

	public Browser(HttpServletRequest request) {
		super();
		this.userAgent = request.getHeader("User-Agent");
		this.initializeCompany();
		this.initializeVersion();
	}

	/** Parse version from user agent label. */
	private void initializeVersion() {
		String ua = this.userAgent.toLowerCase();

		if (this.company.equals(Company.MICROSOFT)) {
			String str = ua.substring(ua.indexOf("msie") + 5);
			this.version = str.substring(0, str.indexOf(";"));

		} else if (this.company.equals(Company.GOOGLE)) {
			this.version = getVersion(ua, "chrome/([0-9\\.]+)");

		} else if (this.company.equals(Company.MOZILLA)) {
			this.version = getVersion(ua, "firefox/([0-9\\.]+)");

		} else if (this.company.equals(Company.APPLE)) {
			this.version = getVersion(ua, "version/([0-9\\.]+)");

		} else if (this.company.equals(Company.OPERA)) {
			this.version = getVersion(ua, "version/([0-9\\.]+)");

		} else {
			int tmpPos;
			String tmpString;
			tmpString = (ua.substring(tmpPos = (ua.indexOf("/")) + 1, tmpPos + ua.indexOf(" "))).trim();
			this.version = tmpString.substring(0, tmpString.indexOf(" "));
		}

		if ((this.version != null) && (this.version.length() > 0)) {
			this.mainVersion = this.version.substring(0, this.version.indexOf("."));
			this.minorVersion = this.version.substring(this.version.indexOf(".") + 1).trim();
		}
	}

	private String getVersion(String ua, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(ua);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	private void initializeCompany() {
		String ua = this.userAgent.toLowerCase();
		this.company = Company.findByUserAgent(ua);
	}

	/**
	 * Check if browser is Internet Explorer with version <= 9.
	 *
	 * @return True if browser is Internet Explorer with version <= 9, false otherwise.
	 */
	public boolean ltIE9() {
		if (!isIE()) {
			return false;
		}
		int v = Integer.parseInt(this.mainVersion);
		return v <= 9;
	}

	/**
	 * Check if browser is Internet Explorer.
	 *
	 * @return True if browser is Internet Explorer, false otherwise.
	 */
	public boolean isIE() {
		return this.company.equals(Company.MICROSOFT);
	}

	/**
	 * Check if browser is Internet Explorer with version <= 8.
	 *
	 * @return True if browser is Internet Explorer with version <= 8, false otherwise.
	 */
	public boolean ltIE8() {
		if (!isIE()) {
			return false;
		}
		int v = Integer.parseInt(this.mainVersion);
		return v <= 8;
	}

	/**
	 * Check if browser is Internet Explorer with version <= 7.
	 *
	 * @return True if browser is Internet Explorer with version <= 7, false otherwise.
	 */
	public boolean ltIE7() {
		if (!isIE()) {
			return false;
		}
		int v = Integer.parseInt(this.mainVersion);
		return v <= 7;
	}

	/**
	 * Check if browser is Internet Explorer with version <= 6.
	 *
	 * @return True if browser is Internet Explorer with version <= 6, false otherwise.
	 */
	public boolean ltIE6() {
		if (!isIE()) {
			return false;
		}
		int v = Integer.parseInt(this.mainVersion);
		return v <= 6;
	}

	/**
	 * Get {@link #userAgent}
	 *
	 * @return {@link #userAgent}
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * Get {@link #company}
	 *
	 * @return {@link #company}
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Get browser commercial name.
	 *
	 * @return Commercial name of browser.
	 */
	public String getName() {
		return company.getBrowserName();
	}

	/**
	 * Get {@link #version}
	 *
	 * @return {@link #version}
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Get {@link #mainVersion}
	 *
	 * @return {@link #mainVersion}
	 */
	public String getMainVersion() {
		return mainVersion;
	}

	/**
	 * Get {@link #minorVersion}
	 *
	 * @return {@link #minorVersion}
	 */
	public String getMinorVersion() {
		return minorVersion;
	}

	public static enum Company {
		MICROSOFT("msie", "Internet Explorer"),
		GOOGLE("chrome", "Google Chrome"),
		MOZILLA("firefox", "Firefox"),
		OPERA("opera", "Opera"),
		APPLE("safari", "Safari"),
		UNKNOWN;

		private String patternUserAgent;

		private String browserName;

		private Company(String browserName) {
			this.patternUserAgent = null;
			this.browserName = browserName;
		}

		private Company(String patternUserAgent, String browserName) {
			this.patternUserAgent = patternUserAgent;
			this.browserName = browserName;
		}

		private Company() {
			this.patternUserAgent = null;
			this.browserName = "";
		}

		public static Company findByUserAgent(String ua) {
			Company[] companies = Company.values();
			for (Company company : companies) {
				String patternUserAgent = company.patternUserAgent;
				if ((patternUserAgent != null) && (ua.indexOf(patternUserAgent) > -1)) {
					return company;
				}
			}
			return Company.UNKNOWN;
		}

		public String getBrowserName() {
			return browserName;
		}
	}
}
