package com.eldhelm.samsung.iap {
	import flash.events.Event;
	/**
	 * ...
	 * @author Andrey Glavchev
	 */
	public class IapEvent extends Event {
		
		/**
		 * Fires when the Iap object is ready for interaction
		 */
		public static const ON_READY:String = "iapEvent_ready";
		
		/**
		 * Fires when the user account is authorized
		 */
		public static const ON_INIT:String = "iapEvent_init";
		
		/**
		 * Fires when an error occures while interacting with the api
		 * Please refer to the Samsung IAP Dev guide for the correspsonging error code or erro message
		 */
		public static const ON_ERROR:String = "iapEvent_error";
		
		/**
		 * Fires when a purchase has failed 
		 */
		public static const ON_PURCHASE_FAILED:String = "iapEvent_payment_failed";
		
		/**
		 * Fires when a purchase has completed
		 * Please note that this doesn't mean the purchase is successfull
		 * You should check the sharedObject whether it is IapPurchase and it has no errorString
		 */
		public static const ON_PURCHASE_COMPLETED:String = "iapEvent_payment_completed";
		
		public var sharedObject:Object;
		
		public function IapEvent(type:String, sharedObject:Object = null, bubbles:Boolean = false, cancelable:Boolean = false) {
			super(type, bubbles, cancelable);
			this.sharedObject = sharedObject;
		}
		
	}

}