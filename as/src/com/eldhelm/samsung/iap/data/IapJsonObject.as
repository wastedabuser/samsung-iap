package com.eldhelm.samsung.iap.data {
	/**
	 * ...
	 * @author Andrey Glavchev
	 */
	public class IapJsonObject {
		
		public var data:Object;
		
		public function IapJsonObject() {
			
		}
		
		public function set jsonData(jsn:String):void {
			data = JSON.parse(jsn);
			for (var i:String in data) {
				if (hasOwnProperty(i)) this[i] = data[i];
			}
		}
		
	}

}