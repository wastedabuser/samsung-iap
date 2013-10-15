use strict;

my ($projectPath, $name, $flexSdkPath) = @ARGV;

my $swc = "../build/$name.swc";
my $ane = "../build/com.eldhelm.samsung.iap.ane";

print "Cleaning up build directory\n";
unlink $swc;
unlink $ane;

my @commands = (

	qq~"$flexSdkPath/bin/acompc"
		-source-path src
		-namespace com.eldhelm.samsung.iap extensionManifest.xml
		-include-namespaces com.eldhelm.samsung.iap
		-swf-version=20
		-output $swc
	~,

	map (qq~"C:/Program Files/7-Zip/7z" 
		e 
		bin/$name.swc
		library.swf
		-o"../platform/$_"
		-y
	~, "default", "Android"),

	qq~"$flexSdkPath/bin/adt" 
		-package
		-target ane $ane extension.xml 
		-swc $swc 
		-platform Android-ARM -C ../platform/Android . 
		-platform default -C ../platform/default .
	~,

);

foreach (@commands) {
	print $_;
	s/[\n\r]//g;
	s/[\t]/ /g;
	print `$_`;
}
