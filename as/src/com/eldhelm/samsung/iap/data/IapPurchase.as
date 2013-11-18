package com.eldhelm.samsung.iap.data {
	/**
	 * ...
	 * @author Andrey Glavchev
	 */
	public class IapPurchase extends IapPurchasedItem {
		
		public var itemId:String;
		public var errorString:String;		
		public var statusCode:int;		
		public var mVerifyUrl:String;
		
		public function IapPurchase() {
			super();
		}
		
	}

}