ANE for Samsung's IAP 2.0

=========================== How to use ===================================

Find the ready to use SWC and ANE inside the /build folder.

Add the following nodes to your application.xml:
<extensions>
	<extensionID>com.eldhelm.samsung.iap.InAppPurchase</extensionID>
</extensions>

Also add the following nodes to manifestAdditions in your application.xml:
<application android:enabled="true">
	<activity android:name="com.eldhelm.samsung.iap.InAppAccountActivity" android:theme="@android:style/Theme.NoDisplay"></activity>
	<activity android:name="com.eldhelm.samsung.iap.InAppPaymentActivity"></activity>
</application>

The Samsung IAP API uses activities, currently i haven't found a way of doing interacting with the API without implementing activities, and they must be allowed as shown above.

=========================== What's inside ================================

The /as folder contains a FlashDevelop project for developing the action script part of the extension and may be creating a testbench (in the future).
The /java folder contains a ADT (Eclipse) project with the native java source code.
The /platform folder conatins all the files that are needed to package the extension.

=========================== Building =====================================

To build the stuff yourself using the current setup you need: ADT, FlashDevelop and Perl 
1) Open the Eclipse project and export the jar file into the /platfrom/Android folder
2) Building the SWC and the ANE is automatic and is configured as a post-build instruction of the FlashDevelop project. It is done by a perl script - as/package_extension.pl. You just run the build command in FlashDevelop (F8).

It is advised that you visit this page: http://developer.samsung.com/in-app-purchase and become familiar with the application flow of the samsung API as the extension is transparent.

The extension is built so that it will run on any system with the Action Script iplementation only (for debuging purposes, without throwing exceptions). You should check ADL's documentaion here: http://help.adobe.com/en_US/air/build/WSfffb011ac560372f-6fa6d7e0128cca93d31-8000.html