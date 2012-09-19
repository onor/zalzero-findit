package net.user1.union.zz.zalerioGame.lib.exception;

public class ZalerioBoardCoordinateOutOfBoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg = "";
	
	public ZalerioBoardCoordinateOutOfBoundException(Integer noOfCols,Integer noOfRows,Integer coordX,Integer coordY){
		super();
		msg = "Board coordinates out of bound exception [coordX:" + coordX + ",coordY:" + coordY + "]. Should be between [x:" + noOfCols + ",y:" + noOfRows + "]";
	}
	public ZalerioBoardCoordinateOutOfBoundException(Integer noOfCols,Integer noOfRows,Integer coordNum) {
		super();
		msg = "Board coordinates out of bound exception [coordNum:" + coordNum + "]. Should be between [x:" + noOfCols + ",y:" + noOfRows + "]";
	}
	
	public String getError(){
		return msg;
	}
	
	public String toString(){
		return msg;
	}
}
