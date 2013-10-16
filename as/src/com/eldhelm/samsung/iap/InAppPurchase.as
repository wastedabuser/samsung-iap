package com.eldhelm.samsung.iap {
	import com.eldhelm.samsung.iap.data.IapItem;
	import com.eldhelm.samsung.iap.data.IapPurchasedItem;
	import flash.events.EventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	/**
	 * ...
	 * @author Andrey Glavchev
	 */
	public class InAppPurchase extends EventDispatcher {
		
		public static const CONSUMABLE:String = "00";
		public static const NON_CONSUMABLE:String = "01";
		public static const SUBSCRIPTION:String = "02";
		public static const ALL:String = "10";
		
		private var extContext:ExtensionContext;
		
		public function InAppPurchase() {
			extContext = ExtensionContext.createExtensionContext("com.eldhelm.samsung.iap", "");
			if (!extContext) {
				throw new Error("Samsung IAP extension is not supported on this platform.");
			}
			
			extContext.addEventListener(StatusEvent.STATUS, onStatus);
		}
		
		private function onStatus(event:StatusEvent):void {
			dispatchEvent(new IapEvent("iapEvent_" + event.level, extContext.actionScriptData, false, false));
			extContext.actionScriptData = null;
		}
		
		/**
		 * This function initializes your samsung account to purchase
		 * @param	mode Could be 0 (production), 1 (test mode, always success) or -1 (test mode, always fail)
		 */
		public function init(mode:int = 0):void {
			extContext.call("init", mode);
		}
		
		/**
		 * Returns a list of items available for purchase
		 * @param	itemGroupId
		 * @param	startNum defaults to 0
		 * @param	endNum defaults to 99
		 * @param	itemType defaults to ALL
		 * @return
		 */
		public function getItemList(itemGroupId:String, startNum:int = 0, endNum:int = 99, itemType:String = ALL):Vector.<IapItem> {
			return (Vector.<IapItem>)(extContext.call("getItemList", itemGroupId, startNum, endNum, itemType));
		}
		
		/**
		 * Returns a list of already purchased items
		 * @param	itemGroupId
		 * @param	startNum
		 * @param	endNum
		 * @param	startDate
		 * @param	endDate
		 * @return
		 */
		public function getItemsInbox(itemGroupId:String, startNum:int, endNum:int, startDate:String, endDate:String):Vector.<IapPurchasedItem> {
			return (Vector.<IapPurchasedItem>)(extContext.call("getItemsInbox", itemGroupId, startNum, endNum, startDate, endDate));
		}
		
		/**
		 * Initiate a purchase of an item
		 * @param	itemGroupId
		 * @param	itemId
		 */
		public function purchase(itemGroupId:String, itemId:String):void {
			extContext.call("purchase", itemGroupId, itemId);
		}
		
		/**
		 * Disposes the iap and related objects
		 */
		public function dispose():void {
			extContext.dispose();
			extContext = null;
		}
		
	}

}