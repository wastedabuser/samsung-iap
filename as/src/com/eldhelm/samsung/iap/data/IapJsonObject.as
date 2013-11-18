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
			try {
				_jsonData = jsn;
			} catch (e:Error) {
				trace("=========== Error parsing json ===========");
				trace(e);
				trace(jsn);
			}
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