package com.ring.controller.test;

import static org.testng.Assert.assertEquals;

import com.ring.bo.ClientBo;
import com.ring.bo.ResponseBo;
import com.ring.bo.ResponseDataBo;
import com.ring.controller.AppCoreController;
import com.ring.exceptions.RingException;
import com.ring.service.UserService;
import com.ring.util.ClientBoValidator;
import com.ring.util.ResponseUtil;
import com.ring.util.UserProfileBoValidator;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

@PrepareForTest({ResponseUtil.class})
@PowerMockIgnore("javax.management.*")
public class AppCoreControllerTest extends PowerMockTestCase {

  @Mock
  private UserService registerService;

  @Mock
  private ConsumerTokenServices consumerTokenServices;

  @Mock
  private UserProfileBoValidator userProfileBoValidator;

  @Mock
  private ClientBoValidator clientBoValidator;

  @InjectMocks
  AppCoreController controller;

  @BeforeMethod(
      alwaysRun = true)
  public void initMocks() throws IOException {
    MockitoAnnotations.initMocks(this);
  }

  ResponseDataBo responseDataBo = new ResponseDataBo();
  ResponseBo responseBo = new ResponseBo();

  @Test
  public void registerClientTest1() throws RingException {
    ClientBo clientBo = new ClientBo();
    clientBo.setClientId("789w6589265789");
    clientBo.setClientSecret("RingAuto");
    Mockito.doNothing().when(clientBoValidator)
        .validateForClientAddition(clientBo);
    Mockito.doNothing().when(registerService).registerClient(clientBo);

    responseBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
    responseBo.setMessage("Client added successfully");

    PowerMockito.mockStatic(ResponseUtil.class);
    Mockito.when(ResponseUtil.genericResponse(Mockito.anyString()))
        .thenReturn(responseBo);

    ResponseEntity<ResponseBo> response = new ResponseEntity<ResponseBo>(
        responseBo, HttpStatus.OK);

    assertEquals(response.toString(),
        controller.registerClient(clientBo).toString());
  }

  @Test
  public void screenDetailsTest1() throws RingException {
    ClientBo clientBo = new ClientBo();
    clientBo.setClientId("789w6589265789");
    clientBo.setClientSecret("RingAuto");
    clientBo.setUserId(123);
    
    Mockito.doNothing().when(clientBoValidator)
        .validateForNextScreenInfo(clientBo);
    Mockito.doNothing().when(registerService).registerClient(clientBo);
    
    
    Map<String,Object> map = new HashMap<>();
    map.put("code", 1);
    map.put("screenMessage", "Eula Screen");
    responseDataBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
    responseDataBo.setMessage("Next Screen Information");
    responseDataBo.setData(map);

    PowerMockito.mockStatic(ResponseUtil.class);
    Mockito.when(ResponseUtil.genericResponseData(Mockito.anyString(), Mockito.any(Object.class))).thenReturn(responseDataBo);

    ResponseEntity<ResponseDataBo> response = new ResponseEntity<ResponseDataBo>(
        responseDataBo, HttpStatus.OK);

    assertEquals(response.toString(),
        controller.screenDetails(clientBo).toString());
  }
  
