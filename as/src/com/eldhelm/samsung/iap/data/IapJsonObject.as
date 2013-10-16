package com.eldhelm.samsung.iap.data {
	/**
	 * ...
	 * @author Andrey Glavchev
	 */
	public class IapJsonObject {
		
		public var data:Object;
		private var _jsonData:String;
		
		public function IapJsonObject() {
			
		}
		
		public function set jsonData(jsn:String):void {
			_jsonData = jsn;
			data = JSON.parse(jsn);
			for (var i:String in data) {
				if (hasOwnProperty(i)) this[i] = data[i];
			}
		}
		
		public function get jsonData():String {
			return _jsonData;
		}
		
	}

}