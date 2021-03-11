use HTTP::Daemon;
use HTTP::Status;  
#use IO::File;

$DIR = './';

my $d = HTTP::Daemon->new(
	LocalAddr =>'127.0.0.1',
	LocalPort => 4322,
)|| die;

print "Please contact me at: <URL:", $d->url, ">\n";


while (my $c = $d->accept) {
	while (my $r = $c->get_request) {
		if($r->method eq 'GET'){
			$file_s = $r->uri;

			if($file_s eq "/"){
				$file_s = "/index.html";
			}
			$c->send_file_response($DIR.$file_s);
		}
		else{
			$c->send_error(RC_FORBIDDEN);	
		}
	}
	$c->close;
	undef($c);
}
