Zadanie 1.
/*COMMENT*/

    openssl genrsa -out privkeyA.pem

/*COMMENT*/

    openssl req -new -key privkeyA.pem -out certA.csr

Country Name (2 letter code) [AU]:PL
State or Province Name (full name) [Some-State]:DNSK
Locality Name (eg, city) []:Wroclaw
Organization Name (eg, company) [Internet Widgits Pty Ltd]:Firma X
Organizational Unit Name (eg, section) []:Dzial Y
Common Name (eg, YOUR name) []:telepchuk
Email Address []:admin@firma_x.pl 

/*COMMENT*/
	
	openssl genrsa -out privkeyB.pem

/*COMMENT*/

	openssl x509 -req -days 45 -in certA.csr -CA CAcert.crt -CAkey privkeyB.pem -set_serial 01 -out certA.crt

Zadanie 2.

	Privacy and security -> Manage certificates -> Authorities -> import CAcert.crt
