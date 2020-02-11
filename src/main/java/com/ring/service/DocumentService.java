package com.ring.service;

import com.ring.exceptions.RingException;

import java.util.List;

public interface DocumentService {

  
  List<String> getListOfDocument() throws RingException;

}
