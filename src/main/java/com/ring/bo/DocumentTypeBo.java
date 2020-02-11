package com.ring.bo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
public class DocumentTypeBo extends BaseBo {

  /**
   * 
   */
  private static final long serialVersionUID = -7183198163226361426L;

  @Value("#{'${documentType.name}'.split(',')}")
  private List<String> documentType;

  public List<String> getDocumentType() {
    return documentType;
  }

  public void setDocumentType(List<String> documentType) {
    this.documentType = documentType;
  }

}
