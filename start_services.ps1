[CmdletBinding()]

Param(
    [Parameter(Mandatory=$True)][string]$SiteName,
    [Parameter(Mandatory=$True)][string]$PoolName
)

$esc="$([char]27)"; $TXT_GREEN="$esc[32m"; $TXT_YELLOW="$esc[33m";  $TXT_CLEAR="$esc[0m"

Import-Module WebAdministration
$ErrorActionPreference = "Stop";

Write-Host $TXT_YELLOW"Starting website '$SiteName' and pool '$PoolName'"$TXT_CLEAR
Start-WebAppPool -Name $PoolName
Start-Website -Name $SiteName
Write-Host $TXT_GREEN"Started website '$SiteName' and pool '$PoolName'"$TXT_CLEAR
