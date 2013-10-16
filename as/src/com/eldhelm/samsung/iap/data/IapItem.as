package com.eldhelm.samsung.iap.data {
	/**
	 * ...
	 * @author Andrey Glavchev
	 */
	public class IapItem extends IapJsonObject {
		
		public var mItemId:String; 
		public var mItemName:String; 
		public var mItemPrice:String; 
		public var mItemPriceString:String;
		public var mCurrencyUnit:String; 
		public var mItemDesc:String; 
		public var mItemImageUrl:String; 
		public var mItemDownloadUrl:String; 
		public var mReserved1:String; 
		public var mReserved2:String; 
		public var mType:String;
		
		public var mSubscriptionDurationUnit:String;
		public var mSubscriptionDurationMultiplier:String;
		
		public function IapItem() {
			
		}
		
	}

}