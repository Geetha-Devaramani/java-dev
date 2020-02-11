package com.ring.service;

import com.ring.bo.DocumentTypeBo;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentServiceImpl  implements  DocumentService{

  @Autowired
  DocumentTypeBo documentTypeBo;
  
  
  @Override
  public List<String> getListOfDocument() throws RingException {
    List<String> documentList = new ArrayList<String>();
    try {
      documentList = documentTypeBo.getDocumentType();
    } catch (Exception e) {
      throw new RingException(RingErrorCode.RA_8000, RingResponseCode.OK);
    }
    return documentList;
  }

}
