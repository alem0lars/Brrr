# Detect TCP scan.
@load misc/scan

# Detect traceroute being run on the network.
@load misc/detect-traceroute

# Script to detect various activity in FTP sessions.
@load protocols/ftp/detect

# Detect hosts doing SSH bruteforce attacks.
@load protocols/ssh/detect-bruteforcing

# Detect logins using "interesting" hostnames.
@load protocols/ssh/interesting-hostnames

# Detect SQL injection attacks.
@load protocols/http/detect-sqli

# Detect SHA1 sums in Team Cymru's Malware Hash Registry.
@load frameworks/files/detect-MHR
