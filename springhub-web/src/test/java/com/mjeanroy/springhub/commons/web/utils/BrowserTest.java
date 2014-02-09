package com.mjeanroy.springhub.commons.web.utils;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static com.mjeanroy.springhub.commons.web.utils.Browser.Company;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BrowserTest {

	@Test
	public void testUserAgentNull() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("User-Agent")).thenReturn(null);

		Browser browser = new Browser(request);
		assertThat(browser.getCompany()).isNotNull().isEqualTo(Company.UNKNOWN);
		assertThat(browser.getMainVersion()).isNull();
		assertThat(browser.getMinorVersion()).isNull();
		assertThat(browser.getVersion()).isNull();
		assertThat(browser.getName()).isNull();
		assertThat(browser.getUserAgent()).isNotNull().isEmpty();

		assertThat(browser.isIE()).isFalse();
		assertThat(browser.ltIE6()).isFalse();
		assertThat(browser.ltIE7()).isFalse();
		assertThat(browser.ltIE8()).isFalse();
		assertThat(browser.ltIE9()).isFalse();
	}

	@Test
	public void testBrowserChrome() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11");

		Browser browser = new Browser(request);
		assertThat(browser).isNotNull();
		assertThat(browser.getCompany()).isNotNull().isEqualTo(Company.GOOGLE);
		assertThat(browser.getMainVersion()).isNotNull().isEqualTo("23");
		assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0.1271.97");
		assertThat(browser.getVersion()).isNotNull().isEqualTo("23.0.1271.97");
		assertThat(browser.getName()).isNotNull().isEqualTo("Google Chrome");
		assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11");

		assertThat(browser.isIE()).isFalse();
		assertThat(browser.ltIE6()).isFalse();
		assertThat(browser.ltIE7()).isFalse();
		assertThat(browser.ltIE8()).isFalse();
		assertThat(browser.ltIE9()).isFalse();
	}

	@Test
	public void testBrowserChrome32() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");

		Browser browser = new Browser(request);
		assertThat(browser).isNotNull();
		assertThat(browser.getCompany()).isNotNull().isEqualTo(Company.GOOGLE);
		assertThat(browser.getMainVersion()).isNotNull().isEqualTo("32");
		assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0.1700.107");
		assertThat(browser.getVersion()).isNotNull().isEqualTo("32.0.1700.107");
		assertThat(browser.getName()).isNotNull().isEqualTo("Google Chrome");
		assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");

		assertThat(browser.isIE()).isFalse();
		assertThat(browser.ltIE6()).isFalse();
		assertThat(browser.ltIE7()).isFalse();
		assertThat(browser.ltIE8()).isFalse();
		assertThat(browser.ltIE9()).isFalse();
	}

	@Test
	public void testBrowserFirefox() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:17.0) Gecko/20100101 Firefox/17.0");

		Browser browser = new Browser(request);
		assertThat(browser).isNotNull();
		assertThat(browser.getCompany()).isNotNull().isEqualTo(Company.MOZILLA);
		assertThat(browser.getMainVersion()).isNotNull().isEqualTo("17");
		assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0");
		assertThat(browser.getVersion()).isNotNull().isEqualTo("17.0");
		assertThat(browser.getName()).isNotNull().isEqualTo("Firefox");
		assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:17.0) Gecko/20100101 Firefox/17.0");

		assertThat(browser.isIE()).isFalse();
		assertThat(browser.ltIE6()).isFalse();
		assertThat(browser.ltIE7()).isFalse();
		assertThat(browser.ltIE8()).isFalse();
		assertThat(browser.ltIE9()).isFalse();
	}

	@Test
	public void testBrowserSafari() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/537.13+ (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2");

		Browser browser = new Browser(request);
		assertThat(browser).isNotNull();
		assertThat(browser.getCompany()).isNotNull().isEqualTo(Company.APPLE);
		assertThat(browser.getMainVersion()).isNotNull().isEqualTo("5");
		assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("1.7");
		assertThat(browser.getVersion()).isNotNull().isEqualTo("5.1.7");
		assertThat(browser.getName()).isNotNull().isEqualTo("Safari");
		assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/537.13+ (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2");

		assertThat(browser.isIE()).isFalse();
		assertThat(browser.ltIE6()).isFalse();
		assertThat(browser.ltIE7()).isFalse();
		assertThat(browser.ltIE8()).isFalse();
		assertThat(browser.ltIE9()).isFalse();
	}

	@Test
	public void testBrowserOpera() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("User-Agent")).thenReturn("Opera/12.80 (Windows NT 5.1; U; en) Presto/2.10.289 Version/12.02");

		Browser browser = new Browser(request);
		assertThat(browser).isNotNull();
		assertThat(browser.getCompany()).isNotNull().isEqualTo(Company.OPERA);
		assertThat(browser.getMainVersion()).isNotNull().isEqualTo("12");
		assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("02");
		assertThat(browser.getVersion()).isNotNull().isEqualTo("12.02");
		assertThat(browser.getName()).isNotNull().isEqualTo("Opera");
		assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Opera/12.80 (Windows NT 5.1; U; en) Presto/2.10.289 Version/12.02");

		assertThat(browser.isIE()).isFalse();
		assertThat(browser.ltIE6()).isFalse();
		assertThat(browser.ltIE7()).isFalse();
		assertThat(browser.ltIE8()).isFalse();
		assertThat(browser.ltIE9()).isFalse();
	}

	@Test
	public void testBrowserIE6() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("User-Agent")).thenReturn("Mozilla/4.0 (compatible; MSIE 6.1; Windows XP)");

		Browser browser = new Browser(request);
		assertThat(browser).isNotNull();
		assertThat(browser.getCompany()).isNotNull().isEqualTo(Company.MICROSOFT);
		assertThat(browser.getMainVersion()).isNotNull().isEqualTo("6");
		assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("1");
		assertThat(browser.getVersion()).isNotNull().isEqualTo("6.1");
		assertThat(browser.getName()).isNotNull().isEqualTo("Internet Explorer");
		assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/4.0 (compatible; MSIE 6.1; Windows XP)");

		assertThat(browser.isIE()).isTrue();
		assertThat(browser.ltIE6()).isTrue();
		assertThat(browser.ltIE7()).isTrue();
		assertThat(browser.ltIE8()).isTrue();
		assertThat(browser.ltIE9()).isTrue();
	}

	@Test
	public void testBrowserIE7() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows; U; MSIE 7.0; Windows NT 6.0; en-US");

		Browser browser = new Browser(request);
		assertThat(browser).isNotNull();
		assertThat(browser.getCompany()).isNotNull().isEqualTo(Company.MICROSOFT);
		assertThat(browser.getMainVersion()).isNotNull().isEqualTo("7");
		assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0");
		assertThat(browser.getVersion()).isNotNull().isEqualTo("7.0");
		assertThat(browser.getName()).isNotNull().isEqualTo("Internet Explorer");
		assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (Windows; U; MSIE 7.0; Windows NT 6.0; en-US");

		assertThat(browser.isIE()).isTrue();
		assertThat(browser.ltIE6()).isFalse();
		assertThat(browser.ltIE7()).isTrue();
		assertThat(browser.ltIE8()).isTrue();
		assertThat(browser.ltIE9()).isTrue();
	}

	@Test
	public void testBrowserIE8() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; GTB7.4; InfoPath.2; SV1; .NET CLR 3.3.69573; WOW64; en-US)");

		Browser browser = new Browser(request);
		assertThat(browser).isNotNull();
		assertThat(browser.getCompany()).isNotNull().isEqualTo(Company.MICROSOFT);
		assertThat(browser.getMainVersion()).isNotNull().isEqualTo("8");
		assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0");
		assertThat(browser.getVersion()).isNotNull().isEqualTo("8.0");
		assertThat(browser.getName()).isNotNull().isEqualTo("Internet Explorer");
		assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; GTB7.4; InfoPath.2; SV1; .NET CLR 3.3.69573; WOW64; en-US)");

		assertThat(browser.isIE()).isTrue();
		assertThat(browser.ltIE6()).isFalse();
		assertThat(browser.ltIE7()).isFalse();
		assertThat(browser.ltIE8()).isTrue();
		assertThat(browser.ltIE9()).isTrue();
	}

	@Test
	public void testBrowserIE9() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows; U; MSIE 9.0; WIndows NT 9.0; en-US))");

		Browser browser = new Browser(request);
		assertThat(browser).isNotNull();
		assertThat(browser.getCompany()).isNotNull().isEqualTo(Company.MICROSOFT);
		assertThat(browser.getMainVersion()).isNotNull().isEqualTo("9");
		assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0");
		assertThat(browser.getVersion()).isNotNull().isEqualTo("9.0");
		assertThat(browser.getName()).isNotNull().isEqualTo("Internet Explorer");
		assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (Windows; U; MSIE 9.0; WIndows NT 9.0; en-US))");

		assertThat(browser.isIE()).isTrue();
		assertThat(browser.ltIE6()).isFalse();
		assertThat(browser.ltIE7()).isFalse();
		assertThat(browser.ltIE8()).isFalse();
		assertThat(browser.ltIE9()).isTrue();
	}

	@Test
	public void testBrowserIE10() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");

		Browser browser = new Browser(request);
		assertThat(browser).isNotNull();
		assertThat(browser.getCompany()).isNotNull().isEqualTo(Company.MICROSOFT);
		assertThat(browser.getMainVersion()).isNotNull().isEqualTo("10");
		assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0");
		assertThat(browser.getVersion()).isNotNull().isEqualTo("10.0");
		assertThat(browser.getName()).isNotNull().isEqualTo("Internet Explorer");
		assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");

		assertThat(browser.isIE()).isTrue();
		assertThat(browser.ltIE6()).isFalse();
		assertThat(browser.ltIE7()).isFalse();
		assertThat(browser.ltIE8()).isFalse();
		assertThat(browser.ltIE9()).isFalse();
	}

	@Test
	public void testBrowserSafari7() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9) AppleWebKit/537.71 (KHTML, like Gecko) Version/7.0 Safari/537.71");

		Browser browser = new Browser(request);
		assertThat(browser).isNotNull();
		assertThat(browser.getCompany()).isNotNull().isEqualTo(Company.APPLE);
		assertThat(browser.getMainVersion()).isNotNull().isEqualTo("7");
		assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0");
		assertThat(browser.getVersion()).isNotNull().isEqualTo("7.0");
		assertThat(browser.getName()).isNotNull().isEqualTo("Safari");
		assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9) AppleWebKit/537.71 (KHTML, like Gecko) Version/7.0 Safari/537.71");

		assertThat(browser.isIE()).isFalse();
		assertThat(browser.ltIE6()).isFalse();
		assertThat(browser.ltIE7()).isFalse();
		assertThat(browser.ltIE8()).isFalse();
		assertThat(browser.ltIE9()).isFalse();
	}
}
