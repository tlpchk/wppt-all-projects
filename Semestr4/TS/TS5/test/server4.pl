use HTTP::Daemon;
use HTTP::Status;
use HTTP::Response;  

my $d = HTTP::Daemon->new(
	LocalAddr =>'127.0.0.1',
	LocalPort => 4321,
)|| die;

print "Please contact me at: <URL:", $d->url, ">\n";


while (my $c = $d->accept) {
	while (my $r = $c->get_request) {
		$response = Response->new(200,'OK');
		$response->content( $r->as_string );
		$c->send_response( $response );
	}
	$c->close;
	undef($c);
}
