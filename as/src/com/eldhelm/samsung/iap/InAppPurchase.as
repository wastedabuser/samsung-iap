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
		
		public static const CONSUMABLE:String = 	"00";
		public static const NON_CONSUMABLE:String = "01";
		public static const SUBSCRIPTION:String = 	"02";
		public static const ALL:String = 			"10";
		
		private var extContext:ExtensionContext;
		private var inited:Boolean;
		private var _callbackMethod:String;
		private var _callbackArgs:Array;
		
		public function InAppPurchase() {
			extContext = ExtensionContext.createExtensionContext("com.eldhelm.samsung.iap", "");
			if (extContext != null) {
				extContext.call("initializeExtension");
				extContext.addEventListener(StatusEvent.STATUS, onStatus);				
			} else {
				inited = true;
			}
		}
		
		private function onStatus(event:StatusEvent):void {
			if (event.level == "result") {
				dispatchEvent(new IapEvent("iapEvent_" + event.code, extContext.actionScriptData));
				extContext.actionScriptData = null;
				return;
				
			} else if (event.level == "ready") {
				inited = true;
				
				if (_callbackMethod != null) {
					this[_callbackMethod].apply(null, _callbackArgs);
					_callbackMethod = null;
					_callbackArgs.length = 0;
					_callbackArgs = null;
				}
			} else if (event.level == "error") {
				trace("================ Extension error ===================");
				trace(extContext.actionScriptData);
				return;
			}
			
			dispatchEvent(new IapEvent("iapEvent_" + event.level));
		}
		
		private function checkWhetherInited(method:String, args:Array):Boolean {
			if (inited) return true;
			
			_callbackMethod = method;
			_callbackArgs = args;
			init();
			
			return false;
		}
		
		/**
		 * This function initializes your samsung account
		 * In general it will be called automatically if you call purchase prior to init
		 * @param	mode Could be 0 (production), 1 (test mode, always success) or -1 (test mode, always fail)
		 */
		public function init(mode:int = 0):void {
			if (!extContext) return;
			
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
			if (!extContext) return null;
			
			return (Vector.<IapItem>)(extContext.call("getItemList", itemGroupId, startNum, endNum, itemType));
		}
		
		/**
		 * Returns a list of already purchased items
		 * If init is not called this method will throw an error
		 * @param	itemGroupId
		 * @param	startNum
		 * @param	endNum
		 * @param	startDate
		 * @param	endDate
		 * @return
		 */
		public function getItemsInbox(itemGroupId:String, startNum:int, endNum:int, startDate:String, endDate:String):Vector.<IapPurchasedItem> {
			if (!extContext) return null;
			
			if (!inited) throw new Error("The IAP is not inited");
			return (Vector.<IapPurchasedItem>)(extContext.call("getItemsInbox", itemGroupId, startNum, endNum, startDate, endDate));
		}
		
		/**
		 * Initiate a purchase of an item
		 * @param	itemGroupId
		 * @param	itemId
		 */
		public function purchase(itemGroupId:String, itemId:String):void {
			if (!extContext) return;
			
			if (!checkWhetherInited("purchase", [itemGroupId, itemId])) return;
			extContext.call("purchase", itemGroupId, itemId);
		}
		
		/**
		 * Disposes the iap and related objects
		 */
		public function dispose():void {
			extContext.dispose();
			extContext = null;
			inited = false;
		}
		
	}

}