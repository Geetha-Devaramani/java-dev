package com.ring.bo;

public class ResponseDataBo extends ResponseBo {

  private Object data;

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "ResponseDataBo [[" + super.toString() + "], data=" + data + "]";
  }

}
