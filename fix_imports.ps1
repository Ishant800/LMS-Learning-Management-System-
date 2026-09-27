# Fix all imports in the modular project structure

$replacements = @(
    # Entity imports
    @{ Pattern = 'import com\.example\.product_service\.entity\.'; Replace = 'import com.example.product_service.modules.$MODULE$.entity.' }
    @{ Pattern = 'import com\.example\.product_service\.Dto\.'; Replace = 'import com.example.product_service.modules.$MODULE$.dto.' }
    @{ Pattern = 'import com\.example\.product_service\.repository\.'; Replace = 'import com.example.product_service.modules.$MODULE$.repository.' }
    @{ Pattern = 'import com\.example\.product_service\.service\.'; Replace = 'import com.example.product_service.modules.$MODULE$.service.' }
    @{ Pattern = 'import com\.example\.product_service\.controller\.'; Replace = 'import com.example.product_service.modules.$MODULE$.controller.' }
    
    # Common imports
    @{ Pattern = 'import com\.example\.product_service\.exception\.'; Replace = 'import com.example.product_service.common.exception.' }
    @{ Pattern = 'import com\.example\.product_service\.utility\.'; Replace = 'import com.example.product_service.common.utility.' }
    @{ Pattern = 'import com\.example\.product_service\.entity\.enums\.'; Replace = 'import com.example.product_service.common.enums.' }
    @{ Pattern = 'import com\.example\.product_service\.Exceptionhandler\.'; Replace = 'import com.example.product_service.common.exception.' }
    @{ Pattern = 'import com\.example\.product_service\.EmailService\.'; Replace = 'import com.example.product_service.common.email.' }
    @{ Pattern = 'import com\.example\.product_service\.rabbitmq\.'; Replace = 'import com.example.product_service.common.messaging.' }
)

$modules = @("auth", "tenant", "course", "student", "teacher", "batch", "attendance", "product")

foreach ($module in $modules) {
    $path = "src/main/java/com/example/product_service/modules/$module"
    if (Test-Path $path) {
        Get-ChildItem -Path $path -Filter "*.java" -Recurse | ForEach-Object {
            $content = Get-Content $_.FullName -Raw
            $modified = $false
            
            foreach ($rep in $replacements) {
                $newPattern = $rep.Replace -replace '\$MODULE\$', $module
                if ($content -match $rep.Pattern) {
                    $content = $content -replace $rep.Pattern, $newPattern
                    $modified = $true
                }
            }
            
            if ($modified) {
                Set-Content $_.FullName -Value $content -NoNewline
                Write-Host "Fixed imports in: $($_.Name)"
            }
        }
    }
}

Write-Host "Import fixing complete!"
