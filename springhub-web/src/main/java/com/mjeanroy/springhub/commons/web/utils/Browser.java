package com.mjeanroy.springhub.commons.web.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Objects.firstNonNull;
import static com.mjeanroy.springhub.commons.web.utils.Browser.Company.findByUserAgent;
import static java.lang.Integer.parseInt;

public class Browser {

	/** User agent of client */
	private final String userAgent;

	/** Company of client browser */
	private final Company company;

	/** Full version of client browser */
	private final String version;

	/** Major version of client browser */
	private final String mainVersion;

	/** Minor version of client browser */
	private final String minorVersion;

	public Browser(HttpServletRequest request) {
		super();

		// User agent may be null
		userAgent = firstNonNull(request.getHeader("User-Agent"), "");

		company = parseCompany();
		version = parseVersion();
		mainVersion = parseMainVersion();
		minorVersion = parseMinorVersion();
	}

	/**
	 * Parse version from user agent label.
	 *
	 * @return Browser version.
	 */
	private String parseVersion() {
		return company.parseVersion(userAgent.toLowerCase());
	}

	/**
	 * Check if version is empty.
	 *
	 * @return True if version field is empty, false otherwise.
	 */
	private boolean isVersionEmpty() {
		return version == null || version.isEmpty();
	}

	/**
	 * Parse browser major version.
	 *
	 * @return Browser major version.
	 */
	private String parseMainVersion() {
		return isVersionEmpty() ? null : version.substring(0, version.indexOf("."));
	}

	/**
	 * Parse browser minor version.
	 *
	 * @return Browser minor version.
	 */
	private String parseMinorVersion() {
		return isVersionEmpty() ? null : version.substring(version.indexOf(".") + 1).trim();
	}

	/**
	 * Parse browser company.
	 *
	 * @return Browser company.
	 */
	private Company parseCompany() {
		String ua = userAgent.toLowerCase();
		return findByUserAgent(ua);
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
		MICROSOFT("msie", "Internet Explorer", new UserAgentParser() {
			@Override
			public String parse(String userAgent) {
				String str = userAgent.substring(userAgent.indexOf("msie") + 5);
				return str.substring(0, str.indexOf(";"));
			}
		}),

		GOOGLE("chrome", "Google Chrome", new PatternUserAgentParser("chrome/([0-9\\.]+)")),

		MOZILLA("firefox", "Firefox", new PatternUserAgentParser("firefox/([0-9\\.]+)")),

		OPERA("opera", "Opera", new PatternUserAgentParser("version/([0-9\\.]+)")),

		APPLE("safari", "Safari", new PatternUserAgentParser("version/([0-9\\.]+)")),

		UNKNOWN(new UserAgentParser() {
			@Override
			public String parse(String userAgent) {
				if (userAgent.isEmpty()) {
					return null;
				}

				int tmpPos;
				String tmpString = (userAgent.substring(tmpPos = (userAgent.indexOf("/")) + 1, tmpPos + userAgent.indexOf(" "))).trim();
				return tmpString.substring(0, tmpString.indexOf(" "));
			}
		});

		private final String patternUserAgent;

		private final String browserName;

		private final UserAgentParser parser;

		private Company(UserAgentParser parser) {
			this.patternUserAgent = null;
			this.browserName = null;
			this.parser = parser;
		}

		private Company(String patternUserAgent, String browserName, UserAgentParser parser) {
			this.patternUserAgent = patternUserAgent;
			this.browserName = browserName;
			this.parser = parser;
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

		private String parseVersion(String userAgent) {
			return parser.parse(userAgent);
		}
	}

	private interface UserAgentParser {
		String parse(String userAgent);
	}

	private static class PatternUserAgentParser implements UserAgentParser {

		private final String pattern;

		public PatternUserAgentParser(String pattern) {
			this.pattern = pattern;
		}

		@Override
		public String parse(String userAgent) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(userAgent);
			return m.find() ? m.group(1) : null;
		}
	}

}
