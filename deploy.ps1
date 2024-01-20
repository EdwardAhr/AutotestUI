[CmdletBinding()]

Param(
    [Parameter(Mandatory=$True)][string]$Environment
)

$esc="$([char]27)"; $TXT_YELLOW="$esc[33m"; $TXT_CLEAR="$esc[0m"

$ErrorActionPreference = "Stop";
Import-Module WebAdministration -Force

Write-Host $TXT_YELLOW"Initialize Global Variables..."$TXT_CLEAR
&".\DevOps\Environment\$Environment\InitVariables.ps1"

&".\DevOps\stop_services.ps1" -SiteName $env:WebApp -PoolName $env:WebAppPool

Start-Sleep 10

Write-Host $TXT_YELLOW"Deploying..."$TXT_CLEAR
&".\DevOps\artifacts.ps1" -Artifact $env:Artifacts

Start-Sleep 10

&".\DevOps\start_services.ps1" -SiteName $env:WebApp -PoolName $env:WebAppPool
