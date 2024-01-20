Param(
    [switch]$test,
    [switch]$chtpz,
    [switch]$rimera,
    [switch]$suek,
    [switch]$rshb,
    [switch]$mmk,
    [switch]$alrosa,
    [switch]$nspk,
    [switch]$rtk,
    [switch]$dkudzo,
    [switch]$avtodor,
    [switch]$ricoh,
    [switch]$expobank,
    [switch]$beeline

)

$ErrorActionPreference = 'SilentlyContinue'

Write-Host "Version check mvn..."
mvn -version

Write-Host "Running tests..."
if ($test)
{
    mvn clean test -Dcustomer='test' -DsuiteXml='testng-KO.xml'
}

if ($chtpz)
{
    mvn clean test -Dcustomer='chtpz' -DsuiteXml='testng-KO.xml'
}

if ($rimera)
{
    mvn clean test -Dcustomer='rimera' -DsuiteXml='testng-KO.xml'
}

if ($suek)
{
    mvn clean test -Dcustomer='suek' -DsuiteXml='testng-KO.xml'
}

if ($rshb)
{
    mvn clean test -Dcustomer='rshb' -DsuiteXml='testng-KO.xml'
}

if ($mmk)
{
    mvn clean test -Dcustomer='mmk' -DsuiteXml='testng-KO.xml'
}

if ($alrosa)
{
    mvn clean test -Dcustomer='alrosa' -DsuiteXml='testng-KO.xml'
}

if ($nspk)
{
    mvn clean test -Dcustomer='nspk' -DsuiteXml='testng-KO.xml'
}

if ($rtk)
{
    mvn clean test -Dcustomer='rtk' -DsuiteXml='testng-KO.xml'
}

if ($dkudzo)
{
    mvn clean test -Dcustomer='dkudzo' -DsuiteXml='testng-KO.xml'
}

if ($avtodor)
{
    mvn clean test -Dcustomer='avtodor' -DsuiteXml='testng-KO.xml'
}

if ($ricoh)
{
    mvn clean test -Dcustomer='rico' -DsuiteXml='testng-KO.xml'
}

if($expobank)
{
    mvn clean test -Dcustomer='expob' -DsuiteXml='testng-KO.xml'
}

if($beeline)
{
    mvn clean test -Dcustomer='beeline' -DsuiteXml='testng-KO.xml'
}

Write-Host "Generate allure-results..."
&"C:\Users\Manyukhin.A\scoop\apps\allure\current\bin\allure.bat" generate allure-results --clean -o allure-report

Write-Host $TXT_GREEN"Deleting processes..."$TXT_CLEAR
Start-Process "C:\Environments\kill_proc.bat" -Wait
Write-Host $TXT_GREEN"Deleting processes successfully..."$TXT_CLEAR
