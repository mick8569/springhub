package com.mick8569.springhub.commons.web.utils;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

public class BrowserTest {

	@Test
	public void testBrowserChrome() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11");

		Browser browser = new Browser(request);
		Assertions.assertThat(browser).isNotNull();
		Assertions.assertThat(browser.getCompany()).isNotNull().isEqualTo(Browser.Company.GOOGLE);
		Assertions.assertThat(browser.getMainVersion()).isNotNull().isEqualTo("23");
		Assertions.assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0.1271.97");
		Assertions.assertThat(browser.getVersion()).isNotNull().isEqualTo("23.0.1271.97");
		Assertions.assertThat(browser.getName()).isNotNull().isEqualTo("Google Chrome");
		Assertions.assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11");

		Assertions.assertThat(browser.isIE()).isFalse();
		Assertions.assertThat(browser.ltIE6()).isFalse();
		Assertions.assertThat(browser.ltIE7()).isFalse();
		Assertions.assertThat(browser.ltIE8()).isFalse();
		Assertions.assertThat(browser.ltIE9()).isFalse();
	}

	@Test
	public void testBrowserFirefox() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:17.0) Gecko/20100101 Firefox/17.0");

		Browser browser = new Browser(request);
		Assertions.assertThat(browser).isNotNull();
		Assertions.assertThat(browser.getCompany()).isNotNull().isEqualTo(Browser.Company.MOZILLA);
		Assertions.assertThat(browser.getMainVersion()).isNotNull().isEqualTo("17");
		Assertions.assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0");
		Assertions.assertThat(browser.getVersion()).isNotNull().isEqualTo("17.0");
		Assertions.assertThat(browser.getName()).isNotNull().isEqualTo("Firefox");
		Assertions.assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:17.0) Gecko/20100101 Firefox/17.0");

		Assertions.assertThat(browser.isIE()).isFalse();
		Assertions.assertThat(browser.ltIE6()).isFalse();
		Assertions.assertThat(browser.ltIE7()).isFalse();
		Assertions.assertThat(browser.ltIE8()).isFalse();
		Assertions.assertThat(browser.ltIE9()).isFalse();
	}

	@Test
	public void testBrowserSafari() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/537.13+ (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2");

		Browser browser = new Browser(request);
		Assertions.assertThat(browser).isNotNull();
		Assertions.assertThat(browser.getCompany()).isNotNull().isEqualTo(Browser.Company.APPLE);
		Assertions.assertThat(browser.getMainVersion()).isNotNull().isEqualTo("5");
		Assertions.assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("1.7");
		Assertions.assertThat(browser.getVersion()).isNotNull().isEqualTo("5.1.7");
		Assertions.assertThat(browser.getName()).isNotNull().isEqualTo("Safari");
		Assertions.assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/537.13+ (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2");

		Assertions.assertThat(browser.isIE()).isFalse();
		Assertions.assertThat(browser.ltIE6()).isFalse();
		Assertions.assertThat(browser.ltIE7()).isFalse();
		Assertions.assertThat(browser.ltIE8()).isFalse();
		Assertions.assertThat(browser.ltIE9()).isFalse();
	}

	@Test
	public void testBrowserOpera() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Opera/12.80 (Windows NT 5.1; U; en) Presto/2.10.289 Version/12.02");

		Browser browser = new Browser(request);
		Assertions.assertThat(browser).isNotNull();
		Assertions.assertThat(browser.getCompany()).isNotNull().isEqualTo(Browser.Company.OPERA);
		Assertions.assertThat(browser.getMainVersion()).isNotNull().isEqualTo("12");
		Assertions.assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("02");
		Assertions.assertThat(browser.getVersion()).isNotNull().isEqualTo("12.02");
		Assertions.assertThat(browser.getName()).isNotNull().isEqualTo("Opera");
		Assertions.assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Opera/12.80 (Windows NT 5.1; U; en) Presto/2.10.289 Version/12.02");

		Assertions.assertThat(browser.isIE()).isFalse();
		Assertions.assertThat(browser.ltIE6()).isFalse();
		Assertions.assertThat(browser.ltIE7()).isFalse();
		Assertions.assertThat(browser.ltIE8()).isFalse();
		Assertions.assertThat(browser.ltIE9()).isFalse();
	}

	@Test
	public void testBrowserIE6() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/4.0 (compatible; MSIE 6.1; Windows XP)");

		Browser browser = new Browser(request);
		Assertions.assertThat(browser).isNotNull();
		Assertions.assertThat(browser.getCompany()).isNotNull().isEqualTo(Browser.Company.MICROSOFT);
		Assertions.assertThat(browser.getMainVersion()).isNotNull().isEqualTo("6");
		Assertions.assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("1");
		Assertions.assertThat(browser.getVersion()).isNotNull().isEqualTo("6.1");
		Assertions.assertThat(browser.getName()).isNotNull().isEqualTo("Internet Explorer");
		Assertions.assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/4.0 (compatible; MSIE 6.1; Windows XP)");

		Assertions.assertThat(browser.isIE()).isTrue();
		Assertions.assertThat(browser.ltIE6()).isTrue();
		Assertions.assertThat(browser.ltIE7()).isTrue();
		Assertions.assertThat(browser.ltIE8()).isTrue();
		Assertions.assertThat(browser.ltIE9()).isTrue();
	}

	@Test
	public void testBrowserIE7() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows; U; MSIE 7.0; Windows NT 6.0; en-US");

		Browser browser = new Browser(request);
		Assertions.assertThat(browser).isNotNull();
		Assertions.assertThat(browser.getCompany()).isNotNull().isEqualTo(Browser.Company.MICROSOFT);
		Assertions.assertThat(browser.getMainVersion()).isNotNull().isEqualTo("7");
		Assertions.assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0");
		Assertions.assertThat(browser.getVersion()).isNotNull().isEqualTo("7.0");
		Assertions.assertThat(browser.getName()).isNotNull().isEqualTo("Internet Explorer");
		Assertions.assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (Windows; U; MSIE 7.0; Windows NT 6.0; en-US");

		Assertions.assertThat(browser.isIE()).isTrue();
		Assertions.assertThat(browser.ltIE6()).isFalse();
		Assertions.assertThat(browser.ltIE7()).isTrue();
		Assertions.assertThat(browser.ltIE8()).isTrue();
		Assertions.assertThat(browser.ltIE9()).isTrue();
	}

	@Test
	public void testBrowserIE8() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; GTB7.4; InfoPath.2; SV1; .NET CLR 3.3.69573; WOW64; en-US)");

		Browser browser = new Browser(request);
		Assertions.assertThat(browser).isNotNull();
		Assertions.assertThat(browser.getCompany()).isNotNull().isEqualTo(Browser.Company.MICROSOFT);
		Assertions.assertThat(browser.getMainVersion()).isNotNull().isEqualTo("8");
		Assertions.assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0");
		Assertions.assertThat(browser.getVersion()).isNotNull().isEqualTo("8.0");
		Assertions.assertThat(browser.getName()).isNotNull().isEqualTo("Internet Explorer");
		Assertions.assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; GTB7.4; InfoPath.2; SV1; .NET CLR 3.3.69573; WOW64; en-US)");

		Assertions.assertThat(browser.isIE()).isTrue();
		Assertions.assertThat(browser.ltIE6()).isFalse();
		Assertions.assertThat(browser.ltIE7()).isFalse();
		Assertions.assertThat(browser.ltIE8()).isTrue();
		Assertions.assertThat(browser.ltIE9()).isTrue();
	}

	@Test
	public void testBrowserIE9() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows; U; MSIE 9.0; WIndows NT 9.0; en-US))");

		Browser browser = new Browser(request);
		Assertions.assertThat(browser).isNotNull();
		Assertions.assertThat(browser.getCompany()).isNotNull().isEqualTo(Browser.Company.MICROSOFT);
		Assertions.assertThat(browser.getMainVersion()).isNotNull().isEqualTo("9");
		Assertions.assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0");
		Assertions.assertThat(browser.getVersion()).isNotNull().isEqualTo("9.0");
		Assertions.assertThat(browser.getName()).isNotNull().isEqualTo("Internet Explorer");
		Assertions.assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (Windows; U; MSIE 9.0; WIndows NT 9.0; en-US))");

		Assertions.assertThat(browser.isIE()).isTrue();
		Assertions.assertThat(browser.ltIE6()).isFalse();
		Assertions.assertThat(browser.ltIE7()).isFalse();
		Assertions.assertThat(browser.ltIE8()).isFalse();
		Assertions.assertThat(browser.ltIE9()).isTrue();
	}

	@Test
	public void testBrowserIE10() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");

		Browser browser = new Browser(request);
		Assertions.assertThat(browser).isNotNull();
		Assertions.assertThat(browser.getCompany()).isNotNull().isEqualTo(Browser.Company.MICROSOFT);
		Assertions.assertThat(browser.getMainVersion()).isNotNull().isEqualTo("10");
		Assertions.assertThat(browser.getMinorVersion()).isNotNull().isEqualTo("0");
		Assertions.assertThat(browser.getVersion()).isNotNull().isEqualTo("10.0");
		Assertions.assertThat(browser.getName()).isNotNull().isEqualTo("Internet Explorer");
		Assertions.assertThat(browser.getUserAgent()).isNotNull().isEqualTo("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");

		Assertions.assertThat(browser.isIE()).isTrue();
		Assertions.assertThat(browser.ltIE6()).isFalse();
		Assertions.assertThat(browser.ltIE7()).isFalse();
		Assertions.assertThat(browser.ltIE8()).isFalse();
		Assertions.assertThat(browser.ltIE9()).isFalse();
	}
}