  @Test
  public void logoutTest() throws RingException {
    HttpServletRequest request = new HttpServletRequest() {
      
      @Override
      public AsyncContext startAsync(ServletRequest servletRequest,
          ServletResponse servletResponse) throws IllegalStateException {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public AsyncContext startAsync() throws IllegalStateException {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public void setCharacterEncoding(String env)
          throws UnsupportedEncodingException {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void setAttribute(String name, Object o) {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void removeAttribute(String name) {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public boolean isSecure() {
        // TODO Auto-generated method stub
        return false;
      }
      
      @Override
      public boolean isAsyncSupported() {
        // TODO Auto-generated method stub
        return false;
      }
      
      @Override
      public boolean isAsyncStarted() {
        // TODO Auto-generated method stub
        return false;
      }
      
      @Override
      public ServletContext getServletContext() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public int getServerPort() {
        // TODO Auto-generated method stub
        return 0;
      }
      
      @Override
      public String getServerName() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getScheme() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public RequestDispatcher getRequestDispatcher(String path) {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public int getRemotePort() {
        // TODO Auto-generated method stub
        return 0;
      }
      
      @Override
      public String getRemoteHost() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getRemoteAddr() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getRealPath(String path) {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public BufferedReader getReader() throws IOException {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getProtocol() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String[] getParameterValues(String name) {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public Enumeration<String> getParameterNames() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public Map<String, String[]> getParameterMap() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getParameter(String name) {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public Enumeration<Locale> getLocales() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public int getLocalPort() {
        // TODO Auto-generated method stub
        return 0;
      }
      
      @Override
      public String getLocalName() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getLocalAddr() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public ServletInputStream getInputStream() throws IOException {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public DispatcherType getDispatcherType() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getContentType() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public long getContentLengthLong() {
        // TODO Auto-generated method stub
        return 0;
      }
      
      @Override
      public int getContentLength() {
        // TODO Auto-generated method stub
        return 0;
      }
      
      @Override
      public String getCharacterEncoding() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public Enumeration<String> getAttributeNames() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public Object getAttribute(String name) {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public AsyncContext getAsyncContext() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public <T extends HttpUpgradeHandler> T upgrade(
          Class<T> httpUpgradeHandlerClass) throws IOException, ServletException {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public void logout() throws ServletException {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void login(String username, String password) throws ServletException {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public boolean isUserInRole(String role) {
        // TODO Auto-generated method stub
        return false;
      }
      
      @Override
      public boolean isRequestedSessionIdValid() {
        // TODO Auto-generated method stub
        return false;
      }
      
      @Override
      public boolean isRequestedSessionIdFromUrl() {
        // TODO Auto-generated method stub
        return false;
      }
      
      @Override
      public boolean isRequestedSessionIdFromURL() {
        // TODO Auto-generated method stub
        return false;
      }
      
      @Override
      public boolean isRequestedSessionIdFromCookie() {
        // TODO Auto-generated method stub
        return false;
      }
      
      @Override
      public Principal getUserPrincipal() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public HttpSession getSession(boolean create) {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public HttpSession getSession() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getServletPath() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getRequestedSessionId() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public StringBuffer getRequestURL() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getRequestURI() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getRemoteUser() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getQueryString() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getPathTranslated() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getPathInfo() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public Collection<Part> getParts() throws IOException, ServletException {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public Part getPart(String name) throws IOException, ServletException {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getMethod() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public int getIntHeader(String name) {
        // TODO Auto-generated method stub
        return 0;
      }
      
      @Override
      public Enumeration<String> getHeaders(String name) {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public Enumeration<String> getHeaderNames() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getHeader(String name) {
        // TODO Auto-generated method stub
        return "Bearer 890jiou8o3u8923v5725n290";
      }
      
      @Override
      public long getDateHeader(String name) {
        // TODO Auto-generated method stub
        return 0;
      }
      
      @Override
      public Cookie[] getCookies() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getContextPath() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String getAuthType() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public String changeSessionId() {
        // TODO Auto-generated method stub
        return null;
      }
      
      @Override
      public boolean authenticate(HttpServletResponse response)
          throws IOException, ServletException {
        // TODO Auto-generated method stub
        return false;
      }
    };
    
    Mockito.when(consumerTokenServices.revokeToken(Mockito.anyString())).thenReturn(true);
    responseBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
    responseBo.setMessage("User logged out successfully");

    PowerMockito.mockStatic(ResponseUtil.class);
    Mockito.when(ResponseUtil.genericResponse(Mockito.anyString()))
        .thenReturn(responseBo);

    ResponseEntity<ResponseBo> response = new ResponseEntity<ResponseBo>(
        responseBo, HttpStatus.OK);

    assertEquals(response.toString(),
        controller.logout(request).toString());
  }
  
  

}
