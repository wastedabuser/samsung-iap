package {
	import com.eldhelm.samsung.iap.data.IapItem;
	import com.eldhelm.samsung.iap.data.IapJsonObject;
	import com.eldhelm.samsung.iap.IapEvent;
	import com.eldhelm.samsung.iap.InAppPurchase;
	import flash.desktop.NativeApplication;
	import flash.events.Event;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.ui.Multitouch;
	import flash.ui.MultitouchInputMode;
	
	/**
	 * ...
	 * @author Andrey Glavchev
	 */
	public class Main extends Sprite {
		
		private var iap:InAppPurchase;
		
		public function Main():void {
			stage.scaleMode = StageScaleMode.NO_SCALE;
			stage.align = StageAlign.TOP_LEFT;
			stage.addEventListener(Event.DEACTIVATE, deactivate);
			
			// touch or gesture?
			Multitouch.inputMode = MultitouchInputMode.TOUCH_POINT;
			
			//var ijo:IapJsonObject = new IapJsonObject('{"kiro":"a"}');
			//trace(ijo.data);
			//trace(ijo.data.kiro);
			//
			//ijo = new IapItem('{"kiro":"b"}');
			//trace(ijo.data);
			//trace(ijo.data.kiro);
			
			// samsung iap test
			iap = new InAppPurchase;
			iap.addEventListener(IapEvent.ON_READY, onIapReady);
			iap.addEventListener(IapEvent.ON_INIT, onIapInit);
			iap.init();
		}
		
		private function onIapReady(event:IapEvent):void {
			iap.removeEventListener(IapEvent.ON_READY, onIapReady);
			// iap.getItemList("", 0, 1, "");
			iap.init(1);
		}
		
		private function onIapInit(event:IapEvent):void {
			iap.removeEventListener(IapEvent.ON_INIT, onIapInit);
			
			
		}
		
		private function deactivate(e:Event):void {
			// make sure the app behaves well (or exits) when in background
			//NativeApplication.nativeApplication.exit();
		}
		
	}
	
}