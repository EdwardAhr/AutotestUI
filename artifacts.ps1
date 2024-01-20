[CmdletBinding()]

Param(
    [Parameter(Mandatory=$True)][string]$Artifact
)

$esc="$([char]27)"; $TXT_GREEN="$esc[32m"; $TXT_CLEAR="$esc[0m"

$ErrorActionPreference = "Stop";

[string]$Source = "$Artifact\*"
[string]$Destination = "$env:Namespace\"

Write-Warning "Cleaning $Destination..."
Remove-Item -Recurse $Destination\* -Force

Write-Host $TXT_GREEN"Copying artifacts from '$Source' to workspace '$Destination'..."$TXT_CLEAR
Copy-Item -Path $Source -Destination $Destination -Recurse -Force
Write-Host $TXT_GREEN"Transfer artifacts to workspace '$Destination' has been successfully!"$TXT_CLEAR
