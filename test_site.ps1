###

Write-Host "Checking if sites are working https://repo.maven.apache.org"

$HTTP_Request = [System.Net.WebRequest]::Create('https://repo.maven.apache.org')
$HTTP_Response = $HTTP_Request.GetResponse()
$HTTP_Status = [int]$HTTP_Response.StatusCode

If ($HTTP_Status -eq 200) {
    Write-Host "Site is OK!"
}
Else {
    Write-Host "The Site may be down, please check!"
}

If ($HTTP_Response -eq $null) { } 
Else { $HTTP_Response.Close() }

###

Write-Host "Checking if sites are working https://chromedriver.storage.googleapis.com"

$HTTP_Request = [System.Net.WebRequest]::Create('https://chromedriver.storage.googleapis.com')
$HTTP_Response = $HTTP_Request.GetResponse()
$HTTP_Status = [int]$HTTP_Response.StatusCode

If ($HTTP_Status -eq 200) {
    Write-Host "Site is OK!"
}
Else {
    Write-Host "The Site may be down, please check!"
}

If ($HTTP_Response -eq $null) { } 
Else { $HTTP_Response.Close() }

###

Write-Host "Checking if sites are working https://github.com"

$HTTP_Request = [System.Net.WebRequest]::Create('https://github.com')
$HTTP_Response = $HTTP_Request.GetResponse()
$HTTP_Status = [int]$HTTP_Response.StatusCode

If ($HTTP_Status -eq 200) {
    Write-Host "Site is OK!"
}
Else {
    Write-Host "The Site may be down, please check!"
}

If ($HTTP_Response -eq $null) { } 
Else { $HTTP_Response.Close() }

###

Write-Host "Checking if sites are working https://www.npmjs.com"

$HTTP_Request = [System.Net.WebRequest]::Create('https://www.npmjs.com')
$HTTP_Response = $HTTP_Request.GetResponse()
$HTTP_Status = [int]$HTTP_Response.StatusCode

If ($HTTP_Status -eq 200) {
    Write-Host "Site is OK!"
}
Else {
    Write-Host "The Site may be down, please check!"
}

If ($HTTP_Response -eq $null) { } 
Else { $HTTP_Response.Close() }

###

Write-Host "Checking if sites are working https://www.seleniumhq.org"

$HTTP_Request = [System.Net.WebRequest]::Create('https://www.seleniumhq.org')
$HTTP_Response = $HTTP_Request.GetResponse()
$HTTP_Status = [int]$HTTP_Response.StatusCode

If ($HTTP_Status -eq 200) {
    Write-Host "Site is OK!"
}
Else {
    Write-Host "The Site may be down, please check!"
}

If ($HTTP_Response -eq $null) { } 
Else { $HTTP_Response.Close() }

###