package com.ring.constants;

public enum LoadType {
  

	  STANDARD("Standard"), EXTRA("Extra"),CUSTOM("Custom");
	  private String loadType;
	  

	  private LoadType(String loadType) {
	    this.loadType = loadType;
	  }

	  public String getLoadType() {
	    return loadType;
	  }

	  public static LoadType getLoadType(
	      String loadType) {

	    for (LoadType loadTypeRequest : LoadType.values()) {

	      if (loadTypeRequest.getLoadType() == loadType) {
	        return loadTypeRequest;
	      }

	    }
	    return null;

	  }


}
