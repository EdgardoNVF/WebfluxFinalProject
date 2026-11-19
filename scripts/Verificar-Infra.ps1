[CmdletBinding()]
param([switch]$CheckCustomer)

$ErrorActionPreference = 'Stop'

function Test-Endpoint {
    param([string]$Label, [string]$Url)
    try {
        $null = Invoke-RestMethod -Uri $Url -TimeoutSec 10
        Write-Host "[OK] $Label" -ForegroundColor Green
        return $true
    } catch {
        Write-Host "[ERROR] $Label - $($_.Exception.Message)" -ForegroundColor Red
        return $false
    }
}

$tests = @(
    (Test-Endpoint 'Keycloak OIDC Discovery' 'http://localhost:8180/realms/customer-final/.well-known/openid-configuration'),
    (Test-Endpoint 'Prometheus API' 'http://localhost:9091/api/v1/status/buildinfo'),
    (Test-Endpoint 'Grafana Health' 'http://localhost:3001/api/health')
)

if ($CheckCustomer) {
    $tests += Test-Endpoint 'Customer Service Health' 'http://localhost:8083/actuator/health'
    $tests += Test-Endpoint 'Customer Service Prometheus' 'http://localhost:8083/actuator/prometheus'
}

if ($tests -contains $false) { exit 1 }
Write-Host 'Infraestructura verificada. Recuerda: los endpoints de negocio son tarea del alumno.' -ForegroundColor Cyan
