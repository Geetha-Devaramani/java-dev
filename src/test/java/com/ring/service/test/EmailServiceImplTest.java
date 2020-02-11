/*package com.ring.service.test;

import static org.testng.Assert.assertThrows;

import com.ring.bo.UserBo;
import com.ring.bo.UserProfileBo;
import com.ring.config.UrlProvider;
import com.ring.exceptions.RingException;
import com.ring.service.EmailServiceImpl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.mail.internet.MimeMessage;

public class EmailServiceImplTest {

  @Mock
  JavaMailSender emailSender;

  @Mock
  UrlProvider urlProvider;

  @InjectMocks
  EmailServiceImpl service;

  @Mock
  MimeMessage mimeMessage;

  @BeforeMethod(
      alwaysRun = true)
  public void injectDoubles() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void sendUpdatePasswordLinkTest1() throws Exception {
    UserProfileBo userBo = new UserProfileBo();
    userBo.setLoginId("xyz@gmail.com");
    userBo.setUserName("Reeth");
    Mockito.when(urlProvider.getForgotPasswordUrl()).thenReturn("http://.....");
    Mockito.when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
    Mockito.doNothing().when(emailSender)
        .send(Mockito.any(MimeMessagePreparator.class));
    service.sendUpdatePasswordLink(userBo, "hgsfgsfsuhsuhjsgjos");
  }
  

}
*/