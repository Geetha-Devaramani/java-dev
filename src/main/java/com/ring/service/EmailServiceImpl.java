package com.ring.service;

import com.ring.bo.UserProfileBo;
import com.ring.config.MailProperties;
import com.ring.config.UrlProvider;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public JavaMailSender emailSender;

  @Autowired
  private UrlProvider urlProvider;
  
  @Autowired
  private MailProperties mailProperties;

  @Async
  public void sendUpdatePasswordLink(UserProfileBo user, String token)
      throws RingException {

    try {
      VelocityEngine velEng = initVelocityEngine();
      VelocityContext context = new VelocityContext();
      context.put("userName", user.getUserName());
      context.put("token", token);
      context.put("resetPasswordUrl", urlProvider.getForgotPasswordUrl());
      context.put("reportIncidentUrl", urlProvider.getReportIncidentUrl());
      Template tmplt = velEng.getTemplate(
          "templates/RingAutoForgotPasswordEmailTemplate.vm",
          "UTF-8");
      final StringWriter writer = new StringWriter();
      tmplt.merge(context, writer);

      MimeMessage mimeMessage = emailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
          true);
      mimeMessageHelper.setTo(String.valueOf(user.getLoginId()));
      mimeMessageHelper.setFrom(mailProperties.getFromAddress());
      mimeMessageHelper.setSubject("Reset Password Request");
      mimeMessageHelper.setText(writer.toString(), true);
      FileSystemResource file = new FileSystemResource(
          new ClassPathResource("ring_logo.png").getFile());
      mimeMessageHelper.addInline("ringLogo", file);

      emailSender.send(mimeMessage);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new RingException(RingErrorCode.RA_4000,
          RingResponseCode.OK);
    }

  }

  private VelocityEngine initVelocityEngine() {
    VelocityEngine velEng = new VelocityEngine();
    velEng.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    velEng.setProperty("classpath.resource.loader.class",
        ClasspathResourceLoader.class.getName());
    velEng.init();
    return velEng;
  }

}
