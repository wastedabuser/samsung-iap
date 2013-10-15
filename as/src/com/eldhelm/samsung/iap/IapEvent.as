package com.eldhelm.samsung.iap {
	import flash.events.Event;
	/**
	 * ...
	 * @author Andrey Glavchev
	 */
	public class IapEvent extends Event {
		
		public static const ON_READY:String = "iapEvent_ready";
		public static const ON_INIT:String = "iapEvent_init";
		public static const ON_PURCHASE:String = "iapEvent_purchase";
		
		public var sharedObject:Object;
		
		public function IapEvent(type:String, sharedObject:Object, bubbles:Boolean = false, cancelable:Boolean = false) {
			super(type, bubbles, cancelable);
			this.sharedObject = sharedObject;
		}
		
	}

}