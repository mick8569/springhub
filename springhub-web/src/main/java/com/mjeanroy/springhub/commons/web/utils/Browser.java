package com.mjeanroy.springhub.commons.web.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Objects.firstNonNull;
import static com.mjeanroy.springhub.commons.web.utils.Browser.Company.findByUserAgent;
import static java.lang.Integer.parseInt;

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

		// User agent may be null
		userAgent = firstNonNull(request.getHeader("User-Agent"), "");
		initializeCompany();
		initializeVersion();
	}

	/** Parse version from user agent label. */
	private void initializeVersion() {
		String ua = userAgent.toLowerCase();

		if (company.equals(Company.MICROSOFT)) {
			String str = ua.substring(ua.indexOf("msie") + 5);
			version = str.substring(0, str.indexOf(";"));

		} else if (company.equals(Company.GOOGLE)) {
			version = getVersion(ua, "chrome/([0-9\\.]+)");

		} else if (company.equals(Company.MOZILLA)) {
			version = getVersion(ua, "firefox/([0-9\\.]+)");

		} else if (company.equals(Company.APPLE)) {
			version = getVersion(ua, "version/([0-9\\.]+)");

		} else if (company.equals(Company.OPERA)) {
			version = getVersion(ua, "version/([0-9\\.]+)");

		} else if (!ua.isEmpty()) {
			int tmpPos;
			String tmpString = (ua.substring(tmpPos = (ua.indexOf("/")) + 1, tmpPos + ua.indexOf(" "))).trim();
			version = tmpString.substring(0, tmpString.indexOf(" "));
		}

		if (version != null && !version.isEmpty()) {
			mainVersion = version.substring(0, version.indexOf("."));
			minorVersion = version.substring(version.indexOf(".") + 1).trim();
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
		String ua = userAgent.toLowerCase();
		company = findByUserAgent(ua);
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
		int v = parseInt(mainVersion);
		return v <= 9;
	}

	/**
	 * Check if browser is Internet Explorer.
	 *
	 * @return True if browser is Internet Explorer, false otherwise.
	 */
	public boolean isIE() {
		return company.equals(Company.MICROSOFT);
	}

	/**
	 * Check if browser is Internet Explorer with version <= 8.
	 *
	 * @return True if browser is Internet Explorer with version <= 8, false otherwise.
	 */
	public boolean ltIE8() {
		return isIE() && parseInt(mainVersion) <= 8;
	}

	/**
	 * Check if browser is Internet Explorer with version <= 7.
	 *
	 * @return True if browser is Internet Explorer with version <= 7, false otherwise.
	 */
	public boolean ltIE7() {
		return isIE() && parseInt(mainVersion) <= 7;
	}

	/**
	 * Check if browser is Internet Explorer with version <= 6.
	 *
	 * @return True if browser is Internet Explorer with version <= 6, false otherwise.
	 */
	public boolean ltIE6() {
		return isIE() && parseInt(mainVersion) <= 6;
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

		private Company() {
		}

		private Company(String patternUserAgent, String browserName) {
			this.patternUserAgent = patternUserAgent;
			this.browserName = browserName;
		}

		public static Company findByUserAgent(String ua) {
			Company[] companies = Company.values();
			for (Company company : companies) {
				String patternUserAgent = company.patternUserAgent;
				if (patternUserAgent != null && ua.contains(patternUserAgent)) {
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
