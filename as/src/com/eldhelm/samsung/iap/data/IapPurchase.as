package com.eldhelm.samsung.iap.data {
	/**
	 * ...
	 * @author Andrey Glavchev
	 */
	public class IapPurchase extends IapJsonObject {
		
		public var itemId:String;
		public var errorString:String;
		
		public var pucrhasedItem:IapPurchasedItem;
		
		public var mVerifyUrl:String;
		
		public function IapPurchase() {
			
		}
		
		override public function set jsonData(jsn:String):void {
			super.jsonData = jsn;
			pucrhasedItem = new IapPurchasedItem();
			pucrhasedItem.jsonData = jsn;
		}
		
	}

}