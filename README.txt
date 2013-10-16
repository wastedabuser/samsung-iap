Find the SWC and the ANE inside the build folder.

Warning, this extension in under developemnt and it is not fully tested!

The /as folder contains a test FlashDevelop project for developing the action script part of the extension.
The /java folder contains a ADT project with the native java source code.
The /platform folder conatins the all the files that are packaged inside the extension.

To build the stuff yourself using the default setup you need: ADT, FlashDevelop and Perl 
You export the jar file from ADT into the /platfrom/Android folder
Building the swc and the ane is automatic and is configured as a pre-build instruction of the FlashDevelop project. It is done by a perl script - as/package_extension.pl. You just build the project using FlashDevelop.