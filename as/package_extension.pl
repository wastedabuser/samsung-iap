use strict;

my ($projectPath, $name, $flexSdkPath) = @ARGV;

my $swc = "$projectPath\\..\\build\\$name.swc";
my $ane = "$projectPath\\..\\build\\com.eldhelm.samsung.iap.InAppPurchase.ane";

print "Cleaning up build directory\n";
unlink $swc;
unlink $ane;

# ===========================================================================================
# Well, this is configured for my enviroment so i can push the build to my production project
# Change it if you are using another (but similar) setup
# ===========================================================================================
my $productionPath;
$productionPath = do "production_path.pl" if -f "production_path.pl";

opendir DIR, "../platform";
my @platfroms = grep /^[^\.]/, readdir DIR;
close DIR;

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
		$swc
		library.swf
		-o"../platform/$_"
		-y
	~, @platfroms),

	qq~"$flexSdkPath/bin/adt" 
		-package
		-target ane $ane extension.xml 
		-swc $swc
	~.join(" ", map qq~-platform $_ -C ../platform/$_ .~, @platfroms),

	-d $productionPath ? (
		qq~copy "$swc" "$productionPath/lib" /y~,
		qq~copy "$ane" "$productionPath/ane" /y~,
	) : ()
);

foreach (@commands) {
	print "$_\n";
	s/[\n\r]//g;
	s/[\t]/ /g;
	print `$_`;
}