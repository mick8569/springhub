package com.mick8569.springhub.filters;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InternetExplorerFilterTest {

	@Test
	public void testInternetExplorerFilter_JS_Pattern() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/js/app.js");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request, Mockito.never()).getHeader(Mockito.anyString());
	}

	@Test
	public void testInternetExplorerFilter_Rest_Pattern() throws Exception {
		FilterConfig config = Mockito.mock(FilterConfig.class);
		Mockito.when(config.getInitParameter("restPattern")).thenReturn("/controllers");

		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(config);

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/controllers/user");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request, Mockito.never()).getHeader(Mockito.anyString());
	}

	@Test
	public void testInternetExplorerFilter_CSS_Pattern() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/css/app.css");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request, Mockito.never()).getHeader(Mockito.anyString());
	}

	@Test
	public void testInternetExplorerFilter_PNG_Pattern() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/images/img.png");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request, Mockito.never()).getHeader(Mockito.anyString());
	}

	@Test
	public void testInternetExplorerFilter_JPG_Pattern() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/images/img.jpg");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request, Mockito.never()).getHeader(Mockito.anyString());
	}

	@Test
	public void testInternetExplorerFilter_JPEG_Pattern() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/images/img.jpeg");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request, Mockito.never()).getHeader(Mockito.anyString());
	}

	@Test
	public void testInternetExplorerFilter_GIF_Pattern() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/images/img.gif");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request, Mockito.never()).getHeader(Mockito.anyString());
	}

	@Test
	public void testInternetExplorerFilter_SVG_Pattern() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/images/img.svg");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request, Mockito.never()).getHeader(Mockito.anyString());
	}

	@Test
	public void testInternetExplorerFilter_MP3_Pattern() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/sounds/sound.mp3");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request, Mockito.never()).getHeader(Mockito.anyString());
	}

	@Test
	public void testInternetExplorerFilter_OGG_Pattern() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/sounds/sound.ogg");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request, Mockito.never()).getHeader(Mockito.anyString());
	}

	@Test
	public void testInternetExplorerFilter_SWF_Pattern() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/flash/anim.swf");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request, Mockito.never()).getHeader(Mockito.anyString());
	}

	@Test
	public void testInternetExplorerFilter_FLV_Pattern() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/flash/anim.flv");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request, Mockito.never()).getHeader(Mockito.anyString());
	}

	@Test
	public void testInternetExplorerFilter_ChromeShouldNotRedirect() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/mon-url");
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request).getHeader("User-Agent");
	}

	@Test
	public void testInternetExplorerFilter_FirefoxShouldNotRedirect() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/mon-url");
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:17.0) Gecko/20100101 Firefox/17.0");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request).getHeader("User-Agent");
	}

	@Test
	public void testInternetExplorerFilter_SafariShouldNotRedirect() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/mon-url");
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/537.13+ (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request).getHeader("User-Agent");
	}

	@Test
	public void testInternetExplorerFilter_OperaShouldNotRedirect() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/mon-url");
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Opera/12.80 (Windows NT 5.1; U; en) Presto/2.10.289 Version/12.02");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request).getHeader("User-Agent");
	}

	@Test
	public void testInternetExplorerFilter_IE6ShouldRedirect() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/mon-url");
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/4.0 (compatible; MSIE 6.1; Windows XP)");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain, Mockito.never()).doFilter(request, response);
		Mockito.verify(response).sendRedirect("/#/mon-url");
	}

	@Test
	public void testInternetExplorerFilter_IE7ShouldRedirect() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/mon-url");
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows; U; MSIE 7.0; Windows NT 6.0; en-US");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain, Mockito.never()).doFilter(request, response);
		Mockito.verify(response).sendRedirect("/#/mon-url");
	}

	@Test
	public void testInternetExplorerFilter_IE8ShouldRedirect() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/mon-url");
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; GTB7.4; InfoPath.2; SV1; .NET CLR 3.3.69573; WOW64; en-US)");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain, Mockito.never()).doFilter(request, response);
		Mockito.verify(response).sendRedirect("/#/mon-url");
	}

	@Test
	public void testInternetExplorerFilter_IE9ShouldRedirect() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/mon-url");
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (Windows; U; MSIE 9.0; WIndows NT 9.0; en-US))");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain, Mockito.never()).doFilter(request, response);
		Mockito.verify(response).sendRedirect("/#/mon-url");
	}

	@Test
	public void testInternetExplorerFilter_IE10ShouldRedirect() throws Exception {
		InternetExplorerFilter filter = new InternetExplorerFilter();
		filter.init(Mockito.mock(FilterConfig.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);

		Mockito.when(request.getContextPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/mon-url");
		Mockito.when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");

		filter.doFilter(request, response, filterChain);
		Mockito.verify(filterChain).doFilter(request, response);
		Mockito.verify(request).getHeader("User-Agent");
	}
}
