#!/usr/bin/perl

use IO::Socket;

$testdir='.';

# check if Selenium server is running
$socket = IO::Socket::INET->new(Proto=>"tcp", PeerPort=>"4444", PeerAddr=>"localhost");
if (! $socket) {

    # Selenium server is not running
    my $pid = fork();
    if (defined($pid) && $pid==0) {
	# Selenium server is executed as a background process
        exec("java", "-jar", "selenium-server-standalone-2.28.0.jar");
    }

    sleep 5;
}

my @files = glob( $testdir . '/*.t' );

foreach (@files) {
    print $_ . "\n";
    system("perl", $_);
}